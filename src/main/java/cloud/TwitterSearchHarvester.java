package cloud;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cloud.dao.DBManager;
import cloud.dao.PlainStatusCBD;
import cloud.filter.Filter;
import twitter4j.*;

public class TwitterSearchHarvester{
	//
	private final int numOfThreads = 4;
	private FarmThread threads[] = new FarmThread[numOfThreads];

	public TwitterSearchHarvester() throws IOException{
		//
		NLP.init();
				
		//distribute key and places to each thread
		int keyLength = TwitterHelper.KEYS.length;
		int placeLength = TwitterHelper.PLACES_IDS.length;

		int numOfKeysPerThread = Math.floorDiv(keyLength, numOfThreads);
		int numOfPlacesPerThread = Math.floorDiv(placeLength, numOfThreads);

		int extraKeys = keyLength % numOfThreads;
		int extraPlaces = placeLength % numOfThreads;
		
		int[] keys = new int[numOfThreads];
		int[] places = new int[numOfThreads];

		
		for(int i=0;i<numOfThreads;i++){
			keys[i] = numOfKeysPerThread;
			places[i] = numOfPlacesPerThread;
			if(extraKeys>0){
				keys[i] ++;
				extraKeys --;
			}
			if(extraPlaces>0){
				places[i] ++;
				extraPlaces --;
			}
		}

		int keyLastSum =0;
		int placeLastSum =0;
		for(int i=0;i<numOfThreads;i++){
			int[] keyRange = {keyLastSum, keyLastSum + keys[i]-1};
			int[] placeRange = {placeLastSum, placeLastSum + places[i]-1};
			keyLastSum += keys[i];
			placeLastSum += places[i];

			String[][] threadKeys = new String[keyRange[1]-keyRange[0]+1][4];
			String[][] threadPlaces = new String[placeRange[1]-placeRange[0]+1][2];

			//copy keys
			for(int j=0;j<threadKeys.length;j++){
				threadKeys[j][0] = TwitterHelper.KEYS[j+keyRange[0]][0];
				threadKeys[j][1] = TwitterHelper.KEYS[j+keyRange[0]][1];
				threadKeys[j][2] = TwitterHelper.KEYS[j+keyRange[0]][2];
				threadKeys[j][3] = TwitterHelper.KEYS[j+keyRange[0]][3];
			}

			//copy places
			for(int j=0;j<threadPlaces.length;j++){
				threadPlaces[j][0] = TwitterHelper.PLACES_IDS[j+placeRange[0]][0];
				threadPlaces[j][1] = TwitterHelper.PLACES_IDS[j+placeRange[0]][1];
			}

			threads[i] = new FarmThread(threadKeys,threadPlaces,i+2);
		}			

		//test
		System.out.println("[TEST]Key length: " + keyLength);
		System.out.println("[TEST]Places length: " + placeLength);	
	}

	public void start(){
		for(int i=0;i<numOfThreads;i++){
			threads[i].start();
		}	
	}

	private class FarmThread extends Thread{
		//
		private String threadName;

		//
		private String[][] keys; //assigned keys for this thread
		private String[][] places; //assigned places fot this thread

		//	
		private Twitter[] twitters;
		private int currentTwitterIndex;
		private int numOfTwitters;	

		//
		private TwitterFactory factory;
		private AccessToken at;
		private HashMap<String,String> melbournPlaces;	

		//rate limit status
		private Map<String,RateLimitStatus> rateLimitStatus;
		private RateLimitStatus search_limit,rate_limit,timeline_limit;
		private int search_remain,rate_remain,timeline_remain;

		//
		private DBManager dbManager;
		
		public FarmThread(String[][] keys, String[][] places,int id) throws IOException{
			this.keys = keys;
			this.places = places;
			this.factory = new TwitterFactory();	
			this.numOfTwitters = keys.length;
			this.twitters = new Twitter[numOfTwitters];
			this.currentTwitterIndex = 0;
			
			//read IP address of this machine
			BufferedReader br = new BufferedReader(new FileReader("/IpAddress.txt"));
			String ip = br.readLine();
			this.dbManager = new DBManager(ip+":5984", "admin", "admin", "ccdb");
			
			setDaemon(false); //prevent main thread ends

			//test			
			pln("[TEST] THREAD ================================");
			pln("[TEST] Assigned keys: ");
			for(String[] key:keys){
				System.out.printf("%s %s %s %s\n",key[0],key[1],key[2],key[3]);
			}
			pln("[TEST] Assigned places: ");
			for(String[] place:places){
				System.out.printf("%s = %s\n",place[0],place[1]);
			}
			 
			//login all access tokens
			ConfigurationBuilder[] cb = new ConfigurationBuilder[keys.length];
			for(int i=0;i<cb.length;i++){
				cb[i] = new ConfigurationBuilder();
				cb[i].setOAuthConsumerKey(keys[i][0]);
				cb[i].setOAuthConsumerSecret(keys[i][1]);
				cb[i].setOAuthAccessToken(keys[i][2]);
				cb[i].setOAuthAccessTokenSecret(keys[i][3]);
				twitters[i] = new TwitterFactory(cb[i].build()).getInstance();
			}
			/* old login
			for(int i=0; i<keys.length;i++){
				this.twitters[i] = factory.getInstance();
				this.twitters[i].setOAuthConsumer(
						keys[i][0], 
						keys[i][1]);
				this.at = new AccessToken(
						keys[i][2],
						keys[i][3]);
				this.twitters[i].setOAuthAccessToken(at);
			}
			*/
		}

		//WARNING: YOU HAVE TO CALL updateRateLimitStatus() AFTER CALLING switchToken()
		private void switchToken(){
			currentTwitterIndex = (currentTwitterIndex+1) % numOfTwitters;
			System.out.printf("[%s] Changing to token: %d-%s\n",threadName,currentTwitterIndex,keys[currentTwitterIndex][3]);
		}

		private void updateRateLimitStatus(){
			boolean succesfullyUpdated = true;
			try {
				do{
					succesfullyUpdated = true;
					rateLimitStatus = twitters[currentTwitterIndex].getRateLimitStatus();

					//get rate limit object
					rate_limit = rateLimitStatus.get("/application/rate_limit_status");
					search_limit = rateLimitStatus.get("/search/tweets");
					timeline_limit = rateLimitStatus.get("/statuses/user_timeline");

					//update local remaining value
					rate_remain = rate_limit.getRemaining();
					search_remain = search_limit.getRemaining();
					timeline_remain = timeline_limit.getRemaining();

					//force sleep if rate limit below 5
					if(rate_remain <= 5){
						long sleeptime = rate_limit.getSecondsUntilReset();
						System.out.printf("[%s] Rate limit remain = %d < 5, force wait %d seconds.\n",threadName,rate_remain,sleeptime); //test
						Thread.sleep(sleeptime*1000);
						succesfullyUpdated = false;

					}else if(search_remain <= 5){
						long sleeptime = search_limit.getSecondsUntilReset();
						System.out.printf("[%s] Search limit remain = %d < 5, force wait %d seconds.\n",threadName,search_remain,sleeptime); //test
						Thread.sleep(sleeptime*1000);
						succesfullyUpdated = false;

					}else if(timeline_remain <= 5){
						long sleeptime = timeline_limit.getSecondsUntilReset();
						System.out.printf("[%s] Timeline limit remain = %d < 5, force wait %d seconds.\n",threadName,timeline_remain,sleeptime); //test
						Thread.sleep(sleeptime*1000);
						succesfullyUpdated = false;						

					}
				}while(!succesfullyUpdated);
				System.out.printf("[%s,R:%d,S:%d,T:%d] Successfully updated rate limit status.\n",threadName,rate_remain,search_remain,timeline_remain);

			} catch (TwitterException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
		}

		private void searchUserTimeline(User user){
			/////////////////////////
			//search for the time line the user tweeted

			Paging page;
			int pageNo;
			pageNo = 1;
			System.out.printf(" -------====== %s's timeline tweets ======-----\n",user.getScreenName());
			try {
				List<Status> timelineStatus;
				do{
					page = new Paging (pageNo, 200);
					pageNo++;
					timelineStatus = twitters[currentTwitterIndex].getUserTimeline(user.getId(),page);
					timeline_remain--;

					System.out.printf("[%s,R:%d,S:%d,T:%d] %d twittes.\n",threadName,rate_remain,search_remain,timeline_remain,timelineStatus.size());

					//DB save
					for(Status sta:timelineStatus){
						dbSave(sta);
					}
					
					//switch token if approaching limit
					if(rate_remain <= 5 || search_remain <= 5 || timeline_remain <= 5){
						switchToken();
						updateRateLimitStatus();
					}
				}while(timelineStatus.size()>= 1);
			} catch (TwitterException e) {
				e.printStackTrace();
			}
		}
		
		private void dbSave(Status status){
			// DB instance initialisation
			PlainStatusCBD cbd = new PlainStatusCBD();
			cbd.setStatusID(status.getId());
			Place place = status.getPlace();
			if (status.getPlace() == null)
				cbd.setPlaceName("null");
			else
				cbd.setPlaceName(status.getPlace().getName());
			if(status.getGeoLocation() == null) {
				cbd.setGeolocationLatitude(666);
				cbd.setGeolocationLongitude(666);
			} else {
				cbd.setGeolocationLatitude(status.getGeoLocation().getLatitude());
				cbd.setGeolocationLongitude(status.getGeoLocation().getLongitude());
			}
			HashtagEntity[] hashTagEntites = status.getHashtagEntities();
			String[] hashtag =  new String[hashTagEntites.length];
			for(int i=0;i<hashTagEntites.length;i++){
				hashtag[i] = hashTagEntites[i].getText();
			}
			cbd.setHashtagEntities(hashtag);
			cbd.setLang(status.getLang());
			cbd.setUserId(status.getUser().getId());
			if(status.getUser().getLocation() == null) {
				cbd.setUserLocation("");
			} else {
				cbd.setUserLocation(status.getUser().getLocation());
			}
			cbd.setText(status.getText());
			cbd.setCreatedAt(status.getCreatedAt());
			cbd.setTimestamp(System.currentTimeMillis());
			
			//sentiment analysis 
			Filter filter = new Filter();
			cbd.setProfanity(filter.isSentenceSafe(status.getText()));
			cbd.setEmotion(NLP.findSentiment(status.getText()));
			cbd.setEvaluated(true);
			
			// DB save
			dbManager.saveNoDuplication(cbd);
		}

		public void run(){
			this.threadName = Thread.currentThread().getName();
			System.out.printf("[%s] Thread start.\n",threadName);  //test

			//variables place holders
			String queryString;
			Query query;
			QueryResult queryResult;
			long minID = Long.MAX_VALUE;
			List<Status> status;

			//
			updateRateLimitStatus();

			//search loop
			for(String[] place: places){
				try{
					System.out.printf("[%s] System searching for tweets from : %s.\n", threadName, place[0]); //test

					//build query string
					queryString = "place:" + place[1]; 
					//queryString = queryString + " until:2016-05-06";
					//queryString = queryString + " fun";

					//set query attributes
					query = new Query(queryString);
					query.setCount(100);

					//query
					queryResult = twitters[currentTwitterIndex].search(query);
					search_remain --;

					System.out.printf("[%s] query string = %s\n",threadName, queryString);
					System.out.printf("[%s] query result size = %d\n",threadName, queryResult.getTweets().size());

					//deal with results
					minID = Long.MAX_VALUE;					
					do{
						query.setMaxId(minID);
						QueryResult result = twitters[currentTwitterIndex].search(query);
						search_remain --; //-----------

						status = result.getTweets();
						//all status from 
						for(Status stat:status){ 
							minID = Math.min(stat.getId(),minID);
							System.out.printf("[%s,R:%d,S:%d,T:%d] Tweet ID: %s, Date: %s.\n",	threadName, rate_remain, search_remain, timeline_remain, stat.getId(), stat.getCreatedAt());
							
							//DB save
							dbSave(stat);

							searchUserTimeline(stat.getUser());

							if(stat.getGeoLocation() != null){
								System.out.println(stat.getGeoLocation());
							}
						}

						//switch token if approaching limit
						if(rate_remain <= 5 || search_remain <= 5 || timeline_remain <= 5){
							switchToken();
							updateRateLimitStatus();
						}
					}while(status.size()==100);



				}catch(TwitterException te){
					te.printStackTrace();
				}				
			}
			System.out.printf("[%s]Thread ends.\n",threadName);
		}
	}

	public static void main(String[] args){
		TwitterSearchHarvester tsh;
		try {
			tsh = new TwitterSearchHarvester();
			tsh.start();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//life saviour
	public static void p(){System.out.println();}
	public static void p(String s){System.out.print(s);}
	public static void p(int s){System.out.print(s);}
	public static void pln(){System.out.println();}
	public static void pln(String s){System.out.println(s);}
	public static void pln(int s){System.out.println(s);}
	public static void pln(Object o){System.out.println(o);}

}

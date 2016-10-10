package cloud;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import twitter4j.FilterQuery;
import twitter4j.GeoLocation;
import twitter4j.Place;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;
import cloud.dao.DBManager;
import cloud.dao.StatusCBD;
import cloud.filter.Filter;

//////////////////////////////////////////////////////
//THIS CLASS IS REPLACED BY TwitterStreamService
/////////////////////////////////////////////////////
public class TwitterStreamHarvester {
	public static DBManager dbManager;

	//constructor
	public TwitterStreamHarvester() throws MalformedURLException {
		// TODO Auto-generated constructor stub
		dbManager = new DBManager("127.0.0.1:5985", "admin1", "admin1", "ccdb");
		NLP.init();
	}

	//main
	public static void main(String[] args) throws IOException, InterruptedException{
		TwitterStreamHarvester twitterStreamHarvester = new TwitterStreamHarvester();		
		//load places into 
		final Map<String,String> places = TwitterHelper.getPlacesIDs();
		final Map<String,String> newPlaces = new HashMap<String,String>();
		final Place place;

		//write new places to file
		File file = new File("new_places.txt");
		if(!file.exists())file.createNewFile();
		final FileWriter fw = new FileWriter(file);

		//Initialise twitter instance
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setOAuthConsumerKey(TwitterHelper.STREAM_KEY[0]);
		cb.setOAuthConsumerSecret(TwitterHelper.STREAM_KEY[1]);
		cb.setOAuthAccessToken(TwitterHelper.STREAM_KEY[2]);
		cb.setOAuthAccessTokenSecret(TwitterHelper.STREAM_KEY[3]);
		TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
		/* olg login
		TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
		twitterStream.setOAuthConsumer(Helper.STREAM_KEY[0], Helper.STREAM_KEY[1]);
		AccessToken at = new AccessToken(Helper.STREAM_KEY[2],Helper.STREAM_KEY[3]);
		twitterStream.setOAuthAccessToken(at);	
		*/	
		
		
		//override status listener
		StatusListener listener = new StatusListener() {
			public void onStatus(Status status) {
				Place place = status.getPlace();
				try {
					if(!newPlaces.containsKey(place.getName()) && !places.containsKey(place.getName())){
						//if not save it, and write to file
						newPlaces.put(place.getName(), place.getId());
						fw.write("{\""+place.getName()+"\",\"" + place.getId()+"\"},\n");
						fw.flush();
					}

					//DB instance initialisation
					StatusCBD cbd = new StatusCBD();
					cbd.setStatus(status);
					cbd.setStatusID(status.getId());
					if(status.getPlace() == null) cbd.setPlaceName("null");
					else cbd.setPlaceName(status.getPlace().getName());
					
					//sentiment analysis 
					Filter filter = new Filter();
					cbd.setProfanity(filter.isSentenceSafe(status.getText()));
					cbd.setEmotion(NLP.findSentiment(status.getText()));
					cbd.setEvaluated(true);
					
					//DB save
					dbManager.saveNoDuplication(cbd);


				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			public void onException(Exception ex) {
				ex.printStackTrace();
			}
			public void onTrackLimitationNotice(int arg0) {
				System.err.println("================ TRACK LIMITATION NOTICE : " + arg0);
			}
			public void onStallWarning(StallWarning arg0) {
				System.err.println("================ STALL WARNING : " + arg0);
			}
			public void onScrubGeo(long arg0, long arg1) {
				System.err.println("================ SCRUB GEO : " + arg0 + "," + arg1);
			}
			public void onDeletionNotice(StatusDeletionNotice arg0){
				System.err.println("================ DELETEON NOTICE : " + arg0);
			}
		};		
		twitterStream.addListener(listener);		

		

		//filter - should specify at least one of ?????
		FilterQuery streamFilter = new FilterQuery();
		//streamFilter.count(5);             	//don't know what's this
		//streamFilter.track("happy","fun");    //search 'happy' or 'fun'
		//streamFilter.language("en"); 			//only one language can be specified， remove language barrier ;P

		//The coordinates should be {southwestLon,southwestLat},{northeastLon,northeastLat}
		//vic = {-38.989160, 141.031448},{-34.326946, 150.146951}
		streamFilter.locations(new double[]{141.031448,-38.989160},new double[]{150.146951, -34.326946}); //vic area
		twitterStream.filter(streamFilter);
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

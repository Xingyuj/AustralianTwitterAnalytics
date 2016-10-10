package twitterservice;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import twitter4j.FilterQuery;
import twitter4j.HashtagEntity;
import twitter4j.Place;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterObjectFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;
import cloud.NLP;
import cloud.dao.DBManager;
import cloud.dao.PlainStatusCBD;
import cloud.filter.Filter;

/**
 * Created by lld on 10/05/2016.
 */
public class TwitterStreamService {
	// public DBManager dbManager;
	private DBManager dbManagerNew;

	public TwitterStreamService() throws IOException {
		//read IP address of this machine
		BufferedReader br = new BufferedReader(new FileReader("/IpAddress.txt"));
		String ip = br.readLine();
		dbManagerNew = new DBManager(ip+":5984", "admin", "admin", "ccdb");
		//System.out.println(ip+":5984");
		//System.out.println(dbManagerNew);
		NLP.init();
	}

	public void runTwitterService() throws IOException, InterruptedException {

		// Initialise twitter instance
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setOAuthConsumerKey("vfMCoKxv5wDE0862AEhcPXfsI");
		cb.setOAuthConsumerSecret("6TLMKtcp9Vr98l9bzx18a9TrdOGwGxisvm2j7tgM40FOklgZUE");
		cb.setOAuthAccessToken("722777478725394433-Qu7ARh1l6kRoHdc1HH5RuyazwoiZMnp");
		cb.setOAuthAccessTokenSecret("PM5HF4ABZfjaJO7Gej5HhOkg7K9i2eJCnfeUKxrBiaeLx");
		TwitterStream twitterStream = new TwitterStreamFactory(cb.build())
				.getInstance();

		// override status listener
		StatusListener listener = new StatusListener() {
			public void onStatus(Status status) {
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

				// sentiment analysis
				Filter filter = new Filter();
				cbd.setProfanity(filter.isSentenceSafe(status.getText()));
				cbd.setEmotion(NLP.findSentiment(status.getText()));
				cbd.setEvaluated(true);

				// DB save
				dbManagerNew.saveNoDuplication(cbd);
				System.out.println(status.getText() + "^^^^^^^^^^^^^^^^^^");

			}

			public void onException(Exception ex) {
				ex.printStackTrace();
			}

			public void onTrackLimitationNotice(int arg0) {
				System.err
						.println("================ TRACK LIMITATION NOTICE : "
								+ arg0);
			}

			public void onStallWarning(StallWarning arg0) {
				System.err.println("================ STALL WARNING : " + arg0);
			}

			public void onScrubGeo(long arg0, long arg1) {
				System.err.println("================ SCRUB GEO : " + arg0 + ","
						+ arg1);
			}

			public void onDeletionNotice(StatusDeletionNotice arg0) {
				System.err
						.println("================ DELETEON NOTICE : " + arg0);
			}
		};

		twitterStream.addListener(listener);
		FilterQuery streamFilter = new FilterQuery();
		streamFilter.locations(new double[] { 141.031448, -38.989160 },
				new double[] { 150.146951, -34.326946 }); // vic area
		twitterStream.filter(streamFilter);
	}

}

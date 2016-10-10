package cloud.dao;
import java.sql.Date;

import org.ektorp.support.CouchDbDocument;

import twitter4j.Status;

/**
 * Permanent Class for storing implemented Serializable
 *
 * @author xingyuji
 */
public class StatusCBD extends CouchDbDocument{
	private static final long serialVersionUID = 4980153852911157654L;
	private boolean profanity;
	private int emotion;
	private boolean politics;
	private String placeName;
	private long statusID; // Status id
	private boolean isEvaluated = false;
	private String text;
	private float geolocationLatitude;
	private float geolocationLongitude;
	private String lang;
	private String[] hashtagEntities;
	private long userId;
	private String userLocation;
	private Status status;
	private Date createdAt;

	public boolean isEvaluated() {
		return isEvaluated;
	}
	public void setEvaluated(boolean isEvaluated) {
		this.isEvaluated = isEvaluated;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public float getGeolocationLatitude() {
		return geolocationLatitude;
	}

	public void setGeolocationLatitude(float geolocationLatitude) {
		this.geolocationLatitude = geolocationLatitude;
	}

	public float getGeolocationLongitude() {
		return geolocationLongitude;
	}

	public void setGeolocationLongitude(float geolocationLongitude) {
		this.geolocationLongitude = geolocationLongitude;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String[] getHashtagEntities() {
		return hashtagEntities;
	}

	public void setHashtagEntities(String[] hashtagEntities) {
		this.hashtagEntities = hashtagEntities;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserLocation() {
		return userLocation;
	}

	public void setUserLocation(String userLocation) {
		this.userLocation = userLocation;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getPlaceName() {
		return placeName;
	}
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
	public long getStatusID() {
		return statusID;
	}
	public void setStatusID(long statusID) {
		this.statusID = statusID;
	}
	public boolean isProfanity() {
		return profanity;
	}
	public void setProfanity(boolean profanity) {
		this.profanity = profanity;
	}
	public int getEmotion() {
		return emotion;
	}
	public void setEmotion(int emotion) {
		this.emotion = emotion;
	}
	public boolean isPolitics() {
		return politics;
	}
	public void setPolitics(boolean politics) {
		this.politics = politics;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
}

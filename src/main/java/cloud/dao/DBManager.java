package cloud.dao;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;

/**
 * Manager of database, providing interfaces to invoke corresponding database features.
 * @author xingyuji
 *
 */
public class DBManager {
	CouchDbInstance dbInstance;
	CouchDbConnector db;
	StatusRepository statusRepository;

	public static void main(String[] args) {
		DBManager manager = new DBManager("127.0.0.1:5984", "admin", "admin", "ccdb");
//		DBManager manager = new DBManager("115.146.87.210:5984", "admin", "admin", "ccdb");
//		int aa = manager.statusRepository.countNegativeEmotionWithinPlace("Aachen");
		List<Integer> aa = manager.statusRepository.countNegativeEmotionWithinPlaceByDaytime("Aachen");
		System.out.println(aa);
	}
	
	/**
	 * Constructs a DBManager with assigned address, user name and password
	 * @param address, user, password
	 * @throws MalformedURLException
	 */
	public DBManager(String address, String user, String password, String dbName) {
		HttpClient httpClient;
		try {
			httpClient = new StdHttpClient.Builder().url(
					"http://"+address).socketTimeout(60000).username(user).password(user).build();
			dbInstance = new StdCouchDbInstance(httpClient);
			db = new StdCouchDbConnector(dbName, dbInstance);
			db.createDatabaseIfNotExists();
			statusRepository = new StatusRepository(PlainStatusCBD.class, db);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Find documents which have the assigned statusID, return PlainStatusCBD that with no Status details
	 * @param statusID
	 * @return PlainStatusCBD
	 */
	public List<PlainStatusCBD> findRecentTwitter(long timestamp){
		return statusRepository.findRecentTwitter(timestamp);
	}

	/**
	 * save a PlainStatusCBD without checking duplications
	 * @param PlainStatusCBD status
	 */
	public void save(PlainStatusCBD status) {
		db.create(status);
	}
	
	/**
	 * save a statusCBD without checking duplications
	 * @param PlainStatusCBD status
	 */
	public void save(StatusCBD status) {
		db.create(status);
	}

	/**
	 * update a PlainStatusCBD
	 * @param PlainStatusCBD status
	 */
	public void update(PlainStatusCBD status) {
		db.update(status);
	}
	
	/**
	 * update a statusCBD
	 * @param PlainStatusCBD status
	 */
	public void update(StatusCBD status) {
		db.update(status);
	}

	/**
	 * delete a statusCBD
	 * @param PlainStatusCBD status
	 */
	public void delete(StatusCBD status) {
		db.delete(status);
	}
	
	/**
	 * delete a statusCBD
	 * @param PlainStatusCBD status
	 */
	public void delete(PlainStatusCBD status) {
		db.delete(status);
	}

	/**
	 * Find documents which have the assigned statusID
	 * @deprecated findByStatusID is deprecated
	 * @param statusID
	 * @return List<StatusCBD>
	 */
	public List<StatusCBD> findByStatusID(long statusID) {
		return statusRepository.findByStatusID(statusID);
	}
	
	/**
	 * Find documents which have the assigned statusID
	 * 
	 * @param statusID
	 * @return List<PlainStatusCBD>
	 */
	public List<PlainStatusCBD> searchByStatusID(long statusID) {
		return statusRepository.searchByStatusID(statusID);
	}
	
	/**
	 * delete duplications in database by statusID
	 */
	public void deleteDuplication(){
		for (StatusCBD cbd : statusRepository.iterator()) {
			if(this.isExistStatus(cbd.getStatusID())){
				System.out.println("_id: " + cbd.getId()+" status: "+cbd.getStatusID()+" is deleted");
				this.delete(cbd);
			}
		}
	}
	
	/**
	 * only save a statusCBD when there is no duplication in the database
	 * @param PlainStatusCBD
	 */
	public void saveNoDuplication(PlainStatusCBD status) {
		long statusID = status.getStatusID();
		if (!isExistStatus(statusID)) {
			db.create(status);
		} else {
			System.err.println("The status is already exists!");
		}
	}
	
	/**
	 * only save a statusCBD when there is no duplication in the database
	 * @deprecated saveNoDuplication is deprecated
	 * @param StatusCBD
	 */
	public void saveNoDuplication(StatusCBD status) {
		long statusID = status.getStatusID();
		if (!isExistStatus(statusID)) {
			db.create(status);
		} else {
			System.err.println("The status is already exists!");
		}
	}
	
	/**
	 * calculate appearance percentage of politics twitter within a specific place
	 * @param placeName
	 * @return float
	 */
	public float calculatePoliticsProportionWithinPlace(String placeName){
		float targetNum = (float)statusRepository.countPoliticsWithinPlace(placeName);
		float totalNum = (float)statusRepository.countTwitterNumberWithinPlace(placeName);
		return totalNum == 0? 0:targetNum/totalNum;
	}
	
	/**
	 * calculate appearance percentage of twitter which contains bad words within a specific place
	 * @param placeName
	 * @return float
	 */
	public float calculateProfanityProportionWithinPlace(String placeName){
		float targetNum = (float)statusRepository.countProfanityWithinPlace(placeName);
		float totalNum = (float)statusRepository.countTwitterNumberWithinPlace(placeName);
		return totalNum == 0? 0:targetNum/totalNum;
	}
	
	/**
	 * calculate appearance percentage of twitter that show plain emotion within a specific place
	 * @param placeName
	 * @return float
	 */
	public float calculatePlainEmotionProportionWithinPlace(String placeName){
		float targetNum = (float)statusRepository.countPlainEmotionWithinPlace(placeName);
		float totalNum = (float)statusRepository.countTwitterNumberWithinPlace(placeName);
		return totalNum == 0? 0:targetNum/totalNum;
	}
	
	/**
	 * calculate appearance percentage of twitter that show negative emotion within a specific place
	 * @param placeName
	 * @return float
	 */
	public float calculateNegativeEmotionProportionWithinPlace(String placeName){
		float targetNum = (float)statusRepository.countNegativeEmotionWithinPlace(placeName);
		float totalNum = (float)statusRepository.countTwitterNumberWithinPlace(placeName);
		return totalNum == 0? 0:targetNum/totalNum;
	}
	
	/**
	 * calculate appearance percentage of twitter that show positive emotion within a specific place
	 * @param placeName
	 * @return float
	 */
	public float calculatePositiveEmotionProportionWithinPlace(String placeName){
		float targetNum = (float)statusRepository.countPositiveEmotionWithinPlace(placeName);
		float totalNum = (float)statusRepository.countTwitterNumberWithinPlace(placeName);
		return totalNum == 0? 0:targetNum/totalNum;
	}
	
	/**
	 * count politic twitter's appearance times within a specific place
	 * @param placeName
	 * @return integer
	 */
	public int countPoliticsWithinPlace(String placeName){
		return statusRepository.countPoliticsWithinPlace(placeName);
	}
	
	/**
	 * count bad words twitter's appearance times within a specific place
	 * @param statusID
	 * @return integer
	 */
	public int countProfanityWithinPlace(String placeName){
		return statusRepository.countProfanityWithinPlace(placeName);
	}
	
	/**
	 * count positive emotion twitter's appearance times within a specific place
	 * @param placeName
	 * @return integer
	 */
	public int countPositiveEmotionWithinPlace(String placeName){
		return statusRepository.countPositiveEmotionWithinPlace(placeName);
	}
	
	/**
	 * count plain emotion twitter's appearance times within a specific place
	 * @param placeName
	 * @return integer
	 */
	public int countPlainEmotionWithinPlace(String placeName){
		return statusRepository.countPlainEmotionWithinPlace(placeName);
	}
	
	/**
	 * count negative emotion twitter's appearance times within a specific place
	 * @param placeName
	 * @return integer
	 */
	public int countNegetiveEmotionWithinPlace(String placeName){
		return statusRepository.countNegativeEmotionWithinPlace(placeName);
	}
	
	/**
	 * count twitter times within a specific place
	 * @param placeName
	 * @return integer
	 */
	public int countTwitterNumberWithinPlace(String placeName){
		return statusRepository.countTwitterNumberWithinPlace(placeName);
	}
	
	/**
	 * Judge whether a status is exists or not. 
	 * <p><tt>true</tt> represent exists
	 * <p><tt>false</tt> represent not exists
	 * @param statusID
	 * @return boolean
	 */
	public boolean isExistStatus(long statusID) {
		if (statusRepository.countStatusWithSpecificStatusID(statusID) > 0) {
			return true;
		} else  {
			return false;
		}
	}

	public CouchDbInstance getDbInstance() {
		return dbInstance;
	}


	public void setDbInstance(CouchDbInstance dbInstance) {
		this.dbInstance = dbInstance;
	}


	public CouchDbConnector getDb() {
		return db;
	}


	public void setDb(CouchDbConnector db) {
		this.db = db;
	}


	public StatusRepository getStatusRepository() {
		return statusRepository;
	}


	public void setStatusRepository(StatusRepository statusRepository) {
		this.statusRepository = statusRepository;
	}
	
}

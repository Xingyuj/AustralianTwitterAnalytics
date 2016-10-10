package cloud.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.ektorp.ComplexKey;
import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.ViewResult;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.View;

import twitter4j.JSONException;
import twitter4j.JSONObject;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterObjectFactory;

/**
 * 
 * Repository class for creating views
 * 
 * @author xingyuji
 *
 */
@View(name = "all", map = "function(doc) {emit( null, doc._id )}")
public class StatusRepository extends CouchDbRepositorySupport<PlainStatusCBD> {

	/**
	 * Constructs a StatusRepository with the specific class type and
	 * CouchDbConnector db
	 * 
	 * @param Class
	 *            type
	 * @param CouchDbConnector
	 *            db
	 */
	protected StatusRepository(Class<PlainStatusCBD> type, CouchDbConnector db) {
		super(type, db);
		initStandardDesignDocument();
		System.setProperty(AUTO_UPDATE_VIEW_ON_CHANGE, "true");
	}

	/**
	 * Find documents which have the assigned statusID, return PlainStatusCBD
	 * that with no Status details
	 * 
	 * @param statusID
	 * @return PlainStatusCBD
	 */

	@View(name = "find_recent", map = "function(doc) {if(doc.timestamp) emit(doc.timestamp, doc._id)}")
	public List<PlainStatusCBD> findRecentTwitter(long timestamp) {
		System.out.println(timestamp + "________________");
		ViewQuery query = new ViewQuery().designDocId("_design/PlainStatusCBD")
				.viewName("find_recent").includeDocs(true)
				.startKey(timestamp - 5000).endKey(timestamp);
		List<PlainStatusCBD> statusList = db.queryView(query,
				PlainStatusCBD.class);
		return statusList;
	}

	/**
	 * count number of Status With Specific StatusID
	 * 
	 * @param statusID
	 * @return int
	 */
	@View(name = "count_by_statusID", map = "function(doc) { if (doc.statusID != 0) {emit(doc.statusID, doc._id)} }", reduce = "_count")
	public int countStatusWithSpecificStatusID(long statusID) {
		int resultNum = 0;
		ViewResult result = db.queryView(createQuery("count_by_statusID").key(
				statusID));
		if (result.getRows().size() != 0) {
			resultNum = result.getRows().get(0).getValueAsInt();
		}
		return resultNum;
	}

	/**
	 * Iterate database
	 * 
	 * @deprecated iterator is deprecated
	 * @param statusID
	 * @return List<StatusCBD>
	 */
	@View(name = "find_by_statusID", map = "function(doc) { if (doc.statusID != 0 && doc.status) {emit(doc.statusID, doc._id)} }")
	public List<StatusCBD> iterator() {
		List<StatusCBD> statusList = new ArrayList<StatusCBD>();

		ViewQuery query = new ViewQuery().designDocId("_design/StatusCBD")
				.viewName("find_by_statusID").includeDocs(true);
		ViewResult result = db.queryView(query);

		try {
			for (ViewResult.Row row : result.getRows()) {
				JSONObject jsonObj = new JSONObject(row.getDoc());
				StatusCBD statusCBD = new StatusCBD();
				statusCBD.setId(jsonObj.getString("_id"));
				statusCBD.setRevision(jsonObj.getString("_rev"));
				if (jsonObj.has("placeName")) {
					statusCBD.setPlaceName(jsonObj.getString("placeName"));
				}
				statusCBD.setEmotion(jsonObj.getInt("emotion"));
				statusCBD.setProfanity(jsonObj.getBoolean("profanity"));
				statusCBD.setPolitics(jsonObj.getBoolean("politics"));
				statusCBD.setEvaluated(jsonObj.getBoolean("evaluated"));
				Status status = null;
				if (jsonObj.has("status")) {
					status = TwitterObjectFactory.createStatus(jsonObj
							.getString("status"));
				}
				statusCBD.setStatus(status);
				statusList.add(statusCBD);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return statusList;
	}

	/**
	 * Find documents which have the assigned statusID
	 * 
	 * @deprecated findByStatusID is deprecated
	 * @param statusID
	 * @return List<StatusCBD>
	 */
	@View(name = "find_by_statusID", map = "function(doc) { if (doc.statusID != 0 && doc.status) {emit(doc.statusID, doc._id)} }")
	public List<StatusCBD> findByStatusID(long statusID) {
		List<StatusCBD> statusList = new ArrayList<StatusCBD>();

		ViewQuery query = new ViewQuery().designDocId("_design/StatusCBD")
				.viewName("find_by_statusID").includeDocs(true);

		ViewResult result = db.queryView(query);
		for (ViewResult.Row row : result.getRows()) {
			JSONObject jsonObj;
			try {
				jsonObj = new JSONObject(row.getDoc());
				if (jsonObj.has("statusID")
						&& Long.valueOf(jsonObj.get("statusID").toString()) == statusID) {
					StatusCBD statusCBD = new StatusCBD();
					if (jsonObj.has("placeName")) {
						statusCBD.setPlaceName(jsonObj.getString("placeName"));
					}
					statusCBD.setEmotion(jsonObj.getInt("emotion"));
					statusCBD.setProfanity(jsonObj.getBoolean("profanity"));
					statusCBD.setPolitics(jsonObj.getBoolean("politics"));
					statusCBD.setEvaluated(jsonObj.getBoolean("evaluated"));
					Status status = null;
					if (jsonObj.has("status")) {
						status = TwitterObjectFactory.createStatus(jsonObj
								.getString("status"));
					}
					statusCBD.setStatus(status);
					statusList.add(statusCBD);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TwitterException e) {
				e.printStackTrace();
			}
		}
		return statusList;
	}

	/**
	 * Find documents which have the assigned statusID, return PlainStatusCBD
	 * that with no Status details
	 * 
	 * @param statusID
	 * @return PlainStatusCBD
	 */
	@View(name = "find_by_statusID", map = "function(doc) { if (doc.statusID != 0) {emit(doc.statusID, doc._id)} }")
	public List<PlainStatusCBD> searchByStatusID(long statusID) {
		ViewQuery query = new ViewQuery().designDocId("_design/StatusCBD")
				.viewName("find_by_statusID").includeDocs(true).key(statusID);
		List<PlainStatusCBD> statusList = db.queryView(query,
				PlainStatusCBD.class);
		return statusList;
	}

	/**
	 * count twitter number within a specific place
	 * 
	 * @param placeName
	 * @return
	 */
	@View(name = "count_twitter_nember_within_place", map = "function (doc) {if (doc.placeName != 'null') {emit (doc.placeName, doc._id)}}", reduce = "_count")
	public int countTwitterNumberWithinPlace(String placeName) {
		int resultNum = 0;
		ViewResult result = db.queryView(createQuery(
				"count_twitter_nember_within_place").key(placeName));
		if (result.getRows().size() != 0) {
			resultNum = result.getRows().get(0).getValueAsInt();
		}
		return resultNum;
	}

	/**
	 * count politic twitter's appearance times within a specific place
	 * 
	 * @param placeName
	 * @return integer
	 */
	@View(name = "count_politics_within_place", map = "function (doc) {if (doc.placeName != 'null' && doc.politics == true) {emit (doc.placeName, doc._id)}}", reduce = "_count")
	public int countPoliticsWithinPlace(String placeName) {
		int resultNum = 0;
		ViewResult result = db.queryView(createQuery(
				"count_politics_within_place").key(placeName));
		if (result.getRows().size() != 0) {
			resultNum = result.getRows().get(0).getValueAsInt();
		}
		return resultNum;
	}

	/**
	 * count bad words twitter's appearance times within a specific place
	 * 
	 * @param statusID
	 * @return integer
	 */
	@View(name = "count_profanity_within_place", map = "function (doc) {if (doc.placeName != 'null' && doc.profanity == false) {emit (doc.placeName, doc._id)}}", reduce = "_count")
	public int countProfanityWithinPlace(String placeName) {
		int resultNum = 0;
		ViewResult result = db.queryView(createQuery(
				"count_profanity_within_place").key(placeName));
		if (result.getRows().size() != 0) {
			resultNum = result.getRows().get(0).getValueAsInt();
		}
		return resultNum;
	}

	/**
	 * count positive emotion twitter's appearance times within a specific place
	 * 
	 * @param placeName
	 * @return integer
	 */
	@View(name = "count_positive_emotion_within_place", map = "function (doc) {if (doc.placeName != 'null' && doc.emotion == 3) {emit (doc.placeName, doc._id)}}", reduce = "_count")
	public int countPositiveEmotionWithinPlace(String placeName) {
		int resultNum = 0;
		ViewResult result = db.queryView(createQuery(
				"count_positive_emotion_within_place").key(placeName));
		if (result.getRows().size() != 0) {
			resultNum = result.getRows().get(0).getValueAsInt();
		}
		return resultNum;
	}
	
	/**
	 * count negative emotion twitter's appearance times within a specific place
	 * by day time
	 * 
	 * @param placeName
	 * @return integer
	 */
	@View(name = "count_positive_emotion_within_place", map = "function (doc) {if (doc.placeName != 'null' && doc.emotion == 3) {emit (doc.placeName, doc._id)}}", reduce = "_count")
	public List<Integer> countPositiveEmotionWithinPlaceByDaytime(
			String placeName) {
		LinkedList<Integer> resultNum = new LinkedList<Integer>();
		int morningCount = 0;
		int nightCount = 0;
		ViewResult result = db.queryView(createQuery(
				"count_positive_emotion_within_place").designDocId("_design/StatusCBD").reduce(false)
				.includeDocs(true).key(placeName));
		for (ViewResult.Row row : result.getRows()) {
			JSONObject jsonObj;
			try {
				if (row.getDoc() == null) {
					return null;
				}
				jsonObj = new JSONObject(row.getDoc());
				if (jsonObj.has("status")) {
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd'T'HH:mm:ss");
					sdf.parse(jsonObj.getJSONObject("status").getString(
							"createdAt"));
					Calendar targetTime = Calendar.getInstance();
					targetTime.setTime(sdf.parse(jsonObj
							.getJSONObject("status").getString("createdAt")));
					targetTime.get(Calendar.YEAR);
					targetTime.get(Calendar.MONDAY);
					targetTime.get(Calendar.DAY_OF_MONTH);

					Calendar morningBegin = Calendar.getInstance();
					morningBegin.set(targetTime.get(Calendar.YEAR),
							targetTime.get(Calendar.MONDAY),
							targetTime.get(Calendar.DAY_OF_MONTH), 8, 0);
					Calendar morningEnd = Calendar.getInstance();
					morningEnd.set(targetTime.get(Calendar.YEAR),
							targetTime.get(Calendar.MONDAY),
							targetTime.get(Calendar.DAY_OF_MONTH), 12, 0);
					Calendar nightBegin = Calendar.getInstance();
					nightBegin.set(targetTime.get(Calendar.YEAR),
							targetTime.get(Calendar.MONDAY),
							targetTime.get(Calendar.DAY_OF_MONTH), 18, 0);
					Calendar nightEnd = Calendar.getInstance();
					nightEnd.set(targetTime.get(Calendar.YEAR),
							targetTime.get(Calendar.MONDAY),
							targetTime.get(Calendar.DAY_OF_MONTH), 22, 0);

					if (targetTime.after(morningBegin)
							&& targetTime.before(morningEnd)) {
						morningCount++;
					}

					if (targetTime.after(nightBegin)
							&& targetTime.before(nightEnd)) {
						nightCount++;
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		resultNum.add(morningCount);
		resultNum.add(nightCount);
		return resultNum;
	}

	/**
	 * count plain emotion twitter's appearance times within a specific place
	 * 
	 * @param placeName
	 * @return integer
	 */
	@View(name = "count_plain_emotion_within_place", map = "function (doc) {if (doc.placeName != 'null' && doc.emotion == 2) {emit (doc.placeName, doc._id)}}", reduce = "_count")
	public int countPlainEmotionWithinPlace(String placeName) {
		int resultNum = 0;
		ViewResult result = db.queryView(createQuery(
				"count_plain_emotion_within_place").key(placeName));
		if (result.getRows().size() != 0) {
			resultNum = result.getRows().get(0).getValueAsInt();
		}
		return resultNum;
	}

	/**
	 * count negative emotion twitter's appearance times within a specific place
	 * by day time
	 * 
	 * @param placeName
	 * @return integer
	 */
	@View(name = "count_plain_emotion_within_place", map = "function (doc) {if (doc.placeName != 'null' && doc.emotion == 2) {emit (doc.placeName, doc._id)}}", reduce = "_count")
	public List<Integer> countPlainEmotionWithinPlaceByDaytime(
			String placeName) {
		LinkedList<Integer> resultNum = new LinkedList<Integer>();
		int morningCount = 0;
		int nightCount = 0;
		ViewResult result = db.queryView(createQuery(
				"count_plain_emotion_within_place").designDocId("_design/StatusCBD").reduce(false)
				.includeDocs(true).key(placeName));
		for (ViewResult.Row row : result.getRows()) {
			JSONObject jsonObj;
			try {
				if (row.getDoc() == null) {
					return null;
				}
				jsonObj = new JSONObject(row.getDoc());
				if (jsonObj.has("status")) {
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd'T'HH:mm:ss");
					sdf.parse(jsonObj.getJSONObject("status").getString(
							"createdAt"));
					Calendar targetTime = Calendar.getInstance();
					targetTime.setTime(sdf.parse(jsonObj
							.getJSONObject("status").getString("createdAt")));

					targetTime.get(Calendar.YEAR);
					targetTime.get(Calendar.MONDAY);
					targetTime.get(Calendar.DAY_OF_MONTH);

					Calendar morningBegin = Calendar.getInstance();
					morningBegin.set(targetTime.get(Calendar.YEAR),
							targetTime.get(Calendar.MONDAY),
							targetTime.get(Calendar.DAY_OF_MONTH), 8, 0);
					Calendar morningEnd = Calendar.getInstance();
					morningEnd.set(targetTime.get(Calendar.YEAR),
							targetTime.get(Calendar.MONDAY),
							targetTime.get(Calendar.DAY_OF_MONTH), 12, 0);
					Calendar nightBegin = Calendar.getInstance();
					nightBegin.set(targetTime.get(Calendar.YEAR),
							targetTime.get(Calendar.MONDAY),
							targetTime.get(Calendar.DAY_OF_MONTH), 18, 0);
					Calendar nightEnd = Calendar.getInstance();
					nightEnd.set(targetTime.get(Calendar.YEAR),
							targetTime.get(Calendar.MONDAY),
							targetTime.get(Calendar.DAY_OF_MONTH), 22, 0);

					if (targetTime.after(morningBegin)
							&& targetTime.before(morningEnd)) {
						morningCount++;
					}

					if (targetTime.after(nightBegin)
							&& targetTime.before(nightEnd)) {
						nightCount++;
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		resultNum.add(morningCount);
		resultNum.add(nightCount);
		return resultNum;
	}
	
	/**
	 * count negative emotion twitter's appearance times within a specific place
	 * 
	 * @param placeName
	 * @return integer
	 */
	@View(name = "count_negative_emotion_within_place", map = "function (doc) {if (doc.placeName != 'null' && doc.emotion == 1) {emit (doc.placeName, doc._id)}}", reduce = "_count")
	public int countNegativeEmotionWithinPlace(String placeName) {
		int resultNum = 0;
		ViewResult result = db.queryView(createQuery(
				"count_negative_emotion_within_place").designDocId("_design/StatusCBD").key(placeName));
		if (result.getRows().size() != 0) {
			resultNum = result.getRows().get(0).getValueAsInt();
		}
		return resultNum;
	}

	/**
	 * count negative emotion twitter's appearance times within a specific place
	 * by day time
	 * 
	 * @param placeName
	 * @return integer
	 */
	@View(name = "count_negative_emotion_within_place", map = "function (doc) {if (doc.placeName != 'null' && doc.emotion == 1) {emit (doc.placeName, doc._id)}}", reduce = "_count")
	public List<Integer> countNegativeEmotionWithinPlaceByDaytime(
			String placeName) {
		LinkedList<Integer> resultNum = new LinkedList<Integer>();
		int morningCount = 0;
		int nightCount = 0;
		ViewResult result = db.queryView(createQuery(
				"count_negative_emotion_within_place").designDocId("_design/StatusCBD").reduce(false)
				.includeDocs(true).key(placeName));
		for (ViewResult.Row row : result.getRows()) {
			JSONObject jsonObj;
			try {
				if (row.getDoc() == null) {
					return null;
				}
				jsonObj = new JSONObject(row.getDoc());
				if (jsonObj.has("status")) {
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd'T'HH:mm:ss");
					sdf.parse(jsonObj.getJSONObject("status").getString(
							"createdAt"));
					Calendar targetTime = Calendar.getInstance();
					targetTime.setTime(sdf.parse(jsonObj
							.getJSONObject("status").getString("createdAt")));

					targetTime.get(Calendar.YEAR);
					targetTime.get(Calendar.MONDAY);
					targetTime.get(Calendar.DAY_OF_MONTH);

					Calendar morningBegin = Calendar.getInstance();
					morningBegin.set(targetTime.get(Calendar.YEAR),
							targetTime.get(Calendar.MONDAY),
							targetTime.get(Calendar.DAY_OF_MONTH), 8, 0);
					Calendar morningEnd = Calendar.getInstance();
					morningEnd.set(targetTime.get(Calendar.YEAR),
							targetTime.get(Calendar.MONDAY),
							targetTime.get(Calendar.DAY_OF_MONTH), 12, 0);
					Calendar nightBegin = Calendar.getInstance();
					nightBegin.set(targetTime.get(Calendar.YEAR),
							targetTime.get(Calendar.MONDAY),
							targetTime.get(Calendar.DAY_OF_MONTH), 18, 0);
					Calendar nightEnd = Calendar.getInstance();
					nightEnd.set(targetTime.get(Calendar.YEAR),
							targetTime.get(Calendar.MONDAY),
							targetTime.get(Calendar.DAY_OF_MONTH), 22, 0);

					if (targetTime.after(morningBegin)
							&& targetTime.before(morningEnd)) {
						morningCount++;
					}

					if (targetTime.after(nightBegin)
							&& targetTime.before(nightEnd)) {
						nightCount++;
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		resultNum.add(morningCount);
		resultNum.add(nightCount);
		return resultNum;
	}

}

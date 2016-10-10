package cloud.dao;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import cloud.TwitterHelperLGA;


public class ScenarioDataGenerator {
	private DBManager dbmanager;
	private FileWriter fileWriter;
	public ScenarioDataGenerator() throws MalformedURLException {
		dbmanager = new DBManager("115.146.89.156:5985","admin","admin","ccdb");
		// TODO Auto-generated constructor stub
	}
	
	public void scenarioHappyWithinPlace(Map<String, List<String>> placeSet, List<String> places, boolean naive) throws IOException{
		fileWriter = new FileWriter("scenario1_happy_rich.csv");
		if(naive){
			String header = "place, happy_percentage\n"; // 8:00~12  18:00~10:00
			fileWriter.write(header);
			for (String place : places) {
				float percentage = dbmanager.calculatePositiveEmotionProportionWithinPlace(place);
				fileWriter.write(place + ",");
				fileWriter.write(percentage + "\n");
				fileWriter.flush();
			}
		} else {
			String header = "place, emotion1, emotion2, emotion3, happy_percentage\n"; // 8:00~12  18:00~10:00
			fileWriter.write(header);
			for (Map.Entry<String, List<String>> tuple : placeSet.entrySet()) {
				int positiveCount = dbmanager.countPositiveEmotionWithinPlace(tuple.getKey());
				int negetiveCount = dbmanager.countNegetiveEmotionWithinPlace(tuple.getKey());
				int plainCount = dbmanager.countPlainEmotionWithinPlace(tuple.getKey());
				for (String place : tuple.getValue()) {
					positiveCount += dbmanager.countPositiveEmotionWithinPlace(place);
					negetiveCount += dbmanager.countNegetiveEmotionWithinPlace(place);
					plainCount += dbmanager.countPlainEmotionWithinPlace(place);
				}
				float percentage = ((float)positiveCount)/((float)(positiveCount+negetiveCount+plainCount));
				fileWriter.write(tuple.getKey() + ",");
				fileWriter.write(negetiveCount + ",");
				fileWriter.write(plainCount + ",");
				fileWriter.write(positiveCount + ",");
				fileWriter.write(percentage + "\n");
				fileWriter.flush();
			}
		}
		fileWriter.close();
	}
	
	public void scenarioProfanityWithinPlace(Map<String, List<String>> placeSet, List<String> places, boolean naive){
		try {
			fileWriter = new FileWriter("scenario2_profanity.csv");
			if(naive){
				String header = "place, profanity_percentage\n";
				fileWriter.write(header);
				for (String place : places) {
					float percentage = dbmanager.calculateProfanityProportionWithinPlace(place);
					fileWriter.write(place + ",");
					fileWriter.write(percentage + "\n");
					fileWriter.flush();
				}
			} else {
				String header = "place, profanityCount, total, profanity_percentage\n";
				fileWriter.write(header);
				for (Map.Entry<String, List<String>> tuple : placeSet.entrySet()) {
					int profanityCount = dbmanager.countProfanityWithinPlace(tuple.getKey());
					int total = dbmanager.countTwitterNumberWithinPlace(tuple.getKey());
					for (String place : tuple.getValue()) {
						profanityCount += dbmanager.countProfanityWithinPlace(place);
						total += dbmanager.countTwitterNumberWithinPlace(place);
					}
					float percentage = (float)profanityCount/(float)total;
					fileWriter.write(tuple.getKey() + ",");
					fileWriter.write(profanityCount + ",");
					fileWriter.write(total + ",");
					fileWriter.write(percentage + "\n");
					fileWriter.flush();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void scenarioHappyWithinPlaceWithDaytime(Map<String, List<String>> placeSet, List<String> places, boolean naive) throws IOException{
		fileWriter = new FileWriter("scenario3_happy_rich_with_daytime.csv");
		if(naive){
			String header = "place, happy_percentage\n"; // 8:00~12  18:00~10:00
			fileWriter.write(header);
			for (String place : places) {
				float percentage = dbmanager.calculatePositiveEmotionProportionWithinPlace(place);
				fileWriter.write(place + ",");
				fileWriter.write(percentage + "\n");
				fileWriter.flush();
			}
		} else {
			String header = "place, daytime, emotion1, emotion2, emotion3, happy_percentage\n"; // 8:00~12:00  18:00~10:00
			fileWriter.write(header);
			for (Map.Entry<String, List<String>> tuple : placeSet.entrySet()) {
				List<Integer> positiveCount = dbmanager.statusRepository.countPositiveEmotionWithinPlaceByDaytime(tuple.getKey());
				List<Integer> negetiveCount = dbmanager.statusRepository.countNegativeEmotionWithinPlaceByDaytime(tuple.getKey());
				List<Integer> plainCount = dbmanager.statusRepository.countPlainEmotionWithinPlaceByDaytime(tuple.getKey());
				for (String place : tuple.getValue()) {
					List<Integer> positiveCountSub = dbmanager.statusRepository.countPositiveEmotionWithinPlaceByDaytime(place);
					List<Integer> negetiveCountSub = dbmanager.statusRepository.countNegativeEmotionWithinPlaceByDaytime(place);
					List<Integer> plainCountSub = dbmanager.statusRepository.countPlainEmotionWithinPlaceByDaytime(place);
					positiveCount.set(0, positiveCount.get(0)+positiveCountSub.get(0));
					positiveCount.set(1, positiveCount.get(1)+positiveCountSub.get(1));
					negetiveCount.set(0, negetiveCount.get(0)+negetiveCountSub.get(0));
					negetiveCount.set(1, negetiveCount.get(1)+negetiveCountSub.get(1));
					plainCount.set(0, plainCount.get(0)+plainCountSub.get(0));
					plainCount.set(1, plainCount.get(1)+plainCountSub.get(1));
				}
				float percentage = ((float)positiveCount.get(0))/((float)(positiveCount.get(0)+negetiveCount.get(0)+plainCount.get(0)));
				fileWriter.write(tuple.getKey() + ",");
				fileWriter.write("morning" + ",");
				fileWriter.write(negetiveCount + ",");
				fileWriter.write(plainCount + ",");
				fileWriter.write(positiveCount + ",");
				fileWriter.write(percentage + "\n");
				fileWriter.flush();
				percentage = ((float)positiveCount.get(1))/((float)(positiveCount.get(1)+negetiveCount.get(1)+plainCount.get(1)));
				fileWriter.write(tuple.getKey() + ",");
				fileWriter.write("morning" + ",");
				fileWriter.write(negetiveCount + ",");
				fileWriter.write(plainCount + ",");
				fileWriter.write(positiveCount + ",");
				fileWriter.write(percentage + "\n");
				fileWriter.flush();
			}
			fileWriter.close();
		}
	}
	
	public static void main(String[] args) throws IOException {
		ScenarioDataGenerator scenario = new ScenarioDataGenerator();
//		scenario.scenarioHappyWithinPlace(TwitterHelperLGA.getLGA(), null, false);
//		scenario.scenarioProfanityWithinPlace(TwitterHelperLGA.getLGA(), null, false);
		scenario.scenarioHappyWithinPlaceWithDaytime(TwitterHelperLGA.getLGA(), null, false);
	}
}

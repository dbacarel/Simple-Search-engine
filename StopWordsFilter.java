/**
 * SimpleSearchEngine
 * 
 * Daniele Bacarella 2013
 */

package se.findwise.assignments.searchengine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class StopWordsFilter {
	
	private static HashMap<String,Boolean> sw_map;
	private static final String FILEPATH= "src/se/findwise/assignments/searchengine/resources/stopwords.txt";
	
	public StopWordsFilter(){
		sw_map = new HashMap<String,Boolean>();
		load();
	}
	
	public boolean isStopWord(String token){
		return sw_map.containsKey(token);
	}
	
	private void load(){
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(FILEPATH));
			String word;
			while ((word = br.readLine()) != null) {
				sw_map.put(word, true);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}

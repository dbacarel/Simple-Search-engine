/**
 * SimpleSearchEngine
 * 
 * Daniele Bacarella 2013
 */

package se.findwise.assignments.searchengine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeSet;

public class Index {

	private Tokenizer tokenizer;
	private Stemmer stemmer;
	private StopWordsFilter filter;

	private Map<String, TreeSet<IndexEntry>> inverted_index;

	private double average_document_size;
	private long n_documents;
	
	public Index() {
		this.tokenizer = new BasicTokenizer();
		this.stemmer = new Stemmer();
		this.inverted_index = new HashMap<String, TreeSet<IndexEntry>>();
		this.filter = new StopWordsFilter();
		this.average_document_size=0;
		this.n_documents=0;
	}

	protected TreeSet<IndexEntry> getPostingList(String token){
		if(inverted_index.containsKey(token)){
			return new TreeSet<IndexEntry>(inverted_index.get(token));
		}else
			return new TreeSet<IndexEntry>();
	}
	
	/**
	 * Index Building task goes through the following steps:
	 * 
	 * 1. Document access
	 * 2. Content tokenization
	 * 3. Stop-words pruning
	 * 4. Stemming
	 * 5. Inverse index update
	 * 
	 * @param folderName
	 * @throws Exception 
	 */
	public void build(String folderName) throws Exception {
		File dir_file = new File(folderName);
		if(!dir_file.exists())
			throw new FileNotFoundException();
		
		BufferedReader br = null;
		HashMap<String, IndexEntry> document_index;

		//For each file (Document) in the folder
		for (File document : dir_file.listFiles()) {
			//Temporary map to store the informations of each token in the current document
			document_index = new HashMap<String, IndexEntry>();
		
			if (document.isFile()) {
				
				try {
					br = new BufferedReader(new FileReader(
							document.getAbsolutePath()));
					String line;
					long ntokens = 0;
					
					this.n_documents++;
					
					/*
					 * Tokenization
					 */
					while ((line = br.readLine()) != null) {
						tokenizer.tokenize(line);
						String token;
						
						//stop-words are skipped
						while (tokenizer.hasMoreTokens()) {
							if(filter.isStopWord((token = tokenizer.nextToken()))) continue;
							ntokens++;
							token= this.stemmer.stem(token);
							
							if (document_index.containsKey(token)) {
								IndexEntry entry = document_index.get(token);
								entry.setFrequency(entry.getFrequency() + 1);
							} else {
								document_index.put(token, new IndexEntry(document.getName(),
										1, 0));
							}

						}
					}

					
					this.average_document_size+=document_index.size();
					
					// Iterate through the map to update the IndexEntry<_,_,
					// n_token> field
					// of each entry
					Iterator<Entry<String, IndexEntry>> it = (Iterator<Entry<String, IndexEntry>>) document_index
							.entrySet().iterator();
					while (it.hasNext()) {
						Entry<String, IndexEntry> map_entry = (Entry<String, IndexEntry>) it
								.next();
						map_entry.getValue().setN_words(ntokens);

						assert (document_index.get(map_entry.getKey())
								.getN_words() == map_entry.getValue()
								.getN_words());

						// Adding the entry to the inverted_index
						if (inverted_index.containsKey(map_entry.getKey()))
							inverted_index.get(map_entry.getKey()).add(
									map_entry.getValue());
						else {
							TreeSet<IndexEntry> tSet = new TreeSet<IndexEntry>();
							tSet.add(map_entry.getValue());
							inverted_index.put(map_entry.getKey(), tSet);
						}
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
		
		this.average_document_size/=this.n_documents;

	}

	/*
	 * Number of documents indexed
	 */
	protected long getIndexSize(){
		return this.inverted_index.size();
	}
	
	/*
	 * Average size of a document in the index
	 */
	protected double getAvgDocumentSize(){
		return this.average_document_size;
	}
	
	

}

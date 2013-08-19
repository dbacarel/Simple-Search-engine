/**
 * SimpleSearchEngine
 * 
 * Daniele Bacarella 2013
 */



import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


import java.util.TreeSet;

public class Query {
	

	private Index index_ref;
	private Stemmer stemmer;
	private String query;
	private List<Integer> token_docs_frequency;
	private List<String> query_tokens;
	private List<TreeSet<IndexEntry>> token_posting_list;
	private Ranking ranking;
	private List<Result> query_result;
	
	public Query(String query, Index index){
		this.index_ref=index;
		this.query_tokens= new ArrayList<String>();
		this.token_docs_frequency= new ArrayList<Integer>();
		this.token_posting_list= new ArrayList<TreeSet<IndexEntry>>();
		this.query=query;
		this.ranking = new Ranking(index_ref.getIndexSize(), index_ref.getAvgDocumentSize());
		this.query_result= new ArrayList<Result>();
		this.stemmer= new Stemmer();
		
	}
	
	
	public void run(){
		
		Tokenizer tokenizer= new BasicTokenizer();
		tokenizer.tokenize(this.query);
		
		//Query string is tokenized
		while(tokenizer.hasMoreTokens())
			query_tokens.add(this.stemmer.stem(tokenizer.nextToken()));
		
		//Get the posting list for the first token
		TreeSet<IndexEntry> intersection_list= index_ref.getPostingList(query_tokens.get(0));
		
		token_docs_frequency.add(intersection_list.size());
		
		//Get the intersection of the various posting lists (aka set of documents matching all the tokens in the query)
		for(int i=1; i< query_tokens.size(); i++){
			TreeSet<IndexEntry> current_posting_list= index_ref.getPostingList(query_tokens.get(i));
			token_docs_frequency.add(current_posting_list.size());

			intersection_list.retainAll(current_posting_list);
		}
		
		//For each token, get a copy of the matching document entries
		TreeSet<IndexEntry> current_posting_list = null;
		for(int i=0; i< query_tokens.size(); i++){
			current_posting_list= index_ref.getPostingList(query_tokens.get(i));
			current_posting_list.retainAll(intersection_list);
			token_posting_list.add(current_posting_list);
		}
		
		//For each document matching the query, calculate the score
		while(!current_posting_list.isEmpty()){
			ArrayList<IndexEntry> current_document_scoring_list= new ArrayList<IndexEntry> ();
			IndexEntry entry=null;
			for(int i=0; i< token_posting_list.size();i++){
				entry=token_posting_list.get(i).pollFirst();
				current_document_scoring_list.add(entry);
			}
			query_result.add(new Result(entry.getDoc_name(), this.ranking.score(current_document_scoring_list, token_docs_frequency)));
			
		}
		
		//Sort in desc order   #keep a tree set also for this result 
		Collections.sort(query_result, new Comparator<Result>() {
		    public int compare(Result m1, Result m2) {
		        return m1.getScore() < m2.getScore()? 1 : -1;
		    }
		});
		
		//Print result
		for(int i=0;i< query_result.size();i++)
			System.out.println(query_result.get(i)) ;
		
		
	}
	
	
	
}

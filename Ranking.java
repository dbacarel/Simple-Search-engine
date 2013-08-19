/**
 * SimpleSearchEngine
 * 
 * Daniele Bacarella 2013
 */



import java.util.List;

public class Ranking {
	
	private long index_size;
	private double index_avg_size;
	private final double K1= 1.8;
	private final double B= 0.75;
	
	protected Ranking(long idx_size, double avg_size){
		index_size=idx_size;
		index_avg_size = avg_size;
	}
	
	/*
	 * Ranking function  BM25
	 * https://en.wikipedia.org/wiki/Probabilistic_relevance_model_(BM25)
	 */
	protected double score(List<IndexEntry> document, List<Integer> n_docs_term){
		double score=0;
		
		for(int i=0;i<n_docs_term.size();i++){
			IndexEntry entry=document.get(i);
			Integer n_docs = n_docs_term.get(i);
			
			double numerator = entry.getFrequency()*(K1+1);
			double denominator  = entry.getFrequency()+K1*(1-B+(B*index_size/index_avg_size));
		
			score+= IDF(n_docs)* numerator/denominator;		
			
		}
		return score;
	}
	
	private double IDF(Integer nd){
		return Math.log((double)(index_size-nd+0.5)/(nd+0.5));
	}

}

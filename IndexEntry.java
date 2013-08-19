/**
 * SimpleSearchEngine
 * 
 * Daniele Bacarella 2013
 */




public class IndexEntry implements Comparable<IndexEntry>{
	
	//Document name
	private String Doc_name;
	//Number of occurrences of the posting token in the document
	private long frequency;
	//Number of words found in the document
	private long n_words;
	
	
	protected IndexEntry(String name,long freq, long nwords){
		this.Doc_name= new String(name);
		this.frequency= freq;
		this.n_words= nwords;
	}


	protected String getDoc_name() {
		return Doc_name;
	}


	protected void setDoc_name(String doc_name) {
		Doc_name = doc_name;
	}


	protected long getFrequency() {
		return frequency;
	}


	protected void setFrequency(long frequency) {
		this.frequency = frequency;
	}


	protected long getN_words() {
		return n_words;
	}


	protected void setN_words(long n_words) {
		this.n_words = n_words;
	}


	@Override
	public int compareTo(IndexEntry arg0) {
		
		return this.Doc_name.compareTo(arg0.getDoc_name());
	}
	
	@Override
    public boolean equals(Object obj) {
        return obj instanceof IndexEntry &&  this.Doc_name.equals(((IndexEntry) obj).getDoc_name());
    }

    @Override
    public int hashCode() {
        return this.Doc_name.hashCode();
    }
	
    public String toString(){
    	return "Document name: "+this.Doc_name+" frequency of posting token:"+this.frequency+" #words in document: "+this.n_words;
    }
	
}

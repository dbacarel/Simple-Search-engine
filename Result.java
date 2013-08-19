/**
 * SimpleSearchEngine
 * 
 * Daniele Bacarella 2013
 */

package se.findwise.assignments.searchengine;

public class Result implements Comparable<Result>{

	private String document_name;
	private double score;
	
	protected Result(String name, double s){
		this.document_name=name;
		this.score=s;
	}
	
	protected double getScore(){
		return this.score;
	}
	
	protected String getName(){
		return this.document_name;
	}
	
	@Override
	public int compareTo(Result arg0) {
		return (new Double(this.score)).compareTo(arg0.getScore());
	}
	
	@Override
    public boolean equals(Object obj) {
        return obj instanceof Result &&  this.document_name.equals(((Result) obj).getName());
    }

    @Override
    public int hashCode() {
        return this.document_name.hashCode();
    }

	
	public String toString(){
		return this.document_name+"\t"+this.score;
	}
}

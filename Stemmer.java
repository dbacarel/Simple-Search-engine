/**
 * SimpleSearchEngine
 * 
 * Daniele Bacarella 2013
 */

package se.findwise.assignments.searchengine;

public class Stemmer extends Stemmer_Porter {

	private Stemmer_Porter stemmer;

	protected Stemmer() {

		super();
		stemmer = new Stemmer_Porter();

	}

	protected String stem(String token) {
		stemmer.add(token.toCharArray(), token.length());
		stemmer.stem();
		return stemmer.toString();
	}

}



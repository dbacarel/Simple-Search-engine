/**
 * SimpleSearchEngine
 * 
 * Daniele Bacarella 2013
 */


import java.util.Scanner;


public class BasicTokenizer extends Tokenizer {

		Scanner tokenizer;
		
	protected BasicTokenizer(){
		super();	
	}
	

	protected boolean hasMoreTokens() {
		return this.current_token!=null ;
	}

	void tokenize_(String text) {
		if (text != null) {
			this.current_token=null;
			this.tokenizer = new Scanner(text).useDelimiter("[.;!?\\s]+");
			tokenize_(null);
		} else if (tokenizer.hasNext()) {
			this.current_token = (String) tokenizer.next();
		} else
			this.current_token = null;

	}

}

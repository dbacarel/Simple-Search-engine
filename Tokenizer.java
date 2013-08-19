/**
 * SimpleSearchEngine
 * 
 * Daniele Bacarella 2013
 */



abstract class Tokenizer {
	
	protected String current_token;
	
	
		protected Tokenizer(){
				this.current_token=null;
		}
		
		protected void tokenize(String text){
			tokenize_(text);
		}

		protected String nextToken(){
			if(current_token!=null){
			String return_token=current_token;
			tokenize(null);
			return return_token;
			}else
				return null;
		}
		
		
		
		abstract protected boolean hasMoreTokens();
		abstract void tokenize_(String text);
		
}


/**
 * SimpleSearchEngine
 * 
 * Daniele Bacarella 2013
 */



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SearchEngine {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if(args.length<1){
			System.out.println("Usage: java searchengine [DOCUMENTS FOLDER PATH]");
			return;
		}
			
		Index index = new Index();
		try {
			index.build(args[0]);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		

		BufferedReader br;
		
		while (true) {
			System.out.print("Query:");
			br =new BufferedReader(new InputStreamReader(System.in));
			Query query=null;
			try {
				String query_line= br.readLine();
				query = new Query(query_line, index);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			query.run();
		}

	}

}

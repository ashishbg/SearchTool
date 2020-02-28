import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author Ashish
 *
 */
public class Main {
	static Logger logger = null;

	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		String test_path = ".";
		String test_type = "";
		String test_key = "";
		String test_writepath = null;
		File test_dir = new File(test_path);
		
		/* CLI */
		
		final Map<String, List<String>> params = new HashMap<>();

		List<String> options = null;
		for (int i = 0; i < args.length; i++) {
		    final String a = args[i];

		    if (a.charAt(0) == '-') {
		        if (a.length() < 2) {
		            System.err.println("Error at argument " + a);
		            printCliHelp();
		            return;
		        }

		        options = new ArrayList<>();
		        params.put(a.substring(1), options);
		    }
		    else if (options != null) {
		        options.add(a);
		    }
		    else {
		        System.err.println("Illegal parameter usage");
		        printCliHelp();
		        return;
		    }
		}
		
		if(params.isEmpty()) {
			System.out.println("All parameters ar required!");
			printCliHelp();
			return;
		}
		test_path = params.get("d").get(0);
		test_type = params.get("t").get(0);
		test_key = params.get("k").get(0);
		test_dir = new File(test_path);
		
		
		Finder finder = new Finder();
		finder.find(test_dir, test_type, test_key);
		finder.printSearchResult();
		finder.printSearchStats();
		
		if (params.containsKey("w")) {
			System.out.println("w option exists");
			if (!params.get("w").isEmpty() && params.get("w") !=null) {
				System.out.println("file name: " + params.get("w").get(0) );
				finder.writeSearchResult(params.get("w").get(0));
			}
			
		}
		
		
		
		
		/*CLI END*/
		
		

		/* Test values
		
		// String test_path = "D:\\ashish_workspace\\SearchTool\\test_folder_2";
		// String test_path = "D:/ashish_workspace/";
		test_path = "D:/ashish_workspace/";
		test_type = "java";
		test_key = "for";
		test_dir = new File(test_path);
		*/
		
		/*	
		
		Finder finder = new Finder();
		finder.find(test_dir, test_type, test_key);
		//finder.printSearchResult();
		//finder.writeSearchResult();
		finder.printSearchStats();
		*/
		
	}
	
	private static void printCliHelp() {
		/*CLI Help*/
		System.out.println("[required] -d \"dir\" to search; (.) for current directory");
		System.out.println("[required] -k \"search-key\"");
		System.out.println("[required] -t \"file-type\"");
		System.out.println("[optional] -w \"file-name\" to save search results ");
		System.out.println("Ex: SourceCode -d \"project\" -k \"hello\" -t \"txt\" -- searches for hello in project folder in file of type txt");
		System.out.println("");
	}
}

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Ashish
 *
 */
public class Main {
	static Logger logger = null;

	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		/* CLI */
		

		// Test values
		// String test_path = "D:\\ashish_workspace\\SearchTool\\test_folder_2";
		// String test_path = "D:/ashish_workspace/SearchTool/test_folder_2";
		String test_path = "D:/ashish_workspace/";
		String test_type = "java";
		String test_key = "for";
		File test_dir = new File(test_path);
		
		
		Finder finder = new Finder();
		finder.find(test_dir, test_type, test_key);
		//finder.printSearchResult();
		finder.writeSearchResult();
		finder.printSearchStats();
		
	}
}

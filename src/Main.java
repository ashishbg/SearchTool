import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * @author Ashish
 *
 */
public class Main {
	static Logger logger = null;

	public static void main(String[] args) throws FileNotFoundException, IOException {

		logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
		Finder finder = new Finder();
		
		//Test values
		String path = "test_folder";
		String type ="java";
		String searchKey = "project";
		
		File dir = new File(path);
		
		/*CLI*/
		System.out.println("");
		
		
		finder.find(dir, type, searchKey);
		

	}
}

 

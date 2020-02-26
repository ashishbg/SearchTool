import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class Main {
	static BufferedReader br = null;

	public static void main(String[] args) throws FileNotFoundException, IOException {
		String searchKey = "the";
		String path = "test_folder";
		File file = new File(path);
		String type = "java";
		String pattern = ".*\\." + type;
		// searchKeyInFile(searchKey, file);

		/* Listing all files */
		List<String> files = new ArrayList<String>();

		search(pattern, file, files);

		for (String f : files) {
			System.out.println("\n\n" + f + "\n");
			searchKeyInFile(searchKey,(String)f);
			
		}

	}
	
	public static void search(final String pattern, final File folder, List<String> result) {
        for (final File f : folder.listFiles()) {

            if (f.isDirectory()) {
                search(pattern, f, result);
            }

            if (f.isFile()) {
                if (f.getName().matches(pattern)) {
                    result.add(f.getPath());
                }
            }
        }
	}
    
	

	public static void searchKeyInFile(String key, String path) {
		int lineNumber = 0;

		try {
			br = new BufferedReader(new FileReader(path));
			String line = "";

			/* Searching for key */
			while ((line = br.readLine()) != null) {
				lineNumber++;
				if (line.contains(key)) {
					System.out.println(lineNumber + ": " + line);
				}
			}
			;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void printFile(String path) {
		String line = "";

		/* Print all text from file */
		try {
			br = new BufferedReader(new FileReader(path));
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

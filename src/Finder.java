import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Finder {

	static BufferedReader br;
	List<String> filePathList;
	List<String> searchHitList;
	static HashMap<String, List<String>> searchResult;

	private long startTime, endTime, totalTime;
	private static long searchHits;
	private long memoryUsed;

	Finder() {
		br = null;
		filePathList = null;
		searchHitList = null;
		searchResult = null;

		startTime = 0; endTime = 0; totalTime = 0;
		searchHits = 0; memoryUsed = 0;
	}

	public void find(File dir, String type, String key) {
		if (!dir.exists()) {
			System.out.println("No such directory found!");
			return;
		}

		filePathList = new ArrayList<String>();
		searchHitList = new ArrayList<String>();

		startTime = System.currentTimeMillis();

		try {
			filePathList = Finder.listFilePaths(dir, type); // getting paths of all files of 'type' in 'dir'
			searchResult = Finder.searchKeyInFile2(key, filePathList);

			// Finding memory usage

			Runtime runtime = Runtime.getRuntime(); // Get the Java runtime
			runtime.gc(); // Run the garbage collector
			memoryUsed = runtime.totalMemory() - runtime.freeMemory(); // Calculate the used memory
		} catch (Exception e) {
			e.printStackTrace();
		}
		endTime = System.currentTimeMillis();
	}

	/* returns List of file paths of type */
	private static List<String> listFilePaths(File dir, final String type) {

		List<String> files = new ArrayList<String>();

		String pattern = ".*\\." + type;
		if (type == null || type.contentEquals("")) {
			pattern = ".*";
		}
		for (File f : dir.listFiles()) {
			if (f.isDirectory()) {
				List<String> tempFilePath = new ArrayList<String>();
				tempFilePath = listFilePaths(f, type);
				files.addAll(tempFilePath);
			}

			if (f.isFile()) {
				if (Pattern.matches(pattern, f.getName())) {
					files.add(f.getPath());
				}
			}
		}
		return files;
	}

	private static HashMap<String, List<String>> searchKeyInFile2(String key, List<String> paths) {
		String line = "";
		searchResult = new HashMap<String, List<String>>();
		List<String> lines = null;

		try {
			for (String filePath : paths) {
				lines = new ArrayList<String>();
				br = new BufferedReader(new FileReader(filePath));

				/* Searching for key */
				while ((line = br.readLine()) != null) {
					if (line.contains(key)) {
						lines.add(line.replaceAll("\t", ""));
						searchHits++;
					}
				}
				searchResult.put(filePath, lines);
				lines = null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return searchResult;
	}
	
	private static List<String> searchKeyInFile(String key, String path) {
		String line = "";

		List<String> lines = null;

		try {
			br = new BufferedReader(new FileReader(path));
			lines = new ArrayList<String>();
			/* Searching for key */
			while ((line = br.readLine()) != null) {
				if (line.contains(key)) {
					lines.add(line);
				} else {
					lines.add(null);
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lines;
	}


	public void printSearchResult() {

		if (searchResult == null) {
			System.out.println("No search search results");
			return;
		}
		ArrayList<String> hits = null;
		for (Map.Entry file : searchResult.entrySet()) {

			hits = (ArrayList<String>) file.getValue();

			if (hits.isEmpty()) {
				continue;
			} else {
				System.out.println("\n" + file.getKey() + ": ");
				for (String s : hits) {
					System.out.println(s);
				}
			}
			System.out.println("\n");
		}
	}

	public void writeSearchResult(String outFile){
		

		if (searchResult == null) {
			System.out.println("No search search results");
			return;
		}
		
		BufferedWriter bw = null;
		ArrayList<String> hits = null;		
		

		try {
			if(outFile == null) {
				System.out.println("Search results will be saved to file search_result.txt");
				bw = new BufferedWriter(new FileWriter("search_result.txt"));
			}
			else {
				System.out.println("Search results will be saved to file " + outFile + ".txt");
				bw = new BufferedWriter(new FileWriter( outFile + ".txt"));
			}
			for (Map.Entry file : searchResult.entrySet()) {

				hits = (ArrayList<String>) file.getValue();

				if (hits.isEmpty()) {
					continue;
				} else {
					bw.append(file.getKey() + ": ");
				}
				for (String s : hits) {
					bw.write("\n\t" + s);
				}
				bw.write("\n\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (Exception e) {
					e.getMessage();
				}
			}
		}

	}

	public void printSearchStats() {
		totalTime = endTime - startTime;
		System.out.println("==================================================");
		if (totalTime > 1000) {
			System.out.println(searchHits + " results in " + (float) (totalTime / 1000) + " s");
		} else if (totalTime >= 0) {
			System.out.println(searchHits + " results in " + totalTime + " ms");
		}
		System.out.println("Used memory: " + memoryUsed + " B" + (float) (memoryUsed / (1024 * 1024)) + " MB");
		System.out.println("Used memory: " + (float) (memoryUsed / (1024 * 1024)) + " MB");

		System.out.println("==================================================");
	}

	public static void printFileContents(String path) {
		String line = "";

		/* Print all text from file */
		try {

			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

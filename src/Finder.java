import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Finder {
	
	static BufferedReader br = null;
	List<String> filePathList = null;			
	List<String> searchHitList = null;
	static HashMap<String, List<String>> searchResult = null;
	
	
	Finder(){
		filePathList = new ArrayList<String>();			
		searchHitList = new ArrayList<String>();
		
	}
	
	public void find(File dir, String type, String key){
		try {
			filePathList = Finder.listFilePaths(dir, type);	//getting paths of all files of 'type' in 'dir'
			searchResult = Finder.searchKeyInFile2(key, filePathList);

			Finder.printSearchResult(searchResult);
			/*
			for (String filePath : filePathList) {
				System.out.println("\n" + filePath.toString());
				searchHitList = Finder.searchKeyInFile(key, filePath);
				for (String hit : searchHitList) {
					System.out.println(hit.toString());
				}
			}
			*/
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	/* returns List of file paths of type */
	private static List<String> listFilePaths(File dir, final String type) {
		List<String> files = new ArrayList<String>();
		
		String pattern = ".*\\." + type;
		if(type == null || type.contentEquals("")) {
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
	
	public static HashMap<String, List<String>> searchKeyInFile2(String key, List<String> paths) {
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
						lines.add(line);
					}
				}
				
				searchResult.put(filePath, lines);
				lines = null;
			}

			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return searchResult;
	}
	
	public static void printSearchResult(HashMap<String, List<String>> searchResult) {
		ArrayList<String> hits = null;
		for(Map.Entry file : searchResult.entrySet()){
			System.out.println("\n" + file.getKey() + ": ");
			hits = (ArrayList<String>) file.getValue();			
			for(String s : hits) {
				System.out.println(s);
			}
		}
	}

	public static List<String> searchKeyInFile(String key, String path) {
		String line = "";

		List<String> lines = new ArrayList<String>();

		try {
			br = new BufferedReader(new FileReader(path));

			/* Searching for key */
			while ((line = br.readLine()) != null) {
				if (line.contains(key)) {
					lines.add(line);
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lines;
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

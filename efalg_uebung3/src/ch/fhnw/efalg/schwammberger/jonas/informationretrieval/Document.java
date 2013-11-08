package ch.fhnw.efalg.schwammberger.jonas.informationretrieval;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Represents a document which is indexed by the search engine
 * 
 * @author Jon
 */
public class Document {
	private File f;

	/**
	 * @param path
	 *            absolute URI to the document
	 */
	public Document(String path) {
		this.f = new File(path);
	}

	/**
	 * Reads the document and gives a Hashmap
	 * 
	 * @return Table containing the words found and their corresponding
	 *         frequency within the document
	 */
	public Map<String, Integer> readDoc() {
		HashMap<String, Integer> docContent = new HashMap<>();
		BufferedReader r = null;

		try {
			r = new BufferedReader(new FileReader(this.f));
			String line = null;
			while ((line = r.readLine()) != null) {
				String[] words = line.split("\\s+");

				for (String w : words) {
					String n = cleanString(w);

					if (docContent.containsKey(n)) {
						Integer i = docContent.get(n);
						docContent.remove(n);
						docContent.put(n, i + 1); //Integer is immutable, you can't increment it directly
					} else {
						docContent.put(n, 1);
					}
				}
			}
		} catch (IOException e) {
			System.out.println();
		} finally {
			try {
				r.close();
			} catch (Exception e) {
			}
		}

		return docContent;
	}

	/**
	 * Source: stackoverflow
	 * 
	 * @param str
	 *            cleaned string, lowercase, no special characters and removes
	 *            any punctuations.
	 * @return cleaned string
	 */
	public static String cleanString(String str) {
		return deAccent(str).toLowerCase().replaceAll("ß", "ss").replaceAll("\\W", "");
	}

	/**
	 * Source: stackoverflow
	 * 
	 * @param str
	 * @return
	 */
	private static String deAccent(String str) {
		String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(nfdNormalizedString).replaceAll("");
	}

	/**
	 * @return absolute path of document
	 */
	public String getAbsolutePath() {
		return f.getAbsolutePath();
	}

	/**
	 * @return filename of document
	 */
	public String getFileName() {
		return f.getName();
	}
}

package ch.fhnw.efalg.schwammberger.jonas.informationretrieval;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a dictionary of words. It supports vector space search.
 * 
 * @author Jon
 * 
 */
public class Dictionary {
	private static final double ld = Math.log(2.0);

	//map of word per document per frequency
	private final Map<String, Map<String, Integer>> dictionary;
	private int documentCount;

	public Dictionary() {
		this.dictionary = new HashMap<>();

		documentCount = 0;
	}

	public void indexDocument(Document doc) {
		Map<String, Integer> map = doc.readDoc();
		documentCount++;

		for (String word : map.keySet()) {
			Map<String, Integer> docToFreq = null;
			if (dictionary.containsKey(word))
				docToFreq = dictionary.get(word);
			else {
				docToFreq = new HashMap<>();
				dictionary.put(word, docToFreq);
			}

			docToFreq.put(doc.getAbsolutePath(), map.get(word));
		}
	}

	/**
	 * Creates a unit document vector from document reference.
	 * 
	 * @param doc
	 * @return
	 */
	public Vector createDocVector(Document doc) {
		Vector v = new Vector(dictionary.size());

		int i = 0;
		for (String word : dictionary.keySet()) {
			v.setPos(i, this.getTF_IDF(word, doc));
			i++;
		}

		v.convetToUnitVector();

		return v;
	}

	/**
	 * Creates a lookup table which returns the index of a word in a
	 * document/search vector
	 * 
	 * @return hashmap used as lookup table.
	 */
	public Map<String, Integer> createPositionLookup() {
		Map<String, Integer> map = new HashMap<>();

		int i = 0;
		for (String word : dictionary.keySet()) {
			map.put(word, new Integer(i));
			i++;
		}
		return map;
	}

	/**
	 * @return number of Words in this dictionary
	 */
	public int getWordCount() {
		return this.dictionary.size();
	}

	/**
	 * 
	 * @param word
	 *            in dictionary
	 * @return IDF value of a given word
	 */
	public double getIDF(String word) {
		double idf = 0;
		if (dictionary.containsKey(word)) {
			Map<String, Integer> docToPosting = dictionary.get(word);

			idf = Math.log((double) documentCount / docToPosting.size()) / ld;

		}
		return idf;
	}

	/**
	 * Calculates Heaps Law index statistics
	 * 
	 * @return
	 */
	public double calculateHeapsLaw() {
		return 44 * Math.pow(this.getWordCount(), 0.49);
	}

	/**
	 * Calculates TF_IDF, used by createDocVector()
	 * 
	 * @param word
	 * @param doc
	 * @return
	 */
	private double getTF_IDF(String word, Document doc) {
		double idf = 0;
		double tf = 0;
		if (dictionary.containsKey(word)) {
			Map<String, Integer> docToPosting = dictionary.get(word);

			if (docToPosting.containsKey(doc.getAbsolutePath())) {
				Integer freq = docToPosting.get(doc.getAbsolutePath());
				idf = Math.log((double) documentCount / docToPosting.size()) / ld;

				tf = Math.log(freq) / ld + 1;
			}
		}

		return tf * idf;
	}

}

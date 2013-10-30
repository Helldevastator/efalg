package ch.fhnw.efalg.schwammberger.jonas.informationretrieval;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * provides the search functionality. 
 * It uses the vector space search approach, as the name suggests.
 * @author Jon
 */
public class VectorSpaceSearch {
	private Dictionary dic;
	private ArrayList<Document> docs;
	private ArrayList<Vector> docVectors;
	private Map<String,Integer> wordToIndex;
	
	/**
	 * 
	 * @param path
	 */
	public VectorSpaceSearch(String path) {
		this.dic = new Dictionary();
		File data = new File(path);
		int size = data.listFiles().length;
		docVectors = new ArrayList<>(size);
		docs = new ArrayList<>(size);
		
		//create index
		for(File f : data.listFiles()) {
			Document doc = new Document(f.getAbsolutePath());
			dic.indexDocument(doc);
			docs.add(doc);
		}
		
		//create document vectors
		for(Document d : docs) 
			docVectors.add(dic.createDocVector(d));
		
		this.wordToIndex = dic.createPositionLookup();
	}
	
	
	/**
	 * Search the words
	 * @param words
	 * @param weights weight for each word
	 */
	public PriorityQueue<SearchResult> search(List<String> words, List<Integer> weights) {
		PriorityQueue<SearchResult> res = new PriorityQueue<>(docs.size(),Collections.reverseOrder());
		Vector searchV = new Vector(dic.getWordCount());
		
		//init vector
		for(int i = 0; i < words.size();i++) {
			String w = words.get(i);
			Integer pos = wordToIndex.get(w);
			double idf = dic.getIDF(w);
			
			//check if word does exist
			if(pos != null)
				//calculate idf
				searchV.setPos(pos, (Math.log(weights.get(i)) / Math.log(2) + 1) *idf );
			
		}
		searchV.convetToUnitVector();
		
		//search
		for(int i = 0; i < this.docs.size();i++) {
			double d = searchV.dotProduct(this.docVectors.get(i));
			res.add(new SearchResult(this.docs.get(i),d));
		}
		
		return res;
	}
	
	
	/**
	 * Represents a result of the vectorspace search
	 * @author Jon
	 */
	public class SearchResult implements Comparable<SearchResult> {
		private Document doc;
		private Double cos;
		
		public SearchResult(Document doc, Double cos) {
			this.doc = doc;
			this.cos = cos;
		}
		
		/**
		 * 
		 * @return document
		 */
		public Document getDoc() {return doc;}
		
		/**
		 * 
		 * @return cosinus value ascosiated with this document
		 */
		public Double getCos() { return cos;}
		@Override
		public int compareTo(SearchResult o) {
			return cos.compareTo(o.cos);
		}
	}
	
	/**
	 * Print heaps law on the console
	 */
	public void printHeapsLaw() {
		System.out.println("Heaps Law Statistics: "+ this.dic.calculateHeapsLaw());
	}
}

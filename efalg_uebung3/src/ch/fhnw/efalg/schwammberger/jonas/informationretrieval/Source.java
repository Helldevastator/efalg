package ch.fhnw.efalg.schwammberger.jonas.informationretrieval;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Source {

	public static void main(String[] args) {
		//VectorSpaceSearch s = new VectorSpaceSearch("debugfiles");
		VectorSpaceSearch s = new VectorSpaceSearch("testfiles");

		Scanner in = new Scanner(System.in);
		ArrayList<String> words = new ArrayList<>();
		;
		ArrayList<Integer> wordCount = new ArrayList<>();
		analyzeInput(in.nextLine(), words, wordCount);

		PriorityQueue<VectorSpaceSearch.SearchResult> res = s.search(words, wordCount);
		printResults(res);
		s.printHeapsLaw();
	}

	/**
	 * Prints the results (duh)
	 * 
	 * @param res
	 */
	private static void printResults(PriorityQueue<VectorSpaceSearch.SearchResult> res) {
		if (res.peek().getCos().equals(Double.NaN)) {
			System.out.println("This term is too unspecific.");
		} else {
			int i = 1;
			System.out.println("Document\tCos value\tPosition");
			System.out.println("------------------------------------------");
			while (!res.isEmpty()) {
				VectorSpaceSearch.SearchResult r = res.poll();
				System.out.format("%12s\t%.3f\t\t%3d", r.getDoc().getFileName(), r.getCos(), i);
				System.out.println();
				i++;
			}
		}
	}

	/**
	 * analyzes an input line for the SmallestRectangle
	 * 
	 * @param inputLine
	 */
	private static void analyzeInput(String inputLine, ArrayList<String> searchWords, ArrayList<Integer> wordCount) {
		for (String w : inputLine.split("\\s+")) {
			if (searchWords.contains(w)) {
				int i = searchWords.indexOf(w);
				Integer bla = wordCount.get(i);
				wordCount.set(i, ++bla);
			} else {
				searchWords.add(Document.cleanString(w));
				wordCount.add(1);
			}
		}

	}

}

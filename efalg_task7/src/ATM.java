import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class ATM {

	private static int[] noten;
	private static int[] notenCount;
	static int notenAnz;
	static int notenMaxAnz;
	static int min;
	static int max;
	static boolean[] results;

	static int maxSchranke;

	//static HashMap<int,>

	public static void main(String[] args) throws Exception {
		Scanner in = new Scanner(new File("atm.in"));
		PrintWriter out = new PrintWriter("atm.out");

		notenAnz = in.nextInt();
		min = in.nextInt();
		max = in.nextInt() + 1;

		noten = new int[notenAnz];
		notenCount = new int[notenAnz];
		int minValue = Integer.MAX_VALUE;
		for (int i = 0; i < notenAnz; i++) {
			notenCount[i] = in.nextInt();
			notenMaxAnz = notenCount[i];
			noten[i] = in.nextInt();
			if (noten[i] < minValue) {
				minValue = noten[i];
			}
		}

		results = new boolean[max];
		results[0] = true;

		maxSchranke = 1;
		for (int i = 0; i < notenAnz; i++) {
			for (int j = 0; j < notenCount[i]; j++) {
				generateResult(noten[i]);
			}
		}

		for (int i = min; i < max; i++) {
			if (results[i])
				out.println(i);

		}
		out.close();
	}

	public static void generateResult(int note) {
		for (int i = maxSchranke; i >= 0; i--) {
			if (results[i]) {
				if (i + note < max) {
					results[i + note] = true;
					maxSchranke = i + note > maxSchranke ? i + note : maxSchranke;
				}

			}
		}
	}
}

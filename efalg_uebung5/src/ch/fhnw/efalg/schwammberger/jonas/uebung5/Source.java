package ch.fhnw.efalg.schwammberger.jonas.uebung5;

import java.io.File;
import java.util.Scanner;

public class Source {

	public static void main(String[] args) {
		if (args.length < 0) {

		} else {
			//vogel test

		}

	}

	private void read(String filePath) {
		Scanner s;

		try {
			int correction = 0;
			int n;
			int m;
			boolean[] canBeNeg;
			//boolean[] isGreater;
			boolean isMax;
			double[][] table;

			s = new Scanner(new File(filePath));
			s.nextLine(); //skip first line
			s.nextLine(); //skip second
			s.nextLine(); //skip third

			while (s.hasNext()) {
				String line = s.nextLine();
				if (line.startsWith("="))
					correction++;
			}

			s.reset();
			n = s.nextInt() + 1;
			m = s.nextInt() + correction + 1;
			table = new double[n][m];
			isMax = s.nextBoolean();
			for (int i = 0; i < n; i++)
				table[i][m - 1] = s.nextDouble();
			canBeNeg = new boolean[n];
			for (int i = 0; i < n; i++)
				canBeNeg[i] = s.nextBoolean();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

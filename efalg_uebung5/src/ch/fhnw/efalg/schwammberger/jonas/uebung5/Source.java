package ch.fhnw.efalg.schwammberger.jonas.uebung5;

import java.io.File;
import java.util.Scanner;

public class Source {

	public static void main(String[] args) {
		if (args.length == 0) {

		} else {
			//vogel test

		}

	}

	private void read(String filePath) {
		Scanner s;

		try {
			int correction = 0;
			int cols;
			int rows;
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
			cols = s.nextInt() + 1;
			rows = s.nextInt() + correction + 1;
			table = new double[rows][cols];
			isMax = s.next().contains("max");
			//s.next?
			//read target function
			for (int i = 0; i < cols; i++)
				table[i][rows - 1] = s.nextDouble();

			canBeNeg = new boolean[cols];
			for (int i = 0; i < cols; i++)
				canBeNeg[i] = s.nextBoolean();

			//read table
			for (int i = 0; i < rows - 1; i++) {
				String nextLine = s.next();
				if (nextLine.startsWith("=")) {
					for (int j = 0; j < cols; j++) {
						double input = s.nextDouble();
						table[i][j] = input;
						table[i + 1][j] = input; //missing >= , *(-1)??
					}
					i++;

				} else {
					for (int j = 0; j < cols; j++)
						table[i][j] = s.nextDouble();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

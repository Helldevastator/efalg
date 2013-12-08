package ch.fhnw.efalg.schwammberger.jonas.uebung5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class Source {

	public static void main(String[] args) {
		if (args.length == 0) {
			testTwoPhaseSimplex();
			//read("./LP_problems/BasicExample.csv");
		} else {
			//vogel test

		}

	}

	private static void testTwoPhaseSimplex() {
		double[][] table = { { -1, -1, 18 }, { -1, 0, 11 }, { 0, -1, 10 }, { 1, 1, -9 }, { 1, 3, -229 } };
		Simplex simple = new Simplex(table);
		System.out.println(simple.solve());
	}

	private static void testSimpleSimplex() {
		double[][] table = { { -1, -1, 40 }, { -40, -120, 2400 }, { -7, -12, 312 }, { 100, 250, 0 } };
		Simplex simple = new Simplex(table);
		System.out.println(simple.solve());
	}

	private static void read(String filePath) {
		Scanner s = null;

		BufferedReader in;
		try {
			String line;
			int correction = 0;
			int cols;
			int rows;
			boolean[] canBeNeg;

			boolean isMax;
			double[][] table;

			in = new BufferedReader(new FileReader(filePath));
			in.readLine();
			in.readLine();
			in.readLine();

			while ((line = in.readLine()) != null)
				if (line.startsWith("="))
					correction++;

			in.close();
			in = new BufferedReader(new FileReader(filePath)); //reopen

			cols = s.nextInt() + 1;
			rows = s.nextInt() + correction + 1;
			table = new double[rows][cols];
			isMax = s.next().contains("max");
			//s.next?

			//read target function, if we should minimize the function, multiply with -1
			for (int i = 0; i < cols; i++)
				if (isMax)
					table[i][rows - 1] = s.nextDouble();
				else
					table[i][rows - 1] = -s.nextDouble();

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

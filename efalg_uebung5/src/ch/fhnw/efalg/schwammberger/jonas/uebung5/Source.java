package ch.fhnw.efalg.schwammberger.jonas.uebung5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Source {

	public static void main(String[] args) {
		if (args.length == 0) {
			//testSimpleSimplex();
			testTwoPhaseSimplex();
			//read("./LP_problems/BasicExample.csv");
		} else {
			//vogel test
			//read(args[0]);
		}

	}

	private static void testTwoPhaseSimplex() {
		double[][] table = { { -1, -1, 18 }, { -1, 0, 11 }, { 0, -1, 10 }, { 1, 1, -9 }, { -1, -3, 229 } };
		Simplex simple = new Simplex(table, false);
		double solution = simple.solve();

		if (solution == 191)
			System.out.print("pass: ");
		else
			System.out.print("!!!!!!FALSE!!!!!! ");

		System.out.println(solution);
	}

	private static void testSimpleSimplex() {
		double[][] table = { { -1, -1, 40 }, { -40, -120, 2400 }, { -7, -12, 312 }, { 100, 250, 0 } };
		Simplex simple = new Simplex(table, true);
		double solution = simple.solve();

		if (solution == 5400)
			System.out.print("pass: ");
		else
			System.out.print("!!!!!!FALSE!!!!!! ");

		System.out.println(solution);
	}

	private static void read(String filePath) {
		Scanner s = null;

		boolean isMax = false;
		double[][] table = null;

		BufferedReader in;

		try {
			int cols;
			int rows;
			boolean[] canBeNeg;

			in = new BufferedReader(new FileReader(filePath));
			int correction = getCorrection(in);
			in.close();
			in = new BufferedReader(new FileReader(filePath)); //reopen

			String[] tmp = in.readLine().split(";");
			cols = Integer.parseInt(tmp[0]) + 1;
			rows = Integer.parseInt(tmp[1]) + correction + 1;
			table = new double[rows][cols];

			isMax = readTargetFunction(in, table, rows, cols);
			//read canbeNeg
			canBeNeg = new boolean[cols];
			tmp = in.readLine().split(";");
			for (int i = 0; i < cols; i++)
				canBeNeg[i] = Boolean.parseBoolean(tmp[i]);

			readTable(in, table, rows, cols);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (table != null) {
			try {
				Simplex simple = new Simplex(table, isMax);
				double solution = simple.solve();
				System.out.println(solution);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	private static int getCorrection(BufferedReader in) throws IOException {
		int out = 0;
		in.readLine();
		in.readLine();
		in.readLine();

		String line;
		while ((line = in.readLine()) != null)
			if (line.startsWith("="))
				out++;

		return out;
	}

	private static boolean readTargetFunction(BufferedReader in, double[][] table, int rows, int cols) throws IOException {
		String[] tmp = in.readLine().split(";");
		boolean isMax = tmp[0].contains("max");

		for (int i = 0; i < cols; i++) {
			double input = Double.parseDouble(tmp[i + 1]);
			table[rows - 1][i] = input;
		}

		return isMax;
	}

	private static void readTable(BufferedReader in, double[][] table, int rows, int cols) throws IOException {
		for (int i = 0; i < rows - 1; i++) {
			String[] tmp = in.readLine().split(";");

			if (tmp[0].startsWith("=")) {
				for (int j = 0; j < cols; j++) {
					double input = Double.parseDouble(tmp[j + 1]);
					table[i][j] = input;
					table[i + 1][j] = -input; //missing >= , *(-1)??
				}
				i++;

			} else {
				boolean isGreater = tmp[0].startsWith("<");

				for (int j = 0; j < cols; j++) {
					double input = Double.parseDouble(tmp[j + 1]);
					table[i][j] = isGreater ? input : -input;
				}
			}
		}
	}

}

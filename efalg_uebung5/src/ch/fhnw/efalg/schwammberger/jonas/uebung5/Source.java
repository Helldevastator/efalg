package ch.fhnw.efalg.schwammberger.jonas.uebung5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Source {

	public static void main(String[] args) {
		if (args.length == 0) {
			//testSimpleSimplex();
			//testTwoPhaseSimplex();
			//read("./LP_problems/InfiniteSolutions.csv");
			//read("./LP_problems/BasicExample.csv");
			//read("./LP_problems/NichtNegativitaet_1.csv");
			read("./LP_problems/NichtNegativitaet_2.csv");
			//read("./LP_problems/NegSchlupf.csv");
			//read("./LP_problems/ZweiSaefte.csv");
		} else {
			//vogel test
			read(args[0]);
		}

	}

	private static void testTwoPhaseSimplex() {
		double[][] table = { { -1, -1, 18 }, { -1, 0, 11 }, { 0, -1, 10 }, { 1, 1, -9 }, { -1, -3, 229 } };
		try {
			Simplex simple = new Simplex(table, false);
			double solution = simple.solve();

			if (solution == 191)
				System.out.print("pass: ");
			else
				System.out.print("!!!!!!FALSE!!!!!! ");

			System.out.println(solution);
		} catch (SimplexException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void testSimpleSimplex() {
		double[][] table = { { -1, -1, 40 }, { -40, -120, 2400 }, { -7, -12, 312 }, { 100, 250, 0 } };
		Simplex simple = new Simplex(table, true);
		try {
			double solution = simple.solve();

			if (solution == 5400)
				System.out.print("pass: ");
			else
				System.out.print("!!!!!!FALSE!!!!!! ");

			System.out.println(solution);
		} catch (SimplexException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void read(String filePath) {
		Scanner s = null;

		double[] maxima = null;
		boolean isMax = false;
		double[][] table = null;
		boolean[] onlyPositive = null;

		BufferedReader in;

		try {
			int cols;
			int rows;

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
			onlyPositive = new boolean[cols - 1];
			tmp = in.readLine().split(";");
			boolean nonNegative = true;
			for (int i = 0; i < cols - 1; i++) {
				boolean b = "true".equals(tmp[i].trim());
				onlyPositive[i] = b;
				nonNegative = nonNegative & b;
			}

			readTable(in, table, rows, cols);

			if (!nonNegative)
				maxima = handleNegativity(table, onlyPositive, rows, cols);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (table != null) {

			Simplex simple = new Simplex(table, isMax);
			try {
				//simple.printTable();
				double solution = simple.solve();
				//simple.printTable();

				//more bla for non negativity
				if (maxima != null) {
					double[] vars = simple.getSolutionPerVar();
					for (int i = 0; i < onlyPositive.length; i++) {
						if (!onlyPositive[i]) {
							vars[i] = vars[i] - maxima[i];
						}
					}

					System.out.println(simple.insertInTargetFunction(vars));
					System.out.println(simple.getBla());
				} else {
					System.out.println(solution);
					System.out.println(simple.getBla());
				}
			} catch (SimplexException e) {
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
					table[i][j] = -input;
					table[i + 1][j] = input; //missing >= , *(-1)??
				}
				table[i][cols - 1] = -table[i][cols - 1];
				table[i + 1][cols - 1] = -table[i + 1][cols - 1];
				i++;

			} else {
				boolean isGreater = tmp[0].startsWith("<");

				for (int j = 0; j < cols; j++) {
					double input = Double.parseDouble(tmp[j + 1]);
					table[i][j] = isGreater ? -input : input;
				}
				table[i][cols - 1] = -table[i][cols - 1];
			}
		}
	}

	private static double[] handleNegativity(double[][] table, boolean[] canBeNeg, int rows, int cols) {
		//TODO: false
		double[] maxima = new double[canBeNeg.length];
		for (int i = 0; i < canBeNeg.length; i++) {
			if (!canBeNeg[i]) {
				for (int j = 0; j < rows - 1; j++) {
					double current = Math.abs(table[j][cols - 1] / table[j][i]);
					if (!Double.isInfinite(current) && maxima[i] < current)
						maxima[i] = current;
				}

			}
		}

		for (int i = 0; i < canBeNeg.length; i++) {
			if (!canBeNeg[i]) {
				for (int j = 0; j < rows - 1; j++) {
					table[j][cols - 1] += table[j][i] * -maxima[i];
				}
			}
		}

		return maxima;
	}
}

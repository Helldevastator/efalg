package ch.fhnw.efalg.schwammberger.jonas.uebung5;

public class Simplex {
	private double[][] table; //row, col

	private boolean isTwoPhase;
	private double[][] table2;

	//data

	public Simplex(double[][] table) {
		this.table = table;
		int rows = table.length;
		int cols = table[0].length;

		//TODO: two-phase method here
		isTwoPhase = hasNegC();
		if (isTwoPhase) {
			//TODO: create table2;
		}
	}

	private boolean hasNegC() {
		int cols = this.table[0].length;
		for (int i = 0; i < this.table.length; i++) {
			if (this.table[i][cols - 1] < 0)
				return true;
		}
		return false;
	}

	public double solve() {
		if (isTwoPhase) {
			solvePhase(table2);
			//TODO: copy to table
		}

		return solvePhase(table);
	}

	private static final double solvePhase(double[][] table) {
		int rows = table.length;
		int cols = table[0].length;
		double result = table[rows - 1][cols - 1];
		int pivotCol = 0;
		while ((pivotCol = findPCol(table)) >= 0) {
			int pivotRow = findPRow(table, pivotCol); //TODO: case pivotCol == -1

			rotate(table, pivotRow, pivotCol);
			//TODO: handle special cases
			result = table[rows - 1][cols - 1];
		}

		return result;
	}

	private static final int findPRow(double[][] table, int pivotCol) {
		int rows = table.length;
		int cols = table[0].length;
		double min = Double.MAX_VALUE;
		int out = -1;

		for (int i = 0; i < rows; i++) {
			if (table[i][pivotCol] < 0) {
				double rate = Math.abs(table[i][cols - 1] / table[i][pivotCol]);
				if (rate < min) {
					out = i;
					min = rate;
				}
			}
		}

		return out;
	}

	private static final int findPCol(double[][] table) {
		int rows = table.length;
		int cols = table[0].length;
		int out = -1;
		//don't check result column
		for (int i = 0; i < cols - 1; i++) {
			//take first occurence, look up "Satz von Bland"
			if (table[rows - 1][i] > 0) {
				out = i;
				break;
			}
		}
		return out;
	}

	private static final void rotate(double[][] table, int pR, int pC) {
		int rows = table.length;
		int cols = table[0].length;

		double pivot = -table[pR][pC];

		for (int i = 0; i < cols; i++) {
			if (i == pC)
				table[pR][pC] = -1 / pivot;
			else
				table[pR][i] = table[pR][i] / pivot;
		}

		for (int i = 0; i < rows; i++) {
			if (i != pR) {
				for (int j = 0; j < cols; j++) {
					if (j != pC)
						table[i][j] += table[i][pC] * table[pR][j];

				}
				table[i][pC] = table[i][pC] * table[pR][pC];
			}
		}
	}

}

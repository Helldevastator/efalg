package ch.fhnw.efalg.schwammberger.jonas.uebung5;

public class Simplex {
	private double[][] table; //row, col
	private int cols;
	private int rows;

	//data

	public Simplex(double[][] table, int rows, int cols) {
		this.table = table;
		this.rows = rows;
		this.cols = cols;
	}

	public double solve() {
		int pivotCol = 0;
		double result = table[rows - 1][cols - 1];
		while ((pivotCol = findPCol()) >= 0) {
			int pivotRow = findPRow(pivotCol); //TODO: case pivotCol == -1

			rotate(pivotRow, pivotCol);
			//TODO: handle special cases
			result = table[rows - 1][cols - 1];
		}

		return result;
	}

	private int findPRow(int pivotCol) {
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

	private int findPCol() {
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

	private void rotate(int pR, int pC) {
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

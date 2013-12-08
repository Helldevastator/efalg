package ch.fhnw.efalg.schwammberger.jonas.uebung5;

public class Simplex {
	private double[][] table; //row, col

	private int[] columnVar; //helper variable is 0 [val(xo) = 0]
	private int[] rowVar;
	private boolean isTwoPhase;
	private int rows;
	private int cols;

	public Simplex(double[][] table) {
		this.rows = table.length;
		this.cols = table[0].length + 1;
		this.table = new double[rows][cols];

		//copy and add helper variable
		for (int i = 0; i < rows; i++) {
			this.table[i][0] = 1; //add helper variable in case we need it for twophase method
			for (int j = 1; j < cols; j++) {
				this.table[i][j] = table[i][j - 1];
			}
		}
		this.table[rows - 1][0] = 0;

		this.columnVar = new int[cols];
		for (int i = 0; i < cols; i++)
			this.columnVar[i] = i;

		this.rowVar = new int[rows];
		for (int i = 0; i < rows; i++)
			this.rowVar[i] = i + cols;

		//Two phase method initialization
		this.isTwoPhase = hasNegC();
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
			//copy target function
			//rotate

			solvePhase();

			//find x0
			//maybe rotate
			//set x0 to 0
		}

		return solvePhase();
	}

	private final double solvePhase() {
		double result = table[rows - 1][cols - 1];
		int pivotCol = 0;
		while ((pivotCol = findPCol()) >= 0) {
			int pivotRow = findPRow(pivotCol); //TODO: case pivotCol == -1

			rotate(pivotRow, pivotCol);
			//TODO: handle special cases
			result = table[rows - 1][cols - 1];
		}

		return result;
	}

	private final int findPRow(int pivotCol) {
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

	private final int findPCol() {
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

	private final void rotate(int pR, int pC) {
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

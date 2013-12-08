package ch.fhnw.efalg.schwammberger.jonas.uebung5;

public class Simplex {
	private double[][] table; //row, col

	private int[] columnVar; //helper variable is 0 [val(xo) = 0]
	private int[] rowVar;
	private boolean isTwoPhase;
	private int rows;
	private int cols;

	public Simplex(double[][] table) {
		this.isTwoPhase = hasNegC(table);
		this.rows = table.length;
		this.cols = isTwoPhase ? table[0].length + 1 : table[0].length;
		this.table = new double[rows][cols];

		int colStart = isTwoPhase ? 1 : 0;
		//copy and add helper variable
		for (int i = 0; i < rows; i++) {
			if (isTwoPhase)
				this.table[i][0] = 1; //add helper variable for two phase method
			for (int j = colStart; j < cols; j++) {
				this.table[i][j] = table[i][j - colStart];
			}
		}

		if (isTwoPhase)
			this.table[rows - 1][0] = -1;

		this.columnVar = new int[cols];
		for (int i = 0; i < cols; i++)
			this.columnVar[i] = i;

		this.rowVar = new int[rows];
		for (int i = 0; i < rows; i++)
			this.rowVar[i] = i + cols;
	}

	private boolean hasNegC(double[][] table) {
		int cols = table[0].length;
		for (int i = 0; i < table.length; i++) {
			if (table[i][cols - 1] < 0)
				return true;
		}
		return false;
	}

	public double solve() {
		if (isTwoPhase) {
			//copy target function without x0
			double[] tmp = new double[cols - 1];
			for (int i = 1; i < cols; i++) {
				tmp[i - 1] = table[rows - 1][i];
				table[rows - 1][i] = 0;
			}
			this.printTable();
			int pRow = findLowestC();
			rotate(pRow, 0);
			printTable();
			solvePhase();
			printTable();

			//check if table[rows-1][cols-1] = 0

			//if x0 is in a row, swap it to a column
			for (int i = 0; i < rows; i++) {
				if (this.rowVar[i] == 0) {
					rotate(i, 0);
					break;
				}
			}

			//put in target function
			int tmpIndex = 0;
			for (int i = 0; i < cols; i++) {
				if (this.columnVar[i] == 0) {
					//set x0 to 0
					for (int j = 0; j < rows; j++)
						table[j][i] = 0;
					table[rows - 1][i] = 0;
				} else {
					table[rows - 1][i] = tmp[tmpIndex];
					tmpIndex++;
				}
			}

		}

		return solvePhase();
	}

	private final int findLowestC() {
		int out = -1;
		double min = Double.MAX_VALUE;
		for (int i = 0; i < rows; i++) {
			if (table[i][cols - 1] < min) {
				out = i;
				min = table[i][cols - 1];
			}
		}

		return out;
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

		//swap variables, it is used to find x0 later on
		int tmp = this.columnVar[pC];
		this.columnVar[pC] = this.rowVar[pR];
		this.rowVar[pR] = tmp;

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

	public final void printTable() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				System.out.print(table[i][j] + " ");
			}

			System.out.println();
		}

		System.out.println();
	}

}

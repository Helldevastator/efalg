package ch.fhnw.efalg.schwammberger.jonas.uebung5;

public class Simplex {
	private double[][] table; //row, col

	private int[] columnVar; //helper variable is 0 [val(xo) = 0]
	private int[] rowVar;
	private boolean isTwoPhase;
	private boolean isMax;
	private int rows;
	private int cols;

	public Simplex(double[][] table, boolean isMaximizingProblem) {
		this.isMax = isMaximizingProblem;
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

		if (!isMax)
			this.invertTargetFunction();
	}

	private final void invertTargetFunction() {
		for (int i = 0; i < cols; i++)
			if (this.columnVar[i] != 0 || !this.isTwoPhase)
				table[rows - 1][i] = -table[rows - 1][i];
	}

	private boolean hasNegC(double[][] table) {
		int cols = table[0].length;
		for (int i = 0; i < table.length; i++) {
			if (table[i][cols - 1] < 0)
				return true;
		}
		return false;
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
		int pivotCol = 0;
		while ((pivotCol = findPCol()) >= 0) {
			int pivotRow = findPRow(pivotCol);

			//case pivotCol == -1, solution -> infinity
			if (pivotRow < 0)
				return Double.POSITIVE_INFINITY;

			rotate(pivotRow, pivotCol);
			//TODO: handle special cases
		}

		return table[rows - 1][cols - 1];
	}

	private final int findPRow(int pivotCol) {
		double min = Double.MAX_VALUE;
		int out = -1;

		for (int i = 0; i < rows - 1; i++) {
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

	public double solve() {
		if (isTwoPhase) {
			int x0Column = 0;

			//copy target function without x0
			double[] tmp = new double[cols];
			for (int i = 1; i < cols; i++) {
				tmp[i] = table[rows - 1][i];
				table[rows - 1][i] = 0;
			}
			int pRow = findLowestC();
			rotate(pRow, 0);
			double tmpSolution = solvePhase();
			printTable(); //ok

			//TODO check if tmpSolution = 0; if so, gooooooooddd!

			//if x0 is in a row, swap it to a column. It doesn't matter which column
			for (int i = 0; i < rows; i++) {
				if (this.rowVar[i] == 0) {
					rotate(i, 0);
					break;
				}
			}

			//copy in target function, if the x variable is still in a column
			// and remove x0
			for (int i = 0; i < cols; i++) {
				if (this.columnVar[i] == 0) {
					x0Column = i;

					//set x0 to 0
					for (int j = 0; j < rows; j++)
						table[j][i] = 0;
				} else {
					//put in x1-xn. If variable is a y, don't do anything
					int colIndex = this.columnVar[i];
					if (colIndex < this.columnVar.length) {
						table[rows - 1][colIndex] = tmp[colIndex];
					}
				}
			}

			printTable();
			this.replaceTargetFunction(tmp, x0Column);
		}

		printTable();
		double tmp = solvePhase();
		printTable();
		return isMax ? tmp : -tmp;
	}

	private final void replaceTargetFunction(double[] targetFunction, int x0Column) {
		for (int i = 0; i < targetFunction.length; i++) {
			if (i != x0Column) {
				if (columnVar[i] >= cols) {
					int rowIndex = this.findXRowVarIndex(i + 1);
					double originalTargetVal = targetFunction[i + 1];

					//replace
					for (int j = 0; j < this.cols; j++)
						table[rows - 1][j] += originalTargetVal * table[rowIndex][j];
				}
			}

		}
	}

	private final int findXRowVarIndex(int xVar) {
		for (int i = 0; i < rows; i++) {
			if (this.rowVar[i] == xVar)
				return i;
		}
		return -1;
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

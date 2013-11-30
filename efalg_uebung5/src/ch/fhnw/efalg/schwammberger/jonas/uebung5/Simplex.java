package ch.fhnw.efalg.schwammberger.jonas.uebung5;

public class Simplex {
	private double[][] table; //row, col
	private int cols;
	private int rows;

	//data

	public Simplex() {

	}

	private void rotate(int pR, int pC) {
		double pivot = -table[pR][pC];

		for (int i = 0; i < cols; i++) {
			if (cols == pC)
				table[pR][pC] = -1 / pivot;
			else
				table[pR][i] = table[pR][i] / pivot;
		}

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {

				if (j != pC)
					table[i][j] += table[i][pC] * table[pR][j];

			}
			table[i][pC] = table[i][pC] * table[pR][pC];
		}
	}
}

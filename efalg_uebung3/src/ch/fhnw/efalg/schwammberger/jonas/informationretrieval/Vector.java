package ch.fhnw.efalg.schwammberger.jonas.informationretrieval;

/**
 * Represents a vector in an n dimensional plane
 * 
 * @author Jon
 */
public class Vector {
	private double[] vec;

	/**
	 * Creates a vector of n dimensions
	 * 
	 * @param n
	 */
	public Vector(int n) {
		vec = new double[n];
	}

	/**
	 * @return size of vector
	 */
	public int size() {
		return vec.length;
	}

	/**
	 * set value at position
	 * 
	 * @param pos
	 * @param val
	 */
	public void setPos(int pos, double val) {
		vec[pos] = val;
	}

	/**
	 * get value at position
	 * 
	 * @param pos
	 * @return
	 */
	public double getPos(int pos) {
		return vec[pos];
	}

	/**
	 * convert this vector to a unit vector
	 */
	public void convetToUnitVector() {
		// calculate magnitude of vector
		double magnitude = calculateMagnitude();

		// apply
		for (int i = 0; i < vec.length; i++) {
			vec[i] = vec[i] / magnitude;
		}
	}

	/**
	 * 
	 * @return
	 */
	private double calculateMagnitude() {
		// calculate magnitude of vector
		double magnitude = 0;
		for (Double d : vec)
			magnitude += d * d;

		return Math.sqrt(magnitude);
	}

	/**
	 * Calculates the dot product of two unit vectors
	 * 
	 * @param v
	 *            expected to be a unit vector
	 * @return
	 */
	public double dotProduct(Vector v) {
		double dot = 0;
		for (int i = 0; i < vec.length; i++) {
			dot += vec[i] * v.getPos(i);
		}

		return dot;
	}

	/**
	 * Print vector components on the console, for debugging purposes
	 */
	public void printVector() {
		for (int i = 0; i < vec.length; i++) {
			System.out.print(vec[i] + "\t");
		}
		System.out.println();
	}

}

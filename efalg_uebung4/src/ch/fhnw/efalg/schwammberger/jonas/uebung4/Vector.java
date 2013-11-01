package ch.fhnw.efalg.schwammberger.jonas.uebung4;

import java.awt.Point;

public class Vector {
	private final int x;
	private final int y;

	public Vector(Point p0, Point p1) {
		x = p0.x - p1.x;
		y = p0.y - p1.y;
	}

	/**
	 * Copy constructor
	 * 
	 * @param v
	 */
	public Vector(Vector v) {
		x = v.x;
		y = v.y;
	}

	/**
	 * 
	 * @param x
	 * @param y
	 */
	public Vector(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * 
	 * @return
	 */
	public double calculateMagnitude() {
		return Math.sqrt(x * x + y * y);
	}

	public double cross(Vector v) {
		return x * v.y - v.x * y;
	}

	/**
	 * 
	 * @param v
	 * @return angle in radiants
	 */
	public double getAngle(Vector v) {
		int dot;
		dot = x * v.x + y * v.y;
		double mag = this.calculateMagnitude() * v.calculateMagnitude();

		return Math.acos(dot / mag);
	}

}

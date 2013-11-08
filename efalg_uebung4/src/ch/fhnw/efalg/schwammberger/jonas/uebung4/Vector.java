package ch.fhnw.efalg.schwammberger.jonas.uebung4;

import java.awt.geom.Point2D;

/**
 * Represents a two dimensional vector
 * 
 * @author Jon
 * 
 */
public class Vector {
	private double x;
	private double y;

	public Vector(Point2D p, Point2D p2) {
		x = p.getX() - p2.getX();
		y = p.getY() - p2.getY();
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
	public Vector(double x, double y) {
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
		double dot;
		dot = x * v.x + y * v.y;
		double mag = this.calculateMagnitude() * v.calculateMagnitude();

		return Math.acos(dot / mag);
	}

	public void rotate(double angle) {
		double newX = this.x * Math.cos(angle) - this.y * Math.sin(angle);
		double newY = this.y * Math.sin(angle) + this.x * Math.cos(angle);
		this.x = newX;
		this.y = newY;
	}

	@Override
	public String toString() {
		return "Vector[vx=" + x + ",vy=" + y + "]";
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

}

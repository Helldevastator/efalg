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

	/**
	 * create a new vector from startPoint to endpoint
	 * 
	 * @param endPoint
	 *            end point
	 * @param startPoint
	 *            start point
	 */
	public Vector(Point2D endPoint, Point2D startPoint) {
		x = endPoint.getX() - startPoint.getX();
		y = endPoint.getY() - startPoint.getY();
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

	public void rotate(double angle) {
		double newX = this.x * Math.cos(angle) - this.y * Math.sin(angle);
		double newY = this.x * Math.sin(angle) + this.y * Math.cos(angle);
		this.x = newX;
		this.y = newY;
	}

	/**
	 * Calculate angle between this and vector v1
	 * 
	 * @param v1
	 * @return angle in radiant
	 */
	public double calculateAngle(Vector v1) {
		//dot product
		double theta = (x * v1.x + y * v1.y) / (this.calculateMagnitude() * v1.calculateMagnitude());

		return Math.acos(theta);
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

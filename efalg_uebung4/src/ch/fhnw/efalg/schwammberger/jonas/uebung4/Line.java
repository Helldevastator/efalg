package ch.fhnw.efalg.schwammberger.jonas.uebung4;

import java.awt.Point;
import java.awt.geom.Point2D;

/**
 * Represents an infinite line
 * 
 * @author Jon
 * 
 */
public class Line {
	private static double doublePI = Math.PI * 2;
	private Point2D p;
	private Vector v;

	public Line(Point p, Vector v) {
		this.p = p;
		this.v = v;
	}

	/**
	 * Copy constructor
	 * 
	 * @param l
	 */
	public Line(Line l) {
		p = (Point2D) l.p.clone();
		v = new Vector(l.v);
	}

	/**
	 * get the angle which this line has to be turned in order to go through p1
	 * 
	 * @param p1
	 * @return
	 */
	public double calculateAngle(Point p1) {
		Vector v1 = new Vector(p1, this.p);
		return this.v.calculateAngle(v1);

		/*
		 * Point2D p2 = new Point2D.Double(p.getX() + v.getX(), p.getY() +
		 * v.getY()); return calcAngle(p2, this.p, p1);
		 */
	}

	private static double calcAngle(Point2D a, Point2D b, Point2D c) {
		double alpha = Math.atan2(a.getY() - b.getY(), a.getX() - b.getX());
		double beta = Math.atan2(c.getY() - b.getY(), c.getX() - b.getX());

		System.out.println(Math.toDegrees(alpha - beta) + ", alpha:" + Math.toDegrees(alpha) + " beta:" + Math.toDegrees(beta));
		return beta - alpha;
	}

	/**
	 * Turns line so it goes through the point p
	 * 
	 * @param p
	 */
	public void rotateLine(Point2D point) {
		this.v = new Vector(point, this.p);
		this.p = (Point) point.clone();
	}

	/**
	 * Rotate this line arount p with angle
	 * 
	 * @param angle
	 *            in radiants
	 */
	public void rotateLine(double angle) {
		this.v.rotate(angle);
	}

	/**
	 * Get the intersection point with this line
	 * 
	 * @param l
	 * @return
	 */
	public Point calculateIntersectionPoint(Line l) {
		Vector s = this.v;
		Vector t;

		/*
		 * int sx = p2.x - p1.x; int sy = p2.y - p1.y; int tx = -(l.p2.x -
		 * l.p1.x); int ty = -(l.p2.y - l.p1.y);
		 * 
		 * int sm = p1.y - p1.x; int tm = l.p1.y - l.p1.x;
		 * 
		 * int main = sx * (ty) - sy * tx; int minort = tx * tm - ty * sm; int
		 * minors = sx * tm - sy * sm;
		 * 
		 * double s1 = -(double) minort / main; double t1 = (double) minors /
		 * main;
		 */

		return null;
	}

	/**
	 * 
	 * @param rectangle
	 *            Array of 4 lines. Line 0 and 2, and 1 and 3 are parallel.
	 * @return
	 */
	public static double calculateRectangleArea(Line[] rectangle) {
		return calculateRectangleArea(rectangle[0], rectangle[1], rectangle[2], rectangle[3]);
	}

	/**
	 * get rectangle area enclosed by the 4 lines
	 * 
	 * @param a
	 *            is parallel to c
	 * @param b
	 *            is parallel to d
	 * @param c
	 *            is
	 * @param d
	 *            is
	 * @return
	 */
	public static double calculateRectangleArea(Line a, Line b, Line c, Line d) {
		double da = a.getDistanceParallel(c);
		double dc = b.getDistanceParallel(d);

		return da * dc;
	}

	/**
	 * Calculates the distance between this line and par. par has to be parallel
	 * to this.
	 * 
	 * @param par
	 * @return
	 */
	private double getDistanceParallel(Line par) {
		Vector g0 = new Vector(p, par.p);

		return Math.abs(v.cross(g0) / v.calculateMagnitude());
	}

	@Override
	public String toString() {
		return "Line([x=" + p.getX() + ",y=" + p.getY() + "]" + v.toString() + ")";
	}
}

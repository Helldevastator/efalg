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
	 * @return Point with Integer numbers as
	 */
	public Point calculateIntersectionPoint(Line l) {
		Vector s = this.v;
		Vector t = new Vector(-l.v.getX(), -l.v.getY());

		double sm = p.getY() - p.getX();
		double tm = l.p.getY() - l.p.getY();
		double minorT = t.getX() * tm - t.getY() * sm;
		double major = s.getX() * t.getY() - s.getY() * t.getY();

		double s1 = -minorT / major;

		return new Point((int) (this.p.getX() + s1 * s.getX()), (int) (this.p.getY() + s1 * s.getY()));
	}

	/**
	 * Calculates the distance between this line and par. to this.
	 * 
	 * @param par
	 *            has to be parallel to this
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
	 * 
	 * @param d
	 * 
	 * @return
	 */
	public static double calculateRectangleArea(Line a, Line b, Line c, Line d) {
		double da = a.getDistanceParallel(c);
		double dc = b.getDistanceParallel(d);

		return da * dc;
	}

	/**
	 * Calculates the vertices of a rectangle
	 * 
	 * @param rec
	 *            Array of 4 lines representing a rectangle rec[0] is parallel
	 *            to rec[2], rec[1] to rec[3]
	 * @return the 4 Vertices of this rectangle represented in integer
	 *         coordinates;
	 */
	public static Point[] calculateVertices(Line[] rec) {
		Point[] out = new Point[4];

		out[0] = rec[0].calculateIntersectionPoint(rec[1]);
		out[1] = rec[1].calculateIntersectionPoint(rec[2]);
		out[2] = rec[2].calculateIntersectionPoint(rec[3]);
		out[3] = rec[3].calculateIntersectionPoint(rec[0]);

		return out;
	}

}

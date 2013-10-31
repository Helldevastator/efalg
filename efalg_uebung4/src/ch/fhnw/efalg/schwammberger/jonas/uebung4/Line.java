package ch.fhnw.efalg.schwammberger.jonas.uebung4;

import java.awt.Point;

public class Line {
	private final Point p;
	private final Vector v;

	/**
	 * Copy constructor
	 * 
	 * @param l
	 */
	public Line(Line l) {
		p = (Point) l.p.clone();
		v = new Vector(l.v);
	}

	/**
	 * get the angle which this line has to be turned in order to go through p1
	 * 
	 * @param p1
	 * @return angle in radiant
	 */
	public double calculateAngle(Point p1) {
		Vector rp1 = new Vector(p, p1);
		return v.getAngle(rp1);
	}

	/**
	 * Get the intersection point with this line
	 * 
	 * @param l
	 * @return
	 */
	public Point calculateIntersectionPoint(Line l) {

		return null;
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

		return v.cross(g0) / v.calculateMagnitude();
	}
}

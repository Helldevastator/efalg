package efalg_task3;

/**
 * ----------------------------------------------------------------- -------
 * Effiziente Algorithmen ---------------- ------- ---------------- -------
 * helper class for the treatement of ---------------- ------- points in the
 * xy-plane ---------------- ------- ---------------- ------- @author Manfred
 * Vogel ---------------- ------- 2013 october ----------------
 * -----------------------------------------------------------------
 */

class Point {
	public double x, y;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double dist(Point p) {
		double dx = x - p.x;
		double dy = y - p.y;
		return Math.sqrt(dx * dx + dy * dy);
	}

	public Point(Point p) {
		this(p.x, p.y);
	}

	public Point relTo(Point p) {
		return new Point(x - p.x, y - p.y);
	}

	public void makeRelTo(Point p) {
		x -= p.x;
		y -= p.y;
	}

	public double dot(Point p) {
		return x * p.x + y * p.y;
	}

	public Point reversed() {
		return new Point(-x, -y);
	}

	public double mdist() { // Manhattan-distance
		return Math.abs(x) + Math.abs(y);
	}

	public double mdist(Point p) {
		return relTo(p).mdist();
	}

	public boolean isFurther(Point p) {
		return mdist() > p.mdist();
	}

	public boolean isBetween(Point p0, Point p1) {
		return p0.mdist(p1) >= mdist(p0) + mdist(p1);
	}

	public double cross(Point p) {
		return x * p.y - p.x * y;
	}

	public boolean isLess(Point p) {
		double f = cross(p);
		return f > 0 || f == 0 && isFurther(p);
	}

	public double area2(Point p0, Point p1) {
		return p0.relTo(this).cross(p1.relTo(this));
	}

	public boolean isConvex(Point p0, Point p1) {
		double f = area2(p0, p1);
		return f < 0 || f == 0 && !isBetween(p0, p1);
	}
}
package ch.fhnw.efalg.schwammberger.jonas.uebung4;

/**
 * ----------------------------------------------------------------- -------
 * Effiziente Algorithmen ---------------- ------- ---------------- -------
 * helper class for the treatement of ---------------- ------- points in the
 * xy-plane ---------------- ------- ---------------- ------- @author Manfred
 * Vogel ---------------- ------- 2013 october ----------------
 * -----------------------------------------------------------------
 */

class VogelPoint {
	public double x, y;

	public VogelPoint(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double dist(VogelPoint p) {
		double dx = x - p.x;
		double dy = y - p.y;
		return Math.sqrt(dx * dx + dy * dy);
	}

	public VogelPoint(VogelPoint p) {
		this(p.x, p.y);
	}

	public VogelPoint relTo(VogelPoint p) {
		return new VogelPoint(x - p.x, y - p.y);
	}

	public void makeRelTo(VogelPoint p) {
		x -= p.x;
		y -= p.y;
	}

	public double dot(VogelPoint p) {
		return x * p.x + y * p.y;
	}

	public VogelPoint reversed() {
		return new VogelPoint(-x, -y);
	}

	public double mdist() { // Manhattan-distance
		return Math.abs(x) + Math.abs(y);
	}

	public double mdist(VogelPoint p) {
		return relTo(p).mdist();
	}

	public boolean isFurther(VogelPoint p) {
		return mdist() > p.mdist();
	}

	public boolean isBetween(VogelPoint p0, VogelPoint p1) {
		return p0.mdist(p1) >= mdist(p0) + mdist(p1);
	}

	public double cross(VogelPoint p) {
		return x * p.y - p.x * y;
	}

	public boolean isLess(VogelPoint p) {
		double f = cross(p);
		double bla1 = mdist();
		double bla2 = p.mdist();
		return f > 0 || f == 0 && isFurther(p);
	}

	public double area2(VogelPoint p0, VogelPoint p1) {
		return p0.relTo(this).cross(p1.relTo(this));
	}

	public boolean isConvex(VogelPoint p0, VogelPoint p1) {
		double f = area2(p0, p1);
		return f < 0 || f == 0 && !isBetween(p0, p1);
	}
}

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Wireless {
	public static double[] lengths;
	public static ArrayList<Double> angles;
	public static ArrayList<Double> radiuses;
	public static double minRadius;
	public static Point minPoint;
	public static ArrayList<Point> points;
	public static Point[] ps;
	static int index;
	private static double halfPI = Math.PI / 2;
	private static double doublePI = Math.PI * 2;

	public static void read() throws Exception {
		Scanner in = new Scanner(new File("wireless.in"));
		int count = in.nextInt();
		ps = new Point[count];
		for (int i = 0; i < count; i++) {
			ps[i] = new Point(in.nextDouble(), in.nextDouble());
		}
	}

	public static void main(String[] args) throws Exception {
		read();

		if (ps.length > 1) {

			int h = Graham.computeHull(ps);
			points = new ArrayList<>(Arrays.asList(ps));
			calcualteAngles(h);

			boolean isDone = false;

			stuff(h);

		} else {
			minPoint = ps[0];
			minRadius = 0.0;
		}

		PrintWriter out = new PrintWriter("wireless.out");
		out.println(String.format("%.2f", minRadius));
		out.println(String.format("%.2f", minPoint.x) + " "
				+ String.format("%.2f", minPoint.y));
		out.close();

	}

	public static void calcualteAngles(int h) {
		angles = new ArrayList<>(h);
		for (int i = 0; i < h; i++)
			angles.add(calcAngle(points.get((i + h - 1) % h), points.get(i),
					points.get((i + 1) % h)));
	}

	public static double calcAngle(Point a, Point b, Point c) {
		double alpha = Math.atan2(a.x - b.x, a.y - b.y);
		double beta = Math.atan2(c.x - b.x, c.y - b.y);

		return (4 * Math.PI + (beta - alpha)) % doublePI;
	}

	public static void stuff(int h) {
		minRadius = Double.MAX_VALUE;
		boolean isDone = false;
		while (!isDone) {
			double maxRadius = 0.0;
			double maxAngle = 0.0;
			int maxIndex = 0;
			Point maxCenter = null;
			for (int i = 0; i < h; i++) {

				double radius;
				double angle;
				Point center = new Point(0, 0);
				Point p1 = points.get((i + h - 1) % h);
				Point p2 = points.get(i);
				Point p3 = points.get((i + 1) % h);

				radius = radius(p1, p2, p3, center);
				angle = calcAngle(p1, p2, p3);
				if (maxRadius < radius
						|| (maxRadius == radius && maxAngle < angle)) {
					maxRadius = radius;
					maxAngle = angle;
					maxIndex = i;
					maxCenter = center;
				}

			}

			if (!(maxAngle <= halfPI)) {
				points.remove(maxIndex);
				h--;
			} else {
				minRadius = maxRadius;
				minPoint = maxCenter;
				isDone = true;
			}
		}
	}

	private static double radius(Point a, Point b, Point c, Point center) {
		if (a == b && b == c) {
			center = a;
		} else if (a == b || b == c) {
			center.x = (a.x + c.x) / 2;
			center.y = (a.y + c.y) / 2;
		} else if (a == c) {
			center.x = (a.x + b.x) / 2;
			center.y = (a.y + b.y) / 2;
		} else {
			Point p1 = a;
			Point p2 = b;
			Point p3 = c;
			double ad = p2.x - p1.x;
			double bd = p2.y - p1.y;
			double cd = p3.x - p1.x;
			double d = p3.y - p1.y;
			double e = ad * (p2.x + p1.x) * 0.5 + bd * (p2.y + p1.y) * 0.5;
			double f = cd * (p3.x + p1.x) * 0.5 + d * (p3.y + p1.y) * 0.5;
			double det = ad * d - bd * cd;

			center.x = (d * e - bd * f) / det;
			center.y = (-cd * e + ad * f) / det;
		}

		double radius = center.dist(a);

		return radius;
	}

	private static int getIndex(int index) {
		return 0;
	}

	static class Point {
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

	public static class Graham {

		protected static Point[] p;
		protected static int n;
		protected static int h;

		public static int computeHull(Point[] p) {
			setPoints(p);
			if (n < 3) {
				return n;
			}
			graham();
			return h;
		}

		private static void graham() {
			exchange(0, indexOfLowestPoint());
			Point pl = new Point(p[0]);
			makeRelTo(pl);
			sort();
			makeRelTo(pl.reversed());
			int i = 3, k = 3;
			while (k < n) {
				exchange(i, k);
				while (!isConvex(i - 1)) {
					exchange(i - 1, i--);
				}
				k++;
				i++;
			}
			h = i;
		}

		private static void sort() {
			quicksort(1, n - 1); // without point 0
		}

		protected static void quicksort(int lo, int hi) {
			int i = lo, j = hi;
			Point q = p[(lo + hi) / 2];
			while (i <= j) {
				while (p[i].isLess(q)) {
					i++;
				}
				while (q.isLess(p[j])) {
					j--;
				}
				if (i <= j) {
					exchange(i++, j--);
				}
			}
			if (lo < j) {
				quicksort(lo, j);
			}
			if (i < hi) {
				quicksort(i, hi);
			}
		}

		public static void setPoints(Point[] p0) {
			p = p0;
			n = p.length;
			h = 0;
		}

		protected static void exchange(int i, int j) {
			Point t = p[i];
			p[i] = p[j];
			p[j] = t;
		}

		protected static void makeRelTo(Point p0) {
			int i;
			Point p1 = new Point(p0); // necessary, because p0 migth be in p[]
			for (i = 0; i < n; i++) {
				p[i].makeRelTo(p1);
			}
		}

		protected static int indexOfLowestPoint() {
			int i, min = 0;
			for (i = 1; i < n; i++) {
				if (p[i].y < p[min].y || p[i].y == p[min].y
						&& p[i].x < p[min].x) {
					min = i;
				}
			}
			return min;
		}

		protected static boolean isConvex(int i) {
			return p[i].isConvex(p[i - 1], p[i + 1]);
		}
	}
}
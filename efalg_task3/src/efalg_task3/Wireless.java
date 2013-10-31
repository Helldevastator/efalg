package efalg_task3;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class Wireless {
	public static double[] lengths;
	public static double[] angles;
	public static double[] radiuses;
	public static double minRadius;
	public static Point[] points;

	public static void read() throws Exception {
		Scanner in = new Scanner(new File("wireless.in"));
		int count = in.nextInt();
		points = new Point[count];
		for (int i = 0; i < count; i++) {
			points[i] = new Point(in.nextInt(), in.nextInt());
		}
	}

	public static void main(String[] args) throws Exception {
		read();

		PrintWriter out = new PrintWriter("wireless.out");

		int h = Graham.computeHull(points);
		calcualteAngles(h);
		calculateRadius(h);

		// away
		System.out.println((-1 + 3) % 3);
		for (int i = 0; i < h; i++) {
			System.out.println("Convex hull point Nr. " + (i + 1) + " ("
					+ points[i].x + " , " + points[i].y + ")");
			// if (angles[i] <= Math.PI / 2) {
			System.out.println(angles[i]);
			// }
		}
		// out.println("solution");

		out.close();
	}

	public static void calcualteAngles(int h) {
		angles = new double[h];
		for (int i = 0; i < h; i++)
			angles[i] = calcAngle(points[(i + h - 1) % h], points[i],
					points[(i + 1) % h]);
	}

	public static double calcAngle(Point a, Point b, Point c) {
		Point v1 = new Point(b.x - a.x, b.y - a.y);
		Point v2 = new Point(b.x - c.x, b.y - c.y);
		return Math.abs(Math.atan2(v1.cross(v2), v1.dot(v2)));
	}

	private static double getRadiusG(Point p1, Point p2, Point p3) {

		double a = p2.x - p1.x;
		double b = p2.y - p1.y;
		double c = p3.x - p1.x;
		double d = p3.y - p1.y;
		double e = a * (p2.x + p1.x) * 0.5 + b * (p2.y + p1.y) * 0.5;
		double f = c * (p3.x + p1.x) * 0.5 + d * (p3.y + p1.y) * 0.5;
		double det = a * d - b * c;

		Point center = new Point((d * e - b * f) / det, (-c * e + a * f) / det);
		return center.dist(p1);
	}

	private static double getRadius0(Point p1, Point p3) {
		return (Math.sqrt((p1.x - p3.x) * (p1.x - p3.x) + (p1.y - p3.y)
				* (p1.y - p3.y)));

	}

	public static void calculateRadius(int h) {
		minRadius = Double.MAX_VALUE;
		for (int i = 0; i < h; i++) {
			long timeG = System.nanoTime();

			double radiusG = getRadiusG(points[(i + h - 1) % h], points[i],
					points[(i + 1) % h]);

			timeG = System.nanoTime() - timeG;

			long timeO = System.nanoTime();

			double radiusO = getRadius0(points[(i + h - 1) % h], points[(i + 1)
					% h])
					/ (2 * Math.sin(angles[i]));

			timeO = System.nanoTime() - timeO;

			System.out.println("gürber: " + radiusG + " (" + timeG
					+ ") oesch: " + radiusO + " (" + timeO + ")");
			if (radiusO < minRadius && angles[i] < Math.PI / 2)
				minRadius = radiusO;
		}
		System.out.println();
		System.out.println("The minimal Radius is: " + minRadius);
	}
}
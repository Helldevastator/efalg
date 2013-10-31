package efalg_task3;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class Wireless {
	public static double[] lengths;
	public static double[] angles;
	public static double minRadius;

	public static void main(String[] args) throws Exception {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(new File("wireless.in"));
		PrintWriter out = new PrintWriter("wireless.out");

		int count = in.nextInt();
		Point[] points = new Point[count];
		for (int i = 0; i < count; i++) {
			points[i] = new Point(in.nextInt(), in.nextInt());
		}

		int h = Graham.computeHull(points);
		calculateLength(points, h);
		calcualteAngles(points, h);
		calculateMinRadius(points, h);

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

	public static void calculateLength(Point points[], int h) {
		lengths = new double[h];
		for (int i = 0; i < h; i++) {
			lengths[i] = Math.sqrt((points[i].x - points[(i + 1) % h].x)
					* (points[i].x - points[(i + 1) % h].x)
					+ (points[i].y - points[(i + 1) % h].y)
					* (points[i].y - points[(i + 1) % h].y));
		}
	}

	public static void calcualteAngles(Point points[], int h) {
		angles = new double[h];
		for (int i = 0; i < h; i++) {
			int index = (i + h - 1) % h;
			double length = Math
					.sqrt((points[(i + h - 1) % h].x - points[(i + 1) % h].x)
							* (points[(i + h - 1) % h].x - points[(i + 1) % h].x)
							+ (points[(i + h - 1) % h].y - points[(i + 1) % h].y)
							* (points[(i + h - 1) % h].y - points[(i + 1) % h].y));
			angles[i] = Math
					.acos((lengths[i] * lengths[i] + lengths[(i + h - 1) % h]
							* lengths[(i + h - 1) % h] - length * length)
							/ (2 * lengths[i] * lengths[(i + h - 1) % h]));
		}

	}

	public static void calculateMinRadius(Point points[], int h) {
		minRadius = Double.MAX_VALUE;
		for (int i = 0; i < h; i++) {
			double radius = (Math
					.sqrt((points[(i + h - 1) % h].x - points[(i + 1) % h].x)
							* (points[(i + h - 1) % h].x - points[(i + 1) % h].x)
							+ (points[(i + h - 1) % h].y - points[(i + 1) % h].y)
							* (points[(i + h - 1) % h].y - points[(i + 1) % h].y)))
					/ (2 * Math.sin(angles[i]));
			if (radius < minRadius && angles[i] < Math.PI / 2) {
				minRadius = radius;
			}
			// radiuses[i] = (Math
			// .sqrt((points[(i + h - 1) % h].x - points[(i + 1) % h].x)
			// * (points[(i + h - 1) % h].x - points[(i + 1) % h].x)
			// + (points[(i + h - 1) % h].y - points[(i + 1) % h].y)
			// * (points[(i + h - 1) % h].y - points[(i + 1) % h].y)))
			// / (2 * Math.sin(angles[i]));
		}
		System.out.println("The minimal Radius is: " + minRadius);
	}
}

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;

public class Heuberger {

	static Point center;
	static ArrayList<Point> sortedPoints; // only one of the lines pair

	public static void read() throws Exception {
		Scanner in = new Scanner(new File("heuberger.in"));

		center = new Point(0, 0);
		int size = in.nextInt();
		sortedPoints = new ArrayList<>(size + 1);

		sortedPoints.add(center);
		for (int i = 0; i < size; i += 2) {
			Point p1 = new Point(in.nextDouble(), in.nextDouble());
			Point p2 = new Point(in.nextDouble(), in.nextDouble());
			Line l;
			if (p1.x < p2.x) {
				sortedPoints.add(p1);
				l = new Line(p1, p2);
			} else {
				sortedPoints.add(p2);
				l = new Line(p2, p1);
			}
			l.setObstacle();
		}

		Collections.sort(sortedPoints);
	}

	public static void main(String[] args) throws Exception {
		read();

		HashSet<Line> visible = new HashSet<>();
		checkHalf(visible, true);
		checkHalf(visible, false);

		/*
		 * Iterator<Line> it = visible.iterator(); while (it.hasNext()) { Line l
		 * = it.next(); System.out.println(l.p1.x + " " + l.p1.y + " " + l.p2.x
		 * + " " + l.p2.y); }
		 */
		PrintWriter out = new PrintWriter("heuberger.out");
		out.println(visible.size());
		out.close();
	}

	public static void checkHalf(HashSet<Line> visible, boolean startLow) {
		ArrayList<Line> viewLines = new ArrayList<>(sortedPoints.size() >>> 2);

		boolean foundCenter = false;
		int i = startLow ? 0 : sortedPoints.size() - 1;

		while (!foundCenter) {
			Point p = sortedPoints.get(i);

			if (p != center) {
				Line obstacle = p.obstacle;

				// check intercections
				for (int j = 0; j < viewLines.size(); j++) {
					Line view = viewLines.get(j);
					int intersects = obstacle.intersects(view);

					if (intersects > 0) {
						// intersects, so don't need to check it again
						viewLines.remove(j);
						j--;
					}

				}

				viewLines.add(new Line(obstacle.p1, center));
				viewLines.add(new Line(obstacle.p2, center));
			} else
				foundCenter = true;

			if (startLow)
				i++;
			else
				i--;
		}

		for (int j = 0; j < viewLines.size(); j++) {
			Line l = viewLines.get(j);
			visible.add(l.p1.obstacle);
		}
	}

	public static class Point implements Comparable<Point> {
		double x;
		double y;
		Line obstacle;

		public Point(double x, double y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public int compareTo(Point o) {
			return Double.compare(x, o.x);
		}
	}

	public static class Line {
		Point p1;
		Point p2;

		public Line(Point p1, Point p2) {
			this.p1 = p1;
			this.p2 = p2;
		}

		public int intersects(Line l) {
			double sx = p2.x - p1.x;
			double sy = p2.y - p1.y;
			double tx = -(l.p2.x - l.p1.x);
			double ty = -(l.p2.y - l.p1.y);

			double sm = p1.y - p1.x;
			double tm = l.p1.y - l.p1.x;

			double main = sx * (ty) - sy * tx;
			double minort = tx * tm - ty * sm;
			double minors = sx * tm - sy * sm;

			double s1 = -minort / main;
			double t1 = minors / main;

			if (s1 > 1.0 || s1 < 0.0 || t1 > 1.0 || t1 < 0.0)
				return -1;
			else if (s1 == 0.0 || s1 == 1.0 || t1 == 0 || t1 == 1.0)
				return 0;
			else
				return 1;

		}

		public void setObstacle() {
			p1.obstacle = this;
			p2.obstacle = this;
		}
	}

}

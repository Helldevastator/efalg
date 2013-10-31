package efalg_task3;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

public class HeubergerWat {
	static double pi2 = Math.PI * 2;
	static Point center;
	static ArrayList<Point> sortedPoints; // only one of the lines pair

	public static void read() throws Exception {
		Scanner in = new Scanner(new File("heuberger_test.in"));

		center = new Point(in.nextInt(), in.nextInt());
		int size = in.nextInt();
		sortedPoints = new ArrayList<>(size);

		for (int i = 0; i < size; i += 2) {
			Point p1 = new Point(in.nextInt(), in.nextInt());
			Point p2 = new Point(in.nextInt(), in.nextInt());
			Line l;
			sortedPoints.add(p1);
			l = new Line(p1, p2);
			// l.setObstacle();
		}

		Collections.sort(sortedPoints);
	}

	public static void main(String[] args) throws Exception {
		center = new Point(0, 0);

		ArrayList<Point> bäm = new ArrayList<>();

		Point p1 = new Point(0, 3);
		Point p2 = new Point(0, 4);

		bäm.add(new Point(-3, 2));
		bäm.add(p2);
		bäm.add(p1);
		bäm.add(new Point(-3, -2));
		bäm.add(new Point(3, 1));
		bäm.add(new Point(3, 2));
		// bäm.add(new Point(3, -4));

		/*
		 * 
		 */

		Collections.sort(bäm);

		for (Point p : bäm) {
			System.out.println(p.x + " " + p.y);
		}
		// System.out.println(p1.compareTo(p2));
		// System.out.println(Integer.compare(0, 1));
		read();

		HashSet<Line> visible = new HashSet<>();
		checkHalf(visible, true);
		checkHalf(visible, false);

		Iterator<Line> it = visible.iterator();
		while (it.hasNext()) {
			Line l = it.next();
			System.out.println(l.p1.x + " " + l.p1.y + " " + l.p2.x + " "
					+ l.p2.y);
		}
		PrintWriter out = new PrintWriter("heuberger.out");
		out.close();

	}

	public static void check() {

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

					if (intersects >= 0) {
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
		int x;
		int y;
		Line obstacle;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int cross(Point p) {
			return x * p.y - p.x * y;
		}

		public int mDist() {
			return Math.abs(x) + Math.abs(y);
		}

		@Override
		public int compareTo(Point o) {
			Point v1 = new Point(o.x - center.x, o.y - center.y);
			Point v2 = new Point(x - center.x, y - center.y);

			double alpha = Math.atan2(v1.cross(v2), v1.x * v2.x + v1.y * v2.y);

			if (alpha < 0.0)
				return -1;
			else if (alpha > 0.0)
				return 1;
			else {
				int mDist1 = v1.mDist();
				int mDist2 = v2.mDist();

				if (mDist1 == mDist2)
					return 0;
				else
					return mDist1 < mDist2 ? 1 : -1;
			}

			// return Integer.compare(x, o.x);
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
			int sx = p2.x - p1.x;
			int sy = p2.y - p1.y;
			int tx = -(l.p2.x - l.p1.x);
			int ty = -(l.p2.y - l.p1.y);

			int sm = p1.y - p1.x;
			int tm = l.p1.y - l.p1.x;

			int main = sx * (ty) - sy * tx;
			int minort = tx * tm - ty * sm;
			int minors = sx * tm - sy * sm;

			double s1 = -(double) minort / main;
			double t1 = (double) minors / main;

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

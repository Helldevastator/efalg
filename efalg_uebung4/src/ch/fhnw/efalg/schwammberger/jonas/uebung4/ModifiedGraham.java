package ch.fhnw.efalg.schwammberger.jonas.uebung4;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Graham Convex Hull Algorithm with small modifications. It calculates the
 * minimum convex hull. Read the MODIFICATION comment in the graham() method for
 * more information.
 * 
 * @author Jon
 */
public class ModifiedGraham {
	private ArrayList<HelperPoint> helperP;
	private Point lowest;

	/**
	 * 
	 * @param p
	 * @return
	 */
	public ArrayList<Point> calculateMinConvexHull(List<Point> p) {
		if (p.size() <= 3)
			return new ArrayList<Point>(p);

		this.createHelperList(p);
		return this.graham();
	}

	/**
	 * Create the HelperPoint list
	 * 
	 * @param in
	 */
	private void createHelperList(List<Point> in) {
		int low = findLowest(in);
		lowest = in.get(low);
		helperP = new ArrayList<>(in.size());

		for (int i = 0; i < in.size(); i++)
			if (low != i)
				helperP.add(new HelperPoint(in.get(i), lowest));
		Collections.sort(helperP);
	}

	private ArrayList<Point> graham() {
		// heuristic,expect that half of the points are not inside the
		// convexhull
		int startSize = helperP.size() > 21 ? helperP.size() / 2 : 10;
		ArrayList<Point> convexHull = new ArrayList<>(startSize);

		// fill convexHull
		convexHull.add(lowest);
		convexHull.add(helperP.get(0).getP());

		for (int i = 1; i < helperP.size(); i++) {
			Point next = helperP.get(i).getP();

			while (!isConvex(convexHull, next))
				convexHull.remove(convexHull.size() - 1);

			convexHull.add(next);
		}

		/*
		 * MODIFICATION: check if the last Point has to be in the ConvexHull. if
		 * P0 and Pn-1 build a line with Pn on it, remove Pn
		 */
		int n = convexHull.size() - 1;
		if (!isConvex(convexHull.get(n - 1), convexHull.get(n), convexHull.get(0)))
			convexHull.remove(n);

		return convexHull;
	}

	/**
	 * 
	 * 
	 * @param convexHull
	 * @param next
	 * @return
	 */
	private boolean isConvex(ArrayList<Point> convexHull, Point next) {
		int i = convexHull.size() - 1;
		return isConvex(convexHull.get(i - 1), convexHull.get(i), next);
	}

	/**
	 * isConvex check.
	 * 
	 * @param p0
	 * @param p1
	 * @param p2
	 * @return
	 */
	private static boolean isConvex(Point p0, Point p1, Point p2) {
		Point p0Rel = new Point(p0.x - p1.x, p0.y - p1.y);
		Point p2Rel = new Point(p2.x - p1.x, p2.y - p1.y);
		int area = cross(p0Rel, p2Rel);

		//return area < 0;
		boolean bla1 = area < 0;
		boolean bla2 = area == 0 && !isBetween(p0, p1, p2);
		return area < 0 || area == 0 && !isBetween(p0, p1, p2);
	}

	private static int cross(Point p0, Point p1) {
		return p0.x * p1.y - p1.x * p0.y;
	}

	private static boolean isBetween(Point p0, Point p1, Point p2) {
		int p0p2 = Math.abs(p0.x - p2.x) + Math.abs(p0.y - p2.y);
		int p1p0 = Math.abs(p1.x - p0.x) + Math.abs(p1.y - p0.y);
		int p1p2 = Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);

		return p0p2 >= p1p0 + p1p2;
	}

	/**
	 * Find lowest point
	 * 
	 * @param p
	 * @return index of lowest point in p
	 */
	private final static int findLowest(List<Point> p) {
		int min = 0;
		for (int i = 1; i < p.size(); i++)
			if (p.get(i).y < p.get(min).y || p.get(i).y == p.get(min).y && p.get(i).x < p.get(min).x)
				min = i;
		return min;
	}

	/**
	 * represents a small helper class to sort the input
	 * 
	 * @author Jon
	 */
	private class HelperPoint implements Comparable<HelperPoint> {
		private final Point current;
		private int relX; // relative to lowest point
		private int relY;

		public HelperPoint(Point p, Point p0) {
			current = p;
			if (p == p0) {
				relX = 0;
				relY = 0;
			} else {
				relX = p.x - p0.x;
				relY = p.y - p0.y;
			}
		}

		private int relativeCross(HelperPoint p) {
			return relX * p.relY - relY * p.relX;
		}

		private int relativeMDist() {
			return Math.abs(relX) + Math.abs(relX);
		}

		@Override
		public int compareTo(HelperPoint o) {
			if (o.relX == relX && o.relY == relY)
				return 0;

			int f = this.relativeCross(o);
			boolean isLess = f > 0 || f == 0 && relativeMDist() > o.relativeMDist();
			return isLess ? -1 : 1;
		}

		public Point getP() {
			return current;
		}

	}
}

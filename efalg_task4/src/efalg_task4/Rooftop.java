package efalg_task4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;

public class Rooftop {

	//static ArrayList<Line> roof;
	static LinkedList<Point> building;

	private static void read() throws FileNotFoundException {
		Scanner in = new Scanner(new File("rooftop_test.in"));
		int size = in.nextInt();

		ArrayList<Point> input = new ArrayList<>(size);
		for (int i = 0; i < size; i++)
			input.add(new Point(in.nextDouble(), in.nextDouble()));

		//for(int i = 0; i < size;i++) {

	}

	public static void main(String[] args) throws Exception {
		read();

		sweep();
	}

	private static void sweep() {
		OrderedList<Point> cornerPoints = null;
		ArrayList<Line> roof = new ArrayList<>();
		ArrayList<Line> preliminaryRoof = new ArrayList<>();

		while (cornerPoints.size() > 0) {
			Point p = cornerPoints.pop();

			if (p.isRoofPoint) {

			} else {
				Line newRoof = null;
				if (p.isCounterClockWise()) {

				} else {

				}
				checkNewRoof(newRoof, roof, preliminaryRoof, cornerPoints);
			}

		}

		//look for highest roof
		double max = 0;
		for (int i = 0; i < roof.size(); i++) {
			if (roof.get(i).isDiagonal) {
				double magnitude = roof.get(i).magnitude();
				if (magnitude > max)
					max = magnitude;
			}
		}

	}

	private static void checkNewRoof(Line l, ArrayList<Line> roof, ArrayList<Line> preliminaryRoof, OrderedList<Point> cornerPoints) {
		boolean foundIntersection = false;
		//check for intersection
		for (int i = 0; i < preliminaryRoof.size(); i++) {
			Line other = preliminaryRoof.get(i);
			int intersects = other.intersects(l);
			if (intersects == 1) {
				Point intersection = null;

				//get intersection point
				Point interP = null;
				other.end = interP;
				l.end = interP;

				//add both rooflines to roof
				roof.add(other);
				roof.add(l);
				preliminaryRoof.remove(other);
				cornerPoints.orderedAdd(interP);
				foundIntersection = true;
				break;
			}
		}

		boolean foundTouch = false;
		if (!foundIntersection) {
			for (int i = 0; i < roof.size(); i++) {
				int touches = roof.get(i).intersects(l);
				if (touches == 0) {
					foundTouch = true;

					Point touchPoint = null;
					l.end = touchPoint;
					roof.add(l);
					break;
				}
			}
		}

		if (!foundIntersection && !foundTouch)
			preliminaryRoof.add(l);
	}

	private static Line newRoof(Point before, Point current, Point after) {

		return null;
	}

	public static class Point implements Comparable<Point> {
		double x;
		double y;
		Point before;
		Point after;
		boolean isRoofPoint;
		boolean isHorizontal;

		public Point(double x, double y) {
			this.x = x;
			this.y = y;
			isRoofPoint = false;
		}

		public Point(double x, double y, boolean isRoofPoint) {
			this.isRoofPoint = isRoofPoint;
			this.x = x;
			this.y = y;
		}

		public boolean isCounterClockWise() {
			return false;
		}

		@Override
		public int compareTo(Point o) {
			int comp = Double.compare(x, o.x);

			return comp == 0 ? Double.compare(y, o.y) : comp;
		}

	}

	public static class Line {
		Point start;
		Point end;
		boolean isDiagonal;

		public double magnitude() {
			return 0;
		}

		public int intersects(Line l) {
			//			double sx = p2.x - p1.x;
			//			double sy = p2.y - p1.y;
			//			double tx = -(l.p2.x - l.p1.x);
			//			double ty = -(l.p2.y - l.p1.y);
			//
			//			double sm = p1.y - p1.x;
			//			double tm = l.p1.y - l.p1.x;
			//
			//			double main = sx * (ty) - sy * tx;
			//			double minort = tx * tm - ty * sm;
			//			double minors = sx * tm - sy * sm;
			//
			//			double s1 = -minort / main;
			//			double t1 = minors / main;
			//
			//			if (s1 > 1.0 || s1 < 0.0 || t1 > 1.0 || t1 < 0.0)
			//				return -1;
			//			else if (s1 == 0.0 || s1 == 1.0 || t1 == 0 || t1 == 1.0)
			//				return 0;
			//			else
			//				return 1;

			return 0;

		}
	}

	public class OrderedList<T extends Comparable<T>> extends LinkedList<T> {

		private static final long serialVersionUID = 1L;

		public boolean orderedAdd(T element) {
			ListIterator<T> itr = listIterator();
			while (true) {
				if (itr.hasNext() == false) {
					itr.add(element);
					return true;
				}

				T elementInList = itr.next();
				if (elementInList.compareTo(element) > 0) {
					itr.previous();
					itr.add(element);
					System.out.println("Adding");
					return true;
				}
			}
		}
	}
}

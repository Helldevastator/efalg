package efalg_task4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;

public class Rooftop {

	static ArrayList<Point> roof;
	static ArrayList<Line> roofLines;

	private static void read() throws FileNotFoundException {
		Scanner in = new Scanner(new File("rooftop_test.in"));
		int size = in.nextInt();

		roof = new ArrayList<>(size);
		roofLines = new ArrayList<>(size - 1);
		for (int i = 0; i < size; i++)
			roof.add(new Point(in.nextDouble(), in.nextDouble()));

		for (int i = 0; i < size - 1; i++) {
			roofLines.add(new Line(roof.get(i), roof.get((i + 1) % size)));
		}

	}

	public static void main(String[] args) throws Exception {
		read();
		double superSize = 1000;

		int size = roof.size();

		double minDiagonal = Double.MAX_VALUE;
		for (int i = 0; i < roof.size(); i++) {
			Point current = roof.get(i);
			Point next = roof.get(i % size);
			double angle = calcAngle(roof.get(size - 2 + i), current, next);
			double currentDiag = 0;

			if (Math.signum(angle) > 0) {
				//clockwise, do it twice
			} else {
				//counter clock wise
				Line l = new Line(current, next);
				l.vector.x *= superSize;
				l.vector.y *= superSize;
				currentDiag = makeRectangle(0, l);
			}

			//is counter clock wise
			//one size
			//twice

		}

	}

	private static double makeRectangle(int pointIndex, Line startLine) {
		Point current;
		Point ps1 = new Point(startLine.start.x + startLine.vector.x, startLine.start.y + startLine.vector.y);
		Point ps3 = new Point(startLine.start.x + startLine.vector.y, startLine.start.y + startLine.vector.x);
		Point ps2 = new Point(ps3.x + startLine.vector.x, ps3.y + startLine.vector.y);

		Line[] rect = new Line[4];
		rect[0] = startLine;
		rect[1] = new Line(startLine.start, ps1);
		rect[2] = new Line(ps3, ps2);
		rect[3] = new Line(startLine.start, ps3);
		boolean foundRec = false;

		while (!foundRec) {

			for (int i = 0; i < roofLines.size(); i++) {
				for (int j = 0; j < 4; j++) {
					Line line = roofLines.get(i);
					Point intersection = new Point(0, 0);
					int inters = line.intersects(rect[j], intersection);
					if (inters == -1 && !Double.isInfinite(intersection.x)) {
						//calculate shrink factor
						Point p1 = line.start;
						Point p2 = new Point(line.start.x + line.vector.x, line.start.y + line.vector.y);
						double factor = 0;

						if (p1.mdist(rect[0].start) > p2.mdist(rect[0].start)) {
							factor = p1.y - intersection.y > 0 ? p1.y - intersection.y : p1.x - intersection.x;
						} else {
							factor = p2.y - intersection.y > 0 ? p2.y - intersection.y : p2.x - intersection.x;
						}

						shrink(rect, factor);

						//retry
						j = 5;
						i = roofLines.size();
					}
				}
			}
			foundRec = true;
		}

		//calculate 
		return 0;
	}

	private static void shrink(Line[] rec, double factor) {
		for (int i = 0; i < 4; i++) {
			rec[0].vector.x -= factor;
			rec[0].vector.y -= factor;
			rec[1].start.x = rec[0].start.x + rec[0].vector.x;
			rec[1].start.y = rec[0].start.y + rec[0].vector.y;
			rec[2].start.x = rec[3].start.x + rec[3].vector.x;
			rec[2].start.y = rec[3].start.y + rec[3].vector.y;
		}

	}

	public static double calcAngle(Point a, Point b, Point c) {
		double alpha = Math.atan2(a.x - b.x, a.y - b.y);
		double beta = Math.atan2(c.x - b.x, c.y - b.y);
		return beta - alpha;
	}

	public static class Line {
		Point start;
		Point vector;
		boolean isDiagonal;

		public Line(Point start, Point end) {
			this.start = start;
			this.vector = new Point(end.x - start.x, end.y - start.y);
		}

		public double magnitude() {
			return Math.sqrt(vector.x * vector.x + vector.y * vector.y);
		}

		public int intersects(Line l, Point intersection) {
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

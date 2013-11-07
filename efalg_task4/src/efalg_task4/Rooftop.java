package efalg_task4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Rooftop {

	static ArrayList<Point> roof;
	static ArrayList<Line> roofLines;

	private static void read() throws FileNotFoundException {
		Scanner in = new Scanner(new File("rooftop_test.in"));
		int size = in.nextInt();

		roof = new ArrayList<>(size);

		for (int i = 0; i < size; i++)
			roof.add(new Point(in.nextDouble(), in.nextDouble()));

		roofLines = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			roofLines.add(new Line(roof.get(i), roof.get((i + 1) % size)));
		}

	}

	public static void main(String[] args) throws Exception {
		read();
		double superSize = 1000;

		int size = roof.size();

		Line l1 = new Line(new Point(0, 0), new Point(5, 0));
		Line l2 = new Line(new Point(2, 2), new Point(2, -2));
		Point inters = new Point(0, 0);
		l1.intersects(l2, inters);

		double minDiagonal = Double.MAX_VALUE;
		for (int i = 0; i < roof.size(); i++) {
			Point current = roof.get(i);
			Point next = roof.get((i + 1) % size);
			double angle = calcAngle(roof.get((size - 2 + i) % size), current, next);
			double currentDiag = 0;

			if (Math.signum(angle) > 0) {
				//counter clock wise
				Line l = new Line(current, next);

				currentDiag = makeMaxRectangle(l);

			} else {
				//if clockwise, shift and do it twice
				System.out.println("clockwise");
				//l.vector.x *= superSize;
				//l.vector.y *= superSize;
			}

			if (minDiagonal > currentDiag)
				minDiagonal = currentDiag;

		}

		System.out.println(minDiagonal);

	}

	private static double makeMaxRectangle(Line startLine) {
		Point current;
		Point ps1 = new Point(startLine.start.x + startLine.vector.x, startLine.start.y + startLine.vector.y);
		Point ps3 = new Point(startLine.start.x + -1 * startLine.vector.y, startLine.start.y + startLine.vector.x);
		Point ps2 = new Point(ps3.x + startLine.vector.x, ps3.y + startLine.vector.y);

		Line[] rect = new Line[4];
		rect[0] = startLine;
		rect[1] = new Line(ps1, ps2);
		rect[2] = new Line(ps3, ps2);
		rect[3] = new Line(startLine.start, ps3);
		boolean foundRec = false;

		while (!foundRec) {
			for (int i = 0; i < roofLines.size(); i++) {
				Line line = roofLines.get(i);

				for (int j = 0; j < 4; j++) {
					Point intersection = new Point(0, 0);
					int inters = line.intersects(rect[j], intersection);
					if (inters > 0 && !Double.isNaN(intersection.x)) {
						shrink(rect, line, intersection);
						//retry
						j = 5;
						i = roofLines.size();
					}

					//shrink if square touches and no other side overlays this line
					if (inters == 0) {
						boolean overlay = false;
						Point over = new Point(0, 0);
						for (int k = 0; k < 4; k++) {
							line.intersects(rect[k], over);
							if (Double.isNaN(over.x) || Double.isNaN(over.y)) {
								overlay = true;
								break;
							}
						}

						if (!overlay) {
							shrink(rect, line, intersection);
							//retry
							j = 5;
							i = roofLines.size();
						}
					}

				}
			}
			foundRec = true;
		}

		//calculate diagonal
		rect[0].vector.x += rect[1].vector.x;
		rect[0].vector.y += rect[1].vector.y;
		return rect[0].magnitude();
	}

	private static void shrink(Line[] rect, Line line, Point intersection) {
		//calculate shrink factor
		Point p1 = line.start;
		Point p2 = new Point(line.start.x + line.vector.x, line.start.y + line.vector.y);
		double factor = 0;

		if (p1.mdist(rect[0].start) > p2.mdist(rect[0].start)) {
			factor = p1.y - intersection.y > 0 ? p1.y - intersection.y : p1.x - intersection.x;
		} else {
			factor = p2.y - intersection.y > 0 ? p2.y - intersection.y : p2.x - intersection.x;
		}

		for (int i = 0; i < 4; i++) {
			rect[i].vector.x = rect[i].vector.x == 0 ? 0 : rect[i].vector.x - factor;
			rect[i].vector.y -= rect[i].vector.y == 0 ? 0 : rect[i].vector.y - factor;
		}

		rect[1].start.x = rect[0].start.x + rect[0].vector.x;
		rect[1].start.y = rect[0].start.y + rect[0].vector.y;
		rect[2].start.x = rect[3].start.x + rect[3].vector.x;
		rect[2].start.y = rect[3].start.y + rect[3].vector.y;

	}

	public static double calcAngle(Point a, Point b, Point c) {
		double alpha = Math.atan2(a.x - b.x, a.y - b.y);
		double beta = Math.atan2(c.x - b.x, c.y - b.y);
		return beta - alpha;
	}

	public static class Line {
		Point start;
		Point vector;

		public Line(Point start, Point end) {
			this.start = start;
			this.vector = new Point(end.x - start.x, end.y - start.y);
		}

		public double magnitude() {
			return Math.sqrt(vector.x * vector.x + vector.y * vector.y);
		}

		public int intersects(Line l, Point intersection) {
			//						double sx = p2.x - p1.x;	//this.vector
			//						double sy = p2.y - p1.y;
			//						double tx = -(l.p2.x - l.p1.x); //l.vector
			//						double ty = -(l.p2.y - l.p1.y);

			/*
			 * double tx = -l.vector.x; double ty = -l.vector.y;
			 * 
			 * double sm = start.y - start.x; double tm = l.start.y - l.start.x;
			 * 
			 * double main = vector.x * ty - vector.y * tx; double minort = tx *
			 * tm - ty * sm; double minors = vector.x * tm - vector.y * sm;
			 * 
			 * double s1 = -minort / main; double t1 = minors / main;
			 * 
			 * intersection.x = start.x + vector.x * s1; intersection.y =
			 * start.y + vector.y * s1;
			 * 
			 * if (s1 > 1.0 || s1 < 0.0 || t1 > 1.0 || t1 < 0.0) return -1; else
			 * if (s1 == 0.0 || s1 == 1.0 || t1 == 0 || t1 == 1.0) return 0;
			 * else return 1;
			 */

			Point p1 = this.start;
			Point p2 = new Point(start.x + vector.x, start.y + vector.y);
			Point p3 = l.start;
			Point p4 = new Point(l.start.x + l.vector.x, l.start.y + l.vector.y);
			double x = (p1.x * p2.y - p1.y * p2.x) * (p3.x - p4.x) - (p1.x - p2.x) * (p3.x * p4.y - p3.y * p4.x);
			double y = (p1.x * p2.y - p1.y * p2.x) * (p3.y - p4.y) - (p1.y - p2.y) * (p3.x * p4.y - p3.y * p4.x);
			double divisor = (p1.x - p2.x) * (p3.y - p4.y) - (p1.y - p2.y) * (p3.x - p4.x);
			intersection.x = x / divisor;
			intersection.y = y / divisor;
			return new Point(x / divisor, y / divisor);

		}
	}

}

package efalg_task4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
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

		LinkedList<Point> cornerPoints = null;
		ArrayList<Line> roof = new ArrayList<>();
		ArrayList<Line> preliminaryRoof = new ArrayList<>();

		while (cornerPoints.size() > 0) {
			Point p = cornerPoints.pop();

			//if is building point

			//if is roofPoint

		}

		//look for highest roof
		for (int i = 0; i < roof.size(); i++) {

		}

	}

	private static void checkNewLine(Line l, ArrayList<Line> roof, ArrayList<Line> preliminaryRoof, LinkedList<Point> cornerPoints) {
		boolean foundIntersection = false;
		//check for intersection
		for (int i = 0; i < preliminaryRoof.size(); i++) {
			Line other = preliminaryRoof.get(i);
			int intersects = other.intersects(l);
			if (intersects == 1) {
				Point intersection = null;

				//get intersection point
				//set endpoint to intersection
				//add both rooflines to roof
				//add intersection point
				cornerPoints.add(intersection);
				foundIntersection = true;
				break;
			}

			//touches
			if (intersects == 0) {
				//remove found roofLine

				//add l to roof
				foundIntersection = true;
				break;
			}
		}

		//needed?
		boolean foundTouch = false;
		if (!foundIntersection) {
			for (int i = 0; i < roof.size(); i++) {
				int touches = roof.get(i).intersects(l);
				if (touches == 0) {
					foundTouch = true;

					//set endpoint to touchpoint
					break;
				}
			}
		}
	}

	private static Line newRoof(Point before, Point current, Point after) {

		return null;
	}

	public static class Line {
		Point start;
		Point end;
		boolean isDiagonal;

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
}

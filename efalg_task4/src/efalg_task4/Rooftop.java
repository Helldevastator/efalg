package efalg_task4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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
		/*
		 * LinkedList<Corner> cornerPoints = null; ArrayList<Line> roof = new
		 * ArrayList<>();
		 * 
		 * while (cornerPoints.size() > 0) { // add roof line Corner p =
		 * cornerPoints.pop(); Line r = newRoof(p);
		 * 
		 * // check if new roof line intersection with other rooflines for (int
		 * i = 0; i < roof.size(); i++) { int intersects =
		 * roof.get(i).intersects(r); if (intersects >= 0) { if (intersects ==
		 * 0) { //if touches, just set end point of this roof } else { //set
		 * other roof line to cross point //insert cross point
		 * cornerPoints.add(); //break loop } } } }
		 */

		//look for highest roof

	}

	private static Line newRoof(Point before, Point current, Point after) {

		return null;
	}

	public static class Line {
		Point p1;
		Point p2;
		boolean isDiagonal;
	}

	public static class Intersection {
		List<Line> lines;
		Point current;

	}
}

package ch.fhnw.efalg.schwammberger.jonas.uebung4;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Algorithm to calculate the smallest enclosing rectangle from a set of points.
 * 
 * @author Jon
 */
public class SmallestRectangle {
	private static final double piHalf = Math.PI / 2;

	/**
	 * Calculates the smallest rectangle of the given points
	 * 
	 * @return Line array, [0] + [2] and [1] + [3] are parallel.
	 */
	public static Line[] calculate(List<Point> points) {
		ModifiedGraham graham = new ModifiedGraham();
		ArrayList<Point> convexHull = graham.calculateMinConvexHull(points);

		if (convexHull.size() >= 4) {
			return handleGeneralCase(convexHull);
		} else {
			return handleTriangle(convexHull);
		}
	}

	/**
	 * Handle Triangle case, main algorithm doesn't work with less than 4
	 * points. Luckily, this is very easy.
	 * 
	 * @param convexHull
	 * @return
	 */
	private static Line[] handleTriangle(ArrayList<Point> convexHull) {

		//find biggest side
		Vector[] sides = new Vector[3];
		for(int i = 0; i < 3; i++) {
			sides = new Vector()
		}
		return null;
	}

	/**
	 * Handle the general case for convexHull >= 4
	 * 
	 * @param convexHull
	 * @return
	 */
	private static Line[] handleGeneralCase(ArrayList<Point> convexHull) {
		Line[] minRectangle = new Line[4];
		Line[] currentRectangle = new Line[4];
		int[] hullIndices = { 0, findMin(true, convexHull), findMax(false, convexHull), findMax(true, convexHull) };
		int size = convexHull.size();
		double minArea;
		double totalAngle = 0;

		//initialize
		minRectangle[0] = new Line(convexHull.get(hullIndices[0]), new Vector(1, 0)); // lower horizontal line
		minRectangle[1] = new Line(convexHull.get(hullIndices[1]), new Vector(0, -1)); // left vertical line
		minRectangle[2] = new Line(convexHull.get(hullIndices[2]), new Vector(-1, 0)); // top horizontal line
		minRectangle[3] = new Line(convexHull.get(hullIndices[3]), new Vector(0, 1)); // right vertical line
		minArea = Line.calculateRectangleArea(minRectangle);

		// copy
		for (int i = 0; i < 4; i++)
			currentRectangle[i] = new Line(minRectangle[i]);

		//find minimum
		while (totalAngle < Math.PI / 2) {
			// find line with smallest turning angle
			double smallestAngle = Double.MAX_VALUE;
			int index = 0;
			for (int i = 0; i < 4; i++) {
				int nextIndex = (hullIndices[i] + 1) % size;
				double currentAngle = currentRectangle[i].calculateAngle(convexHull.get(nextIndex));

				if (Math.abs(smallestAngle) > Math.abs(currentAngle)) {
					smallestAngle = currentAngle;
					index = i;
				}
			}

			// turn smallest line
			hullIndices[index] = (hullIndices[index] + 1) % size;
			currentRectangle[index].rotateLine(convexHull.get(hullIndices[index]));

			// turn other lines
			for (int i = 0; i < 4; i++) {
				if (i != index)
					currentRectangle[i].rotateLine(smallestAngle);
			}

			totalAngle += Math.abs(smallestAngle);
			double currentArea = Line.calculateRectangleArea(currentRectangle);

			if (currentArea < minArea) {
				minArea = currentArea;

				// copy to minimum
				for (int i = 0; i < 4; i++)
					minRectangle[i] = new Line(currentRectangle[i]);
			}
		}

		for (int i = 0; i < minRectangle.length; i++) {
			System.out.println(minRectangle[i].toString());
		}

		return minRectangle;
	}

	/**
	 * find minimum in convex hull
	 * 
	 * @param useX
	 *            true if minimum should be searched in y space
	 * @return index of minimum
	 */
	private static int findMin(boolean useX, ArrayList<Point> convexHull) {
		int min = Integer.MAX_VALUE;
		int index = -1;
		for (int i = 0; i < convexHull.size(); i++) {
			int val = useX ? convexHull.get(i).x : convexHull.get(i).y;
			if (min > val) {
				min = val;
				index = i;
			}
		}
		return index;
	}

	/**
	 * find max in convex hull
	 * 
	 * @param useX
	 *            true if maximum should be searched in X space
	 * @return index of maximum
	 */
	private static int findMax(boolean useX, ArrayList<Point> convexHull) {
		int max = 0;
		int index = -1;
		for (int i = 0; i < convexHull.size(); i++) {
			int val = useX ? convexHull.get(i).x : convexHull.get(i).y;
			if (max < val) {
				max = val;
				index = i;
			}
		}
		return index;
	}

	/**
	 * Debug main function,
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ArrayList<Point> points = new ArrayList<>();
		points.add(new Point(0, 5));
		points.add(new Point(5, 5));
		points.add(new Point(5, 0));
		points.add(new Point(10, 5));
		points.add(new Point(5, 10));

		Line[] l = SmallestRectangle.calculate(points);
		Point[] bla = Line.calculateVertices(l);
		for (int i = 0; i < 4; i++)
			System.out.println(bla[i]);

		System.out.println("------------------------");
		Line l1 = new Line(new Point2D.Double(0, 0), new Vector(0, 5));
		Line l2 = new Line(new Point2D.Double(0, 6), new Vector(5, 0));
		Point p = l2.calculateIntersectionPoint(l1);
		System.out.println(p);

	}

}

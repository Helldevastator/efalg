package ch.fhnw.efalg.schwammberger.jonas.uebung4;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Algorithm to calculate the smallest enclosing rectangle from a set of points.
 * 
 * @author Jon
 * 
 */
public class SmallestRectangle {
	private final ArrayList<Point> convexHull;
	private final double piHalf = Math.PI / 2;

	public SmallestRectangle(List<Point> points) {
		ModifiedGraham graham = new ModifiedGraham();
		this.convexHull = graham.calculateMinConvexHull(points);
	}

	public Line[] calculateSmallestRectangle() {
		Line[] minRectangle = new Line[4];
		Line[] currentRectangle = new Line[4];
		int[] hullIndices = { 0, this.findMin(true), this.findMax(false), this.findMax(true) };
		int size = this.convexHull.size();
		double minArea;
		double totalAngle = 0;

		minRectangle[0] = new Line(convexHull.get(hullIndices[0]), new Vector(1, 0)); // lower horizontal line
		minRectangle[1] = new Line(convexHull.get(hullIndices[1]), new Vector(0, -1)); // left vertical line
		minRectangle[2] = new Line(convexHull.get(hullIndices[2]), new Vector(-1, 0)); // top horizontal line
		minRectangle[3] = new Line(convexHull.get(hullIndices[3]), new Vector(0, 1)); // right vertical line

		minArea = Line.calculateRectangleArea(minRectangle);

		// copy
		for (int i = 0; i < 4; i++)
			currentRectangle[i] = new Line(minRectangle[i]);

		// set minimum
		while (totalAngle < Math.PI / 2) {
			// find line with smallest turning angle
			double smallestAngle = Double.MAX_VALUE;
			int index = 0;
			for (int i = 0; i < 4; i++) {
				int nextIndex = (hullIndices[i] + 1) % size;
				double currentAngle = currentRectangle[i].calculateAngle(convexHull.get(nextIndex));
				System.out.println(Math.toDegrees(currentAngle));
				System.out.println(convexHull.get(nextIndex));
				System.out.println(currentRectangle[i].toString());
				System.out.println("--------------------------------");
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

			totalAngle += smallestAngle;
			double currentArea = Line.calculateRectangleArea(currentRectangle);

			if (currentArea < minArea) {
				minArea = currentArea;

				// copy to minimum
				for (int i = 0; i < 4; i++)
					minRectangle[i] = new Line(currentRectangle[i]);
			}
		}

		return minRectangle;
	}

	/**
	 * 
	 * @param useX
	 * @return
	 */
	private int findMin(boolean useX) {
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
	 * 
	 * @param useX
	 * @return
	 */
	private int findMax(boolean useX) {
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

	public static void main(String[] args) {
		//Line l = new Line(new Point(0, 0), new Vector(1, 0));
		//l.calculateAngle(new Point(0, 5));
		ArrayList<Point> points = new ArrayList<>();
		points.add(new Point(0, 5));
		points.add(new Point(5, 5));
		points.add(new Point(5, 0));
		points.add(new Point(10, 5));
		points.add(new Point(5, 10));
		SmallestRectangle rec = new SmallestRectangle(points);

		Line[] l = rec.calculateSmallestRectangle();
	}

}

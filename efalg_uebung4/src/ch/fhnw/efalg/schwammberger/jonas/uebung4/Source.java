package ch.fhnw.efalg.schwammberger.jonas.uebung4;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Source {

	public static void main(String[] args) {
		ArrayList<Point> points = new ArrayList<>();
		points.add(new Point(0, 0));
		points.add(new Point(1, 0));
		points.add(new Point(2, 0));
		points.add(new Point(2, 2));

		ModifiedGraham g = new ModifiedGraham();
		List<Point> out = g.calculateMinConvexHull(points);

		Iterator<Point> it = out.iterator();
		while (it.hasNext()) {
			Point p = it.next();
			System.out.println(p.x + " " + p.y);
		}
	}
}

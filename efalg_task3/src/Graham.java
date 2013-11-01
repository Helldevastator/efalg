

/*  -----------------------------------------------------------------
 *  -------  Effiziente Algorithmen                  ----------------
 *  -------                                          ----------------
 *  -------  implements the Graham algorithm         ----------------
 *  -------  for the calculation of the convex       ----------------
 *  -------  hull of points in the xy-plane          ----------------
 *  -------                                          ----------------
 *  -------  @author  Manfred Vogel                  ----------------
 *  -------  2013 october                            ----------------
 *  -----------------------------------------------------------------
 */

public class Graham {

	protected static Point[] p;
	protected static int n;
	protected static int h;

	public static int computeHull(Point[] p) {
		setPoints(p);
		if (n < 3) {
			return n;
		}
		graham();
		return h;
	}

	private static void graham() {
		exchange(0, indexOfLowestPoint());
		Point pl = new Point(p[0]);
		makeRelTo(pl);
		sort();
		makeRelTo(pl.reversed());
		int i = 3, k = 3;
		while (k < n) {
			exchange(i, k);
			while (!isConvex(i - 1)) {
				exchange(i - 1, i--);
			}
			k++;
			i++;
		}
		h = i;
	}

	private static void sort() {
		quicksort(1, n - 1); // without point 0
	}

	protected static void quicksort(int lo, int hi) {
		int i = lo, j = hi;
		Point q = p[(lo + hi) / 2];
		while (i <= j) {
			while (p[i].isLess(q)) {
				i++;
			}
			while (q.isLess(p[j])) {
				j--;
			}
			if (i <= j) {
				exchange(i++, j--);
			}
		}
		if (lo < j) {
			quicksort(lo, j);
		}
		if (i < hi) {
			quicksort(i, hi);
		}
	}

	public static void setPoints(Point[] p0) {
		p = p0;
		n = p.length;
		h = 0;
	}

	protected static void exchange(int i, int j) {
		Point t = p[i];
		p[i] = p[j];
		p[j] = t;
	}

	protected static void makeRelTo(Point p0) {
		int i;
		Point p1 = new Point(p0); // necessary, because p0 migth be in p[]
		for (i = 0; i < n; i++) {
			p[i].makeRelTo(p1);
		}
	}

	protected static int indexOfLowestPoint() {
		int i, min = 0;
		for (i = 1; i < n; i++) {
			if (p[i].y < p[min].y || p[i].y == p[min].y && p[i].x < p[min].x) {
				min = i;
			}
		}
		return min;
	}

	protected static boolean isConvex(int i) {
		return p[i].isConvex(p[i - 1], p[i + 1]);
	}
}
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Rooftop {

	private static ArrayList<Point> points;
	private static int maxX = Integer.MIN_VALUE;
	private static int minX = Integer.MAX_VALUE;
	private static int maxY = Integer.MIN_VALUE;
	private static int minY = Integer.MAX_VALUE;
	private static boolean[][] form;

	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(new File("rooftop.in"));
		PrintWriter out = new PrintWriter("rooftop.out");
		int size = in.nextInt();

		points = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			int x = in.nextInt();
			int y = in.nextInt();
			if (x < minX)
				minX = x;
			if (x > maxX)
				maxX = x;
			if (y < minY)
				minY = y;
			if (y > maxY)
				maxY = y;
			points.add(new Point(x, y));
		}
		form = new boolean[Math.abs(maxX) + Math.abs(minX)][Math.abs(maxY) + Math.abs(minY)];

		//System.out.println(contains(new Point(8, 9)) ? "YES": "NO");
		buildForm();
		long start = System.nanoTime();
		double result = calculateDemsSquares() / 2;
		//System.out.println(System.nanoTime() - start);
		//System.out.println(result);
		out.println(result);
		out.close();
	}

	public static double calculateDemsSquares() {
		double result = 0;
		for (int x = 0; x < form.length; x++) {
			for (int y = 0; y < form[0].length; y++) {
				if (form[x][y]) {
					int size = 0;
					while (checkDiagonal(x, y, size + 1))
						size++;
					size++;
					if (size > result)
						result = size;
					if (form.length - x < result)
						return result;
					if (form[0].length - y < result)
						y = form[0].length;
				}
			}
		}
		return result;
	}

	public static boolean checkDiagonal(int x, int y, int size) {
		try {
			for (int i = x; i <= size + x; i++)
				if (!form[i][y + size])
					return false;

			for (int i = y; i < size + y; i++)
				if (!form[x + size][i])
					return false;
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}

		return true;
	}

	public static void buildForm() {
		for (int i = 0; i < form.length; i++) {
			for (int j = 0; j < form[0].length; j++) {
				form[i][j] = contains(new Point(i - Math.abs(minX), j - Math.abs(minY)));
			}
		}
	}

	public static boolean contains(Point test) {
		boolean result = false;
		for (int i = 0, j = points.size() - 1; i < points.size(); j = i++) {
			if (points.get(i).y > test.y != points.get(j).y > test.y
					&& test.x < (points.get(j).x - points.get(i).x) * (test.y - points.get(i).y) / (points.get(j).y - points.get(i).y) + points.get(i).x) {
				result = !result;
			}
		}
		return result;
	}

	public static class Point {
		int x;
		int y;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

	}

}
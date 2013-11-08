import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.PriorityQueue;

public class shopping {
	static int[][] table;
	static int width;
	static int height;
	static int max = Integer.MAX_VALUE - 1000000000;

	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(new BufferedInputStream(new FileInputStream(new File("shopping.in")), 100000)));
		PrintWriter out = new PrintWriter("shopping.out");
		String line = in.readLine();
		String[] parts = line.split(" ");
		width = Integer.parseInt(parts[0]);
		height = Integer.parseInt(parts[1]);
		table = new int[width][height];

		int startW = 0;
		int startH = 0;
		int endW = 0;
		int endH = 0;
		vertex[][] vertices = new vertex[width][height];

		for (int i = 0; i < height; ++i) {
			line = in.readLine();
			for (int j = 0; j < width; ++j) {
				char c = line.charAt(j);
				int cost = 0;
				if (c == 'S') {
					startW = j;
					startH = i;

				} else if (c == 'X') {
					cost = max;
				} else if (c == 'D') {
					endW = j;
					endH = i;
				} else {
					cost = Character.getNumericValue(c);
				}

				table[j][i] = cost;
				vertices[j][i] = new vertex(j, i);
			}
		}

		//dijkstra
		vertex start = vertices[startW][startH];
		start.distance = 0;

		PriorityQueue<vertex> pq = new PriorityQueue<>(width * height);
		pq.add(start);

		boolean foundEnd = false;
		int distance = 0;
		while (!pq.isEmpty() && !foundEnd) {
			vertex current = pq.poll();
			current.visited = true;

			if (current.w == endW && current.h == endH) {
				distance = current.distance;
				foundEnd = true;
			}

			for (int i = 0; i < 4; i++) {
				int nextW = current.w + stupidFunction1(i);
				int nextH = current.h + stupidFunction2(i);

				if (check(nextW, nextH)) {
					int acc = current.distance + table[nextW][nextH];
					if (acc < vertices[nextW][nextH].distance && !vertices[nextW][nextH].visited) {
						vertices[nextW][nextH].distance = acc;
						//						vertices[nextW][nextH].previous = current;
						pq.add(vertices[nextW][nextH]);
					}
				}
			}
		}

		out.println(distance);
		out.close();
	}

	private static boolean check(int w, int h) {
		return w >= 0 && w < width && h >= 0 && h < height;
	}

	//height
	private static int stupidFunction2(int i) {
		int out = 0;
		switch (i) {
		case 0:
			out = 0;
			break;
		case 1:
			out = 1;
			break;
		case 2:
			out = 0;
			break;
		case 3:
			out = -1;
			break;
		}
		return out;
	}

	private static int stupidFunction1(int i) {
		int out = 0;
		switch (i) {
		case 0:
			out = -1;
			break;
		case 1:
			out = 0;
			break;
		case 2:
			out = 1;
			break;
		case 3:
			out = 0;
			break;
		}
		return out;
	}

	public static class vertex implements Comparable<vertex> {
		int distance = max;
		boolean visited = false;
		int w;
		int h;

		public vertex(int w, int h) {
			this.w = w;
			this.h = h;
		}

		@Override
		public int compareTo(vertex o) {
			return Integer.compare(distance, o.distance);
		}
	}
}

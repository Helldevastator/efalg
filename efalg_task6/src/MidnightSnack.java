import java.io.File;
import java.io.PrintWriter;
import java.util.PriorityQueue;
import java.util.Scanner;

public class MidnightSnack {

	static Field[] fields;
	static boolean[][] walls;
	static int maxWalls;
	static int max = Integer.MAX_VALUE - 100000000;
	static int width;
	static int height;

	static int startW;
	static int startH;
	static int endW;
	static int endH;

	public static void main(String[] args) throws Exception {
		Scanner in = new Scanner(new File("midnight.in"));
		PrintWriter out = new PrintWriter("midnight.out");

		width = in.nextInt();
		height = in.nextInt();
		maxWalls = in.nextInt();
		walls = new boolean[width][height];
		fields = new Field[maxWalls + 1];

		for (int i = 0; i < maxWalls + 1; i++) {
			fields[i] = new Field();
		}

		String line = in.nextLine();

		for (int i = 0; i < height; ++i) {
			line = in.nextLine();
			for (int j = 0; j < width; ++j) {
				char c = line.charAt(j);
				int cost = 0;
				if (c == 'B') {
					startW = j;
					startH = i;
					walls[j][i] = false;
				} else if (c == '#') {
					walls[j][i] = true;
				} else if (c == '.') {
					walls[j][i] = false;
				} else if (c == 'K') {
					endW = j;
					endH = i;
					walls[j][i] = false;
				} else {
					//cost = Character.getNumericValue(c);
				}

			}
		}

		//dijkstra
		PriorityQueue<Node> pq = new PriorityQueue<Node>();
		Node start = new Node(0, 0, startW, startH);
		pq.add(start);
		int steps = -1;
		while (!pq.isEmpty()) {
			Node current = pq.poll();

			if (fields[current.level].visited[current.w][current.h])
				continue;

			fields[current.level].visited[current.w][current.h] = true;

			if (current.w == endW && current.h == endH) {
				steps = current.cost;
				break;
			}

			for (int i = 0; i < 4; i++) {
				int nextW = current.w + stupidFunction1(i);
				int nextH = current.h + stupidFunction2(i);

				if (check(nextW, nextH)) {
					if (walls[nextW][nextH]) {
						//check if visited

						if (current.level < maxWalls) {
							if (!fields[current.level + 1].visited[nextW][nextH]) {
								Node next = new Node(current.cost + 1, current.level + 1, nextW, nextH);
								pq.add(next);
							}
						}
					} else {
						//not a wall, normal dijkstra
						if (!fields[current.level].visited[nextW][nextH]) {

							Node next = new Node(current.cost + 1, current.level, nextW, nextH);
							pq.add(next);
						}
					}

				}
			}
		}

		out.println(steps);
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

	public static class Node implements Comparable<Node> {
		int cost;
		int level;
		int w;
		int h;

		public Node(int cost, int walls, int x, int y) {
			this.cost = cost;
			this.level = walls;
			this.w = x;
			this.h = y;
		}

		@Override
		public int compareTo(Node o) {
			return Integer.compare(cost, o.cost);
		}
	}

	public static class Field {
		boolean visited[][];

		public Field() {
			visited = new boolean[width][height];
		}
	}

}

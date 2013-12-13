import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class Babylon {
	static int number;
	static Quader[] myQuaders;
	static int[] cache;

	public static void main(String[] args) throws Exception {
		Scanner in = new Scanner(new File("babylon.in"));
		PrintWriter out = new PrintWriter("babylon.out");

		number = in.nextInt();
		myQuaders = new Quader[number * 3];

		cache = new int[number * 3];
		int index = 0;

		for (int i = 0; i < number; i++) {
			int x = in.nextInt();
			int y = in.nextInt();
			int z = in.nextInt();

			cache[index] = -1;
			myQuaders[index++] = new Quader(x, y, z);
			cache[index] = -1;
			myQuaders[index++] = new Quader(y, z, x);
			cache[index] = -1;
			myQuaders[index++] = new Quader(z, x, y);
		}
		number = number * 3;

		int minMax = 0;
		int startIndex = -1;
		for (int i = 0; i < number; i++) {
			if (myQuaders[i].min > minMax) {
				minMax = myQuaders[i].min;
				startIndex = i;
			}
		}

		int height = solve(startIndex, myQuaders[startIndex].h);
		out.println(height);
		out.close();
	}

	public static int solve(int blockIndex, int height) {
		int maxHeight = 0;
		Quader quader = myQuaders[blockIndex];
		for (int i = 0; i < number; i++) {
			Quader current = myQuaders[i];

			if (quader.min > current.min && quader.max > current.max) {
				if (cache[i] == -1) {
					cache[i] = solve(i, current.h);
				}

				maxHeight = cache[i] > maxHeight ? cache[i] : maxHeight;
			}
		}

		return height + maxHeight;

	}

	static class Quader {
		int min;
		int max;
		int h;

		public Quader(int x, int y, int h) {
			if (x < y) {
				this.min = x;
				this.max = y;
			} else {
				this.max = x;
				this.min = y;
			}

			this.h = h;
		}
	}
}
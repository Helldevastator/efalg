import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class Underground {

	static int vertCount;
	static int edgeCount;
	static int queryCount;

	public static void main(String[] args) throws Exception {
		Scanner in = new Scanner(new File("underground.in"));
		PrintWriter out = new PrintWriter("underground.out");
		int colorCount = 0;
		vertCount = in.nextInt();
		edgeCount = in.nextInt();
		queryCount = in.nextInt();

		int[] vertex = new int[vertCount];

		for (int i = 0; i < edgeCount; i++) {
			int v1 = in.nextInt() - 1;
			int v2 = in.nextInt() - 1;

			if (vertex[v1] == vertex[v2] && vertex[v1] != 0) {
				//nichts
			} else if (vertex[v1] == 0 && vertex[v2] == 0) {
				colorCount++;
				vertex[v1] = colorCount;
				vertex[v2] = colorCount;
			} else if (vertex[v1] == 0) {
				vertex[v1] = vertex[v2];
			} else if (vertex[v2] == 0) {
				vertex[v2] = vertex[v1];
			} else {
				//bad
				if (vertex[v1] != vertex[v2]) {
					int colorOld = vertex[v1] < vertex[v2] ? vertex[v2] : vertex[v1];
					int color = vertex[v1] < vertex[v2] ? vertex[v1] : vertex[v2];

					for (int j = 0; j < vertCount; j++)
						if (vertex[j] == colorOld)
							vertex[j] = color;
				}
			}
		}

		for (int i = 0; i < queryCount; i++) {
			int q1 = in.nextInt() - 1;
			int q2 = in.nextInt() - 1;
			if (vertex[q1] == vertex[q2])
				out.println('Y');
			else
				out.println('N');
		}
		out.close();
	}

}

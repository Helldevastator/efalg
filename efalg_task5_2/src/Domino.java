import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Domino {

	public static void main(String[] args) throws Exception {
		Scanner in = new Scanner(new File("domino.in"));
		
		PrintWriter out = new PrintWriter("domino.out");

		int number = in.nextInt();
		Node[] allN = new Node[number+1];
		
		for(int i = 0; i < number;i++) {
			int a = in.nextInt();
			int b = in.nextInt();
			
			if
		}
		//your code here
		//int a=in.nextInt();
		//out.println("solution");

		out.close();
	}

	public static class Node {
		ArrayList<Node> conn;
		int nr;
		boolean visited;

		public Node(int nr) {
			this.nr = nr;
			conn = new ArrayList<>();
		}
	}
}
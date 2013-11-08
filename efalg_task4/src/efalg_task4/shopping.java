package efalg_task4;

import java.io.File;
import java.util.Scanner;

public class shopping {
	static int width;
	static int height;

	public static void read() throws Exception {
		Scanner in = new Scanner(new File("shopping.in"));

		width = in.nextInt();
		height = in.nextInt();

	}

	public static void main(String[] args) throws Exception {

		// solve("shopping.in");
	}
}



import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Streetparade {
	static ArrayList<Integer> carList;
	static LinkedList<Integer> sideStreet;
	
	public static void read() throws Exception {
		Scanner in=new Scanner(new File("streetparade.in"));
		 
		int n = in.nextInt();
		carList = new ArrayList<>(n);
		for(int i = 0; i < n;i++)
			carList.add(in.nextInt());
	}
	
	public static void main(String[] args) throws Exception {
		PrintWriter out=new PrintWriter("streetparade.out");
		carList = new ArrayList<>();
		sideStreet = new LinkedList<>();
		int nextCar = 1;
		int i = 0;
	
		read();
		
		boolean isSolvable = true;
		while(i < carList.size() && isSolvable) {
			Integer car = carList.get(i);
			
			if(car == nextCar)
			{
				//standard case
				i++;
				nextCar++;
			} else if (car > nextCar)
			{
				if(!sideStreet.isEmpty() && sideStreet.peekFirst() == nextCar)
				{
					//side street car is the next car
					sideStreet.pop();
					nextCar++;
				} else {
					//cannot go forward, put in sideStreet, check if a solution is possible
					if(!sideStreet.isEmpty() && car > sideStreet.peekFirst())
						isSolvable = false;
					
					sideStreet.push(car);
					i++;
				}
			}
		}
		
		if(isSolvable)
			out.println("yes");
		else
			out.println("no");
		
		out.close();
	}

}

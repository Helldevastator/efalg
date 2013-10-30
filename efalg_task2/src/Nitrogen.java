

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class Nitrogen {

	
	public static void main(String[] args) throws Exception {
		Scanner in = new Scanner(new File("nitrogen.in"));
		
		int n = in.nextInt();
		double decayRate = in.nextFloat();
		
		double max = 0;
		Bottle[] bottles = new Bottle[n];
		int currentDay = 1;
		int bestDay = 0;
		
		//read
		for(int i = 0; i < n; i++) 
			bottles[i] = new Bottle(in.nextInt(),decayRate);
		
		in.close();
		
		
		for(int i = 0; i < n; i++) {
			double maxToday = 0;
			for(int j = 0; j < currentDay;j++) 
				maxToday += bottles[i].getAmount();
			
			if(max < maxToday) {
				max = maxToday;
				bestDay = currentDay;
			}
			
			//decay
			for(int j = 0; j < currentDay;j++)
				bottles[i].decay();
			
			currentDay++;
		}
		
		System.out.println(max);
		System.out.println(bestDay);
		PrintWriter out = new PrintWriter("nitrogen.out");
		out.println(bestDay);
		out.close();
	}
	
	
	public static class Bottle {
		private double decayRate;
		private double amount;
		
		public Bottle(double content, double decRate) {
			this.decayRate = content / decRate;
			this.amount = content;
		}
		
		public void decay() {
			if(amount > 0)
				this.amount -= this.decayRate;
		}
		
		public double getAmount() {
			return amount > 0 ? this.amount : 0;
		}
	}
}

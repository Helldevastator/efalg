
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Nitro {

	public static void main(String[] args) throws Exception {
		 Scanner in = new Scanner(new File("nitrogen.in"));
		 
         int n = in.nextInt();
         float decayRate = in.nextFloat();
         float[] decay = new float[n];
         float[] amountToday = new float[n];
         float[] sums = new float[n];
         float max = 0;
         int bestDay = 0;
 		
         long currentTime = System.nanoTime();
         
         float sum = 0;
 		 for(int i = 0; i < n; i++) {
 			 int am = in.nextInt();
 			 amountToday[i] = am;
 			 decay[i] = am/decayRate;
 			 sum += am;
 			 sums[i] = sum;
 		 }
 		 
 		 for(int i = 1; i <= n;i++) {
 			 float maxToday = 0;
 			 for(int j = i > decayRate ? i-(int)decayRate: 0; j < i;j++)
 				 maxToday += amountToday[j];
 			
 			 
 			if(max < maxToday) {
 				max = maxToday;
 				bestDay = i;
 			}
 			
 			//decay
 			for(int j = 0; j < i;j++) {
 				if(amountToday[j] > 0) {
 					
 					amountToday[j] -= decay[j];
 				
 					if(amountToday[j] < 0) {
 						amountToday[j] = 0;
 					
 					}
 				}
 			}
 		 }
 		
		PrintWriter out = new PrintWriter("nitrogen.out");
		out.println(bestDay);
		out.close();
	}
	
}

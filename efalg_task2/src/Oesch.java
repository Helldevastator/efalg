import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
 
public final class Oesch {
 
        public static void main(String[] args) throws Exception {
                solve("nitrogen_1.in");
        }
 
        public static void solve(final String inputName)
                        throws FileNotFoundException {
                Scanner in = new Scanner(new File(inputName));
 
                int quantity = in.nextInt();
                double decayRate = in.nextDouble();
                double[] amounts = new double[quantity];
                double[] amountPerDay = new double[quantity];
                double maxAmount = 0;
 
                int day = 1;
                long currentTime = System.nanoTime();
 
                for (int i = 0; i < quantity; i++) {
                        amounts[i] = in.nextInt();
                }
 
                for (int i = 0; i < quantity; ++i) {
                        double currentAmount = 0;
                        for (int j = 0; j <= i; j++) {
                                double decayAmount = (i - j) * (amounts[j] / decayRate);
                                currentAmount += (decayAmount < amounts[j]) ? amounts[j]
                                                - decayAmount : 0;
                        }
                        amountPerDay[i] = currentAmount;
                        if (currentAmount > maxAmount) {
                                day = i + 1;
                                maxAmount = currentAmount;
                        }
 
                }
                for (int i = 0; i < amountPerDay.length; i++) {
					System.out.print(amountPerDay[i] + " ");
				}
                
                System.out.println((System.nanoTime() - currentTime) + "ns, day: "
                                + day + ", amount: " + amountPerDay[day - 1]);
                PrintWriter out = new PrintWriter("nitrogen.out");
                out.println(day);
                out.close();
        }
}
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;


public class FizzBuzz {

	  public static void main(String[] args) throws Exception
	  {
	    Scanner in=new Scanner(new File("fizzbuzz.in"));
	    PrintWriter out=new PrintWriter("fizzbuzz.out");
	    
	    int a=in.nextInt();
	    int b = in.nextInt();
	    for(int i = a; i <= b;i++)
	    {
	    	int mod3 = i % 3;
	    	int mod5 = i % 5;
		    if(mod3 == 0)
		    	out.print("Fizz");
		    
		    if(mod5 == 0)
		    	out.print("Buzz");
		    
		    if(mod3 != 0 && mod5 != 0)
		    	out.print(i);
		    
		    out.println();
	    }
	    
	    out.close();
	  }
}

import java.io.*;
import java.util.*;

public class Stadium
{
	static int[][] field;
	static int stadionWidth;
	static int stadionHeight;
	
  public static void main(String[] args) throws Exception
  {
    PrintWriter out=new PrintWriter("stadion.out");
    BufferedReader in = new BufferedReader(new InputStreamReader(new BufferedInputStream(new FileInputStream(new File("stadion.in")), 100000)));
   
    String l = in.readLine();
    String[] split = l.split(" ");
    stadionWidth = Integer.parseInt(split[0]);
    stadionHeight = Integer.parseInt(split[1]);
    l = in.readLine();
    split = l.split(" ");
    int fieldWidth = Integer.parseInt(split[0]);
    int fieldHeight = Integer.parseInt(split[1]);
    
    //read
    char[] line;
    field = new int[fieldWidth][fieldHeight];
    for(int i = 0; i < fieldWidth;i++) {
    	line = in.readLine().toCharArray();
    	int colIndex = 0;
    	int val = 0;
    	for(int j = 0; j < line.length;j++) {
    		if(line[j] == ' ') {
    			field[i][colIndex++] = val;
    			val = 0;
    		} else if(val > 0) {
    			val = val *10 + Character.getNumericValue(line[j]);
    		} else {
    			val = Character.getNumericValue(line[j]);
    		}
    		
    	}
    	field[i][colIndex] = val;
    	
    }
    
    
    	
    //createSum
    for(int i = 0; i < fieldWidth;i++) {
    	for(int j = 0; j < fieldHeight;j++) {
    		int sum = field[i][j];
    		if( i > 0)
    			sum += field[i-1][j];
    		if( j > 0)
    			sum += field[i][j-1];
    		if(i > 0 && j > 0)
    			sum -= field[i-1][j-1];
    		
    		field[i][j] = sum;
    	}
    }
    
    //find
    int min = Integer.MAX_VALUE;
    for(int i = stadionWidth-1;i < fieldWidth;i++) {
    	for(int j = stadionHeight-1;j < fieldHeight;j++) {
    		int curMin = field[i][j];
    		
    		if(j - stadionWidth >= 0)
    			curMin -= field[i][j-stadionWidth];
    		if(i - stadionHeight >= 0)
    			curMin -= field[i-stadionHeight][j];
    		if(j - stadionWidth >= 0 && i - stadionHeight >= 0)
    			curMin += field[i-stadionHeight][j-stadionWidth];
    		
    		if(curMin < min)
    			min = curMin;
    	}
    }
    
    out.println(min);
    out.close();
  }
}
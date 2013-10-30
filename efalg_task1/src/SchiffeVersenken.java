import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;


public class SchiffeVersenken {

	static int[][] field;
	static int[][] possibilities;
	static int width;
	static int height;
	static int nShips;
	static int total;
	
	public static void read() throws Exception {
		Scanner in=new Scanner(new File("ships.in"));
		width = in.nextInt();
		height = in.nextInt();
		nShips = in.nextInt();
		
		field = new int[width][height];
		possibilities = new int[width][height];
		int shots = in.nextInt();
		for(int i = 0; i < shots;i++)
		{
			int x = in.nextInt()-1;
			int y = in.nextInt()-1;
			field[x][y] = -1;
		}
	}
	
	public static void main(String[] args) throws Exception
	  {
	    
	   
	    
	    read();
	    solveBacktrack(nShips);
	    findBiggest();
	  }
	
	public static void solveBacktrack(int ships) {
		if(ships > 0) {
			for(int i = 0; i < width;i++) {
				for(int j = 0; j < height;j++) {
					if(placeShip(i,j,true)) {
						solveBacktrack(ships-1);
						removeShip(i, j, true);
					} 
					if(placeShip(i,j,false)) {
						solveBacktrack(ships-1);
						removeShip(i,j , false);
					} 
				}
			}
			
			
		} else {
			total++;
			for(int i = 0; i < width;i++) {
				for(int j = 0; j < height;j++) {
					if(field[i][j] == -2) {
						possibilities[i][j]++;
						
					}
				}
			}
		}
	}
	
	public static boolean placeShip(int x, int y, boolean isVert) {
		boolean possible = true;
		if(isVert) {
			for(int i = 0; i < 4;i++)
			{
				if(y+i < height) {
					if(field[x][y+i] < 0)
						possible = false;
				} else {
					possible = false;
				}
			}
		} else {
			for(int i = 0; i < 4;i++)
			{
				if(x+i < width) {
					if(field[x+i][y] < 0)
						possible = false;
				} else {
					possible = false;
				}
			}
		}
		
		if(possible) {
			for(int i = 0; i < 4;i++)
			{
				if(isVert) {
					field[x][y+i] = -2;
				} else {
					field[x+i][y] = -2;
				}
			}
		}
		return possible;
	}
	
	public static void removeShip(int x, int y, boolean isVert) {
		for(int i = 0; i < 4;i++)
		{
			if(isVert) {
				field[x][y+i] = 0;
			} else {
				field[x+i][y] = 0;
			}
		}
	}
	
	public static void findBiggest() throws FileNotFoundException {
		int max = 0;
		int x = 0;
		int y = 0;
		for(int i = 0; i < width;i++) {
			for(int j = 0; j < height;j++) {
				if(possibilities[i][j] > max) {
					max =possibilities[i][j];
					x = i;
					y = j;
				}
			}
		}
		
		 PrintWriter out=new PrintWriter("ships.out");
		 
		 out.print((x+1) + " " + (y+1) +" "+ String.format("%.2f", ((double)max/total)*100)+"%");
		 out.close();
	}
	
}

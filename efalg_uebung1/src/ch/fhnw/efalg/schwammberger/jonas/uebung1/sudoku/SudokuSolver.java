package ch.fhnw.efalg.schwammberger.jonas.uebung1.sudoku;

import java.io.IOException;

public class SudokuSolver {

	private static int solutionCounter = 0;
	
	public static void main(String[] args) {
		try {
			SudokuBoard b = new SudokuBoard("input3.txt");
			solve(b,0,0);
		}
		catch(IOException e) 
		{
			
		}
		System.out.println();
		System.out.println(solutionCounter);
		
	}
	
	/**
	 * 
	 * @param b
	 * @param row
	 * @param col
	 */
	public static void solve(SudokuBoard b,int row, int col) {
		//is a solution?
		
		if(b.isFieldEmpty(row, col)) {
			for(int i = 1; i <= 9;i++) {
				if(b.placeNumber(row,col,i))
				{
					if(b.isSolved())
					{
						solutionCounter++;
						b.printBoard();
						
					} 
					else 
					{
						//solve next field
						findNextRow(b,row,col);
					}
					
					b.removeNumber(row, col);
				}
			}
			
			//all possibilities tried, backtrack
			
		} else {
			findNextRow(b,row,col);
		}
	}
	
	/**
	 * Call solve with the next field
	 * @param b
	 * @param row
	 * @param col
	 */
	public static void findNextRow(SudokuBoard b,int row, int col) {
		//solve next field
		
		int newR = row;
		int newC = col+1;
		
		if(newC >= 9) {
			newC = 0;
			newR++;
		}
		
		if(newR < 9) 
			solve(b,newR,newC);
	}
	
	
}

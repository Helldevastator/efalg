package ch.fhnw.efalg.schwammberger.jonas.uebung1.sudoku;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SudokuBoard {
	private int[][] b = new int[9][9];
	private int filledFields = 0;
	private static final int nFields = 9*9;
	
	public SudokuBoard(String inputFile) throws IOException {
		BufferedReader in = null;
		
		try {
			in = new BufferedReader(new FileReader(inputFile));
			
			String line = null;
			
			//read in rows;
			for(int i = 0; i < 9;i++) {
				line = in.readLine();
				
				//read in a row
				for(int j = 0; j < 9;j++)
				{
					char c = line.charAt(j);
					if(c >= '1' & c <= '9') {
						b[i][j] = Character.getNumericValue(c);
						filledFields++;
					}
				}
			}
		} 
		finally {
			try{in.close();} catch(Exception e) {}
		}
	}
	
	
	/**
	 * @param row row to go through
	 * @param val value to check
	 * @return true if this value doesn't exist in the row
	 */
	private boolean checkRow(int row, int val) {
		for(int i = 0; i < 9;i++)
		{
			if(b[row][i] == val)
				return false;
		}
		
		return true;
	}
	
	/**
	 * @param col column to go through	
	 * @param val value to check
	 * @return true if this value doesn't exist in the column
	 */
	private boolean checkColumn(int col, int val) {
		for(int i = 0; i < 9;i++)
		{
			if(b[i][col] == val)
				return false;
		}
		
		return true;
	}
	
	/**
	 * @param row row in which the value should be placed
	 * @param col column in which the value should be placed
	 * @param val value
	 * @return true if value doesn't exist in square
	 */
	private boolean checkSquare(int row,int col,int val) {
		int rowStart = (-1)*(row % 3);
		int colStart = (-1)*(col % 3);
		
		for(int i = rowStart; i < rowStart+3;i++) {
			for(int j = colStart; j < colStart+3;j++) {
				if(b[row+i][col+j] == val)
					return false;
			}
		}
		return true;
	}
	
	/**
	 * Places a value at the specified position. It doens't do anything
	 * if the value violates the sudoku rules
	 * @param row
	 * @param col
	 * @param val
	 * @return true if value could be placed
	 */
	public boolean placeNumber(int row,int col, int val) {
		
		if(checkRow(row,val) && checkColumn(col,val) && checkSquare(row,col,val))
		{
			if(b[row][col] == 0)
				this.filledFields++;
			
			b[row][col] = val;
			return true;
		}
		
		return false;
	}
	
	/**
	 * 
	 * @param row
	 * @param col
	 */
	public void removeNumber(int row, int col) {
		this.filledFields--;
		b[row][col] = 0;
	}
	
	/**
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean isFieldEmpty(int row,int col) {
		return b[row][col] == 0;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isSolved() {
		return filledFields >= nFields;
	}
	
	/**
	 * Prints out board for debugging purposes
	 */
	public void printBoard() {
		for(int i = 0; i < 9;i++) {
			StringBuilder line = new StringBuilder("");
			for(int j = 0;j < 9;j++) {
				line.append(b[i][j]+", ");
			}
			
			System.out.println(line);
		}
	}
}

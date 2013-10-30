package ch.fhnw.efalg.schwammberger.jonas.uebung1.springerpfad;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ChessBacktrack {
	private LinkedList<Board.Field> solution;
	private int max;
	private Board b;
	private int n;
	
	public static final int fSize = 35; //pixel size of a single field in the solution image
	
	public ChessBacktrack(int n)
	{
		this.n = n;
		solution = new LinkedList<>();
		
		if(n > 4) {
			max = n*n-1;
			b = new Board(n);
		}
	}
	
	/**
	 * Find a solution for a given start position.
	 * @param startX
	 * @param startY
	 * @return solution in reversed order. Use it with the DescendingIterator
	 */
	public LinkedList<Board.Field> findSolution(int startX, int startY) {
		if( n > 4) {
			solveWithBacktrack(0,b.getField(startX, startY));
			
			
		} else {
			//special cases
			if(n == 1)
			{
				System.out.println(startX + " , " + startY);
			}
				
			
			//print nothing, there is no solution
		}
		
		Iterator<Board.Field> it = solution.descendingIterator();
		while(it.hasNext()) {
			Board.Field f = it.next();
			System.out.println(f.x + " , " + f.y);
		}
		
		return solution;
	}	
	
	/**
	 * 
	 * @param step current step of this call, 0 <= step < max
	 * @param f field to solve
	 * @return true if a solution was found
	 */
	private boolean solveWithBacktrack(int step,Board.Field f) {
		Iterator<Board.Field> it = b.getGreedy(f);
		
		if(step < max)
		{
			while(it.hasNext())
			{	
				Board.Field nextf = it.next();
				
				if(solveWithBacktrack(step+1,nextf))
				{
					//found solution
					solution.add(f);
					return true;
				}				
			}
			
			b.backTrack(f);
			return false;
		}
		
		solution.add(f);

		return true;
	}
	
	/**
	 * displays the solution in a nice image
	 * if no solution was found, only draw a 
	 * @return drawn image reference
	 */
	public Image DrawSolution() {
		int offset = fSize/2+1;
		BufferedImage im = new BufferedImage(n*fSize, n*fSize, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = im.createGraphics();
		
		//draw chess board
		int yPos = 0;
		for(int i = 0; i < n;i++)
		{
			int xPos = 0;
			for(int j = 0; j < n;j++)
			{
				if((i+(j % 2))%2 == 0)
				{
					g.setColor(Color.black);
				} else {
					g.setColor(Color.white);
				}
				
				g.fillRect(xPos, yPos, fSize, fSize);
				xPos += fSize;
			}
			yPos += fSize;
		}
		
		Iterator<Board.Field> it = solution.descendingIterator();
		
		//draw begin circle
		Board.Field fBegin = null;
		if(it.hasNext()) {
			fBegin = it.next();
			g.setColor(Color.blue);
			g.fillOval(fBegin.x*fSize,fBegin.y*fSize, fSize, fSize);
		}
		
		//draw line
		while(it.hasNext())
		{
			Board.Field fEnd = it.next();
			g.setColor(Color.red);
			g.drawLine(fBegin.x*fSize+offset, fBegin.y*fSize+offset, fEnd.x*fSize+offset, fEnd.y*fSize+offset);
			fBegin = fEnd;
		}
		
		//draw end dot
		if(fBegin != null) 
			g.fillOval(fBegin.x*fSize,fBegin.y*fSize, fSize, fSize);
		
		return im;
	}
	
	
	public static void main(String[] args) {
		int n = 4;
		
		ChessBacktrack chess = new ChessBacktrack(n);
		chess.findSolution(0, 0);
		
		Image im = chess.DrawSolution();
		
		JFrame frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.add(new JLabel(new ImageIcon(im)),BorderLayout.CENTER);
		frame.setVisible(true);
		Dimension d = new Dimension(fSize*(n+1),fSize*(n+2));
		frame.setMinimumSize(d);
		frame.setPreferredSize(d);
	}

}

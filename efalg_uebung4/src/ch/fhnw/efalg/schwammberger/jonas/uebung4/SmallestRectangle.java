package ch.fhnw.efalg.schwammberger.jonas.uebung4;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class SmallestRectangle {
	private final ArrayList<Point> convexHull;
	private final double piHalf = Math.PI/2;
	
	public SmallestRectangle(ArrayList<Point> convexHull) {
		this.convexHull = convexHull;
		
	}
	
	public List<Line> calculateSmallestRectangle() {
		//index of maxima
		int lowI = 0;
		int leftI = this.findMin(true);
		int rightI = this.findMax(true);
		int topI = this.findMax(false);
		
		
		return null;
	}
	
	private void getPossibleSolutions() {
		double angle = 0;
		Solution min = new Solution();
		Solution current = new Solution();
	}
	
	private int findMin(boolean useX) {
		int min = Integer.MAX_VALUE;
		int index = -1;
		for(int i = 0; i < convexHull.size();i++)  {
			int val = useX ? convexHull.get(i).x : convexHull.get(i).y;
			if(min < val) {
				min = val;
				index = i;
			}
		}
		return index;
	}
	
	private int findMax(boolean useX) {
		int max = 0;
		int index = -1;
		for(int i = 0; i < convexHull.size();i++)  {
			int val = useX ? convexHull.get(i).x : convexHull.get(i).y;
			if(max > val) {
				max = val;
				index = i;
			}
		}
		return index;
	}
	
	private class Solution {
		Line a;
		Line b;
		Line c;
		Line d;
	}
	
}

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;


public class BusProblem {
	static ArrayList<BusStop> allStops;
	static ArrayList<Bus> allBusses;
	static boolean newInfoDiffused = true;
	
	private static int gcd(int a, int b)
	{
	    while (b > 0) {
	    	int temp = b;
	        b = a % b;
	        a = temp;
	    }
	    return a;
	}
	
	private static int lcm(int a, int b)
	{
	    return a * (b / gcd(a, b));
	}

	private static int lcm(ArrayList<Integer> integers)
	{
		int result = integers.get(0);
	    for(int i = 1; i < integers.size(); i++) 
	    	result = lcm(result, integers.get(i));
	    
	    return result;
	}
	
	private static ArrayList<Integer> read() throws Exception {
		Scanner in=new Scanner(new File("bus.in"));
		ArrayList<Integer> output = new ArrayList<>();
		
		int busCount = in.nextInt();
		allBusses = new ArrayList<>(busCount);
		allStops = new ArrayList<>();
		HashMap<Integer,BusStop> helper = new HashMap<>();
		
		for(int i = 0; i < busCount;i++)
		{
			int nStops = in.nextInt();
			output.add(nStops);
			ArrayList<BusStop> stops = new ArrayList<>(nStops);
			for(int j =0; j < nStops;j++) {
				Integer stopNr = new Integer(in.nextInt()-1);
				
				if(!helper.containsKey(stopNr))
					helper.put(stopNr, new BusStop(stopNr));
					
				stops.add(helper.get(stopNr));
			}
			
			allBusses.add(new Bus(i, 0, busCount, stops));
			
		}
		
		allStops = new ArrayList<>(helper.values());
		return output;
	}
	
	public static void main(String[] args) throws Exception {
		PrintWriter out=new PrintWriter("bus.out");
		
		
		ArrayList<Integer> circles = read();
		
		int lcm = lcm(circles);
		int solution = solve(lcm);
		
		if(solution == -1) 
			out.println("NEVER");
		else
			out.println(solution);
		
		out.close();
	}
	
	public static int solve(int kgv) {
		boolean isSolved = false;
		int n = 0;
		long start = System.currentTimeMillis();
		
		while(!isSolved && newInfoDiffused && (System.currentTimeMillis() - start) < 800) {
			newInfoDiffused = true;
			
			for(int i = 0; i < kgv && (System.currentTimeMillis() - start) < 800;i++) {
				//exchange
				for(Bus b : allBusses)
					b.exchangeInfo();
					
				//clear busStops
				for(BusStop s : allStops) {
					if(s != null)
						s.departBusses();
				}	
				
				//move bus
				for(Bus b: allBusses)
					b.move();
				
				//check if solved
				isSolved = true;
				for(Bus b: allBusses)
				{
					if(!b.hasAllInfo()) {
						isSolved = false;
						n++;
						break;
					}	
				}
				if(isSolved)
					break;
				
			}
		}
		
		return isSolved ? n : -1;
	}
	
	public static class BusStop {
		int nr;
		LinkedList<Bus> busses;
		
		public BusStop(int nr) {
			busses = new LinkedList<>();
			this.nr = nr;
		}
		
		public void arrive(Bus b) {
			busses.add(b);
		}
		
		public void departBusses() {
			busses.clear();
		}
	}
	
	public static class Bus {
		int nr;
		int currentStop;
		private Bus[] info;
		ArrayList<BusStop> stops;
		int infos = 1;
		
		public Bus(int nr, int startPos, int busCount, ArrayList<BusStop> stops) {
			this.nr = nr;
			this.currentStop = startPos;
			this.stops = stops;
			info = new Bus[busCount];
			info[nr] = this;
			stops.get(currentStop).arrive(this);
		}
		
		public void move() {
			currentStop = ++currentStop % stops.size();
			stops.get(currentStop).arrive(this);
		}
		
		public void exchangeInfo() {
			for(Bus b : stops.get(currentStop).busses) {
				if(b != this) {
					for(int i =0; i < info.length;i++) {
						if(info[i] == null && b.info[i] != null) {
							info[i] = b.info[i];
							infos++;
							newInfoDiffused = true;
						}
					}
				}
			}
			
		}
		
		public boolean hasAllInfo() {
			return infos == info.length;
		}
		
	}

}


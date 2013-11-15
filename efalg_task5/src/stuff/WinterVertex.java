package stuff;
import java.io.File;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class WinterVertex {
	static ArrayList<ArrayList<Connection>> timeTable;
	static ArrayList<Vertex> cities;
	static int max = Integer.MAX_VALUE - 100000000;

	public static void main(String[] args) throws Exception {
		Scanner s = new Scanner(new File("C:\\Users\\Jon\\Documents\\GitHub\\efalg\\efalg_task5\\winter.in"));

		int cityCount = s.nextInt();
		int connCount = s.nextInt();
		int av = connCount / cityCount;

		cities = new ArrayList<>(cityCount);
		for (int i = 0; i < connCount; i++) {
			long depart = s.nextLong();
			int city = s.nextInt();
			long arrival = s.nextLong();
			int dest = s.nextInt();

			Vertex v = cities.get(city - 1);

			if (v.connections == null)
				v.connections = new ArrayList<>(av);

			v.connections.add(new Connection(depart, arrival, dest - 1));
		}

		//dijkstra
		PriorityQueue<Vertex> pq = new PriorityQueue<>();
		Vertex start = cities.get(0);
		start.currentTime = 0;
		start.waitingTime = 0;
		pq.add(start);

		int minTime = 0;
		while (!pq.isEmpty()) {
			Vertex current = pq.poll();

			if (current.city == cityCount - 1) {
				minTime = current.waitingTime;
				break;
			}

			for (int i = 0; i < current.connections.size(); i++) {
				Connection c = current.connections.get(i);
				if (c.departTime >= current.currentTime) {
					Vertex next = cities.get(c.destCity);
					int waitTime = (int) (c.departTime - current.currentTime);
					int accWaiting = current.waitingTime + waitTime;
					if (accWaiting < next.waitingTime) {
						next.waitingTime = accWaiting;
						//proceed time by traveling distance and time we needed to wait for this connection
						next.currentTime = current.currentTime + waitTime + (c.arrivalTime - c.departTime);
						pq.add(next);
					}
				}
			}
		}

		System.out.println(minTime);

	}

	public static class Vertex implements Comparable<Vertex> {
		int city;
		int waitingTime;
		long currentTime;
		ArrayList<Connection> connections;

		@Override
		public int compareTo(Vertex o) {
			Integer.compare(waitingTime, o.waitingTime);
			return 0;
		}
	}

	public static class Connection {
		int waitingTime;
		long departTime;
		long arrivalTime;
		int destCity;

		public Connection(long depart, long arrival, int destCity) {
			this.departTime = depart;
			this.arrivalTime = arrival;
			this.destCity = destCity;
			this.waitingTime = max;
		}

	}
}

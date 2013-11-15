package efalg_task5_2;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Scanner;

public class WinterVertex {
	static ArrayList<ArrayList<Connection>> timeTable;
	static ArrayList<Vertex> cities;
	static int max = Integer.MAX_VALUE - 100000000;

	public static void main(String[] args) throws Exception {
		Scanner in = null;
		try {
			in = new Scanner(new File("winter_4.in"));
		} catch (Exception e) {
			System.out.println("bla");
		}

		int cityCount = in.nextInt();
		int connCount = in.nextInt();

		int av = connCount / cityCount;

		cities = new ArrayList<>(cityCount);
		for (int i = 0; i < cityCount; i++) {
			cities.add(new Vertex(i));
		}
		for (int i = 0; i < connCount; i++) {
			long depart = in.nextLong();
			int city = in.nextInt();
			long arrival = in.nextLong();
			int dest = in.nextInt();

			Vertex v = cities.get(city - 1);

			v.connections.add(new Connection(depart, arrival, dest - 1));
		}

		for (int i = 0; i < cityCount; i++) {
			Collections.sort(cities.get(i).connections);
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
					if (accWaiting <= next.waitingTime || current.currentTime > next.currentTime) {
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

	public static class Node implements Comparable<Node> {
		Vertex v;

		public Node(Vertex v) {
			this.v = v;
		}

		@Override
		public int compareTo(Node o) {
			return Integer.compare(v.waitingTime, o.v.waitingTime);

		}

	}

	public static class Vertex implements Comparable<Vertex> {
		int city;
		int waitingTime;
		long currentTime;
		ArrayList<Connection> connections;

		public Vertex(int city) {
			this.city = city;
			currentTime = -1;
			waitingTime = max;
			connections = new ArrayList<>();
		}

		@Override
		public int compareTo(Vertex o) {
			int x = Integer.compare(waitingTime, o.waitingTime);
			return x == 0 ? Integer.compare(o.city, city) : x;
		}
	}

	public static class Connection implements Comparable<Connection> {
		long departTime;
		long arrivalTime;
		int destCity;

		public Connection(long depart, long arrival, int destCity) {
			this.departTime = depart;
			this.arrivalTime = arrival;
			this.destCity = destCity;

		}

		@Override
		public int compareTo(Connection o) {
			return Long.compare(departTime, o.departTime);
		}

	}
}

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

public class WinterVertex {
	static HashMap<Long, Node> graph;
	static int max = Integer.MAX_VALUE - 100000000;
	static int startCity = 0;
	static long startNode = 0;

	public static void main(String[] args) throws Exception {
		Scanner in = new Scanner(new File("winter.in"));

		int cityCount = in.nextInt();
		int connCount = in.nextInt();
		ArrayList<Long> allVertices = new ArrayList<>(cityCount * 25);

		graph = new HashMap<>(cityCount * 25);

		for (int i = 0; i < connCount; i++) {
			long depart = in.nextLong();
			int city = in.nextInt();
			long arrival = in.nextLong();
			int dest = in.nextInt();

			long start = encode(depart, city - 1);
			long end = encode(arrival, dest - 1);
			if (!graph.containsKey(start)) {
				graph.put(start, new Node(start));
			}

			if (!graph.containsKey(end)) {
				graph.put(end, new Node(end));
			}
			Node n = graph.get(start);

			n.connections.add(end);
			allVertices.add(start);
			allVertices.add(end);
		}

		Collections.sort(allVertices);
		startNode = -1;
		startCity = 0;

		//add waiting times
		long[] tmp = new long[cityCount];

		for (int i = 0; i < cityCount; i++) {
			tmp[i] = -1;
		}

		for (int i = 0; i < allVertices.size(); i++) {
			int index = decodeCity(allVertices.get(i));
			if (index == 0 && startNode < 0) {
				startNode = allVertices.get(i);
			}

			if (tmp[index] > -1) {
				Node before = graph.get(tmp[index]);
				before.connections.add(allVertices.get(i));
			}
			tmp[index] = allVertices.get(i);
		}

		//dijkstra
		PriorityQueue<Node> pq = new PriorityQueue<>(cityCount);
		Node start = graph.get(startNode);
		start.cost = 0;
		pq.add(start);

		int minTime = -1;
		while (!pq.isEmpty()) {
			Node current = pq.poll();
			current.visited = true;

			int city = decodeCity(current.v);
			int time = decodeTime(current.v);
			ArrayList<Long> conn = current.connections;

			if (city == cityCount - 1) {
				minTime = current.cost;
				break;
			}

			for (int i = 0; i < conn.size(); i++) {
				Node destination = graph.get(conn.get(i));
				int destTime = decodeTime(destination.v);
				int destCity = decodeCity(destination.v);
				if (destTime >= time) {
					int waitTime = destCity == city ? destTime - time : 0;
					if (city == startCity && destCity == startCity) {
						waitTime = 0;
					}
					int accCost = current.cost + waitTime;

					if (accCost < destination.cost && !destination.visited) {
						destination.cost = accCost;
						pq.add(destination);
					}
				}
			}

		}

		//System.out.println(minTime);
		PrintWriter out = new PrintWriter("winter.out");
		out.print(minTime);
		out.close();
	}

	public static long encode(long time, int city) {
		long start = time;
		start = start << 16;
		//start = start * 10000;
		start += city;
		return start;
	}

	public static int decodeCity(long in) {
		return (int) (in & 0xffff);
		//return (int) (in % 10000);
	}

	public static int decodeTime(long in) {
		return (int) (in >>> 16);
		//return (int) (in / 10000);
	}

	public static class Node implements Comparable<Node> {
		Long v;
		int cost;
		ArrayList<Long> connections;
		boolean visited = false;

		public Node(Long v) {
			this.v = v;
			cost = max;
			connections = new ArrayList<>(5);
		}

		@Override
		public int compareTo(Node o) {
			return Integer.compare(cost, o.cost);

		}

	}
}

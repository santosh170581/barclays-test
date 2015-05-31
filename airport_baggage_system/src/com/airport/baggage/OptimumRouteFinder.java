package com.airport.baggage;

/**
 * 
 * @author Santosh Kumar Sahoo
 *
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

public class OptimumRouteFinder {

	public static final String CONVEYOR_SYSTEM = "# Section: Conveyor System";
	public static List<Conveyor> connectionList = new ArrayList<Conveyor>();
	public static Set<String> vertextNamesSet = new HashSet<String>();

	public static final String LINE_SPLITOR = " ";
	public static final String LINE_STARTER = "#";

	public static final int FIRST_POSITION = 1;
	public static final int SECOND_POSITION = 2;
	public static final int ZERO_POSITION = 0;

	public static void computePaths(Vertex source) {
		source.minDistance = 0.;
		PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
		vertexQueue.add(source);

		while (!vertexQueue.isEmpty()) {
			Vertex vertext = vertexQueue.poll();
			if (vertext.adjacencies == null) {
				continue;
			}
			// Visit each edge exiting u
			for (Edge edge : vertext.adjacencies) {

				Vertex innerVertext = edge.target;
				double weight = edge.weight;
				double distanceThroughU = vertext.minDistance + weight;
				if (distanceThroughU < innerVertext.minDistance) {
					vertexQueue.remove(innerVertext);

					innerVertext.minDistance = distanceThroughU;
					innerVertext.previous = vertext;
					vertexQueue.add(innerVertext);
				}
			}
		}
	}

	public static List<Vertex> getShortestPathTo(Vertex targetVertext) {
		List<Vertex> path = new ArrayList<Vertex>();
		for (Vertex vertex = targetVertext; vertex != null; vertex = vertex.previous)
			path.add(vertex);

		Collections.reverse(path);
		return path;
	}

	private static int getVertextByName(String vertexName, Vertex[] vertices) {
		for (int i = 0; i < vertices.length; i++) {
			if (vertices[i].name.equals(vertexName))
				return i;
		}
		return -1;
	}

	public String getRoute(String bagId, String from_node, String to_node)
			throws IOException {
		Scanner scanner = new Scanner(Thread.currentThread()
				.getContextClassLoader().getResourceAsStream("input.txt"));
		String line = "";
		while (scanner.hasNext()) {
			line = scanner.nextLine();
			if (CONVEYOR_SYSTEM.equals(line)) {
				line = scanner.nextLine();
				while (!line.startsWith(LINE_STARTER)) {
					String[] line_array = line.split(LINE_SPLITOR);
					vertextNamesSet.add(line_array[ZERO_POSITION]);
					vertextNamesSet.add(line_array[FIRST_POSITION]);
					connectionList.add(new Conveyor(line_array[ZERO_POSITION],
							line_array[FIRST_POSITION], Integer
									.parseInt(line_array[SECOND_POSITION])));
					connectionList.add(new Conveyor(line_array[FIRST_POSITION],
							line_array[ZERO_POSITION], Integer
									.parseInt(line_array[SECOND_POSITION])));
					if (scanner.hasNext())
						line = scanner.nextLine();
					else
						break;
				}

			}
		}
		scanner.close();
		Iterator<String> itr = vertextNamesSet.iterator();
		Vertex[] vertices = new Vertex[vertextNamesSet.size()];
		int d = 0;
		while (itr.hasNext()) {
			vertices[d++] = new Vertex(itr.next());
		}
		itr = vertextNamesSet.iterator();
		while (itr.hasNext()) {
			List<Edge> edgeList = new ArrayList<Edge>();
			int conectCount = 0;
			String vertexName = itr.next();
			for (Conveyor connection : connectionList) {
				if (connection.fromNode.equals(vertexName)) {
					conectCount++;

					edgeList.add(new Edge(vertices[getVertextByName(
							connection.toNode, vertices)], connection.weight));

				}
			}
			if (conectCount > 0) {
				vertices[getVertextByName(vertexName, vertices)].adjacencies = new Edge[conectCount];
				int vertextCount = 0;
				for (Edge e : edgeList) {
					vertices[getVertextByName(vertexName, vertices)].adjacencies[vertextCount++] = e;

				}
			}
		}
		computePaths(vertices[getVertextByName(from_node, vertices)]);
		String shortestPath = bagId
				+ " "
				+ getShortestPathTo(
						vertices[getVertextByName(to_node, vertices)])
						.toString().replaceAll("\\[|\\]|,", "")
				+ " : "
				+ (int) vertices[getVertextByName(to_node, vertices)].minDistance;
		return shortestPath;
	}

}

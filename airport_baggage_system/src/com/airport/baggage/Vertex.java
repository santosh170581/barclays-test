package com.airport.baggage;

/**
 * 
 * @author Santosh Kumar Sahoo
 *
 */
class Vertex implements Comparable<Vertex> {
	public final String name;
	public Edge[] adjacencies;
	public double minDistance = Double.POSITIVE_INFINITY;
	public Vertex previous;

	public Vertex(String vertexName) {
		this.name = vertexName;
	}

	public String toString() {
		return name;
	}

	public int compareTo(Vertex other) {
		return Double.compare(this.minDistance, other.minDistance);
	}

}

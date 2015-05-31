package com.airport.baggage;

/**
 * 
 * @author Santosh Kumar Sahoo
 *
 */
class Edge {
	public final Vertex target;
	public final double weight;

	public Edge(Vertex argTarget, double argWeight) {
		this.target = argTarget;
		this.weight = argWeight;
	}

}

package com.airport.baggage;

/**
 * 
 * @author Santosh Kumar Sahoo
 *
 */
public class Conveyor {

	public String fromNode;
	public String toNode;
	public double weight;

	public Conveyor(String fromNode, String toNode, int weight) {
		this.fromNode = fromNode;
		this.toNode = toNode;
		this.weight = weight;
	}

}

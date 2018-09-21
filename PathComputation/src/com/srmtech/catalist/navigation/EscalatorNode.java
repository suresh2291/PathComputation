package com.srmtech.catalist.navigation;

public class EscalatorNode {
	public String EscalatorNode;
	public String getEscalatorNode() {
		return EscalatorNode;
	}
	public void setEscalatorNode(String escalatorNode) {
		EscalatorNode = escalatorNode;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEdges() {
		return edges;
	}
	public void setEdges(String edges) {
		this.edges = edges;
	}
	public String getShape() {
		return shape;
	}
	public void setShape(String shape) {
		this.shape = shape;
	}
	public String id;
	public String name;
	public String edges;
	public String shape;
	@Override
	public String toString() {
		return "EscalatorNode [EscalatorNode=" + EscalatorNode + ", id=" + id + ", name=" + name + ", edges=" + edges
				+ ", shape=" + shape + "]";
	}
}


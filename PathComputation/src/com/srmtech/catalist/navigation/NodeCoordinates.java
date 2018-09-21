package com.srmtech.catalist.navigation;

public class NodeCoordinates {
	public String Snode;
	public String id;
	public String name;
	public String edges;
	public String shape;
	public String vrimage;
	public String coords;
	
	public String getSnode() {
		return Snode;
	}
	public void setSnode(String snode) {
		Snode = snode;
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
	public String getVrimage() {
		return vrimage;
	}
	public void setVrimage(String vrimage) {
		this.vrimage = vrimage;
	}
	public String getCoords() {
		return coords;
	}
	public void setCoords(String coords) {
		this.coords = coords;
	}
	
	@Override
	public String toString() {
		return "NodeCoordinates [Snode=" + Snode + ", id=" + id + ", name=" + name + ", edges=" + edges + ", shape="
				+ shape + ", vrimage=" + vrimage + ", coords=" + coords + "]";
	}

	


}

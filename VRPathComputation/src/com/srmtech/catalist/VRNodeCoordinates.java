package com.srmtech.catalist;

public class VRNodeCoordinates {
	public String Snode;
	public String id;
	public String name;
	public String edges;
	public String shape;
	public String vrimage;
	public String coords;


	public String getCoords() {
		return coords;
	}



	public void setCoords(String coords) {
		this.coords = coords;
	}



	public String getVrimage() {
		return vrimage;
	}



	public void setVrimage(String vrimage) {
		this.vrimage = vrimage;
	}



	public String getId() 
	{
		return id;
	}



	public void setId(String id) 
	{
		this.id = id;
	}



	public String getPathName() 
	{
		return name;
	}



	public void setPathName(String name) 
	{
		this.name = name;
	}



	


	public String getSnode() {
		return Snode;
	}



	public void setSnode(String snode) {
		Snode = snode;
	}



	public String getEdges() {
		return edges;
	}



	public void setEdges(String edges) {
		this.edges = edges;
	}



	public String getShape() 
	{
		return shape;
	}



	public void setShape(String shape) 
	{
		this.shape = shape;
	}



	@Override
	public String toString() {
		return "VRNodeCoordinates [Snode=" + Snode + ", id=" + id + ", name=" + name + ", edges=" + edges + ", shape="
				+ shape + ", vrimage=" + vrimage + ", coords=" + coords + "]";
	}



}

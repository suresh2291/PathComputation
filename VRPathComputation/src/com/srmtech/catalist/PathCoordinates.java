package com.srmtech.catalist;

public class PathCoordinates {
	
public String Spath;
public String id;
public String name;
public String edges;
public String shape;










public String getSpath() {
	return Spath;
}





public void setSpath(String spath) {
	Spath = spath;
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





@Override
public String toString() {
  return  "GroundFloor Path [ id=" + id + ", name=" + name + ", coords="+ edges + ", Spath=" + Spath + ", shape=" + shape+ "]";
}

}

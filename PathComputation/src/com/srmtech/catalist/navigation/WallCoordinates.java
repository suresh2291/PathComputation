package com.srmtech.catalist.navigation;

public class WallCoordinates {

	public String Wall;
	
	public String id;
	public String name;
	public String coords;
	public String shape;
	
	public String getWall() {
		return Wall;
	}

	public void setWall(String wall) {
		Wall = wall;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWallName() {
		return name;
	}

	public void setWallName(String name) {
		this.name = name;
	}

	public String getCoords() {
		return coords;
	}

	public void setCoords(String coords) {
		this.coords = coords;
	}

	public String getShape() {
		return shape;
	}

	public void setShape(String shape) {
		this.shape = shape;
	}
	
	@Override
	public String toString() {
	  return  "GroundFloor [ id=" + id + ", name=" + name + ", coords="+ coords + ", Wall=" + Wall + ", shape=" + shape+ "]";
	}

	}
	


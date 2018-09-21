package com.srmtech.catalist.navigation;

public class MapCoordinates {
public String mapSize;
	
	public String id;
	public String name;
	public String coords;
	public String shape;
	
	public String getMap() {
		return mapSize;
	}
	public void setMap(String map) {
		mapSize = map;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMapName() {
		return name;
	}
	public void setMapName(String name) {
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
	  return  "FirstFloor [ id=" + id + ", name=" + name + ", coords="+ coords + ", Map=" + mapSize + ", shape=" + shape+ "]";
	}
	
}

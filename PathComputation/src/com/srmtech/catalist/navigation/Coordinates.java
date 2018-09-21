package com.srmtech.catalist.navigation;

public class Coordinates
 {
	
	private int coordinateX,coordinateY,coordinateZ;
	String Path; 
	private float dist;
private double	time;
	
	public Coordinates(){
		super();
	}
	public Coordinates(int coordinateX, int coordinateY, int coordinateZ,String Path) {
		super();
		this.coordinateX = coordinateX;
		this.coordinateY = coordinateY;
		this.coordinateZ = coordinateZ;
		this.Path = Path;
		
	}

	public String getObstacle() {
		return Path;
	}
	public void setObstacle(String obstacle) {
		this.Path = obstacle;
	}
	public int getCoordinateX() {
		return coordinateX;
	}

	public void setCoordinateX(int coordinateX) {
		this.coordinateX = coordinateX;
	}

	public int getCoordinateY() {
		return coordinateY;
	}

	public void setCoordinateY(int coordinateY) {
		this.coordinateY = coordinateY;
	}

	public int getCoordinateZ() {
		return coordinateZ;
	}

	public void setCoordinateZ(int coordinateZ) {
		this.coordinateZ = coordinateZ;
	}
	
	public float getDist(){
		return dist;
	}
	
	public void setDist(float dist){
		this.dist= dist;
	}
	
	public double getTime(){
		return time;
	}
	
	public void setTime(double time){
		this.time = time;
	}
	@Override
	public String toString() {
		return "Coordinates [coordinateX=" + coordinateX + ", coordinateY=" + coordinateY + ", coordinateZ="
				+ coordinateZ + ", obstacle=" + Path + ", dist=" + dist + ", time=" + time + "]";
	}
	
	
	

	
}

package com.srmtech.catalist.navigation;
import java.util.*;
public class Graph {
  private ArrayList<Vertex> vertices;
	public Graph(int numberVertices){
		vertices = new ArrayList<Vertex>(numberVertices);
		for(int i=0;i<numberVertices;i++){
			vertices.add(new Vertex(Integer.toString(i)));
		}
	}
	
	public void addEdge(int src, int dest, int weight){
		Vertex s = vertices.get(src);
		Edge new_edge = new Edge(vertices.get(dest),weight);
		s.neighbours.add(new_edge);
	}
	
	
	public Edge delEdge(int src, int dest){
		Vertex s = vertices.get(src);
		Vertex d = vertices.get(dest);
		
		Edge delEdge = null;
		for(Edge edge : s.neighbours){
			if(edge.target == d){
				delEdge = edge;
			}
		}
		s.neighbours.remove(delEdge);
		
		return delEdge;
	}
	
	
	public void resetMinDistance(){
		for(Vertex v : vertices){
			v.setMinDistance(Double.POSITIVE_INFINITY);
		}
	}
	
	public ArrayList<Vertex> getVertices() {
		return vertices;
	}
	
	public Vertex getVertex(int vert){
		return vertices.get(vert);
	}
}

package com.srmtech.catalist.navigation;

import java.util.List;
import com.srmtech.catalist.navigation.WallCoordinates;
import com.srmtech.catalist.navigation.PathCoordinates;



public class ExtractXML {
	
	public ExtractXML()
	{
		XMLFileReader read = new XMLFileReader();
		List<WallCoordinates> readConfig = read.readWalls("SecondFloor.xml");
		for (WallCoordinates item : readConfig) {
			//System.out.println(item);
			//System.out.println( item.coords +"  "+item.name);	
		String wallxyz = item.coords;
		String[] wall = wallxyz.split(",");
		String wall_x1 = wall[0];
		String wall_y1 = wall[1];
		String wall_z1 = wall[2];
		String wall_x2 = wall[3];
		String wall_y2 = wall[4];
		String wall_z2 = wall[5];
		System.out.println("\t\t\t\t Wall Coordinates \t\t\t\t");
		System.out.println(wall_x1  + "  ,  " +  wall_y1  + "  ,  " + wall_z1+" , " + wall_x2  + "  ,  " +  wall_y2  + "  ,  " + wall_z2);
		
		}

		//for path.
		XMLFileReader input = new XMLFileReader();
		List<PathCoordinates> readPath = input.readPath("SecondFloor.xml");
		for (PathCoordinates spath : readPath) {
			//System.out.println(spath );
			//System.out.println(spath.coords.indexOf(0)+ " " +spath.name);
		String pathxyz = spath.edges;

		String[] path = pathxyz.split(",");
		String path_x = path[0];
		String path_y = path[1];
		//String path_z = path[2];
		System.out.println("\t\t\t\t Path Coordinates \t\t\t\t");
		System.out.println(path_x  + "  ,  " +  path_y  );
		}
		
		XMLFileReader mapin = new XMLFileReader();
		List<EscalatorNode> readmap = mapin.readMapSize("SecondFloor.xml");
		for (EscalatorNode maps : readmap) {
			//System.out.println(spath );
			//System.out.println(spath.coords.indexOf(0)+ " " +spath.name);
		String map = maps.edges;

		String[] path = map.split(",");
		String map_x = path[0];
		String map_y = path[1];
		//String path_z = path[2];
		System.out.println("\t\t\t\t EscalatorNode \t\t\t\t");
		System.out.println(map_x  + "  ,  " +  map_y );
		}

		XMLFileReader stairs = new XMLFileReader();
		List<NodeCoordinates> staircase = stairs.readstairCase("SecondFloor.xml");
		for (NodeCoordinates stair : staircase) {
			//System.out.println(spath );
			//System.out.println(spath.coords.indexOf(0)+ " " +spath.name);
		String coordinates = stair.edges;
		

		String[] res = coordinates.split(",");
		String stair_x = res[0];
		String stair_y = res[1];
		String stair_z = res[2];
		
		//String path_z = path[2];
		System.out.println("\t\t\t\t node Coordinates \t\t\t\t");
		System.out.println(stair_x  + "  ,  " +  stair_y + ", "+stair_z );
		}

	}
	
	
	public static void main(String[] args) {
		                                                                                                                                                                                      		
		// TODO Auto-generated method stub

		ExtractXML result = new ExtractXML();
		
	}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       

	
}

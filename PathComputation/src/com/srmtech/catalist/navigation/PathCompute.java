package com.srmtech.catalist.navigation;
//add escalator node as snode of staircase point.

import java.awt.geom.Line2D;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import org.json.JSONException;
import org.json.JSONObject;



/*TODO create get all shop coordinate data
 * find and equation whether a point lie inside the shop or not
 * if it lies inside then consider Snode which is inside the shop,else consider outside point and attach respective image to it.*/
public class PathCompute {


	public static String quickgo_sub_category="escalator";
	public static  int j;
	public  int xStart;
	public  int yStart;
	public  int zStart;
	public  int xEnd;
	public  int yEnd;
	public  int zEnd;	
	public  String route;
	public  int wallx1[],wally1[],wallx2[],wally2[],wallz1[],wallz2[];
	
	public  int escalatorCount = 0,escalatorfirst=0,pathfloor,nodeCount=0,shopCount=0,pathCount=0,wallCount=0,firstfloorNodeCount = 0,firstfloorPathCount = 0,firstfloorWallCount = 0;
	public  int nodex[],nodey[],nodez[],escalatorX[],escalatorY[],escalatorZ[];
	public  int spathp1[],spathp2[],pathWeight[];
	int pathDistance;
	int sourcex = 0,sourcey = 0,sourcez= 0;
	int destinationx = 0,destinationy = 0,destinationz= 0;
	String srcx = " " ,srcy = " ",srcz = " ",destx = " ",desty = " ",destz = " ";
	String pathcordinates="";
	////////////////////////////////DATABASE CONNECTION VARIABLES//////////////////////////////////////
	public static final String JDBC_DRIVER = "org.postgresql.Driver";
	public static final String DB_URL = "jdbc:postgresql://localhost:5432/ushopdb";
	public static final String USER = "postgres";
	public static final String PASS = "root";
	private int uwbtag_coordinate_x = 0 ,uwbtag_coordinate_y = 0,uwbtag_coordinate_z = 0,product_position_x = 0,product_position_y = 0,product_position_z = 0;
	public static int  quickgo_position_x=0;
	public static int quickgo_position_y=0;
	public static int quickgo_position_z=0;
	public static int  quickgo_x;
	public static int quickgo_y;
	public static int quickgo_z;
	public static int fualtnode;


	public static  boolean quickgo_status=true;
	public static String quickgo_name="";
	public static SendJson responce;

	public static Connection conn;
	public static Statement stmt;
	public static String sql;
	public static ResultSet rs;
	////////////////////////FIND SOURCE AND DESTINATION COORDINATES////////////////////////////////// 
	public static List<Coordinates> coordinateList = new ArrayList<Coordinates>();
	
	public PathCompute() {
		super();
	}
	/**
	 * 
	 * findSourceCoordinates(Long sourceId)
	 * This method is used get the source id from database
	 *  
	 *  
	 *  
	 * @param sourceId
	 * @return sensorCoordinates(uwbx,uwby,uwbz)
	 */
	private int[] findSourceCoordinates(Long sourceId){
		try {
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			sql = "SELECT uwbtag_coordinate_x,uwbtag_coordinate_y,uwbtag_coordinate_z FROM sensormanagement.uwbtagtable where user_id= "+ sourceId +"";
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				uwbtag_coordinate_x = rs.getInt("uwbtag_coordinate_x");
				uwbtag_coordinate_y = rs.getInt("uwbtag_coordinate_y");
				uwbtag_coordinate_z = rs.getInt("uwbtag_coordinate_z");
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new int[]{uwbtag_coordinate_x,uwbtag_coordinate_y,uwbtag_coordinate_z};

	}

	public static int[] EscalatorCoordinates( ){
		try {
			try {
				Class.forName("org.postgresql.Driver");
				conn =  DriverManager.getConnection(DB_URL, USER, PASS);
				System.out.println("Connected database successfully...");
				System.out.println("Creating statement...");
			}catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Creating statement...");
			stmt = conn.createStatement();

			// String esc="escalator";
			sql = "SELECT quickgo_name,quickgo_position_x,quickgo_position_y,quickgo_position_z,quickgo_status FROM placedirectory.quickgotable where quickgo_sub_category LIKE '"+quickgo_sub_category+"%'";
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				quickgo_position_x = rs.getInt("quickgo_position_x");
				quickgo_position_y = rs.getInt("quickgo_position_y");
				quickgo_position_z = rs.getInt("quickgo_position_z");
				quickgo_status = rs.getBoolean("quickgo_status");
				quickgo_name  = rs.getString("quickgo_name");
				if(quickgo_status==false){
					quickgo_x=quickgo_position_x;
					quickgo_y=quickgo_position_y;
					quickgo_z=quickgo_position_z;
					//System.out.println("EscalatorCoordinates------->  "+"  quickgo_name   "+quickgo_name+"   quickgo_position_x   "+quickgo_x+"   quickgo_position_y   "+quickgo_y+"  quickgo_position_z  "+quickgo_z+"  quickgo_status  "+quickgo_status);
				}		}
			rs.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new int[]{quickgo_x,quickgo_y,quickgo_z};

	}
	//System.out.println("EscalatorCoordinates------->  "+"  quickgo_name   "+quickgo_name+"   quickgo_position_x   "+quickgo_x+"   quickgo_position_y   "+quickgo_y+"  quickgo_position_z  "+quickgo_z+"  quickgo_status  "+quickgo_status);


	/**
	 * findDestinationCoordinates(Long destinationId)
	 * this method will find the coordinate x,y,z form uwb tag table based on product id
	 * @param destinationId-product id of the requesting user
	 * @return int[]  containing coodinateX @ 0,coodinateY @ 1,coodinateZ @ 2
	 */
	private int[] findDestinationCoordinates(Long destinationId){
		try {


			stmt = conn.createStatement();
			sql= "SELECT product_coordinate_id, product_id, product_position_x, product_position_y, product_position_z FROM coordinate.productcoordinates where product_id= "+ destinationId +"";
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				product_position_x  = rs.getInt("product_position_x");
				product_position_y = rs.getInt("product_position_y");
				product_position_z = rs.getInt("product_position_z");

			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new int[]{product_position_x,product_position_y,product_position_z};

	}

	////////////////////////////PATH COMPUTATION USER_ID(CURRENT_LOCATION) PRODUCT_ID(DESTINATION) DESTINATION_TYPE ////////////////////////

	/**
	 * VRPathCompute (long user_id , long product_id , long destination_type)
	 * VRPathCompute takes userId,productId as source and destination and compute the path
	 * @param user_id
	 * @param product_id
	 * @param destination_type
	 */
	//public VRPathCompute (long user_id , long product_id , long destination_type)
	@SuppressWarnings("unused")
	public PathCompute (long user_id , long product_id) {

		int[] source = findSourceCoordinates(user_id);
		int[] destination = findDestinationCoordinates(product_id);

		try {
			PathCompute path;
			path = new PathCompute(source[0], source[1], source[2], destination[0], destination[1], destination[2]);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	//////////////////////////////////OPEN DATABSE AND CLOSE CONNECTION/////////////////////////////////////////////////

	/**
	 * openConnection()
	 * This method is used to open connection in Database
	 * 
	 */
	@SuppressWarnings("unused")
	private void openConnection(){
		try{
			Class.forName("org.postgresql.Driver");
			conn =  DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Connected database successfully...");
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	/**
	 * connectionClose()
	 * this method is used to close the opened connection with postgresql.
	 * 
	 */

	@SuppressWarnings("unused")
	private void connectionClose(){
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//////////////////////////////////////////DB CONNECTION FOR XML FILE FROM MAP TABLE IN PLACE DIRECTORY//////////////////////////////////////////

	/**
	 * CoordinateFilePath()
	 * This method is used to get the  XML filepath link from Postgre database. 
	 * @return path of xml file
	 */
	public String[] CoordinateFilePath()
	{

		String dirPath = "E:/MyServer/apache-tomcat-8.0.32/MapCoordinates/";
		String fileName= " ";
		String path[] = new String[100]; 

		String storePath = " ";

		{
			java.sql.Connection conn = null;
			java.sql.Statement stmt = null; 
			try{
				Class.forName("org.postgresql.Driver");

				System.out.println("Connecting to database...");
				conn = DriverManager.getConnection(DB_URL, USER, PASS);

				//System.out.println("Creating statement...");
				stmt = conn.createStatement();

				String sql = "SELECT map_cooordinate_filepath from placedirectory.maptable ORDER BY map_id ASC";
				ResultSet rs = stmt.executeQuery(sql);

				int i=0;
				while(rs.next())
				{
					storePath =rs.getString("map_cooordinate_filepath");
					fileName=storePath.substring(storePath.lastIndexOf("/") + 1);
					path[i] = dirPath+fileName;
					i++;
				}

				System.out.println("first file--->"+path[0]+"second file---->"+path[1]);
				path[i]="";
			}

			catch(SQLException se){

				se.printStackTrace();
			}catch(Exception e){

				e.printStackTrace();
			}finally{

				try{
					if(stmt!=null)
						conn.close();
				}catch(SQLException se){
				}
				try{
					if(conn!=null)
						conn.close();
				}catch(SQLException se){
					se.printStackTrace();

				}
			}
		}

		return path;
	}
	////////////////////////////////////////////////Rooms////////////////////////////
	/*public void GetRooms(){

		String[] dr =  CoordinateFilePath();
		int i = 0;

		snode = new int[300];

		while(!dr[i].isEmpty())
		{	

			XMLFileReader node = new XMLFileReader();
			List<NodeCoordinates> snode = node.readstairCase(dr[i]);
			j=1+firstfloorNodeCount;
			for (NodeCoordinates stairs : snode){
				String room = stairs.coords;
				String[] rooms = room.split(",");

				shopx1[j] =  Integer.parseInt(rooms[0])/10;
				shopy1[j] =  Integer.parseInt(rooms[1])/10;
				shopx2[j] =  Integer.parseInt(rooms[2])/10;
				shopy2[j] =  Integer.parseInt(rooms[3])/10;
				shopx3[j] =  Integer.parseInt(rooms[4])/10;
				shopy3[j] =  Integer.parseInt(rooms[5])/10;
				shopx4[j] =  Integer.parseInt(rooms[6])/10;
				shopy4[j] =  Integer.parseInt(rooms[7])/10;


			}
		}
		System.out.println("ShopCordinates:       "+shopx1[j]+" , "+shopy1[j]+" , "+shopx2[j]+" , "+shopy2[j]+" , "+shopx3[j]+" , "+shopy3[j]+" , "+shopx4[j]+" , "+shopy4[j]);

	}*/
	//////////////////////////////////XML Coordinate ///////////////////////////////////////////////////////////////////////////

	/**
	 *  GetxmlfromDataBase(int xA, int yA, int zA, int xB, int yB, int zB)
	 * Here we get XML file from database and extract the data which is in xml file
	 * @param xA x-coordinate of source
	 * @param yA y-coordinate of source
	 * @param zA z-coordinate of source
	 * @param xB x-coordinate of destination
	 * @param yB y-coordinate of destination
	 * @param zB z-coordinate of destination
	 * variables
	 * dr = directory where xml file is store
	 */
	public void GetxmlfromDataBase(int xA, int yA, int zA, int xB, int yB, int zB){
		String[] dr =  CoordinateFilePath();
		System.out.println("map1 :" + dr[0]);
		System.out.println("map2 :" + dr[1]);
		System.out.println("map3 :" + dr[2]);
		int i = 0;

		escalatorX = new int[300];
		escalatorY = new int[300];
		escalatorZ = new int[300];

		nodex = new int[300];
		nodey = new int[300];
		nodez = new int[300];

	
		spathp1 =new int[300];
		spathp2 =new int[300];
		pathWeight = new int[300];

		wallx1 = new int[300];
		wally1 = new int[300];
		wallz1 = new int[300];
		wallx2 = new int[300];
		wally2 = new int[300];
		wallz2 = new int[300];

		while(!dr[i].isEmpty())
		{	

			/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			XMLFileReader mapin = new XMLFileReader();
			List<EscalatorNode> readmap = mapin.readMapSize(dr[i]);
			j=1+escalatorCount;
			for (EscalatorNode maps : readmap) {

				String map = maps.edges;


				String[] path = map.split(",");
				if(quickgo_x!=Integer.parseInt(path[0])&& quickgo_y!= Integer.parseInt(path[1])&& quickgo_z!= Integer.parseInt(path[2]))
				{	escalatorX[j] = Integer.parseInt(path[0])/10;
				escalatorY[j] = Integer.parseInt(path[1])/10;
				escalatorZ[j] = Integer.parseInt(path[2]);

				j++;
				}
			}
			escalatorCount=j-1;
			////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////			
			XMLFileReader node = new XMLFileReader();
			List<NodeCoordinates> nodes = node.readstairCase(dr[i]);
			j = 1 + firstfloorNodeCount;
			for (NodeCoordinates stairs : nodes) {
				
				String coord = stairs.edges;
				String[] stairxyz = coord.split(",");
				{
					if(quickgo_x== Integer.parseInt(stairxyz[0]) && quickgo_y == Integer.parseInt(stairxyz[1]) && quickgo_z == Integer.parseInt(stairxyz[2]))
					{

						fualtnode=j;
						j--;

					}
					else{
						
						nodex[j] =  Integer.parseInt(stairxyz[0])/10;
						nodey[j] = Integer.parseInt(stairxyz[1])/10;
						nodez[j] = Integer.parseInt(stairxyz[2]);

					}

				}

					System.out.println("snode nodex "+nodex[j]+ " nodey "+nodey[j]+ " nodez "+nodez[j]+" j "+j);
				j++;

			}
			nodeCount=j-1;


			////////////////////////////////////////////////////////////////
			XMLFileReader input = new XMLFileReader();
			List<PathCoordinates> readPath = input.readPath(dr[i]);
			pathCount=1+firstfloorPathCount;
			for (PathCoordinates spath : readPath) {
				String pathxyz = spath.edges;
				String[] path = pathxyz.split(",");

				if( fualtnode!=Integer.parseInt(path[0]) &&  fualtnode!=Integer.parseInt(path[1])){

					spathp1[pathCount] =  Integer.parseInt(path[0]);
					spathp2[pathCount] =  Integer.parseInt(path[1]);

					pathWeight[pathCount]=PathDistance(nodex[spathp1[pathCount]],nodey[spathp1[pathCount]],nodez[spathp1[pathCount]],nodex[spathp2[pathCount]],nodey[spathp2[pathCount]],nodez[spathp2[pathCount]]);
						System.out.println("sapthp1-- "+spathp1[pathCount]+ " spathp2-- "+spathp2[pathCount]+" weight "+pathWeight[pathCount]+" path "+pathCount+" firstfloorNodeCount "+firstfloorNodeCount);
					pathCount++;
				}

			}

			pathCount--;


			//Reading Wall Coordinates from XML file.
			XMLFileReader read = new XMLFileReader();
			List<WallCoordinates> readwall;
			readwall = read.readWalls(dr[i]);
			wallCount=1+firstfloorWallCount;
			for (WallCoordinates item : readwall) {

				String wallxyz = item.coords;
				String[] wall = wallxyz.split(",");
				wallx1[wallCount] = Integer.parseInt(wall[0])/10;
				wally1[wallCount] = Integer.parseInt(wall[1])/10;
				wallz1[wallCount] = Integer.parseInt(wall[2]);
				wallx2[wallCount] = Integer.parseInt(wall[3])/10;
				wally2[wallCount] = Integer.parseInt(wall[4])/10;
				wallz2[wallCount] = Integer.parseInt(wall[5]);
				//wallfloor = Integer.parseInt(wall[4]);
			   System.out.println("WallCoordinates wallx1-- "+wallx1[wallCount]+ " wally1-- "+wally1[wallCount]+"wallx2--"+wallx2[wallCount]+ " wally2-- "+wally2[wallCount]);

				wallCount++;
			}
			wallCount--;

			
				firstfloorNodeCount = nodeCount;
				firstfloorPathCount = pathCount;
				firstfloorWallCount =wallCount;
				System.out.println("firstfloorNodeCount:--> "+firstfloorNodeCount+"firstfloorPathCount:--> "+firstfloorPathCount+"firstfloorWallCount:--> "+firstfloorWallCount+"nodeCount:--> "+nodeCount+"pathCount:--> "+pathCount+"wallCount:--> "+wallCount);


			i++;
			
		}

		shortestDistancetoSpath(xA,yA,zA,pathCount,wallCount);
		shortestDistancetoSpath(xB,yB,zB,pathCount,wallCount);


	}


	/**
	 * shortestDistancetoSpath(int xA,int yA,int numberofPaths,int wallCount)
	 * this method is used to join the pathLine(Standard Path) when the source abd destination coordinates inside room
	 * ------------					--------------
	 * -	.	  - 		  .		-			 -
	 * -	.	  - 		  .		-			 -
	 * -  	.	  -standard path.   -		     -
	 * -	......-............ 	-			 -
	 * -		  - 				-			 -
	 * -          - 				-			 -
	 * ------------ 				--------------
	 * @param xA  Source X Coordinate
	 * @param yA Sorurce Y Coordinate
	 * @param numberofPaths total count(number) of path in Map(XML file) 
	 * @param wallCount Total number of Walls in XML file
	 */
	public void shortestDistancetoSpath(int xA,int yA,int zA,int numberofPaths,int wallCount)
	{
		/**
		 * 
		 * distance = store distance from node to another node
		 * xcord = x coordinate
		 * ycord = y coodrinate 
		 * pathLine = plot the line to standard path.
		 * 
		 * 
		 */
		//String image = "";
		int distance=99999,xcord=0,ycord=0,zcord=0,pathLine=0;
		for(int path=1;path<=numberofPaths;path++)
		{
			if(nodez[spathp1[path]]==zA && nodez[spathp2[path]]==zA)
			{	
			int[] intersectPoint=null;
			intersectPoint= shortestDistance(nodex[spathp1[path]], nodey[spathp1[path]], nodez[spathp1[path]], nodex[spathp2[path]], nodey[spathp2[path]], nodez[spathp2[path]], xA, yA, zA);
			
			System.out.println("x1 "+nodex[spathp1[path]]+" y1 "+ nodey[spathp1[path]]+" z1 "+nodez[spathp1[path]]+" x2 "+nodex[spathp2[path]]+" y2 "+ nodey[spathp2[path]]+" z2 "+nodez[spathp2[path]]+" xA "+ xA+" yA "+ yA+" zA "+zA+"  path "+path + "  "+spathp1[path]+"  "+spathp2[path]);
				System.out.println("dist "+intersectPoint[0]+" "+intersectPoint[1]+" "+intersectPoint[2]+" "+intersectPoint[3]+ " "+path+ " "+ numberofPaths);

		
			boolean flag=false,clear=true;
			for(int wall=1;wall<=wallCount;wall++)
			{
				if(wallz1[wall]==zA)
				{
					flag = Line2D.linesIntersect(wallx1[wall], wally1[wall], wallx2[wall], wally2[wall], xA, yA, intersectPoint[1], intersectPoint[2]);
					System.out.println("point flag "+flag+" wall number "+ wall +" "+ wallCount+" xA "+xA+" yA "+ yA+" xb "+ xcord+" yb "+ ycord + "path "+ path);
					if(flag) 
					{
						clear=false;
					}
				}
				else continue;
			}
			System.out.println("point flag "+flag+" wall number "+ wallCount+" xA "+xA+" yA "+ yA+" xb "+ xcord+" yb "+ ycord + "path "+ path+ "line "+ pathLine);
			if(clear) 
			{
				if(distance>intersectPoint[0])//previous>current
				{
					distance=intersectPoint[0];
					xcord=intersectPoint[1];
					ycord=intersectPoint[2];
					zcord=intersectPoint[3];
					pathLine=path;	
					System.out.println("pathLine Shortest Path:  "+pathLine);
				}
			}
		}
		}
		//System.out.println(" point "+point+" wall "+wallCount + " "+xcord[point]);
		addPointinGraph(xA,yA,zA, xcord, ycord,zcord,pathLine);

	}


	/**
	 * 
	 * This method we add extra points,which we use in drawing line if the point is inside the room
	 * @param xA source coordinate
	 * @param yA destnation coordinate
	 * @param xcord xcoordinate to plot point inside room
	 * @param ycord y coordinate to plot point inside room
	 * @param pathLine draw line from 
	 * 
	 */
	public void  addPointinGraph(int xA,int yA,int zA,int xcord,int  ycord,int  zcord,int pathLine)

	{
		System.out.println("addpiontingraph x "+xA+" y "+yA+" xcord "+xcord+" ycord "+ycord+" line "+pathLine);
		int point1,point2,vrimage=0;

		point1= spathp1[pathLine];
		point2= spathp2[pathLine];
		System.out.println("Points:  "+"point1   "+point1+"point2  "+point2);
		
		nodeCount++;
		nodex[nodeCount]=xA;
		nodey[nodeCount]=yA;
		nodez[nodeCount]=zA;
		
		nodeCount++;
		nodex[nodeCount]=xcord;
		nodey[nodeCount]=ycord;
		nodez[nodeCount]=zcord;

		//adding two new lines and modify old line
		spathp1[pathLine]=point1;
		spathp2[pathLine]=nodeCount;
		pathWeight[pathLine]=PathDistance(nodex[spathp1[pathLine]],nodey[spathp1[pathLine]],nodez[spathp1[pathLine]],nodex[spathp2[pathLine]],nodey[spathp2[pathLine]],nodez[spathp2[pathLine]]);
		pathCount++;
		spathp1[pathCount]=point2;
		spathp2[pathCount]=nodeCount;
		pathWeight[pathCount]=PathDistance(nodex[spathp1[pathCount]],nodey[spathp1[pathCount]],nodez[spathp1[pathCount]],nodex[spathp2[pathCount]],nodey[spathp2[pathCount]],nodez[spathp2[pathCount]]);

		pathCount++;
		spathp1[pathCount]=nodeCount-1;
		spathp2[pathCount]=nodeCount;
		pathWeight[pathCount]=PathDistance(nodex[spathp1[pathCount]],nodey[spathp1[pathCount]],nodez[spathp1[pathCount]],nodex[spathp2[pathCount]],nodey[spathp2[pathCount]],nodez[spathp2[pathCount]]);

		//	pathCount++;

	}

	/**
	 * Here we calculate the shortest distance from one node to another i.e., calculate edge between 2 vertices
	 * @param x1 source x coordinate
	 * @param y1 source y coordinate
	 * @param x2 destination x coordinate
	 * @param y2 destination y coordinate
	 * @param x3 source x coordinate
	 * @param y3 source y coordinate
	 * @return points  calculated using above parameters
	 **/
	private int[] shortestDistance(float x1,float y1,float z1,float x2,float y2,float z2,float x3,float y3,float z3)
	{
		int point[];
		point = new int[4];

		float px=x2-x1;
		float py=y2-y1;
		float pz=z2-z1;

		float temp=(px*px)+(py*py);//slopeCalculating

		float u =((x3 - x1) * px + (y3 - y1) * py) / (temp);

		if(u>1)
		{
			u=1;
		}
		else if(u<0)
		{
			u=0;
		}
		float x = x1 + u * px;
		float y = y1 + u * py;
		float z = z1 + u * pz;

		float dx = x - x3;
		float dy = y - y3;
		float dz = z - z3;

		point[0] =(int) Math.sqrt(dx*dx + dy*dy + dz*dz);

		if (z1 == z3 && z2 == z3)
		{
			point[1]=Math.abs((int)x);
			point[2]=Math.abs((int)y);
			point[3]=Math.abs((int)z);
		}
		else
		{
			point[0]=99999;
			point[1]=(int) x1;
			point[2]=(int) y1;
			point[3]=(int) z1;

		}
		return point;

	}


	public boolean IsEscalator(int escalatorX2,int escalatorY2,int escalatorZ2)
	{


		for (int count=1;count<=escalatorCount;count++)
		{
			//System.out.println("IsEscalator----------------"+"coorX"+escalatorX2+"coorY"+escalatorY2+"coorZ"+escalatorZ2+ " count"+ escalatorCount);

			if(escalatorX2 == escalatorX[count] && escalatorY2 == escalatorY[count] &&	escalatorZ2 == escalatorZ[count])
			{

				return true;
			}

		}
		return false;
	}

	/**
	 * pathFindDij(int x1, int y1, int z1, int x2, int y2, int z2)
	 * this method will calculate the intermediate coordinates from source to destination.
	 * @param x1 SourceXCoordinate
	 * @param y1 SoourceYcoordinate
	 * @param z1 FloorValue
	 * @param x2 Destination X Coordinate
	 * @param y2 Destination Y Coordinate
	 * @param z2 FloorValue
	 * @return PathCoordinates    from 
	 */
	public String pathFindDij(int x1, int y1, int z1, int x2, int y2, int z2)
	{
		
		// Create a new graph.
		int source=0,destination=0;
		//get all Snodes 
		// for loop throgh all the Spath in  xml
		// calculate the distance and add the edge


		Graph g = new Graph(nodeCount+1);
		//for creating the edges in the graph
		for(int path=1;path <= pathCount;path++)
		{
			g.addEdge(spathp1[path], spathp2[path], pathWeight[path]);
			g.addEdge(spathp2[path], spathp1[path], pathWeight[path]);
		}

		for(int i=1;i<=nodeCount;i++)
		{
			if(nodex[i] == x1 && nodey[i]== y1 && nodez[i] ==z1)  source =i;

			if(nodex[i] == x2 && nodey[i]== y2 && nodez[i] == z2) destination=i;
		}
		//System.out.println("source "+source);
		//System.out.println("destination "+destination);
		// Calculate Dijkstra.
		calculate(g.getVertex(source));	

		Vertex v1 = g.getVertex(destination);
		//System.out.println("Vertex - "+v1+" , Dist - "+ v1.minDistance+" , Path - ");/*+v1.path +" s "*/
		System.out.println("Path");

		for(Vertex pathvert:v1.path) {
			System.out.print(pathvert+" ");
			//System.out.println(pathvert.name.toString());//nodex[v1]
			sourcex = Integer.parseInt(pathvert.name.toString());
			sourcey = Integer.parseInt(pathvert.name.toString());
			sourcez = Integer.parseInt(pathvert.name.toString());
			//System.out.println("sourcex =  " + nodex[sourcex]+"sourcey =  "+nodey[sourcey]);
			srcx = Integer.toString(nodex[sourcex]);
			srcy = Integer.toString(nodey[sourcey]);
			srcz = Integer.toString(nodez[sourcez]);

			boolean escalator=IsEscalator(nodex[sourcex], nodey[sourcey], nodez[sourcez]);
			if(escalator)
			{
				//System.out.println("escatalor "+ escalator);
				pathcordinates=pathcordinates+srcx+","+srcy+","+srcz+","+"escalator"+",";
			}
			else
			{
				pathcordinates=pathcordinates+srcx+","+srcy+","+srcz+","+"node"+",";
			}
			//System.out.println("SRCX STRING              " + srcx+"SRCY STRING    " + srcy);
			//System.out.println("SourceCoordinates"+ pathcordinates);
		}

		System.out.println(""+v1);
		destinationx = Integer.parseInt(v1.name.toString());
		destinationy = Integer.parseInt(v1.name.toString());
		destinationz = Integer.parseInt(v1.name.toString());
		System.out.println("destinationx =  " + nodex[destinationx]+"destinationy =  "+nodey[destinationy]);
		destx = Integer.toString(nodex[destinationx]); 
		desty = Integer.toString(nodey[destinationy]);
		destz = Integer.toString(nodez[destinationz]);
		//System.out.println("DESTX STRING          " + destx+"DESTY STRING          " + desty);
		//pathcordinates = pathcordinates+destx+","+desty+","+destz;
		//System.out.println("DestinationCoordinates"+ pathcordinates);
		boolean escalator=true;
		escalator=IsEscalator(nodex[destinationx], nodey[destinationy], nodez[destinationz]);
		if(escalator)
		{
			System.out.println("escatalor "+ escalator);
			pathcordinates = pathcordinates+destx+","+desty+","+destz+","+"escalator"+",";
		}
		else
		{
			pathcordinates = pathcordinates+destx+","+desty+","+destz+","+"node"+",";
		}
		//}
		return pathcordinates;
	}


	/**
	 * calculate distance from source to next node.
	 * @param source
	 */
	public void calculate(Vertex source){
		// Algo:
		// 1. Take the unvisited node with minimum weight.
		// 2. Visit all its neighbours.
		// 3. Update the distances for all the neighbours (In the Priority Queue).
		// Repeat the process till all the connected nodes are visited.

		source.minDistance = 0;
		PriorityQueue<Vertex> queue = new PriorityQueue<Vertex>();
		queue.add(source);

		while(!queue.isEmpty()){

			Vertex u = queue.poll();

			for(Edge neighbour:u.neighbours){
				Double newDist = u.minDistance+neighbour.weight;

				if(neighbour.target.minDistance>newDist){
					// Remove the node from the queue to update the distance value.
					queue.remove(neighbour.target);
					neighbour.target.minDistance = newDist;

					// Take the path visited till now and add the new node.s
					neighbour.target.path = new LinkedList<Vertex>(u.path);
					neighbour.target.path.add(u);

					//Reenter the node with new distance.
					queue.add(neighbour.target);					
				}
			}
		}
	}

	/**
	 * calculate the distance form one node to another using distance formula
	 * @param x1 sourceX
	 * @param y1 destinationY
	 * @param x2 sourceX
	 * @param y2 destinationY
	 * @return pathDistance
	 */
	public int PathDistance(int x1,int y1,int z1,int x2,int y2,int z2)
	{
		//System.out.println("x1 "+x1+"y1 "+y1+"x2 "+x2+"y2 "+y2);

		pathDistance = (int) Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1) + (z2-z1)*(z2-z1));
		//	System.out.println("pathDistance================================"+ pathDistance);
		return pathDistance;

	}





	////////////////////////////////PATH COMPUTAION CODE FOR TIME TO REACH AND NORMAL PATH COMPUTATION//////////////////
	/**
	 * Computes the path from source to destination
	 * @param xA sourceX
	 * @param yA sourceY
	 * @param zA sourceZ
	 * @param xB destinationX
	 * @param yB destinationY
	 * @param zB destinationZ
	 * @throws IOException
	 */
	public PathCompute(int xA, int yA, int zA, int xB, int yB, int zB) throws IOException {

		xStart = xA/10;
		yStart = yA/10;
		zStart = zA;
		xEnd  = xB/10;
		yEnd  = yB/10;
		zEnd  = zB;


		GetxmlfromDataBase( xStart, yStart, zStart, xEnd, yEnd, zEnd);

		//route = sourceDestinationCoordinates(tempRef_xA, tempRef_yA, tempRef_zA, tempRef_xB, tempRef_yB, tempRef_zB);
		route =pathFindDij(xStart, yStart, zStart, xEnd, yEnd, zEnd);
		System.out.println("route----------->"+route);

		int coordinatex=0; 
		int coordinatey=0;
		int coordinatez=0;
		String Path ;
		String coord = route;
		//	String[] list = coord.split(",");
		String[] list = coord.split(",");
		for(int index = 0;index<list.length-1;index=index+4)
		{
			coordinatex = Integer.parseInt(list[index]);
			//System.out.println("coordinatex"+coordinatex);
			coordinatey = Integer.parseInt(list[index+1]);
			//System.out.println("coordinatey"+coordinatey);
			coordinatez = Integer.parseInt(list[index+2]);
			//System.out.println("coordinatez"+coordinatez);
			Path = list[index+3];
			coordinateList.add(new Coordinates(coordinatex,coordinatey,coordinatez,Path));

		}


	}


	private static void FetchTimeToReach(){
		TimeToReach TTR = new TimeToReach();
		System.out.println("coordinateList----> " + coordinateList);

		coordinateList = TTR.generateTimeToReachForNavi(coordinateList);
		
		JSONObject navi = new JSONObject();
		try {
			navi.put("Navigation", coordinateList);
			System.out.println("JSON Responce Navi----> " + navi.toString());

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public static void main(String[] args) throws IOException {
		EscalatorCoordinates();

		//
		PathCompute path = new PathCompute(4380,13550,1,1630,6540,1);//262,219,844,900
		//PathCompute path = new PathCompute(10690,12490,0,10300,6980,1);
		FetchTimeToReach();

		/*PathCompute pc = new PathCompute();
		pc.openConnection();
		int[] source = pc.findSourceCoordinates(1L);

		int[] destination = pc.findDestinationCoordinates(1L);
		PathCompute path = new PathCompute (source[0], source[1], source[2], destination[0], destination[1], destination[2]);
		pc.connectionClose();*/
		// EscalatorCoordinates();
	//	System.out.println("EscalatorCoordinates------->  "+"  quickgo_name   "+quickgo_name+"   quickgo_position_x   "+quickgo_x+"   quickgo_position_y   "+quickgo_y+"  quickgo_position_z  "+quickgo_z+"  quickgo_status  "+quickgo_status);

	}

}
package com.srmtech.catalist.navigation;

import java.io.IOException;
import java.io.PrintWriter;
//.import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.srmtech.catalist.navigation.PathCompute;
//import com.srmtech.catalist.navigation.Path_compute_logic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class TimeToReach {

	float Dist=0,totalDist = 0;
	private int uwbtag_coordinate_x = 0 ,uwbtag_coordinate_y = 0,uwbtag_coordinate_z = 0,
			rfid_positon_x = 0,rfid_positon_y = 0,rfid_positon_z = 0;
	double time,totalTime = 0;
	public static final String JDBC_DRIVER = "org.postgresql.Driver";
	public static final String DB_URL = "jdbc:postgresql://localhost:5432/ushopdb";
	public static final String USER = "postgres";
	public static final String PASS = "root";
	Connection conn = null;
	Statement stmt = null;
	String sql;
	ResultSet rs;

	public JSONObject jsonObj = new JSONObject();
	private JSONArray jsonArray = new JSONArray();

	public TimeToReach() {
		super();
	}
	/**
	 * this method will find the coordinate x,y,z form uwb tag table based on user id
	 * @param sourceId -user id of the requesting user
	 * @return int[]  containing coodinateX @ 0,coodinateY @ 1,coodinateZ @ 2
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
	/**
	 * this method will find the coordinate x,y,z form uwb tag table based on product id
	 * @param destinationId-product id of the requesting user
	 * @return int[]  containing coodinateX @ 0,coodinateY @ 1,coodinateZ @ 2
	 */
	private int[] findDestinationCoordinates(Long destinationId){
		try {
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			sql= "SELECT rfid_positon_x,rfid_positon_y,rfid_positon_z FROM sensormanagement.rfidreadertable WHERE rfid_reader_id=(SELECT rfid_reader_id from sensormanagement.rfidtagtable WHERE product_id= "+destinationId+")";
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				rfid_positon_x  = rs.getInt("rfid_positon_x");
				rfid_positon_y = rs.getInt("rfid_positon_y");
				rfid_positon_z = rs.getInt("rfid_positon_z");

			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new int[]{rfid_positon_x,rfid_positon_y,rfid_positon_z};
	}
	/**
	 * this method will find the coordinate x,y,z form Intermediate coordinates between source and destination based on user id and  product id
	 * @param soruceX- X coordinate of the current location requesting by the user.
	 * @param soruceY- Y coordinate of the current location requesting by the user.
	 * @param soruceZ- Z coordinate of the current location requesting by the user.
	 * @param DestinationX- X coordinate of the Destination location requesting by the user.
	 * @param DestinationY- Y coordinate of the Destination location requesting by the user.
	 * @param DestinationZ- Z coordinate of the Destination location requesting by the user.
	 * @return List<Coordinates> contains the coordinates for source ,destination and intermediate turn coordinate.
	 */
	private List<Coordinates> findIntermediatePathCoordinates(int soruceX,int soruceY,int soruceZ,int DestinationX,int DestinationY,int DestinationZ){
		PathCompute path;
		try {
			System.out.println("soruceX, soruceY  , soruceZ   , DestinationX   , DestinationY   , DestinationZ  "+soruceX+"   "+soruceY+"   "+ soruceZ+"   "+ DestinationX+"   "+ DestinationY+"   "+ DestinationZ);
			path = new PathCompute(soruceX, soruceY, soruceZ, DestinationX, DestinationY, DestinationZ);
			System.out.println("Coordinate Time to reach-----------"+path.route);
		return path.coordinateList;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * this method will find the logic for time to reach using list of x,y,z coordinates of  source and destination.
	 * @param coordinateList-contains x,y,z of the source, intermediate and destination.
	 * @return JSONObject to send response.
	 */
	private JSONObject generateTimeToReach(List<Coordinates> coordinateList){
		try{
			for (int i=1;i<coordinateList.size();i++){
				jsonObj = new JSONObject();
				Coordinates previousCoord = coordinateList.get(i-1);
				Coordinates currentCoord = coordinateList.get(i);
				Dist=(int)Math.sqrt(((currentCoord.getCoordinateX() - previousCoord.getCoordinateX())*(currentCoord.getCoordinateX() - previousCoord.getCoordinateX()))+
						((currentCoord.getCoordinateY() - previousCoord.getCoordinateY())*(currentCoord.getCoordinateY() - previousCoord.getCoordinateY()))+
						((currentCoord.getCoordinateZ() - previousCoord.getCoordinateZ())*(currentCoord.getCoordinateZ() - previousCoord.getCoordinateZ())));

				Dist = Dist/100;
				if(currentCoord.getCoordinateZ()==0||previousCoord.getCoordinateZ()==0)
					time = (Dist/1.38);
				else
					time = (Dist/0.46);
				jsonObj.put("interDist", Dist);
				jsonObj.put("interTime", time);
				jsonArray.put(i-1,jsonObj);
				totalDist +=Dist;
				totalTime+=time;
			}
			jsonObj = new JSONObject();
			jsonObj.put("TotalDist", totalDist);
			jsonObj.put("TotalTime", totalTime);
			jsonArray.put(jsonObj);
			jsonObj = new JSONObject().put("TTR", jsonArray);
			System.out.println("generateTimeToReach---->"+jsonObj);
		}catch(JSONException je){
			je.printStackTrace();
		}
		return jsonObj;
	}

	public List<Coordinates> generateTimeToReachForNavi(List<Coordinates> coordinateList){
		try{
			for (int i=1;i<coordinateList.size();i++){
				jsonObj = new JSONObject();
				Coordinates previousCoord = coordinateList.get(i-1);
				Coordinates currentCoord = coordinateList.get(i);
				Dist=(int)Math.sqrt(((currentCoord.getCoordinateX() - previousCoord.getCoordinateX())*(currentCoord.getCoordinateX() - previousCoord.getCoordinateX()))+
						((currentCoord.getCoordinateY() - previousCoord.getCoordinateY())*(currentCoord.getCoordinateY() - previousCoord.getCoordinateY()))+
						((currentCoord.getCoordinateZ() - previousCoord.getCoordinateZ())*(currentCoord.getCoordinateZ() - previousCoord.getCoordinateZ())));

				Dist = Dist/100;
				if(currentCoord.getCoordinateZ()==0||previousCoord.getCoordinateZ()==0)
					time = (Dist/1.38);
				else
					time = (Dist/0.46);
				currentCoord.setDist(Dist);
				currentCoord.setTime(time);
				totalDist +=Dist;
				totalTime+=time;
			}
			Coordinates finalCoordinates = new Coordinates();
			finalCoordinates.setDist(totalDist);
			finalCoordinates.setTime(totalTime);
		coordinateList.add(finalCoordinates);
		}catch(Exception je){
			je.printStackTrace();
		}
		return coordinateList;
	}
	
	private void timeConvertion(){
		DecimalFormat df= new DecimalFormat("#.##");
		totalTime=Double.valueOf(df.format(totalTime));

		String str1= Double.toString(totalTime);
		String[] splitTime=str1.split("\\.");

		int min=Integer.parseInt(splitTime[0]);
		int sec=Integer.parseInt(splitTime[1]);
		System.out.println(" ");

		System.out.println("Total distance between the points  is " + totalDist + " metre.");            //Total Distance
		System.out.println("Total Time to reach between the points  is " + min + "Minute "+sec+"Seconds");    //Toatal Time

	}
	/**
	 * this method is used to open connection with postgresql.
	 */
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
	 * this method is used to close the opened connection with postgresql.
	 * 
	 */
	private void connectionClose(){
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	public static void main(String[] args)
	{
		TimeToReach TTR = new TimeToReach();
		TTR.openConnection();
		//TODO get source id from json and change the below hard coded parameter
		int[] source = TTR.findSourceCoordinates(1L);
		//TODO get destination id from json and change the below hard coded parameter
		int[] destination = TTR.findDestinationCoordinates(1L);
		List<Coordinates> coordinates = TTR.findIntermediatePathCoordinates(source[0], source[1], source[2], destination[0], destination[1], destination[2]);
		JSONObject result = TTR.generateTimeToReach(coordinates);
	//	System.out.print(result.toString());
		TTR.connectionClose();
	}
}








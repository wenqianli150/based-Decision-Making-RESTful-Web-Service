package controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import model.House;
import utils.ServiceUtil;

@Path("/")
public class assign4 {

	private static final ServiceUtil util = new ServiceUtil();
	
	@Path("/web/assign4.jsp")
	@GET
	@Produces("text/html")
	public Response uploadFile () throws FileNotFoundException {
		// create a simple string that contains HTML text
		Scanner scanner = new Scanner(new File("/Users/liwenqian/eclipse-workspace/559_assignment2/assign4/src/main/webapp/assign4.jsp"));
		StringBuilder sb = new StringBuilder();
		while (scanner.hasNextLine()) {
			sb.append(scanner.nextLine());
		}
		scanner.close();
		return Response
        	      .status(Response.Status.OK)
        	      .entity(sb.toString())
        	      .build();
	}
	
	@Path("/web/assign4.html")
	@GET
	@Produces("text/html")
	public Response apiOverview () throws FileNotFoundException {
		// create a simple string that contains HTML text
		Scanner scanner = new Scanner(new File("/Users/liwenqian/eclipse-workspace/559_assignment2/assign4/src/main/webapp/assign4.html"));
		StringBuilder sb = new StringBuilder();
		while (scanner.hasNextLine()) {
			sb.append(scanner.nextLine());
		}
		scanner.close();
		return Response
        	      .status(Response.Status.OK)
        	      .entity(sb.toString())
        	      .build();
	}

	
	@Path("")
	@GET
	@Produces("application/json")
	public Response selectRecord ()  {
		List<House> houses = util.getAllRecords();
		GsonBuilder builder = new GsonBuilder(); 
	    builder.setPrettyPrinting();
		Gson gson = builder.create(); 
		return Response.ok().entity(gson.toJson(houses)).build();  
	}
	
	@Path("{filename}")
	@GET
	@Produces("application/json")
	public Response uploadFileToDatabase (@PathParam("filename") String filename)  {
	        try {
	        	// files are uploaded to /Users/liwenqian/Desktop/559/assign/assignment4/
	        	String filePath = "/Users/liwenqian/Desktop/559/assign/assignment4/" + filename;
	        	File file = new File(filePath);
	    		String jsonString = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
	    		
	    		GsonBuilder builder = new GsonBuilder(); 
	    	    builder.setPrettyPrinting();
	    		Gson gson = builder.create(); 
	    		Type listType = new TypeToken<ArrayList<House>>(){}.getType();
	    	    List<House> houseList = gson.fromJson(jsonString, listType); 
	    	    
	    	    Class.forName("com.mysql.cj.jdbc.Driver");
	    	    for (House house: houseList) {
	    	    	String SQL = "INSERT INTO HOUSE VALUES ("
		            		+ "\"" + house.getName() 		+ "\","
		            		+ house.getPrice() 		+ ","
		            		+ house.getSize() 		+ "," 
		            		+ house.getLocationScore() 		+ ","
		            		+ house.getSchoolScore() 		+ ","
		            		+ house.getSecurityScore() 	+ "," 
		            		+ house.getHoa() + ")";
		            System.out.println(SQL);
		            
		        	Connection connection = DriverManager.getConnection(util.connectStr); 
	        		Statement sqlStatement = connection.createStatement();	 
	        		sqlStatement.executeUpdate(SQL);
	        		connection.close();
		            
	    	    }
	    	    String message="record was added to the database";

	            
	    	    return Response
	            	      .status(Response.Status.OK)
	            	      .header("table", "HOUSE")
	            	      .entity(message)
	            	      .build();
	        }
	        catch(Exception e)
	        {
	            System.out.println(e);
	            return null;
	        }	            
	}
	
	@Path("/add")
	@POST
	@Produces("application/json")
	public Response addRecord(@HeaderParam("name") String name, @HeaderParam("price") int price,
			@HeaderParam("size") int size, @HeaderParam("locationScore") int locationScore, 
			@HeaderParam("schoolScore") int schoolScore, @HeaderParam("securityScore") int securityScore,
			@HeaderParam("hoa") int hoa) {
		
        try {
    	    Class.forName("com.mysql.cj.jdbc.Driver");
    	    String SQL = "INSERT INTO HOUSE VALUES ("
            		+ name 		+ ","
            		+ price 		+ ","
            		+ size 		+ "," 
            		+ locationScore 		+ ","
            		+ schoolScore 		+ ","
            		+ securityScore 	+ "," 
            		+ hoa + ")";
            System.out.println(SQL);
            
        	Connection connection = DriverManager.getConnection(util.connectStr); 
    		Statement sqlStatement = connection.createStatement();	 
    		sqlStatement.executeUpdate(SQL);
    		connection.close();
    	    String message="record was added to the database";

            
    	    return Response
            	      .status(Response.Status.OK)
            	      .header("table", "HOUSE")
            	      .entity(message)
            	      .build();
        }
        catch(Exception e)
        {
            System.out.println(e);
            return null;
        }
	}
	
	@Path("/update/{name}")
	@PUT
	@Produces("application/json")
	public Response updateRecord(@PathParam("name") String houseName, @HeaderParam("name") String name, @HeaderParam("price") int price,
			@HeaderParam("size") int size, @HeaderParam("locationScore") int locationScore, 
			@HeaderParam("schoolScore") int schoolScore, @HeaderParam("securityScore") int securityScore,
			@HeaderParam("hoa") int hoa) {
		
        try {
    	    Class.forName("com.mysql.cj.jdbc.Driver");
    	    String SQL = "UPDATE HOUSE SET "
            		+ "price = " + price + ","
            		+ "size = " + size + "," 
            		+ "locationScore = " + locationScore + ","
            		+ "schoolScore = " + schoolScore + ","
            		+ "securityScore = " + securityScore 	+ "," 
            		+ "hoa = " + hoa 
            		+ " WHERE name = \"" + houseName + "\"" ;
            System.out.println(SQL);
            
        	Connection connection = DriverManager.getConnection(util.connectStr); 
    		Statement sqlStatement = connection.createStatement();	 
    		sqlStatement.executeUpdate(SQL);
    		connection.close();
    	    String message="record was updated in the database";

            
    	    return Response
            	      .status(Response.Status.OK)
            	      .header("table", "HOUSE")
            	      .entity(message)
            	      .build();
        }
        catch(Exception e)
        {
            System.out.println(e);
            return null;
        }
	}
	
	@Path("/delete/{name}")
	@DELETE
	@Produces("application/json")
	public Response deleteRecord(@PathParam("name") String name) {
		
        try {
    	    Class.forName("com.mysql.cj.jdbc.Driver");
    	    String SQL = "DELETE FROM HOUSE WHERE name = \"" + name + "\"";
            System.out.println(SQL);
            
        	Connection connection = DriverManager.getConnection(util.connectStr); 
    		Statement sqlStatement = connection.createStatement();	 
    		sqlStatement.executeUpdate(SQL);
    		connection.close();
    	    String message="record was deleted from the database";

            
    	    return Response
            	      .status(Response.Status.OK)
            	      .header("table", "HOUSE")
            	      .entity(message)
            	      .build();
        }
        catch(Exception e)
        {
            System.out.println(e);
            return null;
        }
	}
	
	@Path("/delete")
	@DELETE
	@Produces("application/json")
	public Response deleteAllRecords() {
		
        try {
    	    Class.forName("com.mysql.cj.jdbc.Driver");
    	    String SQL = "DELETE FROM HOUSE";
            System.out.println(SQL);
            
        	Connection connection = DriverManager.getConnection(util.connectStr); 
    		Statement sqlStatement = connection.createStatement();	 
    		sqlStatement.executeUpdate(SQL);
    		connection.close();
    	    String message="All records were deleted from the database";

            
    	    return Response
            	      .status(Response.Status.OK)
            	      .header("table", "HOUSE")
            	      .entity(message)
            	      .build();
        }
        catch(Exception e)
        {
            System.out.println(e);
            return null;
        }
	}
	
	@Path("/process")
	@GET
	@Produces("application/json")
	public Response process() {
		// create a simple string that contains HTML text
		String url = "http://localhost:8080/assign4/topsis";
		String response = util.callService(url);
		return Response.ok().entity(response).build(); 
	}
	
	@Path("/process/entropy")
	@GET
	@Produces("application/json")
	public Response processEntropy() {
		// create a simple string that contains HTML text
		String url = "http://localhost:8080/assign4/topsis/entropy";
		String response = util.callService(url);
		return Response.ok().entity(response).build(); 
	}
	
	@Path("/process/critic")
	@GET
	@Produces("application/json")
	public Response processCritic() {
		// create a simple string that contains HTML text
		String url = "http://localhost:8080/assign4/topsis/critic";
		String response = util.callService(url);
		return Response.ok().entity(response).build(); 
	}
	
	@Path("/process/correlation")
	@GET
	@Produces("application/json")
	public Response processCorrelation() {
		// create a simple string that contains HTML text
		String url = "http://localhost:8080/assign4/topsis/correlation";
		String response = util.callService(url);
		return Response.ok().entity(response).build(); 
	}
	
	@Path("")
	@GET
	@Produces("text/html")
	public Response GETHandler () {
		// create a simple string that contains HTML text
		String outputHTML = "" ;
		outputHTML += "<html>" ;
		outputHTML += " <body>";
		outputHTML += " <h1>TCSS 559: Assignment 4 Demo</h1>"; outputHTML += " <h3>Building a RESTful API in Java</h3>"; outputHTML += " </body>";
	    outputHTML += "</html>";
	    return Response.ok().entity(outputHTML.toString()).build(); 
	}
	
	@Path("")
	@POST
	@Produces("text/html")
	public Response POSTHandler() {
		String outputHTML = "" ;
		outputHTML += "<html>" ;
		outputHTML += " <body>";
		outputHTML += " <h1>Method Now Allowed or Not Supported. IP Logged.</h1>"; outputHTML += " </body>";
	    outputHTML += "</html>";
	    return Response.status(Status.METHOD_NOT_ALLOWED).entity(outputHTML.toString()).build(); 
	}
}

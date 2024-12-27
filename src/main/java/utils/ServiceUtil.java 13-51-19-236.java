package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import model.Attribute;
import model.House;
import model.HouseScore;
import topsis.algorithm.Alternative;
import topsis.algorithm.Criteria;
import topsis.algorithm.Topsis;

public class ServiceUtil {
	
	public String mysql_ip = "34.67.198.93";
	public String username = "wli6";
	public String password = "lwq19941219";
    // do not change the connectStr, keep it as is!
	public String connectStr ="jdbc:mysql://" + mysql_ip + ":3306/house?user=" + username + "&password=" + password ;

	
	public List<House> getAllRecords ()  {
	        try {
	            
	        	Class.forName("com.mysql.cj.jdbc.Driver");
	        	Connection connection = DriverManager.getConnection(connectStr); 
        		Statement sqlStatement = connection.createStatement();	 
        		ResultSet resultSet = sqlStatement.executeQuery("Select * from HOUSE;");
	            List<House> houses = new ArrayList<>();
	            
	            while (resultSet.next() ) {
	            	House house = new House(resultSet.getString("name"), resultSet.getInt("price"),
	            			resultSet.getInt("size"), resultSet.getInt("locationScore"), 
	            			resultSet.getInt("schoolScore"), resultSet.getInt("securityScore"),
	            			resultSet.getInt("hoa"));
	            	houses.add(house);
	            }
	            return houses;
	        }
	        catch(Exception e)
	        {
	            System.out.println(e);
	            return null;
	        }
	    }
	public String callService(String serviceURL) {
    	try {
    		  
    		  // construct query string and set the character set to UTF-8
    		  String charset = "UTF-8";
    		  
    		  // construct the service URL and the query string
    		  URL serviceEndpoint = new URL(serviceURL);
    		  // initialize the HTTP Request with the service endpoint URL
    		  HttpURLConnection httpRequestCon = (HttpURLConnection) serviceEndpoint.openConnection(); 
    		  httpRequestCon.setRequestProperty("Accept-Charset", charset);
    		  // specifies the request method
    		  httpRequestCon.setRequestMethod("GET");
    		  // indicates the content that can be accepted or expected in the response message 
    		  httpRequestCon.setRequestProperty("Accept", "application/json");
    		  // check to see whether we get HTTP OK (status code 200) or not
    		  
    		  if (httpRequestCon.getResponseCode() != 200)
    			 throw new RuntimeException("HTTP Error code is: " + httpRequestCon.getResponseCode());
    			 // read the stream response and store into a buffered reader
    		  BufferedReader httpResponse = new BufferedReader(new InputStreamReader((httpRequestCon.getInputStream()))); 
    		  
    		  // retrieve output from the response object (HTTP Response)
    		  String responseOutput;
    		  StringBuffer responseMessage = new StringBuffer();
    		    while ((responseOutput = httpResponse.readLine()) != null) { 
    		    	responseMessage.append(responseOutput);
    		  } 
    		  httpRequestCon.disconnect();
    		  return responseMessage.toString();
    		}
    		catch (IOException e) {
    		  e.printStackTrace();
    		  }
		return "Failed to process the request"; 
	}
	
	public Topsis convertInput(List<House> houses, Attribute attribute) {
		// construct Criteria
		List<Criteria> criterias = new ArrayList<>();
		criterias.add(new Criteria("price", attribute.getPriceWeight(), true));
		criterias.add(new Criteria("size", attribute.getSizeWeight(), false));
		criterias.add(new Criteria("locationScore", attribute.getLocationScoreWeight(), false));
		criterias.add(new Criteria("schoolScore", attribute.getSchoolScoreWeight(), false));
		criterias.add(new Criteria("securityScore", attribute.getSecurityScoreWeight(), false));
		criterias.add(new Criteria("hoa", attribute.getHoaWeight(), true));
		
		// construct Alternative and Topsis
		Topsis topsis = new Topsis();
		for (House house: houses) {
			Alternative alternative = new Alternative(house.getName());
			alternative.addCriteriaValue(criterias.get(0), (double)house.getPrice());
			alternative.addCriteriaValue(criterias.get(1), (double)house.getSize());
			alternative.addCriteriaValue(criterias.get(2), (double)house.getLocationScore());
			alternative.addCriteriaValue(criterias.get(3), (double)house.getSchoolScore());
			alternative.addCriteriaValue(criterias.get(4), (double)house.getSecurityScore());
			alternative.addCriteriaValue(criterias.get(5), (double)house.getHoa());
			topsis.addAlternative(alternative);
		}
		
		return topsis;
	}
	
	public List<HouseScore> convertOutput(Topsis topsis) {
		List<Alternative> alternatives = topsis.getAlternatives();
		List<HouseScore> houseScores = new ArrayList<>();
		for (int i = 0; i < alternatives.size(); i ++) {
			Alternative alternative = alternatives.get(i);
			HouseScore houseScore = new HouseScore(alternative.getName(), alternative.getCalculatedPerformanceScore());
			houseScores.add(houseScore);
		}
		return houseScores;
	}

	// function that returns correlation coefficient.
	public double calculateCorrelationCoefficient(List<Double> list1, List<Double> list2)
	{
		double sum1 = 0, sum2 = 0, sum = 0;
		double squareSum1 = 0, squareSum2 = 0;
		double num1 = 0, num2 = 0;
		int size = list1.size();
	
		for (int i = 0; i < size; i++)
		{
			num1 = list1.get(i);
			num2 = list2.get(i);
			sum1 = sum1 + num1;
			sum2 = sum2 + num2;
			sum = sum + num1 * num2;
			squareSum1 = squareSum1 + num1 * num1;
			squareSum2 = squareSum2 + num2 * num2;
		}
	
		// use formula for calculating correlationvcoefficient.
		double corr = (size * sum - sum1 * sum2)/
					(Math.sqrt((size * squareSum1 - sum1 * sum1) * (size * squareSum2 - sum2 * sum2)));
	
		return corr;
	}
}

package model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;


public class Test {
	public static void main(String[] args) {
		House house1 = new House("house1", 1000000, 3000, 7, 8, 6, 1000);
		House house2 = new House("house2", 800000, 2000, 8, 7, 8, 200);
		House house3 = new House("house3", 700000, 1000, 9, 9, 9, 80);
		
		List<House> houseData = new ArrayList<>();
		houseData.add(house1);
		houseData.add(house2);
		houseData.add(house3);
		
		GsonBuilder builder = new GsonBuilder(); 
	    builder.setPrettyPrinting();
		Gson gson = builder.create();    
	      
	    String jsonString = gson.toJson(houseData); 
	    System.out.println(jsonString);
	    
	    Type listType = new TypeToken<ArrayList<House>>(){}.getType();
	    List<House> houseList = gson.fromJson(jsonString, listType); 
	    System.out.println(houseList);  
	}
}

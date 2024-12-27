package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.Attribute;
import model.House;
import model.HouseScore;
import topsis.algorithm.Topsis;
import topsis.algorithm.TopsisIncompleteAlternativeDataException;
import utils.ServiceUtil;

@Path("/topsis")
public class topsis {
	private static final ServiceUtil util = new ServiceUtil();

	@Path("")
	@GET
	@Produces("application/json")
	public Response computeDecisionMatrix() {
		List<HouseScore> houseScores1 = computeScore("http://localhost:8080/assign4/entropy/");
		List<HouseScore> houseScores2 = computeScore("http://localhost:8080/assign4/critic/");
		double corr = calculateCorrelationCoefficient(houseScores1, houseScores2);
		
		JSONObject houseJSON = new JSONObject();
        JSONArray entropyArray = new JSONArray();
        JSONArray criticArray = new JSONArray();
        for (int i = 0; i < houseScores1.size(); i ++) {
        	JSONObject object = new JSONObject();
        	object.put("houseName", houseScores1.get(i).getName());
        	object.put("houseRank", i+1);
        	object.put("houseScore", houseScores1.get(i).getScore());
        	entropyArray.put(object);
        }
        
        for (int i = 0; i < houseScores2.size(); i ++) {
        	JSONObject object = new JSONObject();
        	object.put("houseName", houseScores1.get(i).getName());
        	object.put("houseRank", i+1);
        	object.put("houseScore", houseScores1.get(i).getScore());
        	criticArray.put(object);
        }
        
        houseJSON.put("entropy", entropyArray);
        houseJSON.put("critic", criticArray);
        houseJSON.put("correlation", corr);
		
	    return Response.ok().entity(houseJSON.toString()).build(); 
	}
	
	@Path("/entropy")
	@GET
	@Produces("application/json")
	public Response computeDecisionMatrixWithEntropy() {	
		List<HouseScore> houseScores = computeScore("http://localhost:8080/assign4/entropy/");
		GsonBuilder builder = new GsonBuilder(); 
	    builder.setPrettyPrinting();
		Gson gson = builder.create(); 
		return Response.ok().entity(gson.toJson(houseScores)).build();
	}
	
	@Path("/critic")
	@GET
	@Produces("application/json")
	public Response computeDecisionMatrixWithCritic() {
		List<HouseScore> houseScores = computeScore("http://localhost:8080/assign4/critic/");
		GsonBuilder builder = new GsonBuilder(); 
	    builder.setPrettyPrinting();
		Gson gson = builder.create(); 
		return Response.ok().entity(gson.toJson(houseScores)).build();
	}
	
	@Path("/correlation")
	@GET
	@Produces("application/json")
	public Response computeCorrelation() {
		List<HouseScore> houseScores1 = computeScore("http://localhost:8080/assign4/entropy/");
		List<HouseScore> houseScores2 = computeScore("http://localhost:8080/assign4/critic/");
		double corr = calculateCorrelationCoefficient(houseScores1, houseScores2);
		
	    return Response.ok().entity(String.valueOf(corr)).build(); 
	}
	
	private List<HouseScore> computeScore(String url) {
		String weights = util.callService(url);
		GsonBuilder builder = new GsonBuilder(); 
	    builder.setPrettyPrinting();
		Gson gson = builder.create();  
		Attribute attribute = gson.fromJson(weights, Attribute.class);
		List<House> houses = util.getAllRecords();
		Topsis topsis = util.convertInput(houses, attribute);
		
		try {
			topsis.calculateOptimalSolution();
		} catch (TopsisIncompleteAlternativeDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<HouseScore> houseScores = util.convertOutput(topsis);
		return houseScores;
	}
	
	private double calculateCorrelationCoefficient(
			List<HouseScore> houseScores1, List<HouseScore> houseScores2) {
		List<Double> entropyScores = new ArrayList<>();
		List<Double> criticScores = new ArrayList<>();
		
		for (HouseScore houseScore: houseScores1) {
			entropyScores.add(houseScore.getScore());
		}
		
		for (HouseScore houseScore: houseScores2) {
			criticScores.add(houseScore.getScore());
		}
		
		double corr = util.calculateCorrelationCoefficient(entropyScores, criticScores);
		return corr;
	}
}

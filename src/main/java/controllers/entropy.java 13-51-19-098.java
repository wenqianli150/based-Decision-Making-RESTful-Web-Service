package controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.Attribute;

@Path("/entropy")
public class entropy {
	
	@Path("")
	@GET
	@Produces("application/json")
	public Response GETHandler () {
		// create pseudo weights computed by entropy method
		Attribute attribute = new Attribute(0.2, 0.2, 0.2, 0.1, 0.2, 0.1);
		GsonBuilder builder = new GsonBuilder(); 
	    builder.setPrettyPrinting();
		Gson gson = builder.create();
		
	    return Response.ok().entity(gson.toJson(attribute)).build(); 
	}

}

package controllers;

import javax.ws.rs.ApplicationPath; 
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/")
// This main java class will be used to declare a root
// resource for our application as well as other
// provider classes
public class assign4App extends Application{
// This method returns a collection (non-empty) with
// specific classes to provide support for which are
// going to be handled when published our JAX-RS application 
	@Override
	
	public Set<Class<?>> getClasses() {
	
		HashSet h = new HashSet<Class<?>>();
		//add classes that you wish to be supported by application 
		h.add( assign4.class );
		h.add( entropy.class );
		h.add( critic.class );
		h.add( topsis.class );
		return h;
	}
}
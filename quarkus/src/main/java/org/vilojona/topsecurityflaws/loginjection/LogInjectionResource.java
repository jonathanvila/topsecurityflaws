package org.vilojona.topsecurityflaws.loginjection;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.sql.SQLException;

import org.vilojona.topsecurityflaws.common.DBService;

@Path("/loginjection/login")
public class LogInjectionResource {
   @Inject
   DBService dbService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String login(@QueryParam("username") String username, @QueryParam("password") String password) throws SQLException {
        return dbService.login(username, password);
    }    
    
    @GET
    @Path("sanitized")
    @Produces(MediaType.TEXT_PLAIN)
    public String sanitizedLogin(@QueryParam("username") String username, @QueryParam("password") String password) throws SQLException {
        return dbService.login(sanitizeInput(username), sanitizeInput(password));
    }

    private String sanitizeInput(String input) { 
        // Replace newline and carriage return characters with a safe placeholder 
        if (input != null) { 
            input = input.replaceAll("[\\n\\r]", "_"); 
        } 
        return input; 
    }
}

package org.vilojona.topsecurityflaws.sqlinjection;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.sql.SQLException;

import org.vilojona.topsecurityflaws.common.DBService;

@Path("/sqlinjection/login")
public class SQLInjectionResource {
   @Inject
   DBService dbService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String login(@QueryParam("username") String username, @QueryParam("password") String password) throws SQLException {
        return dbService.login(username, password);
    }
}

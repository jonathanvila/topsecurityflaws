package org.vilojona.topsecurityflaws.deserialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.sql.SQLException;
import java.util.List;

import org.vilojona.topsecurityflaws.common.DBService;
import org.vilojona.topsecurityflaws.common.User;

import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/deserialization/user")
public class DeserializationResource {
    private final DBService dbService;

    @Inject
    public DeserializationResource(DBService dbService) {
        this.dbService = dbService;
    }

    @POST
    public String save(User user) throws SQLException {
        Log.info("Saving user: " + user.getName());
        return String.valueOf(dbService.save(user));
    }

    @GET
    public List<User> getUsers() throws SQLException {
        Log.info("Getting user");
        return dbService.getUsers();
    }

    @POST
    @Path("/binary")
    public String saveBinary(InputStream userStream) throws SQLException, ClassNotFoundException, IOException {
        Log.info("Saving binary user ");
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(userStream);
            User user = (User) objectInputStream.readObject();
            return String.valueOf(dbService.save(user));
        } catch (Exception e) {
            // Handle the exception here
            e.printStackTrace();
            return "Error occurred during deserialization : " + e.getMessage();
        }
    }    
    
    @POST
    @Path("/binary-secure")
    public String saveBinarySecure(InputStream userStream) throws SQLException, ClassNotFoundException, IOException {
        Log.info("Saving binary-secure user ");
        try {
            ObjectInputStream objectInputStream = new SecureObjectInputStream(userStream);
            User user = (User) objectInputStream.readObject();
            return String.valueOf(dbService.save(user));
        } catch (Exception e) {
            // Handle the exception here
            e.printStackTrace();
            return "Error occurred during deserialization : " + e.getMessage();
        }
    }
}
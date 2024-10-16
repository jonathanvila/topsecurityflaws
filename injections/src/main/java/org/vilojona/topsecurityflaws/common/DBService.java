package org.vilojona.topsecurityflaws.common;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jboss.logging.Logger;

import io.agroal.api.AgroalDataSource;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class DBService {
    @Inject
    AgroalDataSource defaultDataSource;

    @Inject
    Logger log;

    // Extracted method to prepare and execute the SQL statement
    private ResultSet executeQueryPrepareStatement(String username, String password) throws SQLException {
        // Prepare the SQL statement with placeholders for username and password
        String sql = "SELECT id FROM usuario WHERE username = ? AND password = ?";
        PreparedStatement statement = defaultDataSource.getConnection().prepareStatement(sql);
        statement.setString(1, username);
        statement.setString(2, password);

        // Execute the query
        return statement.executeQuery();
    }

    // Extracted method to prepare and execute the SQL statement
    private ResultSet executeQueryStringConcatenation(String username, String password) throws SQLException {
        // Prepare the SQL statement with placeholders for username and password
        String sql = "SELECT id FROM usuario WHERE username = '" + username + "' AND password = '" + password + "'";
        PreparedStatement statement = defaultDataSource.getConnection().prepareStatement(sql);

        // Execute the query
        return statement.executeQuery();
    }

    public String login(String username, String password) throws SQLException {
        try {
            // Execute the query
            boolean secureMode = false; //Change to false to test SQL Injection vulnerability
            ResultSet resultSet = secureMode ? executeQueryPrepareStatement(username, password)
                    : executeQueryStringConcatenation(username, password);

            String response = username + " : ";

            // Process the result
            if (resultSet.next()) {
                response += " User authenticated";
                log.info(response);
            } else {
                response += " Invalid credentials";
                log.error(response);
            }
            return response;
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error occurred";
        }
    }

    public int save(User user) throws SQLException {
        return defaultDataSource.getConnection().createStatement()
                .executeUpdate("INSERT INTO usuario (username, password) VALUES ('" + user.getName() + "', '"
                        + user.getPassword() + "')");
    }

    public List<User> getUsers() throws SQLException {
        var usuarioRs = defaultDataSource.getConnection().createStatement().executeQuery("SELECT username, password FROM usuario");
        List<User> userList = new ArrayList<>();
        while (usuarioRs.next()) {
            User user = new User();
            user.setName(usuarioRs.getString("username"));
            user.setPassword(usuarioRs.getString("password"));
            userList.add(user);
        }
        return userList;
    }

}

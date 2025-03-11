package service;

import dataaccess.AuthDAO;
import dataaccess.DatabaseManager;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import model.ApiResponse;

import java.sql.Connection;

public class ClearService  {
    public ApiResponse clear() {
        try  {
            // Call the static clear() methods in each DAO, need to add GameDAO after I create it
            GameDAO.clear();
            AuthDAO.clear();
            UserDAO.clear();

            // Return success (empty response)
            return new ApiResponse(null);
        } catch (Exception e) {
            // Return an error response if something goes wrong
            return new ApiResponse("Error: failed to clear the database");
        }
    }
}

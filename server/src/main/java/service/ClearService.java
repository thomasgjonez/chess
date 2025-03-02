package service;

import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import model.ClearResult;

public class ClearService  {
    public ClearResult clear() {
        try {
            // Call the static clear() methods in each DAO, need to add GameDAO after I create it
            UserDAO.clear();
            AuthDAO.clear();
            GameDAO.clear();

            // Return success (empty response)
            return new ClearResult(null);
        } catch (Exception e) {
            // Return an error response if something goes wrong
            return new ClearResult("Error: failed to clear the database");
        }
    }
}

package service;

import model.ClearResult;
import model.LogoutRequest;
import dataaccess.AuthDAO;

public class LogoutService extends BaseService{
    public ClearResult logout(LogoutRequest req){
        try {
            if (!AuthDAO.isValidAuth(req.authToken())) {
                return new ClearResult("Error: unauthorized");
            }

            AuthDAO.deleteAuth(req.authToken());
            return new ClearResult(null); // Empty success response

        } catch (Exception e) {
            return new ClearResult("Error: internal server error");
        }
    }
}

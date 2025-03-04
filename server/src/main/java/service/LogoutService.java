package service;

import model.SuccessResult;
import model.LogoutRequest;
import dataaccess.AuthDAO;

public class LogoutService extends BaseService{
    public SuccessResult logout(LogoutRequest req){
        try {
            if (!AuthDAO.isValidAuth(req.authToken())) {
                return new SuccessResult("Error: unauthorized");
            }

            AuthDAO.deleteAuth(req.authToken());
            return new SuccessResult(null); // Empty success response

        } catch (Exception e) {
            return new SuccessResult("Error: internal server error");
        }
    }
}

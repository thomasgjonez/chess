package service;

import dataaccess.GameDAO;
import model.GameData;
import model.SuccessResult;
import model.LogoutRequest;
import dataaccess.AuthDAO;

import java.util.List;

public class LogoutService extends BaseService{
    public SuccessResult logout(LogoutRequest req){
        try {
            if (!AuthDAO.isValidAuth(req.authToken())) {
                return new SuccessResult("Error: unauthorized");
            }

            AuthDAO.deleteAuth(req.authToken());
            return new SuccessResult(null);

        } catch (Exception e) {
            return new SuccessResult("Error: internal server error");
        }
    }
}

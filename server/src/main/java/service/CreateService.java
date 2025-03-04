package service;

import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import model.CreateRequest;
import model.CreateResult;

public class CreateService extends BaseService {
    public CreateResult create(String authToken, CreateRequest req){
        try{
            if (!AuthDAO.isValidAuth(authToken)) {
                return new CreateResult(null,"Error: unauthorized");
            }

            int gameID = GameDAO.createGame(req.gameName());
            return new CreateResult(Integer.toString(gameID), null);

        }catch (Exception e) {
            return new CreateResult(null,"Error: internal server error");
        }
    }
}

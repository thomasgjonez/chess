package service;

import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import model.CreateRequest;
import model.CreateResponse;

public class CreateService extends BaseService {
    public CreateResponse create(String authToken, CreateRequest req){
        try{
            if (!AuthDAO.isValidAuth(authToken)) {
                return new CreateResponse(null,"Error: unauthorized");
            }

            int gameID = GameDAO.createGame(req.gameName());
            return new CreateResponse(Integer.toString(gameID), null);

        }catch (Exception e) {
            return new CreateResponse(null,"Error: internal server error");
        }
    }
}

package service;

import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import model.GameData;
import model.JoinGameRequest;
import model.SuccessResult;


public class JoinGameService extends BaseService {
    public SuccessResult joinGame(String authToken, JoinGameRequest joinGameRequest){
        if (!AuthDAO.isValidAuth(authToken)) {
            return new SuccessResult("Error: unauthorized");
        }

        int gameID;
        try {
            gameID = Integer.parseInt(joinGameRequest.gameID());
        } catch (NumberFormatException e) {
            return new SuccessResult("Error: bad request");
        }

        GameData gameData = GameDAO.getGame(gameID);
        if (gameData == null) {
            return new SuccessResult("Error: bad request");
        }

        String playerColor = joinGameRequest.playerColor().toUpperCase();
        String username = AuthDAO.getUsername(authToken); // Get username from authToken

        if (playerColor.equals("WHITE")) {
            if (gameData.whiteUser() != null) {
                return new SuccessResult("Error: already taken");
            }
            gameData = new GameData(gameID, username, gameData.blackUser(), gameData.gameName());
        } else if (playerColor.equals("BLACK")) {
            if (gameData.blackUser() != null) {
                return new SuccessResult("Error: already taken");
            }
            gameData = new GameData(gameID, gameData.whiteUser(), username, gameData.gameName());
        } else {
            return new SuccessResult("Error: bad request");
        }

        GameDAO.updateGame(gameData);

        return new SuccessResult(null);
    }
}

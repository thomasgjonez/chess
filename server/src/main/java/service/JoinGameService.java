package service;

import chess.ChessGame;
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
        String username = AuthDAO.getUsername(authToken);
        if (username == null) {
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

        ChessGame.TeamColor playerColor = joinGameRequest.playerColor();

        if (playerColor == ChessGame.TeamColor.WHITE) {
            if (gameData.whiteUsername() != null) {
                return new SuccessResult("Error: already taken");
            }
            gameData = new GameData(gameID, username, gameData.blackUsername(), gameData.gameName());
        } else if (playerColor == ChessGame.TeamColor.BLACK) {
            if (gameData.blackUsername() != null) {
                return new SuccessResult("Error: already taken");
            }
            gameData = new GameData(gameID, gameData.whiteUsername(), username, gameData.gameName());
        } else {
            return new SuccessResult("Error: bad request");
        }

        GameDAO.updateGame(gameData);

        return new SuccessResult(null);
    }
}

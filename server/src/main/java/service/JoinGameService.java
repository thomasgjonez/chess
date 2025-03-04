package service;

import chess.ChessGame;
import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import model.GameData;
import model.JoinGameRequest;
import model.ApiResponse;


public class JoinGameService extends BaseService {
    public ApiResponse joinGame(String authToken, JoinGameRequest joinGameRequest) {
        try {
            if (!AuthDAO.isValidAuth(authToken)) {
                return new ApiResponse("Error: unauthorized");
            }

            String username = AuthDAO.getUsername(authToken);
            if (username == null) {
                return new ApiResponse("Error: unauthorized");
            }

            int gameID;
            try {
                gameID = Integer.parseInt(joinGameRequest.gameID());
            } catch (NumberFormatException e) {
                return new ApiResponse("Error: bad request");
            }

            GameData gameData = GameDAO.getGame(gameID);
            if (gameData == null) {
                return new ApiResponse("Error: bad request");
            }

            ChessGame.TeamColor playerColor = joinGameRequest.playerColor();
            ChessGame gameInstance = gameData.game();

            if (playerColor == ChessGame.TeamColor.WHITE) {
                if (gameData.whiteUsername() != null) {
                    return new ApiResponse("Error: already taken");
                }
                gameData = new GameData(gameID, username, gameData.blackUsername(), gameData.gameName(), gameInstance);
            } else if (playerColor == ChessGame.TeamColor.BLACK) {
                if (gameData.blackUsername() != null) {
                    return new ApiResponse("Error: already taken");
                }
                gameData = new GameData(gameID, gameData.whiteUsername(), username, gameData.gameName(), gameInstance);
            } else {
                return new ApiResponse("Error: bad request");
            }

            GameDAO.updateGame(gameData);

            return new ApiResponse(null);
        } catch (Exception e) {
            return new ApiResponse("Error: internal server error");
        }
    }
}

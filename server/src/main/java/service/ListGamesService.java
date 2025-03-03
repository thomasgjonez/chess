package service;

import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import model.GameData;
import model.ListGamesResult;

import java.util.List;

public class ListGamesService extends BaseService{
    public ListGamesResult listGames(String authToken){
        try{
            if (!AuthDAO.isValidAuth(authToken)) {
                return null;
            }

            List<GameData> games =  GameDAO.listGames();
            return new ListGamesResult(games);
        } catch (Exception e) {
            return null;
        }

    }

}

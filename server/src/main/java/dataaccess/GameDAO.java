package dataaccess;

import model.GameData;

import java.util.HashMap;
import java.util.Map;

public class GameDAO {
    private static final Map<Integer, GameData> games = new HashMap<>();
    private static int nextGameID = 1000;

    public static int createGame(String GameName){
        int gameID = nextGameID++;
        GameData game = new GameData(gameID, null, null, GameName);
        games.put(gameID, game);
        return gameID;
    }

    public static GameData getGame(int gameID){
        return games.get(gameID);
    }

    public static void clear(){
        games.clear();
        nextGameID = 1000;
    }

}

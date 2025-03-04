package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameDAO {
    private static final Map<Integer, GameData> GAMES = new HashMap<>();
    private static int nextGameID = 1000;

    public static int createGame(String gameName){
        int gameID = nextGameID++;
        ChessGame newGame = new ChessGame();
        GameData game = new GameData(gameID, null, null, gameName, newGame);
        GAMES.put(gameID, game);
        return gameID;
    }

    public static GameData getGame(int gameID) {
        return GAMES.get(gameID);
    }

    public static List<GameData> listGames(){
        return new ArrayList<>(GAMES.values());
    }

    public static void clear(){
        GAMES.clear();
        nextGameID = 1000;
    }

    public static void updateGame(GameData gameData) {
        if (GAMES.containsKey(gameData.gameID())) {
            GAMES.put(gameData.gameID(), gameData);
        }
    }
}

package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class GameDAO {

    public static int createGame(String gameName) throws DataAccessException {
        String query = "INSERT INTO GameTable (gameName, whiteUsername, blackUsername, gameState ) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ChessGame game = new ChessGame();
            String gameStateJson = new Gson().toJson(game);

            stmt.setString(1, gameName);
            stmt.setNull(2, Types.VARCHAR);
            stmt.setNull(3,Types.VARCHAR);
            stmt.setString(4,gameStateJson);
            stmt.executeUpdate();

            // Retrieve the generated gameID
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Return the new gameID
                } else {
                    throw new DataAccessException("Creating game failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            System.out.println("SQL Error in UserDAO.clear(): " + e.getMessage());
            throw new DataAccessException("Database error: " + e.getMessage());
        }
    }

    public static GameData getGame(int gameID) throws DataAccessException{
        String query = "SELECT * FROM GameTable WHERE gameID = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setInt(1,gameID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                ChessGame chessGame = new Gson().fromJson(rs.getString("gameState"),ChessGame.class);

                return new GameData(
                        rs.getInt("gameID"),
                        rs.getString("whiteUsername"),
                        rs.getString("blackUsername"),
                        rs.getString("gameName"),
                        chessGame
                );
            } else{
                return null;
            }
        } catch (SQLException e) {
            throw new DataAccessException("Database error: " + e.getMessage());
        }
    }

    public static List<GameData> listGames() throws DataAccessException{
        List<GameData> gameList = new ArrayList<>();
        String query = "SELECT gameID, whiteUsername, blackUsername, gameName, gameState FROM GameTable";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)){

            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                gameList.add(new GameData(
                        rs.getInt("gameID"),
                        rs.getString("whiteUsername"),
                        rs.getString("blackUsername"),
                        rs.getString("gameName"),
                        null//don't need actual game data
                ));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Database error: " + e.getMessage());
        }
        return gameList;
    }

    public static void updateGame(GameData gameData) throws DataAccessException{
        String query = "UPDATE GameTable SET whiteUsername = ?, blackUsername = ? WHERE gameID = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, gameData.whiteUsername());
            stmt.setString(2, gameData.blackUsername());
            stmt.setInt(3, gameData.gameID());

            stmt.executeUpdate();

        } catch (SQLException e){
            throw new DataAccessException("Database error: " + e.getMessage());
        }
    }

    public static void clear() throws DataAccessException {
        String query = "DELETE FROM GameTable";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL Error in UserDAO.clear(): " + e.getMessage());
            throw new DataAccessException("Database error: " + e.getMessage());
        }
    }

}

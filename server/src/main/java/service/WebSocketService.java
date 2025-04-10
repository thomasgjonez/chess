package service;

import chess.ChessGame;
import com.google.gson.Gson;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import model.AuthData;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import websocket.commands.ConnectCommand;
import websocket.messages.LoadGame;
import websocket.messages.Notification;
import websocket.messages.ServerMessage;
import websocket.messages.Error;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketService {
    private final Gson gson = new Gson();

    private final Map<Integer, List<Session>> gameSessions = new ConcurrentHashMap<>();
    private final Map<Session, Integer> sessionToGameMap = new ConcurrentHashMap<>();

    public void connect(Session session, ConnectCommand command) throws IOException, DataAccessException {
        // Validate token
        if (!AuthDAO.isValidAuth(command.getAuthToken())) {
            send(session, new Error("Error: Invalid auth token"));
            return;
        }

        //Get game from DAO
        GameData game = GameDAO.getGame(command.getGameID());
        if (game == null) {
            send(session, new Error("Error: Game not found"));
            return;
        }

        // Track session
        gameSessions.computeIfAbsent(command.getGameID(), k -> new ArrayList<>()).add(session);
        sessionToGameMap.put(session, command.getGameID());

        // Send LOAD_GAME to client
        ChessGame chessGame = game.game(); // or parse game.getGameJSON()
        send(session, new LoadGame(chessGame));

        // Notify others
        String username = AuthDAO.getUsername(command.getAuthToken());
        String msg = username + " joined game " + command.getGameID();
        broadcast(command.getGameID(), new Notification(msg), session);

        System.out.println("success?");


    }

    private void send(Session session, ServerMessage message) throws IOException {
        if (session != null && session.isOpen()) {
            session.getRemote().sendString(gson.toJson(message));
        }
    }

    private void broadcast(int gameID, ServerMessage message, Session exclude) {
        var sessions = gameSessions.getOrDefault(gameID, new ArrayList<>());
        for (Session s : sessions) {
            if (s.isOpen() && !s.equals(exclude)) {
                try {
                    s.getRemote().sendString(gson.toJson(message));
                } catch (IOException e) {
                    System.err.println("Failed to broadcast: " + e.getMessage());
                }
            }
        }
    }
}

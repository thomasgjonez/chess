package service;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;
import chess.InvalidMoveException;
import com.google.gson.Gson;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import model.AuthData;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import websocket.commands.ConnectCommand;
import websocket.commands.LeaveCommand;
import websocket.commands.MakeMoveCommand;
import websocket.commands.ResignCommand;
import websocket.messages.LoadGame;
import websocket.messages.Notification;
import websocket.messages.ServerMessage;
import websocket.messages.Error;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketService extends BaseService {
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
        ChessGame chessGame = game.game();
        send(session, new LoadGame(chessGame));

        // Notify others
        String username = AuthDAO.getUsername(command.getAuthToken());
        String msg = username + " joined game " + command.getGameID();
        broadcast(command.getGameID(), new Notification(msg), session);

        System.out.println("success?");


    }

    public void makeMove(Session session, MakeMoveCommand command) throws IOException {
        try {
            if (!AuthDAO.isValidAuth(command.getAuthToken())) {
                send(session, new Error("Error: Invalid auth token"));
                return;
            }

            String username = AuthDAO.getUsername(command.getAuthToken());
            GameData game = GameDAO.getGame(command.getGameID());

            if (game == null) {
                send(session, new Error("Error: Game not found"));
                return;
            }

            ChessGame chessGame = game.game();

            if (chessGame.isGameOver()) {
                System.out.println("chess game is over");
                send(session, new Error("Error: Game is already over"));
                return;
            }

            ChessGame.TeamColor userColor = null;
            if (username.equals(game.whiteUsername())) {
                userColor = ChessGame.TeamColor.WHITE;
            } else if (username.equals(game.blackUsername())) {
                userColor = ChessGame.TeamColor.BLACK;
            } else {
                send(session, new Error("Error: You are observing this game"));
                return;
            }

            if (!chessGame.getTeamTurn().equals(userColor)) {
                send(session, new Error("Error: It's not your turn"));
                return;
            }

            ChessMove move = command.getMove();
            chessGame.makeMove(move);
            System.out.println("After move: " + chessGame);

            ChessGame.TeamColor opponent = chessGame.getTeamTurn();

            String notificationMessage;

            if (chessGame.isInCheckmate(opponent)) {
                chessGame.setGameOver(true);
                notificationMessage = "Checkmate! " + username + " wins!";
            } else if (chessGame.isInStalemate(opponent)) {
                chessGame.setGameOver(true);
                notificationMessage = "Stalemate! It's a draw!";
            } else if (chessGame.isInCheck(opponent)) {
                notificationMessage = username + " made a move — " + opponent + " is in check!";
            } else {
                notificationMessage = username + " made a move.";
            }
//            System.out.println("After move (before DB save):");
//            System.out.println("Turn: " + chessGame.getTeamTurn());
//            System.out.println("Game over? " + chessGame.isGameOver());
//            System.out.println(chessGame);
            GameDAO.updateGame(new GameData(
                    game.gameID(),
                    game.whiteUsername(),
                    game.blackUsername(),
                    game.gameName(),
                    chessGame
            ));

//            GameData justSaved = GameDAO.getGame(game.gameID());
//            System.out.println("After fetching back from DB:");
//            System.out.println("Turn: " + justSaved.game().getTeamTurn());
//            System.out.println("Game over? " + justSaved.game().isGameOver());
//            System.out.println(justSaved.game());

            broadcast(command.getGameID(), new LoadGame(chessGame), null);
            broadcast(command.getGameID(), new Notification(notificationMessage), session);

        } catch (InvalidMoveException e) {
            send(session, new Error("Error: Invalid move- " + e.getMessage()));
        } catch (DataAccessException e) {
            send(session, new Error("Error: Database failure- " + e.getMessage()));
        }
    }


    public void resign(Session session, ResignCommand command) throws IOException {
        try {
            // Validate auth
            if (!AuthDAO.isValidAuth(command.getAuthToken())) {
                send(session, new Error("Error: Invalid auth token"));
                return;
            }

            String username = AuthDAO.getUsername(command.getAuthToken());

            //  Get game
            GameData game = GameDAO.getGame(command.getGameID());
            if (game == null) {
                send(session, new Error("Error: Game not found"));
                return;
            }

            ChessGame chessGame = game.game();

            if (chessGame.isGameOver()) {
                send(session, new Error("Error: Game is already over"));
                return;
            }

            //Ensure the user is a player (not observer)
            if (!username.equals(game.whiteUsername()) && !username.equals(game.blackUsername())) {
                send(session, new Error("Error: Observers cannot resign"));
                return;
            }

            // Set game as over
            chessGame.setGameOver(true);

            GameData updatedGame = new GameData(
                    game.gameID(),
                    game.whiteUsername(),
                    game.blackUsername(),
                    game.gameName(),
                    chessGame
            );
            GameDAO.updateGame(updatedGame);

            //Broadcast the resignation
            String msg = username + " has resigned. Game over!";
            broadcast(command.getGameID(), new Notification(msg), null);

        } catch (DataAccessException e) {
            send(session, new Error("Error: Database failure"));
        }
    }

    public void leave(Session session, LeaveCommand command) throws IOException {
        try{
            if (!AuthDAO.isValidAuth(command.getAuthToken())) {
                send(session, new Error("Error: Invalid auth token"));
                return;
            }

            String username = AuthDAO.getUsername(command.getAuthToken());

            GameData game = GameDAO.getGame(command.getGameID());
            if (game == null) {
                send(session, new Error("Error: Game not found"));
                return;
            }

            String newWhite = username.equals(game.whiteUsername()) ? null : game.whiteUsername();
            String newBlack = username.equals(game.blackUsername()) ? null : game.blackUsername();

            GameData updated = new GameData(
                    game.gameID(),
                    newWhite,
                    newBlack,
                    game.gameName(),
                    game.game()
            );
            GameDAO.updateGame(updated);

            Notification left = new Notification(username + " left the game.");
            broadcast(command.getGameID(), left, session);

            session.close();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }

    }


    private void send(Session session, ServerMessage message) throws IOException {
        if (session != null && session.isOpen()) {
            session.getRemote().sendString(gson.toJson(message));
        }
    }

    private void broadcast(int gameID, ServerMessage message, Session exclude) {
        var sessions = gameSessions.getOrDefault(gameID, new ArrayList<>());
        for (Session session : sessions) {
            if (session.isOpen() && !session.equals(exclude)) {
                try {
                    session.getRemote().sendString(gson.toJson(message));
                } catch (IOException e) {
                    System.err.println("Failed to broadcast: " + e.getMessage());
                }
            }
        }
    }
}

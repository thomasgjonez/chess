package clients;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;
import model.AuthData;
import ui.EscapeSequences;
import ui.ConsoleBoard;
import websocket.WebsocketCommunicator;
import websocket.commands.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class GameClient {
    private final String playerColor;
    private final WebsocketCommunicator socket;
    private final String authToken;
    private final int gameID;
    private ChessGame currentGame;

    public GameClient(String serverUrl, String playerColor, AuthData authData, Integer gameID) throws Exception {
        this.playerColor = playerColor;
        this.authToken = authData.authToken();
        this.gameID = gameID;
        this.socket = new WebsocketCommunicator(serverUrl, this);

        var connect = new ConnectCommand(authToken, gameID);
        socket.sendCommand(connect);
    }
    public void setCurrentGame(ChessGame game) {
        this.currentGame = game;
    }

    public ChessGame getCurrentGame() {
        return this.currentGame;
    }

    public String eval(String input) {
        var tokens = input.toLowerCase().split(" ");
        var cmd = (tokens.length > 0) ? tokens[0] : "help";
        var params = Arrays.copyOfRange(tokens, 1, tokens.length);
        return switch (cmd) {
            case "leave" -> leave();
            case "redraw" -> { printGame(); yield ""; }
            case "highlight" -> highlight(params);
            case "move" -> move(params);
            case "resign" -> resign();
            default -> help();
        };
    }

    public String leave(){
        socket.sendCommand(new LeaveCommand(authToken, gameID));
        return "leave";
    }

    private String resign() {
        socket.sendCommand(new ResignCommand(authToken, gameID));
        return "You resigned the game.";
    }

    private String move(String[] params) {
        if (params.length != 2) {
            return "Usage: move <from> <to> (e.g. move e2 e4)";
        }

        try {
            ChessPosition start = parsePosition(params[0]);
            ChessPosition end = parsePosition(params[1]);
            ChessMove move = new ChessMove(start, end, null); // promotion null for now

            socket.sendCommand(new MakeMoveCommand(authToken, gameID, move));

            return "Move sent: " + params[0] + " to " + params[1];
        } catch (Exception e) {
            return "Invalid move format.";
        }
    }

    private String highlight(String[] params){
        //will need to return the array of possible moves, draw the board and while drawing highlight the appropriate ones
        if (params.length != 1) {
            return "Usage: highlight <position> (e.g. highlight e2)\n";
        }

        try {
            ChessPosition pos = parsePosition(params[0]);
            Collection<ChessMove> legalMoves = currentGame.validMoves(pos);

            if (legalMoves == null || legalMoves.isEmpty()) {
                return "No legal moves for that piece.\n";
            }

            Set<ChessPosition> highlights = new HashSet<>();
            for (ChessMove move : legalMoves) {
                highlights.add(move.getEndPosition());
            }

            System.out.println("Rendering board for: " + playerColor);
            ChessGame.TeamColor color = (playerColor.equalsIgnoreCase("WHITE")) ? ChessGame.TeamColor.WHITE : ChessGame.TeamColor.BLACK;
            ConsoleBoard consoleBoard = new ConsoleBoard(currentGame.getBoard(), color);
            consoleBoard.renderBoard(pos, highlights);

            return "";
        } catch (IllegalArgumentException e) {
            return "Invalid position format. Use something like e2.\n";
        }
    }

    public void printGame(){
        //maybe I should just get the actual ChessGame instance, which will have everything i need, but I'll do that phase 6
        if (currentGame == null) {
            System.out.println("Game not yet loaded. Try again in a moment.");
            return;
        }

        ChessGame.TeamColor color = (playerColor.equalsIgnoreCase("WHITE")) ? ChessGame.TeamColor.WHITE : ChessGame.TeamColor.BLACK;
        ConsoleBoard consoleBoard = new ConsoleBoard(currentGame.getBoard(), color);
        consoleBoard.renderBoard(null, null);
    }

    public String help(){
        return  "- " + EscapeSequences.SET_TEXT_BOLD + EscapeSequences.SET_TEXT_COLOR_GREEN + "Redraw" + EscapeSequences.RESET_TEXT_COLOR +
                EscapeSequences.RESET_TEXT_BOLD_FAINT +
                " - redraw chess board\n" +

                "- " + EscapeSequences.SET_TEXT_BOLD + EscapeSequences.SET_TEXT_COLOR_GREEN + "Highlight" + EscapeSequences.SET_TEXT_COLOR_BLUE +
                " <PIECEPOS>" + EscapeSequences.RESET_TEXT_COLOR + EscapeSequences.RESET_TEXT_BOLD_FAINT +
                " - highlights the legal moves a specific piece\n" +

                "- " + EscapeSequences.SET_TEXT_BOLD + EscapeSequences.SET_TEXT_COLOR_GREEN + "Move" + EscapeSequences.SET_TEXT_COLOR_BLUE +
                " <PIECEPOS> <TARGETPOS>" + EscapeSequences.RESET_TEXT_COLOR + EscapeSequences.RESET_TEXT_BOLD_FAINT +
                " - make a move from piece position to target position\n" +

                "- " + EscapeSequences.SET_TEXT_BOLD + EscapeSequences.SET_TEXT_COLOR_GREEN + "Resign" + EscapeSequences.RESET_TEXT_COLOR +
                EscapeSequences.RESET_TEXT_BOLD_FAINT +
                " - forfeit the game\n" +

                "- " + EscapeSequences.SET_TEXT_BOLD + EscapeSequences.SET_TEXT_COLOR_GREEN + "Leave" + EscapeSequences.RESET_TEXT_COLOR +
                EscapeSequences.RESET_TEXT_BOLD_FAINT +
                " - exit game\n" +

                "- " + EscapeSequences.SET_TEXT_BOLD + EscapeSequences.SET_TEXT_COLOR_GREEN + "Help" + EscapeSequences.RESET_TEXT_COLOR +
                EscapeSequences.RESET_TEXT_BOLD_FAINT +
                " - list possible commands\n";
    }

    private ChessPosition parsePosition(String pos) {
        int col = pos.charAt(0) - 'a' + 1;
        int row = Integer.parseInt(pos.substring(1));
        return new ChessPosition(row, col);
    }
}

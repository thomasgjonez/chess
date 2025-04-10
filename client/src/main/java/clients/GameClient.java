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

public class GameClient {
    private String playerColor;
    private WebsocketCommunicator socket;
    private String authToken;
    private int gameID;

    public GameClient(String serverUrl, String playerColor, AuthData authData, Integer gameID) throws Exception {
        this.playerColor = playerColor;
        this.authToken = authData.authToken();
        this.gameID = gameID;
        this.socket = new WebsocketCommunicator(serverUrl);

        var connect = new ConnectCommand(authToken, gameID);
        socket.sendCommand(connect);
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
        return "highlight was called";
    }

    public void printGame(){
        //maybe I should just get the actual ChessGame instance, which will have everything i need, but I'll do that phase 6
        ChessBoard board = new ChessBoard();// will need to change in the future probs
        board.resetBoard();//this sets the game board up

        ChessGame.TeamColor color = (playerColor.toUpperCase().equals("WHITE")) ? ChessGame.TeamColor.WHITE : ChessGame.TeamColor.BLACK;
        ConsoleBoard game = new ConsoleBoard(board, color);
        game.renderBoard();
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

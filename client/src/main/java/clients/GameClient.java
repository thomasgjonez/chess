package clients;

import chess.*;
import model.AuthData;
import ui.EscapeSequences;
import ui.ConsoleBoard;
import websocket.WebsocketCommunicator;
import websocket.commands.*;

import java.util.*;

import static ui.EscapeSequences.*;

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
        socket.close();
        return "leave\n";
    }

    private String resign() {
        System.out.println(SET_TEXT_BOLD +
                "Are you sure you want to Resign?" +
                SET_TEXT_COLOR_BLUE + " (YES/NO)" +
                RESET_TEXT_COLOR + RESET_TEXT_BOLD_FAINT);
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        var tokens = line.toLowerCase().split(" ");
        var cmd = (tokens.length > 0) ? tokens[0] : "help";

        if (cmd.equalsIgnoreCase("yes")) {
            socket.sendCommand(new ResignCommand(authToken, gameID));
            return "You resigned the game.\n";
        }
        return "";
    }


    private String move(String[] params) {
        if (playerColor.equalsIgnoreCase("OBSERVER")){
            return "Forbidden: Observers cannot make moves\n";
        }
        if (params.length < 2 || params.length > 3 ) {
            return "Usage: move <PIECEPOS> <TARGETPOS> <PROMOTION PIECE>(e.g. move e2 e4)\n";
        }

        try {
            ChessPosition start = parsePosition(params[0]);
            ChessPosition end = parsePosition(params[1]);
            ChessPiece.PieceType promoPiece = null;

            if (params.length == 3 && params[2] != null && !params[2].isBlank()) {
                promoPiece = convertPieceFromString(params[2].trim());
                if (promoPiece == null) {
                    return "Invalid promotion piece. Must be one of: queen, rook, bishop, knight\n";
                }
            }

            ChessMove move = new ChessMove(start, end, promoPiece);
            socket.sendCommand(new MakeMoveCommand(authToken, gameID, move));

            return "Move submitted: " + params[0] + " to " + params[1]+ "\n";
        } catch (Exception e) {
            return "Invalid move format.\n";
        }
    }

    private String highlight(String[] params){
        //will need to return the array of possible moves, draw the board and while drawing highlight the appropriate ones
        if (params.length != 1) {
            return "Usage: highlight <PIECEPOS> (e.g. highlight e2)\n";
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
            ChessGame.TeamColor color = (playerColor.equalsIgnoreCase("BLACK")) ? ChessGame.TeamColor.BLACK : ChessGame.TeamColor.WHITE;
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
            System.out.println("Game not yet loaded. Try again in a moment.\n");
            return;
        }

        ChessGame.TeamColor color = (playerColor.equalsIgnoreCase("BlACK")) ? ChessGame.TeamColor.BLACK : ChessGame.TeamColor.WHITE;
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
                " <PIECEPOS> <TARGETPOS> <PROMOTIONPIECE>" + EscapeSequences.RESET_TEXT_COLOR + EscapeSequences.RESET_TEXT_BOLD_FAINT +
                " - make a move from piece position to target position and specify which promotion piece if applicable\n" +

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
    public void onError(String errorMessage) {
        System.out.println(errorMessage);
    }

    private ChessPosition parsePosition(String pos) {
        int col = pos.charAt(0) - 'a' + 1;
        int row = Integer.parseInt(pos.substring(1));
        return new ChessPosition(row, col);
    }

    private ChessPiece.PieceType convertPieceFromString(String piece){
        return switch (piece.toLowerCase()) {
            case "queen" -> ChessPiece.PieceType.QUEEN;
            case "bishop" -> ChessPiece.PieceType.BISHOP;
            case "knight" -> ChessPiece.PieceType.KNIGHT;
            case "rook" -> ChessPiece.PieceType.ROOK;
            default -> null;
        };
    }

}

package clients;

import chess.ChessBoard;
import chess.ChessGame;
import ui.EscapeSequences;
import ui.ConsoleBoard;
import java.util.Arrays;

public class GameClient {
    private String playerColor;
    public GameClient(String serverUrl, String playerColor){
        this.playerColor = playerColor;
    }

    public String eval(String input) {
        var tokens = input.toLowerCase().split(" ");
        var cmd = (tokens.length > 0) ? tokens[0] : "help";
        var params = Arrays.copyOfRange(tokens, 1, tokens.length);
        return switch (cmd) {
            //will add in more commands in phase 6, I think
            case "leave" -> "leave";
           // case "redraw" -> printGame();
//            case "highlight" -> highlight();
//            case "move" -> move();
//            case " resign" -> resign();
            default -> help();
        };
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


}

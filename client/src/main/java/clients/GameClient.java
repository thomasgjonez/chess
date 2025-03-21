package clients;

import ui.EscapeSequences;
import ui.ChessBoard;
import java.util.Arrays;

public class GameClient {
    public GameClient(String serverUrl){
    }

    public String eval(String input) {
        var tokens = input.toLowerCase().split(" ");
        var cmd = (tokens.length > 0) ? tokens[0] : "help";
        var params = Arrays.copyOfRange(tokens, 1, tokens.length);
        return switch (cmd) {
            //will add in more commands in phase 6, I think
            case "quit" -> "quit";
            default -> help();
        };
    }
    public void printGame(){
        ChessBoard game = new ChessBoard("WHITE");
        game.printBoard();
    }

    public String help(){
        return  "- " + EscapeSequences.SET_TEXT_BOLD + EscapeSequences.SET_TEXT_COLOR_GREEN + "Quit" + EscapeSequences.RESET_TEXT_COLOR +
                EscapeSequences.RESET_TEXT_BOLD_FAINT +
                " - exit game\n" +

                "- " + EscapeSequences.SET_TEXT_BOLD + EscapeSequences.SET_TEXT_COLOR_GREEN + "Help" + EscapeSequences.RESET_TEXT_COLOR +
                EscapeSequences.RESET_TEXT_BOLD_FAINT +
                " - list possible commands\n";
    }


}

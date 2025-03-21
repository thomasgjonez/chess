package clients;

import ui.EscapeSequences;
import ui.PreLoginRepl;

import java.util.Arrays;

public class PostLoginClient {
    public PostLoginClient(String serverUrl) {
        //this will be spot of to initate a server facade with the serverURL
    }

    public String eval(String input) {
        var tokens = input.toLowerCase().split(" ");
        var cmd = (tokens.length > 0) ? tokens[0] : "help";
        var params = Arrays.copyOfRange(tokens, 1, tokens.length);
        return switch (cmd) {
            case "create " -> createGame(params);
            case "list" -> listGames();
            case "join" -> joinGame(params);
            case "observe" -> observeGame(params);
            case "logout" -> logout(params);
            case "quit" -> "quit";
            default -> help();
        };
    }

    public String createGame(String... params){
        return "createGame was called";
    }

    public String listGames(String... params){
        return "listGames was called";
    }

    public String joinGame(String... params){
        return "joinGame was called";
    }

    public String observeGame(String... params){
        return "observeGame was called";
    }

    public String logout(String... params){
        return "logout was called";
    }


    public String help() {
        return "- " + EscapeSequences.SET_TEXT_BOLD + EscapeSequences.SET_TEXT_COLOR_GREEN + "Create" + EscapeSequences.RESET_TEXT_COLOR +
                EscapeSequences.RESET_TEXT_BOLD_FAINT +
                " " + EscapeSequences.SET_TEXT_BOLD + EscapeSequences.SET_TEXT_COLOR_BLUE + "<NAME>" +
                EscapeSequences.RESET_TEXT_COLOR + EscapeSequences.RESET_TEXT_BOLD_FAINT +
                " - to create an a chess game\n" +

                "- " + EscapeSequences.SET_TEXT_BOLD + EscapeSequences.SET_TEXT_COLOR_GREEN + "List" + EscapeSequences.RESET_TEXT_COLOR +
                EscapeSequences.RESET_TEXT_BOLD_FAINT +
                " - to list all current chess games\n" +

                "- " + EscapeSequences.SET_TEXT_BOLD + EscapeSequences.SET_TEXT_COLOR_GREEN + "Join" + EscapeSequences.RESET_TEXT_COLOR +
                EscapeSequences.RESET_TEXT_BOLD_FAINT +
                " " + EscapeSequences.SET_TEXT_BOLD + EscapeSequences.SET_TEXT_COLOR_BLUE + "<ID>" +
                EscapeSequences.SET_TEXT_COLOR_MAGENTA + " [WHITE|BLACK]" +
                EscapeSequences.RESET_TEXT_COLOR + EscapeSequences.RESET_TEXT_BOLD_FAINT +
                " - to join a chess game as either WHITE or BLACK player\n" +

                "- " + EscapeSequences.SET_TEXT_BOLD + EscapeSequences.SET_TEXT_COLOR_GREEN + "Observe" + EscapeSequences.RESET_TEXT_COLOR +
                EscapeSequences.RESET_TEXT_BOLD_FAINT +
                " " + EscapeSequences.SET_TEXT_BOLD + EscapeSequences.SET_TEXT_COLOR_BLUE + "<ID> " +
                EscapeSequences.RESET_TEXT_COLOR + EscapeSequences.RESET_TEXT_BOLD_FAINT +
                " - to join a chess game as an observer\n" +

                "- " + EscapeSequences.SET_TEXT_BOLD + EscapeSequences.SET_TEXT_COLOR_GREEN + "Logout" + EscapeSequences.RESET_TEXT_COLOR +
                EscapeSequences.RESET_TEXT_BOLD_FAINT +
                " - to logout \n" +

                "- " + EscapeSequences.SET_TEXT_BOLD + EscapeSequences.SET_TEXT_COLOR_GREEN + "Quit" + EscapeSequences.RESET_TEXT_COLOR +
                EscapeSequences.RESET_TEXT_BOLD_FAINT +
                " - exit game menu\n" +

                "- " + EscapeSequences.SET_TEXT_BOLD + EscapeSequences.SET_TEXT_COLOR_GREEN + "Help" + EscapeSequences.RESET_TEXT_COLOR +
                EscapeSequences.RESET_TEXT_BOLD_FAINT +
                " - list possible commands\n";
    }
}
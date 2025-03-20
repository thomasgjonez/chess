package clients;

import ui.EscapeSequences;
import ui.PreLoginRepl;

import java.util.Arrays;

public class PreLoginClient {
    public PreLoginClient(String serverUrl) {
        //this will be spot of to initate a server facade with the serverURL
    }

    public String eval(String input) {
        var tokens = input.toLowerCase().split(" ");
        var cmd = (tokens.length > 0) ? tokens[0] : "help";
        var params = Arrays.copyOfRange(tokens, 1, tokens.length);
        return switch (cmd) {
            case "register" -> register(params);
            case "login" -> login(params);
            case "quit" -> "quit";
            default -> help();
        };
    }

    public String register(String... params){
        return "register was called";
    }

    public String login(String... params){
        return "login was called";
    }

    public String help() {
        return """
                - register"""+ EscapeSequences.SET_TEXT_BOLD+ EscapeSequences.SET_TEXT_COLOR_DARK_GREY+ """ <USERNAME> <PASSWORD> <EMAIL>""" + EscapeSequences.RESET_TEXT_BOLD_FAINT + EscapeSequences.RESET_TEXT_COLOR+ """ - to create an account
                - login"""+ EscapeSequences.SET_TEXT_BOLD+ EscapeSequences.SET_TEXT_COLOR_DARK_GREY+ """ <USERNAME> <PASSWORD>""" + EscapeSequences.RESET_TEXT_BOLD_FAINT + EscapeSequences.RESET_TEXT_COLOR+ """- to play chess
                - quit - exit program
                - help - list possible commands
                """;
    }
}

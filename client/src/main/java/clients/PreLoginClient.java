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
        return "- " + EscapeSequences.SET_TEXT_BOLD + EscapeSequences.SET_TEXT_COLOR_GREEN+ "register" + EscapeSequences.RESET_TEXT_COLOR +
                EscapeSequences.RESET_TEXT_BOLD_FAINT +
                " " + EscapeSequences.SET_TEXT_BOLD + EscapeSequences.SET_TEXT_COLOR_BLUE + "<USERNAME> <PASSWORD> <EMAIL>" +
                EscapeSequences.RESET_TEXT_COLOR + EscapeSequences.RESET_TEXT_BOLD_FAINT +
                " - to create an account\n" +

                "- " + EscapeSequences.SET_TEXT_BOLD + EscapeSequences.SET_TEXT_COLOR_GREEN+ "login" + EscapeSequences.RESET_TEXT_COLOR +
                EscapeSequences.RESET_TEXT_BOLD_FAINT +
                " " + EscapeSequences.SET_TEXT_BOLD + EscapeSequences.SET_TEXT_COLOR_BLUE + "<USERNAME> <PASSWORD>" +
                EscapeSequences.RESET_TEXT_COLOR + EscapeSequences.RESET_TEXT_BOLD_FAINT +
                " - to play chess\n" +

                "- " + EscapeSequences.SET_TEXT_BOLD + EscapeSequences.SET_TEXT_COLOR_GREEN + "quit" + EscapeSequences.RESET_TEXT_COLOR +
                EscapeSequences.RESET_TEXT_BOLD_FAINT +
                " - exit program\n" +

                "- " + EscapeSequences.SET_TEXT_BOLD + EscapeSequences.SET_TEXT_COLOR_GREEN + "help" + EscapeSequences.RESET_TEXT_COLOR +
                EscapeSequences.RESET_TEXT_BOLD_FAINT +
                " - list possible commands";
    }



}

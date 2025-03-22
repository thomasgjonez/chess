package clients;

import model.AuthData;
import net.ResponseException;
import ui.EscapeSequences;
import net.ServerFacade;
import java.util.Arrays;

public class PreLoginClient {
    private final ServerFacade serverFacade;
    private AuthData authData;

    public PreLoginClient(String serverUrl) {
        this.serverFacade = new ServerFacade(serverUrl);
    }

    public String eval(String input) throws ResponseException {
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

    public String register(String... params) throws ResponseException {
        // error handling for register/ don't have enough args or do I pass on the error messages from the actual server?
        //make params an object
        if(params.length < 3){
            System.out.println("Need username, password, and email");
            return "register failed\n";
        }
        String username = params[0];
        String password = params[1];
        String email = params [2];
        System.out.println(username);
        System.out.println(password);
        System.out.println(email);
        this.authData = serverFacade.register(username, password, email);

        return "register success\n";
    }

    public String login(String... params){
        return "login success\n";
    }

    public String help() {
        return "- " + EscapeSequences.SET_TEXT_BOLD + EscapeSequences.SET_TEXT_COLOR_GREEN+ "Register" + EscapeSequences.RESET_TEXT_COLOR +
                EscapeSequences.RESET_TEXT_BOLD_FAINT +
                " " + EscapeSequences.SET_TEXT_BOLD + EscapeSequences.SET_TEXT_COLOR_BLUE + "<USERNAME> <PASSWORD> <EMAIL>" +
                EscapeSequences.RESET_TEXT_COLOR + EscapeSequences.RESET_TEXT_BOLD_FAINT +
                " - to create an account\n" +

                "- " + EscapeSequences.SET_TEXT_BOLD + EscapeSequences.SET_TEXT_COLOR_GREEN+ "Login" + EscapeSequences.RESET_TEXT_COLOR +
                EscapeSequences.RESET_TEXT_BOLD_FAINT +
                " " + EscapeSequences.SET_TEXT_BOLD + EscapeSequences.SET_TEXT_COLOR_BLUE + "<USERNAME> <PASSWORD>" +
                EscapeSequences.RESET_TEXT_COLOR + EscapeSequences.RESET_TEXT_BOLD_FAINT +
                " - to play chess\n" +

                "- " + EscapeSequences.SET_TEXT_BOLD + EscapeSequences.SET_TEXT_COLOR_GREEN + "Quit" + EscapeSequences.RESET_TEXT_COLOR +
                EscapeSequences.RESET_TEXT_BOLD_FAINT +
                " - exit program\n" +

                "- " + EscapeSequences.SET_TEXT_BOLD + EscapeSequences.SET_TEXT_COLOR_GREEN + "Help" + EscapeSequences.RESET_TEXT_COLOR +
                EscapeSequences.RESET_TEXT_BOLD_FAINT +
                " - list possible commands\n";
    }

    public AuthData getAuthData() {
        return authData;
    }
}

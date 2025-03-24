package clients;

import model.AuthData;
import model.CreateResult;
import model.GameData;
import model.ListGamesResult;
import net.ResponseException;
import net.ServerFacade;
import ui.EscapeSequences;
import ui.PreLoginRepl;

import java.util.Arrays;

public class PostLoginClient {
    private final ServerFacade serverFacade;
    private AuthData authData;

    public PostLoginClient(String serverUrl, AuthData authData) {
        this.serverFacade = new ServerFacade(serverUrl);
        this.authData = authData;

    }

    public String eval(String input) {
        var tokens = input.strip().split(" ");
        var cmd = (tokens.length > 0) ? tokens[0].toLowerCase() : "help";
        var params = Arrays.copyOfRange(tokens, 1, tokens.length);
        return switch (cmd) {
            case "create" -> createGame(params);
            case "list" -> listGames();
            case "join" -> joinGame(params);
            case "observe" -> observeGame(params);
            case "logout" -> logout();
            case "quit" -> "quit";
            default -> help();
        };
    }

    public String createGame(String... params){
        String gameName = params[0];
        System.out.println(gameName);
        try {
            CreateResult res = serverFacade.createGame(gameName, authData.authToken());
            String gameId = res.gameID();
            return "create game success. The gameID is " +
                    EscapeSequences.SET_TEXT_COLOR_BLUE +
                    gameId +
                    EscapeSequences.RESET_TEXT_COLOR + "\n";
        } catch (ResponseException e) {
            return "create game failed- " + e.getMessage() + "\n";
        }
    }

    public String listGames(){
        try {
            ListGamesResult res = serverFacade.listGames(authData.authToken());
            //res will be an array
            return "ListGame success\n";
        } catch (ResponseException e) {
            return "list game failed- " + e.getMessage() + "\n";
        }
    }

    public String joinGame(String... params) {
        String playerColor = params[0].toUpperCase();
        String gameId = params[1];
        try {
            GameData res = serverFacade.joinGame(playerColor, gameId, authData.authToken());
            return "join game success";
        } catch (ResponseException e) {
            return "join game failed- " + e.getMessage() + "\n";
        }
    }

    public String observeGame(String... params){
        // do nothing for right now, maybe I'll just have a variable that stores the GameState and observe Game just fetches that gameState
        return "observe success\n";
    }

    public String logout(){
        try {
            serverFacade.logout(authData.authToken());
            authData = null;
            return "logout success\n";
        } catch (ResponseException e) {
            return "join game failed- " + e.getMessage() + "\n";
        }
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
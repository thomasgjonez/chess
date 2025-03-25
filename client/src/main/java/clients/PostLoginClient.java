package clients;

import model.AuthData;
import model.CreateResult;
import model.GameData;
import model.ListGamesResult;
import net.ResponseException;
import net.ServerFacade;
import ui.EscapeSequences;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PostLoginClient {
    private final ServerFacade serverFacade;
    private AuthData authData;
    private final Map<Integer, GameData> gameIndexMap = new HashMap<>();

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
            GameData[] games = res.games().toArray(new GameData[0]);

            if (games.length == 0) {
                return "No games found.\n";
            }

            gameIndexMap.clear();

            StringBuilder output = new StringBuilder();
            output.append(EscapeSequences.SET_TEXT_BOLD+"Current Games:"+ EscapeSequences.RESET_TEXT_BOLD_FAINT +"\n");

            for (int i = 0; i < games.length; i++) {
                int displayIndex = i + 1;
                GameData game = games[i];
                gameIndexMap.put(displayIndex, game);
                output.append(String.format(
                        "%d. %sGAME NAME%s: %s\n", i + 1,
                        EscapeSequences.SET_TEXT_COLOR_BLUE, EscapeSequences.RESET_TEXT_COLOR,
                        game.gameName()
                ));

                output.append(String.format(
                        "   %sWHITE%s: %s\n",
                        EscapeSequences.SET_TEXT_COLOR_MAGENTA, EscapeSequences.RESET_TEXT_COLOR,
                        game.whiteUsername() != null ? game.whiteUsername() : "N/A"
                ));

                output.append(String.format(
                        "   %sBLACK%s: %s\n",
                        EscapeSequences.SET_TEXT_COLOR_MAGENTA, EscapeSequences.RESET_TEXT_COLOR,
                        game.blackUsername() != null ? game.blackUsername() : "N/A"
                ));
            }

            return output.toString();
        } catch (ResponseException e) {
            return "list game failed- " + e.getMessage() + "\n";
        }
    }

    public String joinGame(String... params) {
        if (params.length < 2) {
            return "Use: join <NUMBER> <WHITE|BLACK>\n";
        }

        try {
            int index = Integer.parseInt(params[0]); // from user input
            String color = params[1].toUpperCase();

            GameData game = gameIndexMap.get(index);
            if (game == null) {
                return "Invalid game number.\n";
            }

            String gameId = game.gameID().toString();
            GameData result = serverFacade.joinGame(color, gameId, authData.authToken());
            return "join game success\n";

        } catch (NumberFormatException e) {
            return "Invalid index: must be a number.\n";
        } catch (ResponseException e) {
            return "join game failed - " + e.getMessage() + "\n";
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
                " " + EscapeSequences.SET_TEXT_BOLD + EscapeSequences.SET_TEXT_COLOR_BLUE + "<GAME NAME>" +
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

                "- " + EscapeSequences.SET_TEXT_BOLD + EscapeSequences.SET_TEXT_COLOR_GREEN + "Help" + EscapeSequences.RESET_TEXT_COLOR +
                EscapeSequences.RESET_TEXT_BOLD_FAINT +
                " - list possible commands\n";
    }
}
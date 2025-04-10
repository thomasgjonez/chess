package ui;
import static ui.EscapeSequences.*;

import clients.PostLoginClient;
import clients.PreLoginClient;
import model.AuthData;
import model.GameData;

import java.util.Objects;
import java.util.Scanner;

public class PostLoginRepl {
    private final PostLoginClient client;
    private final String serverUrl;
    private final AuthData authData;

    public PostLoginRepl(String serverUrl, AuthData authData){
        this.serverUrl = serverUrl;
        this.client = new PostLoginClient(serverUrl, authData);
        this.authData = authData;
    }

    public boolean run() {
        System.out.println(SET_TEXT_BOLD+  "Welcome to Game Menu! " + WHITE_QUEEN);
        System.out.print(client.help());

        Scanner scanner = new Scanner(System.in);
        Object result = "";
        while (!Objects.equals(result, "quit")) {
            String line = scanner.nextLine();

            try {
                result = client.eval(line);

                if (result instanceof String strResult) {
                    System.out.print(strResult);

                    if (strResult.equals("logout success\n")) {
                        return true; // logout
                    }

                } else if (result instanceof GameData game) {
                    String[] parts = line.strip().split(" ");
                    String playerColor = parts.length > 2 ? parts[2].trim().toUpperCase() : "WHITE";

                    GameRepl gameRepl = new GameRepl(serverUrl, playerColor, authData, game.gameID());
                    System.out.println(game.gameID());
                    boolean returnedToPost = gameRepl.run();

                    if (returnedToPost) {
                        System.out.print("Returned to Game Menu\n");
                        System.out.println(client.help());
                    }
                }

            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }
        System.out.println();
        return true;
    }

}
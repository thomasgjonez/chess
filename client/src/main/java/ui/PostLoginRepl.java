package ui;
import static ui.EscapeSequences.*;

import clients.PostLoginClient;
import clients.PreLoginClient;
import model.AuthData;

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
        var result = "";
        while (!result.equals("quit")) {
            String line = scanner.nextLine();

            try {
                result = client.eval(line);
                System.out.print(result);

                if (result.startsWith("join game success") || result.startsWith("observe game success")){
                    System.out.print("joining game...\n\n");
                    //gets player color to pass to GameRepl
                    String[] parts = result.split(":");
                    String playerColor = parts.length > 1 ? parts[1].trim() : null;

                    GameRepl gameRepl = new GameRepl(serverUrl, playerColor);
                    boolean returnedToPost = gameRepl.run();

                    if (returnedToPost) {
                        System.out.print("Returned to Game Menu\n");
                        System.out.println(client.help());
                    }
                }
                if (Objects.equals(result, "logout success\n")){
                    return true;//returns to preloginRepl
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
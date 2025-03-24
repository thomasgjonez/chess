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

                if (Objects.equals(result, "join success\n") || Objects.equals(result, "observe success\n")){
                    System.out.print("joining game...\n\n");
                    GameRepl gameRepl = new GameRepl(serverUrl);
                    boolean returnedToPost = gameRepl.run();

                    if (returnedToPost) {
                        System.out.print("Returned to Game Menu\n");
                        System.out.println(client.help());
                    }
                }
                if (Objects.equals(result, "logout success\n")){
                    return true;//returns to preloginRepl
                }
                client.help();
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }
        System.out.println();
        return true;
    }

}
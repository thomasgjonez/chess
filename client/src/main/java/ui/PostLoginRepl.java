package ui;
import static ui.EscapeSequences.*;

import clients.PostLoginClient;
import clients.PreLoginClient;

import java.util.Objects;
import java.util.Scanner;

public class PostLoginRepl {
    private final PostLoginClient client;
    private final String serverUrl;

    public PostLoginRepl(String serverUrl){
        this.serverUrl = serverUrl;
        this.client = new PostLoginClient(serverUrl);
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
                    boolean returnToPost = gameRepl.run();

                    if (returnToPost) {
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
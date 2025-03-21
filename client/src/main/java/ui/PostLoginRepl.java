package ui;
import static ui.EscapeSequences.*;

import clients.PostLoginClient;

import java.util.Objects;
import java.util.Scanner;

public class PostLoginRepl {
    private final PostLoginClient client;
    private final String serverUrl;

    public PostLoginRepl(String serverUrl){
        this.serverUrl = serverUrl;
        this.client = new PostLoginClient(serverUrl);
    }

    public void run() {
        System.out.println(SET_TEXT_BOLD+  "Welcome to Game Menu! " + WHITE_QUEEN);
        System.out.print(client.help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            String line = scanner.nextLine();

            try {
                result = client.eval(line);
                System.out.print(result);
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }
        System.out.println();
    }

}
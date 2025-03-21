package ui;

import clients.GameClient;
import clients.PostLoginClient;

import java.util.Scanner;

import static ui.EscapeSequences.*;

public class GameRepl {
    private final GameClient client;
    private final String serverUrl;

    public GameRepl(String serverUrl){
        this.serverUrl = serverUrl;
        this.client = new GameClient(serverUrl);
    }

    public boolean run() {
        System.out.println(SET_TEXT_BOLD+  "Welcome to Game Actions Menu! " + WHITE_QUEEN +"\n");
        client.printGame();
        System.out.print(client.help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            String line = scanner.nextLine();

            try {
                result = client.eval(line);
                System.out.print(RESET_BG_COLOR + RESET_TEXT_COLOR+result);
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }

        System.out.println();
        return true;
    }
}

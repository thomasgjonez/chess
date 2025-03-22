package ui;
import static ui.EscapeSequences.*;
import clients.PreLoginClient;

import java.util.Objects;
import java.util.Scanner;

public class PreLoginRepl {
    private final PreLoginClient client;
    private final String serverUrl;

    public PreLoginRepl(String serverUrl){
        this.serverUrl = serverUrl;
        this.client = new PreLoginClient(serverUrl);
    }

    public void run() {
        System.out.println(SET_TEXT_BOLD+  "Welcome to Chess! " + WHITE_KING);
        System.out.print(client.help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            String line = scanner.nextLine();

            try {
                result = client.eval(line);
                System.out.print(result);
                if (Objects.equals(result, "register success\n") || Objects.equals(result, "login success\n")){
                    System.out.print("you are logged in\n\n");
                    PostLoginRepl postLoginRepl = new PostLoginRepl(serverUrl,client.getAuthData());
                    boolean returnToPre = postLoginRepl.run();

                    if (returnToPre) {
                        System.out.print("Returned to Login Menu\n");
                        System.out.println(client.help());
                    }
                }
                else{
                    System.out.println(client.help());
                }
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }
        System.out.println();
    }

}

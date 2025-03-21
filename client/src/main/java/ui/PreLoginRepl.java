package ui;
import static ui.EscapeSequences.*;
import clients.PreLoginClient;
import java.util.Scanner;

public class PreLoginRepl {
    private final PreLoginClient client;
    private final postLoginRepl postLoginRepl;

    public PreLoginRepl(String serverUrl){
        client = new PreLoginClient(serverUrl);
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
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }
        System.out.println();
    }

}

package websocket;

import chess.ChessGame;
import com.google.gson.Gson;
import websocket.commands.UserGameCommand;
import websocket.messages.ServerMessage;
import websocket.messages.Notification;
import websocket.messages.Error;
import websocket.messages.LoadGame;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static ui.EscapeSequences.ERASE_LINE;

public class WebsocketCommunicator extends Endpoint {

    private Session session;
    private static final Gson gson = new Gson();

    public WebsocketCommunicator(String serverDomain) throws Exception {
        try {

            //URI uri = new URI("ws://" + serverDomain + "/ws");
            URI uri = new URI("ws://localhost:8080/ws");//temp for debugging

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, uri);

            this.session.addMessageHandler(String.class, this::handleMessage);

        } catch (DeploymentException | IOException | URISyntaxException ex) {
            throw new Exception("WebSocket connection failed: " + ex.getMessage());
        }
    }

    @Override
    public void onOpen(Session session, EndpointConfig config) {
        session.addMessageHandler(String.class, this::handleMessage);
    }

    private void handleMessage(String message) {
        ServerMessage base = gson.fromJson(message, ServerMessage.class);
        switch (base.getServerMessageType()) {
            case NOTIFICATION -> {
                Notification notif = gson.fromJson(message, Notification.class);
            }
            case ERROR -> {
                Error error = gson.fromJson(message, Error.class);
            }
            case LOAD_GAME -> {
                LoadGame loadGame = gson.fromJson(message, LoadGame.class);
            }
            default -> System.err.println("Unknown message type received.");
        }
    }

    public void sendCommand(UserGameCommand command) {
        String json = gson.toJson(command);
        this.session.getAsyncRemote().sendText(json);
    }

    public void close() {
        try {
            if (session != null && session.isOpen()) session.close();
        } catch (IOException e) {
            System.err.println("Error closing WebSocket: " + e.getMessage());
        }
    }
}

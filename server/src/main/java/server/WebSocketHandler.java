package server;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import service.WebSocketService;
import websocket.commands.*;

@WebSocket
public class WebSocketHandler extends BaseHandler{
    private static final Gson gson = new Gson();
    private final WebSocketService wsService = new WebSocketService();

    @OnWebSocketConnect
    public void onConnect(Session session) {
        System.out.println("WebSocket connected: " + session.getRemoteAddress());
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) {
        System.out.println("Received WebSocket message: " + message);

        try {
            //debugging purposes
            UserGameCommand base = fromJson(message, UserGameCommand.class);

            switch (base.getCommandType()) {
                case CONNECT -> {
                    ConnectCommand cmd = fromJson(message, ConnectCommand.class);
                    wsService.connect(session, cmd);
                    System.out.println("CONNECT called");
                }
                case MAKE_MOVE -> {
                    MakeMoveCommand cmd = fromJson(message, MakeMoveCommand.class);
                    wsService.makeMove(session, cmd);
                    System.out.println("MOVE called");
                }
                case LEAVE -> {
                    LeaveCommand cmd = fromJson(message, LeaveCommand.class);
                    //wsService.leave(session, cmd);
                    System.out.println("LEAVE called");
                }
                case RESIGN -> {
                    ResignCommand cmd = fromJson(message, ResignCommand.class);
                    //wsService.resign(session, cmd);
                    System.out.println("RESIGN called");
                }
            }
        } catch (Exception e) {
            System.err.println(" Error sending response: " + e.getMessage());
        }
    }

    @OnWebSocketClose
    public void onClose(Session session, int statusCode, String reason) {
        System.out.println("WebSocket closed: " + reason);
    }

    @OnWebSocketError
    public void onError(Session session, Throwable throwable) {
        System.err.println(" WebSocket error: " + throwable.getMessage());
        throwable.printStackTrace();
    }
}

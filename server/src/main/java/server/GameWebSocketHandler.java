package server;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import websocket.commands.*;

@WebSocket
public class GameWebSocketHandler extends BaseHandler{
    private static final Gson gson = new Gson();
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
                    ConnectCommand connect = fromJson(message, ConnectCommand.class);
                    //handleConnect(session, connect);
                    System.out.println("CONNECT called");
                }
                case MAKE_MOVE -> {
                    MakeMoveCommand move = fromJson(message, MakeMoveCommand.class);
                    //handleMakeMove(session, move);
                    System.out.println("MOVE called");
                }
                case LEAVE -> {
                    LeaveCommand leave = fromJson(message, LeaveCommand.class);
                    //handleLeave(session, leave);
                    System.out.println("LEAVE called");
                }
                case RESIGN -> {
                    ResignCommand resign = fromJson(message, ResignCommand.class);
                    //handleResign(session, resign);
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

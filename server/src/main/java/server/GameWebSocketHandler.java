package server;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;

@WebSocket
public class GameWebSocketHandler {
    @OnWebSocketConnect
    public void onConnect(Session session) {
        System.out.println("WebSocket connected: " + session.getRemoteAddress());
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) {
        System.out.println("Received WebSocket message: " + message);

        try {
            //debugging purposes
            session.getRemote().sendString("Echo: " + message);
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

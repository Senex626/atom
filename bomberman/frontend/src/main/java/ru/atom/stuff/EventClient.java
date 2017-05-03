
package ru.atom.stuff;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.eclipse.jetty.websocket.client.masks.ZeroMasker;

import ru.atom.websocket.message.Message;
import ru.atom.websocket.message.Topic;
import ru.atom.websocket.util.JsonHelper;

import java.net.URI;
import java.util.concurrent.Future;

public class EventClient {
    public static void main(String[] args) {
        URI uri = URI.create("ws://localhost:8090/events/");

        WebSocketClient client = new WebSocketClient();
        //client.setMasker(new ZeroMasker());
        try {
            try {
                client.start();
                // The socket that receives events
                EventHandler socket = new EventHandler();
                // Attempt Connect
                Future<Session> fut = client.connect(socket, uri);
                // Wait for Connect
                Session session = fut.get();
                // Send a message
                System.out.println("client: sending hello");
                //session.getRemote().sendString("Hello");
                String message = JsonHelper.toJson(new Message(Topic.MOVE,"Some_Direction"));
                session.getRemote().sendString(message);
                //session.setIdleTimeout(5000);
                Thread.sleep(5000);
                
                // Close session
                session.close();
                System.out.println("session close");
            } finally {
            	System.out.println("client stop");
                client.stop();
            }
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }
}

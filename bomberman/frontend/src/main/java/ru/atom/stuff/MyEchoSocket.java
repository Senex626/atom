package ru.atom.stuff;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket(maxTextMessageSize = 64 * 1024)
public class MyEchoSocket {
	
	@OnWebSocketConnect
	public void onConnect(Session session) {
		System.out.println("Connection established." + session.getRemoteAddress());
	}
	
	@OnWebSocketMessage
	public void onText(Session session, String message) {
		if (session.isOpen()) {
            System.out.printf("Echoing back message [%s]%n",message);
            //session.getRemote().sendString("!"+message, null);
        }
	}

}

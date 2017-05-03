package ru.atom.websocket;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import ru.atom.geometry.Point;
import ru.atom.model.Pawn;
import ru.atom.websocket.message.Message;
import ru.atom.websocket.message.Topic;
import ru.atom.websocket.util.JsonHelper;

@WebSocket(maxTextMessageSize = 64 * 1024)
public class GameSocket {
	
	@OnWebSocketConnect
	public void onConnect(Session session) {
		System.out.println("Connection established." + session.getRemoteAddress());
		//Pawn p1 = new Pawn(new Point(5,5));
		Message msg = new Message(Topic.POSSESS, "\"id\":1");
		String sendMSG = JsonHelper.toJson(msg);
		session.getRemote().sendString(sendMSG, null);
		//Message msg2 = new Message(Topic.REPLICA, "pawn1:{\"type\":\"Pawn\",\"id\":1,\"position\":{\"x\":0,\"y\":0}}");
		//String sendMSG2 = "{\"topic\":\"REPLICA\",\"data\":{{\"type\":\"Pawn\",\"id\":1,\"position\":{\"x\":0,\"y\":0}}}}";
		String sendMSG2 = "{\"topic\":\"REPLICA\",\"data\":[\"Pawn\"]}";
		session.getRemote().sendString(sendMSG2, null);
	}
	
	@OnWebSocketMessage
	public void onText(Session session, String message) {
		if (session.isOpen()) {
            System.out.printf("Echoing back message [%s]%n",message);
            //session.getRemote().sendString("!"+message, null);
        }
	}

}

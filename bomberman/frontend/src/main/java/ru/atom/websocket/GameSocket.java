package ru.atom.websocket;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import com.google.gson.Gson;

import ru.atom.geometry.Point;
import ru.atom.model.Movable.Direction;
import ru.atom.model.Pawn;
import ru.atom.model.Tile;
import ru.atom.model.Tile.Type;
import ru.atom.websocket.message.Message;
import ru.atom.websocket.message.Topic;
import ru.atom.websocket.network.Broker;
import ru.atom.websocket.util.JsonHelper;

@WebSocket(maxTextMessageSize = 64 * 1024)
public class GameSocket {
	
	//Tile: 32x32, x=[0;32*17], y=[0;32*13]
	public static int counterX = 0;
	public static int counterY = 0;
    /*
	enum Type {
        Wood, Wall, grass
    }
	*/
	
	@OnWebSocketConnect
	public void onConnect(Session session) {
		// Нужен теперь не рандомный id, а id сессии
		System.out.println("Connection established." + session.getRemoteAddress());
		Message msg = new Message(Topic.POSSESS, "1");
		String sendMSG = JsonHelper.toJson(msg);
		session.getRemote().sendString(sendMSG, null);
		Pawn pawn1 = new Pawn(200, 200, 1);
		Tile tile1 = new Tile(0,12,Type.Wood,3);
		String sendMSG2 = "{\"topic\":\"REPLICA\",\"data\":{\"objects\":[" + pawn1.toJSON() + "," + tile1.toJSON() + "]}}";
		System.out.println(sendMSG2);
		session.getRemote().sendString(sendMSG2, null);
	}
	
	@OnWebSocketMessage
	public void onText(Session session, String message) {
		if (session.isOpen()) {
            System.out.printf("Echoing back message [%s]%n",message);
            //session.getRemote().sendString("!"+message, null);
            Message msg = JsonHelper.fromJson(message, Message.class);
            if(msg.getTopic().equals(Topic.MOVE)) {
            	//Broker.getInstance().receive(session, message);
            	//String data = gson.fromJson(msg.getData(), String.class);
            	Direction data = JsonHelper.fromJson(msg.getData(), Pawn.class).getDirection();
            	if (data.equals(Direction.RIGHT)) {
            		if(counterX < (543-48)) {
            			counterX++;
            		}
            		String sendMSG3 = "{\"topic\":\"REPLICA\",\"data\":{\"objects\":[{\"type\":\"Pawn\",\"id\":1,\"position\":{\"x\":" + counterX + ",\"y\":" + counterY + "}}]}}";
            		session.getRemote().sendString(sendMSG3, null);
            	} else if (data.equals(Direction.LEFT)) {
            		if (counterX > 0) {
            			counterX--;
            		}
            		String sendMSG3 = "{\"topic\":\"REPLICA\",\"data\":{\"objects\":[{\"type\":\"Pawn\",\"id\":1,\"position\":{\"x\":" + counterX + ",\"y\":" + counterY + "}}]}}";
            		session.getRemote().sendString(sendMSG3, null);
            	} else if (data.equals(Direction.UP)) {
            		if (counterY < (415-48)) {
            			counterY++;
            		}
            		String sendMSG3 = "{\"topic\":\"REPLICA\",\"data\":{\"objects\":[{\"type\":\"Pawn\",\"id\":1,\"position\":{\"x\":" + counterX + ",\"y\":" + counterY + "}}]}}";
            		session.getRemote().sendString(sendMSG3, null);
            	} else if (data.equals(Direction.DOWN)) {
            		if (counterY > 0) {
            			counterY--;
            		}
            		String sendMSG3 = "{\"topic\":\"REPLICA\",\"data\":{\"objects\":[{\"type\":\"Pawn\",\"id\":1,\"position\":{\"x\":" + counterX + ",\"y\":" + counterY + "}}]}}";
            		session.getRemote().sendString(sendMSG3, null);
            	}
            	
            } else if (msg.getTopic().equals(Topic.PLANT_BOMB)) {
            	String sendMSG3 = "{\"topic\":\"REPLICA\",\"data\":{\"objects\":[{\"type\":\"Bomb\",\"id\":2,\"position\":{\"x\":" + counterX + ",\"y\":" + counterY + "}},{\"type\":\"Pawn\",\"id\":1,\"position\":{\"x\":" + counterX + ",\"y\":" + counterY + "}}]}}";
        		session.getRemote().sendString(sendMSG3, null);
            }

        }
	}

}

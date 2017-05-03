
package ru.atom.stuff;

import java.io.IOException;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;
import org.eclipse.jetty.websocket.api.WriteCallback;

import ru.atom.websocket.message.Message;
import ru.atom.websocket.message.Topic;
import ru.atom.websocket.network.Broker;
import ru.atom.websocket.network.ConnectionPool;
import ru.atom.websocket.util.JsonHelper;

public class EventHandler extends WebSocketAdapter {
    @Override
    public void onWebSocketConnect(Session sess) {
        super.onWebSocketConnect(sess);
        //GameSession <-- Session: будет передаваться как Seesion и в ней будет инфа об игроке player
        //Пока заглушка
        //ConnectionPool.getInstance().add(sess, "TestPlayer");
        System.out.println("Socket Connected: " + sess);
        
        /*
        new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				try {
					getRemote().sendString("Hi from Server!");
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
        	
        }).start();
        */
    }

    int n;
    
    @Override
    public void onWebSocketText(String message) {
        super.onWebSocketText(message);
        //Сначала разберем message, чтобы посмотреть topic
        Message msg = JsonHelper.fromJson(message, Message.class);
        
        //Теперь, в зависимости от topic, сообщение отправляется либо клиенту, либо серверу
        if(msg.getTopic().equals(Topic.MOVE) || msg.getTopic().equals(Topic.PLANT_BOMB)) {
        	Broker.getInstance().receive(getSession(), message);
        } else if(msg.getTopic().equals(Topic.POSSESS) || msg.getTopic().equals(Topic.REPLICA)) {
        	String player = ConnectionPool.getInstance().getPlayer(getSession());
        	Broker.getInstance().send(player, msg.getTopic(), msg);
        }
        
        
        /*
        System.out.println("Received TEXT message: " + message);
        System.out.println("Trying to echo it back");
        //getRemote().sendString(message);
		System.out.println(getRemote().toString());
		*/
		
		/*try {
            System.out.printf("Echoing back message [%s]%n",message);
            // echo the message back
//            if(++n<10) {
//            	getRemote().sendString(message);
//            }
            //getSession().wait(1000);
            //getSession().close();
            System.out.println("Success");
        }
        catch (Exception e) {
            e.printStackTrace(System.err);
        }*/
    }

    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        super.onWebSocketClose(statusCode, reason);
        //Удаляет сессию из ConnectionPool
        //Сработет ли?
        //Session sess = getSession();
        //ConnectionPool.getInstance().remove(sess);
        System.out.println("Socket Closed: [" + statusCode + "] " + reason);
    }

    @Override
    public void onWebSocketError(Throwable cause) {
        super.onWebSocketError(cause);
        cause.printStackTrace(System.err);
    }
}

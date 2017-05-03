package ru.atom.websocket;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketListener;

/**
 * Example EchoSocket using Listener.
 */
public class ListenerEchoSocket implements WebSocketListener {
	private Session outbound;



	@Override
	public void onWebSocketConnect(Session session) {
		this.outbound = session;
	}

	@Override
	public void onWebSocketError(Throwable cause) {
		cause.printStackTrace(System.err);
	}
	
	@Override
	public void onWebSocketClose(int statusCode, String reason) {
		this.outbound = null;
	}
	@Override
	public void onWebSocketBinary(byte[] payload, int offset, int len) {
		/* only interested in text messages */
	}

	@Override
	public void onWebSocketText(String message) {
		if ((outbound != null) && (outbound.isOpen())) {
			System.out.printf("Echoing back message [%s]%n", message);
			// echo the message back
			outbound.getRemote().sendString(message, null);
		}
	}
}
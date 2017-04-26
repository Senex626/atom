package ru.atom.lecture09.serialization;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ObjectClient {
	public static void main(String[] args) throws IOException {
	    
		Socket socket = new Socket("wtfis.ru", 12345);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
	    //PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
	    Packet packet = new Packet("Sergey Filatov");
	    out.writeObject(packet);
	    out.close();
	    socket.close();
	  }

}

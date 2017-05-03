package ru.atom.websocket;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

public class EventServer
{
	
    public static void main(String[] args)
    {
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8090);
        server.addConnector(connector);

        // Setup the basic application "context" for this application at "/"
        // This is also known as the handler tree (in jetty speak)
        
        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setWelcomeFiles(new String[]{ "index.html" });
        String serverRoot = EventServer.class.getResource("/webapp").toString();
        resource_handler.setResourceBase(serverRoot);

      
        ServletContextHandler servletCtx = new ServletContextHandler(ServletContextHandler.SESSIONS);        
        servletCtx.setContextPath("/");
        ServletHolder holderEvents = new ServletHolder("ws-events", new WebSocketServlet() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void configure(WebSocketServletFactory factory) {
				factory.register(GameSocket.class);
				
				
			}
		});
        servletCtx.addServlet(holderEvents, "/events/*");
        
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] { resource_handler, servletCtx });
    
        server.setHandler(handlers);
        //server.setHandler(context);

        try
        {
            server.start();
        	System.out.println("- started - ");
            //server.dump(System.err);
            server.join();
        }
        catch (Throwable t)
        {
            t.printStackTrace(System.err);
        }
    }
}
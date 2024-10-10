package org.vilojona.demo;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.DefaultServlet;

public class JettyServer {
    public static void main(String[] args) throws Exception {
        System.setProperty("com.sun.jndi.ldap.object.trustURLCodebase", "true");
        Server server = new Server(8082);

        ServletHandler handler = new ServletHandler();
        handler.addServletWithMapping(LoginServlet.class, "/login");
        handler.addServletWithMapping(DefaultServlet.class, "/");
        
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setWelcomeFiles(new String[]{ "index.html" });
        resourceHandler.setResourceBase("./src/main/webapp");
        
        HandlerList handlerList = new HandlerList();
        handlerList.setHandlers(new Handler[] { resourceHandler, handler });
   
        server.setHandler(handlerList);
        
        server.start();
        server.join();
    }
}

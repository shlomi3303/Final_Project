package com.shenkar.finalProject.Globals;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint ("/endpoint")
public class WebSocket {

	static Session sessionServer;
	
	@OnOpen
    public void onOpen(Session session) {
		sessionServer = session;
        System.out.println("Open session " + sessionServer.getId());
    }
	
	 @OnMessage
	 public static void onTextMessage(String message) throws IOException {
	        System.out.println("message: " + message);
	        if (sessionServer!=null && sessionServer.isOpen())
	        {
	        	sessionServer.getBasicRemote().sendText(message);
	        	System.out.println("message sent to the client");
	        }
	 }
	 

}

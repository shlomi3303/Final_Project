package com.shenkar.finalProject.Globals;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint ("/endpoint")
public class WebSocket {

	static Session sessionServer;
	static Set<Session> loginUsers = Collections.synchronizedSet(new HashSet<Session>());
	
	@OnOpen
    public void onOpen(Session session) {
        System.out.println("Open session " + session.getId());
    }
	
	//method for sending notification from the server to the client
	 public static void sendMessageToClient(String message, String userId) throws IOException {
	        System.out.println("message: " + message);
	        //StringBuilder sb = new StringBuilder(userId);
	        //String strUserId = sb.toString();
	        Iterator<Session> iteratorSession = loginUsers.iterator();
	        Session userSession = null;
	        while (iteratorSession.hasNext())
	        {
	        	Session thisSession = iteratorSession.next();
	        	System.out.println("userId: " + userId);
	        	String sessionUserItr = (String) thisSession.getUserProperties().get("userId");
	        	
	        	if (userId.equals(sessionUserItr))
	        	{
	        		System.out.println("getUser: " + thisSession.getUserProperties().get("userId"));
	        		userSession = thisSession;
	        		System.out.println("the session is: " + userSession);
	        		System.out.println("test");
	        		break;
	        	}
	        	
	        }
	        
	        if (userSession!=null)
	        {
	        	userSession.getBasicRemote().sendText(message);
	        	System.out.println("message: " + message + " was sent to the client");
	        }
	        else
	        	System.out.println("the user is not login");
	 }
	 //overload for receiving massage from the front-end
	 @OnMessage
	 public static void onTextMessage(String message, Session session) throws IOException {
	        System.out.println("message: " + message);
	        
	        if (session!=null && session.isOpen())
	        {
		        session.getUserProperties().put("userId", message);
		        System.out.println("user id: " + session.getUserProperties().get("userId"));
	        	loginUsers.add(session);
		        session.getBasicRemote().sendText("client was added");

		        //String strUserId = (String)session.getUserProperties().get("userId");
		        //int userId = Integer.parseInt(strUserId);

	        }
	        else
	        	System.out.println("session is null");
	 }
	 
	 @OnClose
	 public void removeSession(Session session)
	 {
		 loginUsers.remove(session);
		 System.out.println("session: " + session + " disconnected");
	 }
}

package com.shenkar.finalProject.globals;

import java.io.EOFException;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import api.CoralogixLogger;

@ServerEndpoint ("/endpoint")
public class WebSocket {

	static Session sessionServer;
	static Set<Session> loginUsers = Collections.synchronizedSet(new HashSet<Session>());
	private static final String privateKey = System.getenv("CORALOGIX_privateKey");
	private static final String appName = "Web Socket";
	private static final String subSystem = "error";
	

	
	@OnOpen
    public void onOpen(Session session) {
        System.out.println("Open session " + session.getId());
        System.out.println("the session timeout is: " + session.getMaxIdleTimeout());

    }
	
	//method for sending notification from the server to the client
	 public static void sendMessageToClient(String message, String userId) throws IOException {
	        System.out.println("message: " + message);
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
	 public static void onTextMessage(String message, Session session) throws IOException 
	 {
        System.out.println("message: " + message);
        
        if (session!=null && session.isOpen())
        {
        	if (message.equals("__PING__"))
        	{
		        session.getBasicRemote().sendText("__PONG__");
		        System.out.println("Pong in: " + new Date());
        	}
        	
        	else
        	{
	        	session.setMaxIdleTimeout(ConstantVariables.sessionMaxIdle);
	        	session.getUserProperties().put("userId", message);
		        System.out.println("user id: " + session.getUserProperties().get("userId"));
	        	loginUsers.add(session);
		        session.getBasicRemote().sendText("client was added");
		        System.out.println("the session timeout after adding is: " + session.getMaxIdleTimeout());
        	}
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
	 
	 @OnError
	 public void onErrors(Session session, Throwable t) throws Throwable
	 {
		 CoralogixLogger.configure(privateKey, appName, subSystem);

		 int count = 0;
		 Throwable root = t;
		 while (root.getCause() != null && count < 20) {
		        root = root.getCause();
		        System.out.println("count is: " + count);
		        count ++;
		 }
		 if (root instanceof EOFException) {
		        // Assume this is triggered by the user closing their browser and
		        // ignore it.
		 } 
		 else 
		 {
				CoralogixLogger logger = new CoralogixLogger(WebSocket.class.toString()); 
				logger.error("Error at session user id: " + session.getUserProperties().get("userId"), t.toString());
		        
		 }
	 }
	 
	 public boolean checkIfUserLofin(int userId)
	 {
		Iterator<Session> iteratorSession = loginUsers.iterator();
	    Session userSession = null; 
		
	    String strUserId = new StringBuilder().append(userId).toString(); 
	    
	    while (iteratorSession.hasNext())
        {
        	Session thisSession = iteratorSession.next();
        	System.out.println("userId: " + userId);
        	String sessionUserItr = (String) thisSession.getUserProperties().get("userId");
        	
        	if (strUserId.equals(sessionUserItr))
        	{
        		System.out.println("getUser: " + thisSession.getUserProperties().get("userId"));
        		userSession = thisSession;
        		System.out.println("the session is: " + userSession);
        		return true;
        	}
        	
        }
	    
		return false;
		 
	 }
	 
}

package com.shenakr.finalProject.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.pretty.Printer;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.oracle.jrockit.jfr.RequestableEvent;
import com.shenkar.finalProject.model.HibernateUserDAO;
import com.shenkar.finalProject.model.User;
import com.shenkar.finalProject.model.UserExceptionHandler;

/**
 * Servlet implementation class UserLoginServelt
 */
@WebServlet(value="/UserLoginServelt")
public class UserServelt extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServelt() {super();}
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    //insert function trigger into doGet()
	protected void doGet(HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		response.getWriter().println("doGet function");
		
		//functionTrigger.if(functionTrigger.equals("update_user"))
			//updateUser(request);
		
		//else if(functionTrigger.equals("get_user"))
		//{
			User user = null;
			try {
				user = getUser(request.getParameter("mail"), request.getParameter("password"));
			} catch (UserExceptionHandler e) {
				e.printStackTrace();
			}
			
			String arr =  new Gson().toJson(user).toString();
			System.out.println(arr);
			//String output = new Gson().toJson(user1).toString();
			//response.getWriter().print("string:     " + request.getParameter("mail") +"\n"+ request.getParameter("password"));
			//response.setContentType("application/json");
			response.getWriter().write(arr);
			//response.getWriter().write(new Gson().toJson(user));
			
		//}			
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		deleteUser(req);
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			updateUser(req);
		} catch (UserExceptionHandler e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @throws UserExceptionHandler 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response, String functionTrigger) throws ServletException, IOException, UserExceptionHandler 
	{
		addNewUser(request);
	}
	
	private void addNewUser(HttpServletRequest request) throws UserExceptionHandler
	{
		User user = generateUser(request);
		
		if (user!=null)
			HibernateUserDAO.getInstance().addNewUser(user);

	}
	
	private void deleteUser(HttpServletRequest request)
	{
		String mail = request.getParameter("mail");
		String password = request.getParameter("password");
		HibernateUserDAO.getInstance().deleteUser(mail, password);
	}
	
	private void updateUser(HttpServletRequest request) throws UserExceptionHandler
	{
		
		User user = generateUser(request);
		if (user!=null)
		{
			String userId = request.getParameter("userId");
			HibernateUserDAO.getInstance().updateUser(userId, user);
		}
	}
	
	private User getUser(String mail, String password) throws UserExceptionHandler
	{
		User user = null;
		if (verification(mail, password))
		{
			user = HibernateUserDAO.getInstance().getUser(mail, password);
			if (user!=null)
				return user;
		}
		return null;
	}
	
	private User generateUser (HttpServletRequest request) throws UserExceptionHandler
	{
		String userId = request.getParameter("userId");
		String fname = request.getParameter("firstname");
		String lname = request.getParameter("lastname");
		String mail = request.getParameter("mail");
		String password = request.getParameter("password");
		String phone = request.getParameter("phone");
		String age = request.getParameter("age");
		String familyStatus = request.getParameter("familyStatus");
		String kids = request.getParameter("kids");
		String userLocation = request.getParameter("userLocation");
		String[] interests = request.getParameterValues("interests");
		String interestString = "";
		for (int i=0; i<interests.length; i++)
		{
			interestString +=interests[i] + ";"; 
		}
		System.out.println(interestString);
		
		if (verification(userId, password, mail)==true)
		{

			int IntegerAge = Integer.parseInt(age);
			
			int IntegerKids;
			if (kids.equals(""))
				IntegerKids = 0;
			else
				IntegerKids = Integer.parseInt(kids);

			User user = new User(userId, fname, lname, mail, password, phone, IntegerAge, familyStatus, IntegerKids, userLocation, interestString);
			return user;

		}
		return null;
	}
	
	public boolean verification(String... strings)
	{
		boolean flag = true;
	    for(String str : strings){
    		System.out.println(str);
	    	if(str!=null&&str.isEmpty()) 
	    		flag = false;
	    	else
	    		System.out.println(str);
	    }
	    return flag;
	}
	
	private String getJsonString(User user) {
	    // Before converting to GSON check value of id
	    Gson gson = null;
	    gson = new Gson();
	    
	    return gson.toJson(user);
	}
}

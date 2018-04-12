package com.shenakr.finalProject.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.oracle.jrockit.jfr.RequestableEvent;
import com.shenkar.finalProject.model.HibernateUserDAO;
import com.shenkar.finalProject.model.AppUser;
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
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.getWriter().println("doGet function");
		
		response.getWriter().println("In getUser condition");
		AppUser user = null;
		String mail = request.getParameter("mail");
		String password = request.getParameter("password");
		try {
			user = HibernateUserDAO.getInstance(response).getUser(mail, password, response);
		} catch (Exception e1) 
		{
			response.getWriter().println(e1);
			response.getWriter().println(e1.toString());
			
		}
		response.getWriter().println("for Gal Hagever");
		
		if ( (mail!=null && !mail.isEmpty()) && (password!=null &&  !password.isEmpty() ) )
		{
		
				//user = getUser(mail, password);
				if (user!=null)
				{
					String arr =  new Gson().toJson(user).toString();
					System.out.println(arr);
					response.setContentType("application/json");
					response.getWriter().write(arr);
				}
			
		}

			//String output = new Gson().toJson(user1).toString();
			//response.getWriter().print("string:     " + request.getParameter("mail") +"\n"+ request.getParameter("password"));
			//response.setContentType("application/json");
			//response.getWriter().write(new Gson().toJson(user));
			
		//}			
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		String function = request.getParameter("function");
		response.getWriter().println(function);
		if (function.equals("create"))
		{
			response.getWriter().println("In create condition");
			try 
			{
				addNewUser(request, response);
			} catch (UserExceptionHandler e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (function.equals("getUser"))
		{
			response.getWriter().println("In getUser condition");
			AppUser user = null;
			String mail = request.getParameter("mail");
			String password = request.getParameter("password");
			if ( (mail!=null && !mail.isEmpty()) && (password!=null &&  !password.isEmpty() ) )
			{
				try {
					user = getUser(mail, password, response);
					if (user!=null)
					{
						String arr =  new Gson().toJson(user).toString();
						System.out.println(arr);
						response.setContentType("application/json");
						response.getWriter().write(arr);
						response.getWriter().println(user.getMail());
					}
				} catch (UserExceptionHandler e) {
					e.printStackTrace();
				}
			}
		}
		else if (function.equals("update"))
		{
			AppUser user = null;
			String mail = request.getParameter("mail");
			String password = request.getParameter("password");

			if ( (mail!=null && !mail.isEmpty()) && (password!=null &&  !password.isEmpty()) )
			{
				try 
				{
					user = getUser(mail, password, response);
					if (user!=null)
						updateUser(request, user.getId(), response);
					
				} catch (UserExceptionHandler e) {
					e.printStackTrace();
				}
			}
		}
		else if (function.equals("delete"))
		{
			response.getWriter().println(function);
			String mail = request.getParameter("mail");
			String password = request.getParameter("password");
			if ( (mail!=null && !mail.isEmpty()) && (password!=null &&  !password.isEmpty() ) )
			{
				deleteUser(request, response);

			}
		}
	}
	
	private void addNewUser(HttpServletRequest request, HttpServletResponse response) throws UserExceptionHandler, IOException
	{
		response.getWriter().println("in add new user first");
		AppUser user = generateUser(request);
		
		if (user!=null)
		{
			response.getWriter().println("Add new user, hell yaa!!!! function second");
			HibernateUserDAO.getInstance(response).addNewUser(user, response);
			response.getWriter().println("User was created");

		}

	}
	
	private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		response.getWriter().println("in delete");
		String mail = request.getParameter("mail");
		String password = request.getParameter("password");
		
		if ( (mail!=null && !mail.isEmpty()) && (password!=null &&  !password.isEmpty() ) )
		{
			HibernateUserDAO.getInstance(response).deleteUser(mail, password, response);
			response.getWriter().println("deleted");

		}				
	}
	
	private void updateUser(HttpServletRequest request, int id, HttpServletResponse response) throws UserExceptionHandler, IOException
	{

		AppUser user = generateUser(request);
		response.getWriter().println("User genet");
		
		if (user!=null)
		{
			response.getWriter().println("User is not a null in update");
			HibernateUserDAO.getInstance(response).updateUser(id, user, response);
		}
	}
	
	private AppUser getUser(String mail, String password,HttpServletResponse response) throws UserExceptionHandler, IOException
	{
		AppUser user = null;
		
			user = HibernateUserDAO.getInstance(response).getUser(mail, password, response);
			if (user!=null)
				return user;
		
		return null;
	}
	
	private AppUser generateUser (HttpServletRequest request) throws UserExceptionHandler
	{
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
		
		if (interests!=null && interests.length>0)
		{
			for (int i=0; i<interests.length; i++)
			{
				interestString +=interests[i] + ";"; 
			}
			//System.out.println(interestString);
		}
		
		
			int IntegerAge = Integer.parseInt(age);
			
			int IntegerKids;
			
			if (kids.equals("") || kids.isEmpty())
				IntegerKids = 0;
			else
				IntegerKids = Integer.parseInt(kids);

			AppUser user = new AppUser(fname, lname, mail, password, phone, IntegerAge, familyStatus, IntegerKids, userLocation, interestString);
			return user;
		
		
	}
	
	
	private String getJsonString(AppUser user) {
	    // Before converting to GSON check value of id
	    Gson gson = null;
	    gson = new Gson();
	    
	    return gson.toJson(user);
	}
}

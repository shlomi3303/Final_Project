package com.shenakr.finalProject.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.shenkar.finalProject.model.HibernateUserDAO;
import com.shenkar.finalProject.model.Offer;
import com.shenkar.finalProject.model.OfferExceptionHandler;
import com.shenkar.finalProject.model.AppUser;
import com.shenkar.finalProject.model.Application;
import com.shenkar.finalProject.model.ApplicationExceptionHandler;
import com.shenkar.finalProject.model.HibernateApplicationDAO;
import com.shenkar.finalProject.model.HibernateOfferDAO;
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		String function = request.getParameter("function");
		
		if (function!=null && !function.isEmpty())
		{
			
			switch (function)
			{
			
			case "create":
				{
					response.getWriter().println("In create condition");
					try {addNewUser(request, response);} 
					catch (UserExceptionHandler e) {e.printStackTrace(response.getWriter());}
				}
				break;
				
			case "getUser":
				{
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
								response.setContentType("application/json");
								response.getWriter().write(arr);
							}
						} 
						catch (UserExceptionHandler e) {e.printStackTrace(response.getWriter());}
					}
				 }
				break;
				
			case "update":
				{
					AppUser user = null;
					String mail = request.getParameter("mail");
					String password = request.getParameter("password");
		
					if ( (mail!=null && !mail.isEmpty()) && (password!=null &&  !password.isEmpty()) )
					{
						try 
						{
							user = HibernateUserDAO.getInstance(response).getUser(mail, password, response);
							if (user!=null)
								updateUser(request, user.getId(), response);
							
						} 
						catch (UserExceptionHandler e) {e.printStackTrace(response.getWriter());}
					}
				}
				break;
			case "delete":
				{
					String mail = request.getParameter("mail");
					String password = request.getParameter("password");
					if ( (mail!=null && !mail.isEmpty()) && (password!=null &&  !password.isEmpty() ) )
					{deleteUser(request, response);}
				}
			}
		}
	}
	
	private void addNewUser(HttpServletRequest request, HttpServletResponse response) throws UserExceptionHandler, IOException
	{
		AppUser user = generateUser(request);
		
		if (user!=null)
		{
			HibernateUserDAO.getInstance(response).addNewUser(user, response);
		}
	}
	
	private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String mail = request.getParameter("mail");
		String password = request.getParameter("password");
		
		if ( (mail!=null && !mail.isEmpty()) && (password!=null &&  !password.isEmpty() ) )
		{
			HibernateUserDAO.getInstance(response).deleteUser(mail, password, response);
		}				
	}
	
	private void updateUser(HttpServletRequest request, int id, HttpServletResponse response) throws UserExceptionHandler, IOException
	{

		AppUser user = generateUser(request);
		
		if (user!=null)
		{
			HibernateUserDAO.getInstance(response).updateUser(id, user, response);
		}
	}
	
	private AppUser getUser (String mail, String password,HttpServletResponse response) throws UserExceptionHandler, IOException
	{
		AppUser user = null;
		
		user = HibernateUserDAO.getInstance(response).getUser(mail, password, response);
		if (user!=null)
			return user;
		
		return null;
	}
	
	private List<Object> getUserInforamtin(String mail, String password,HttpServletResponse response) throws UserExceptionHandler, IOException, OfferExceptionHandler, ApplicationExceptionHandler
	{
			AppUser user = null;
		
			user = HibernateUserDAO.getInstance(response).getUser(mail, password, response);
			if (user!=null)
			{
				
				List <Object> list = new ArrayList<Object>();
				String arr =  new Gson().toJson(user, AppUser.class).toString();
				list.add(arr);
				
				List <Offer> offers= HibernateOfferDAO.getInstance().getOffers(user.getId());
				String offerArray = new Gson().toJson(offers, Offer.class).toString();
				list.add(offerArray);

				List <Application> applications = HibernateApplicationDAO.getInstance().getApplications(user.getId());
				String applicationArray = new Gson().toJson(applications, Application.class).toString();
				list.add(applicationArray);
				 
				return list;
			}
		
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
		String strStudent = request.getParameter("student");
		String strHandyman = request.getParameter("handyman");
		String strOlders = request.getParameter("olders");
		String strRide = request.getParameter("ride");
		
		int IntegerAge = Integer.parseInt(age);
		
		int IntegerKids;
		
		if (kids.equals("") || kids.isEmpty())
			IntegerKids = 0;
		else
			IntegerKids = Integer.parseInt(kids);
		
		boolean student = Boolean.valueOf(strStudent);
		boolean handyman = Boolean.valueOf(strHandyman);
		boolean olders = Boolean.valueOf(strOlders);
		boolean ride = Boolean.valueOf(strRide);
		
		AppUser user = new AppUser(fname, lname, mail, password, phone, IntegerAge, familyStatus, IntegerKids, userLocation, student, olders, handyman, ride);
		return user;
	}
	
	
	private String getJsonString(AppUser user) 
	{
	    // Before converting to GSON check value of id
	    Gson gson = null;
	    gson = new Gson();
	    
	    return gson.toJson(user);
	}
}

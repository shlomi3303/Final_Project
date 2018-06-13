package com.shenkar.finalProject.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.shenkar.finalProject.model.HibernateUserDAO;
import com.shenkar.finalProject.model.Offer;
import com.shenkar.finalProject.model.OfferExceptionHandler;
import com.shenkar.finalProject.classes.Match;
import com.shenkar.finalProject.model.AppUser;
import com.shenkar.finalProject.model.Application;
import com.shenkar.finalProject.model.ApplicationExceptionHandler;
import com.shenkar.finalProject.model.HibernateApplicationDAO;
import com.shenkar.finalProject.model.HibernateMatchDAO;
import com.shenkar.finalProject.model.HibernateOfferDAO;
import com.shenkar.finalProject.model.UserExceptionHandler;

/**
 * Servlet implementation class UserLoginServelt
 */
@WebServlet(value="/UserLoginServelt")
public class UserServelt extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UserServelt() {super();}
    
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
					try {addNewUser(request);} 
					catch (UserExceptionHandler e) {e.printStackTrace(response.getWriter());}
				}
				break;
				
			case "getUser":
				{
					List<AppUser> user = null;
					String mail = request.getParameter("mail");
					String password = request.getParameter("password");
					if ( (mail!=null && !mail.isEmpty()) && (password!=null &&  !password.isEmpty() ) )
					{
						try {
							user = getUser(mail, password);
							if (user!=null)
							{
								int userId = user.get(0).getId();
								List<Application> applicationsList = HibernateApplicationDAO.getInstance().getUserApplications(userId);
								
								List <Offer> offersList = HibernateOfferDAO.getInstance().getUserOffers(userId);
								
								List <Match> manualMatchApplication = HibernateMatchDAO.getInstance().getAllUserManualMatches(userId, "User Application");
								List<Offer> userMatchOfferList = HibernateOfferDAO.getInstance().getaAllOfferMatches(manualMatchApplication);
								
								List <Match> manualMatchOffer = HibernateMatchDAO.getInstance().getAllUserManualMatches(userId, "User Offer");
								List<Application> userMatchApplicationsList = HibernateApplicationDAO.getInstance().getaAllApplicationMatches(manualMatchOffer);
								
								List<Object> userInfo = new ArrayList<Object>();
								
								userInfo.add(user);
								userInfo.add(applicationsList);
								userInfo.add(offersList);
								userInfo.add(userMatchApplicationsList);
								userInfo.add(userMatchOfferList);
								
								String strUserInfoJson = new Gson().toJson(userInfo).toString();
								response.setContentType("application/json");
					        	response.setCharacterEncoding("utf-8");
								response.getWriter().write(strUserInfoJson);
							}
						} 
						catch (UserExceptionHandler | ApplicationExceptionHandler | OfferExceptionHandler e) {e.printStackTrace(response.getWriter());}
					}
				 }
				break;
			case "getUserInfo":
			{
				AppUser user = null;
				String strUserId = request.getParameter("userId");
				if (strUserId!=null)
				{
					int userId = Integer.parseInt(strUserId);
					try 
					{
						user = HibernateUserDAO.getInstance().getUserInfo(userId);
						JSONObject json = new JSONObject();
						json.put("firstname", user.getFirstname());
						json.put("lastname", user.getLastname());
						json.put("phone", user.getPhone());
						json.put("mail", user.getMail());
						json.put("age", user.getAge());
						
						String message = json.toString();
						response.setContentType("application/json");
			        	response.setCharacterEncoding("utf-8");
						response.getWriter().write(message);
					} 
					catch (UserExceptionHandler e) {
						e.printStackTrace();
					}
				}
			}
			break;
			case "update":
				{
					List<AppUser> user = null;
					String mail = request.getParameter("mail");
					String password = request.getParameter("password");
		
					if ( (mail!=null && !mail.isEmpty()) && (password!=null &&  !password.isEmpty()) )
					{
						try 
						{
							user = HibernateUserDAO.getInstance().getUser(mail, password);
							if (user!=null)
								updateUser(request, user.get(0).getId(), response);
							
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
					break;
				}
				
			case "updateUserLocation":
			{
				String lat = request.getParameter("latitude");
				String longt = request.getParameter("longitude");
				
				if (lat!=null && longt!=null)
				{
					try {
						updateUserLocation(request, lat, longt);
					} catch (NumberFormatException | UserExceptionHandler e) {
						e.printStackTrace();
					}
				}
				
				break;
			}
			default:
				String str = "please insert a valid value into fucntion";
				response.getWriter().write(str);
				break;
			}
		}
	}
	
	private void updateUserLocation(HttpServletRequest request, String lat, String longt) throws NumberFormatException, UserExceptionHandler
	{
		String city = request.getParameter("city");
		String street = request.getParameter("street");
		String strHouseNumber = request.getParameter("houseNumber");
		String struserId = request.getParameter("userId");
		
		HibernateUserDAO.getInstance().updateLocation(Integer.valueOf(struserId), lat, longt, city, street, Integer.valueOf(strHouseNumber));
		
	}
	
	
	private void addNewUser(HttpServletRequest request) throws UserExceptionHandler, IOException
	{
		AppUser user = generateUser(request);
		
		if (user!=null)
		{
			HibernateUserDAO.getInstance().addNewUser(user);
		}
	}
	
	private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String mail = request.getParameter("mail");
		String password = request.getParameter("password");
		
		if ( (mail!=null && !mail.isEmpty()) && (password!=null &&  !password.isEmpty() ) )
		{
			HibernateUserDAO.getInstance().deleteUser(mail, password);
		}				
	}
	
	private void updateUser(HttpServletRequest request, int id, HttpServletResponse response) throws UserExceptionHandler, IOException
	{

		AppUser user = generateUser(request);
		
		if (user!=null)
		{
			HibernateUserDAO.getInstance().updateUser(id, user);
		}
	}
	
	private List<AppUser> getUser (String mail, String password) throws UserExceptionHandler, IOException
	{
		List<AppUser> user = null;
		
		user = HibernateUserDAO.getInstance().getUser(mail, password);
		if (user!=null)
			return user;
		
		return null;
	}
	
	
	private AppUser generateUser (HttpServletRequest request) throws UserExceptionHandler
	{
		String fname = request.getParameter("firstName");
		String lname = request.getParameter("lastName");
		String mail = request.getParameter("mail");
		String password = request.getParameter("password");
		String phone = request.getParameter("phone");
		String age = request.getParameter("age");
		String familyStatus = request.getParameter("familyStatus");
		String kids = request.getParameter("kids");
		String street = request.getParameter("street");
		String city = request.getParameter("city");
		String houseNumber = request.getParameter("houseNumber");
		String latitude = request.getParameter("latitude");
		String longitude = request.getParameter("longitude");
		
		
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
		int IntegerhouseNumber = Integer.parseInt(houseNumber);
		
		boolean student = Boolean.valueOf(strStudent);
		boolean handyman = Boolean.valueOf(strHandyman);
		boolean olders = Boolean.valueOf(strOlders);
		boolean ride = Boolean.valueOf(strRide);
		
		AppUser user = new AppUser(fname, lname, mail, password, phone, IntegerAge, familyStatus, IntegerKids,city, street,IntegerhouseNumber,latitude,longitude, student, olders, handyman, ride);
		return user;
	}
}

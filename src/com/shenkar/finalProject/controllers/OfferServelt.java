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
import com.shenkar.finalProject.model.AppUser;
import com.shenkar.finalProject.model.HibernateManualMatchDAO;
import com.shenkar.finalProject.model.HibernateOfferDAO;
import com.shenkar.finalProject.model.HibernateUserDAO;
import com.shenkar.finalProject.model.Offer;
import com.shenkar.finalProject.model.OfferExceptionHandler;
import com.shenkar.finalProject.model.UserExceptionHandler;

@WebServlet(value="/OfferServelt")
public class OfferServelt extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

    public OfferServelt() {super();}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String function = request.getParameter("function");

		if (function!=null && !function.isEmpty())
		{
			switch (function)
			{
				case "create":
				{
					try {addNewOffer(request);
						response.getWriter().println(Thread.currentThread().getName());
					} 
					catch (OfferExceptionHandler e) {e.printStackTrace(response.getWriter());}
					break;
				}
				
				case "update":
				{
					try {updateOffer(request);}
					catch (OfferExceptionHandler e) {e.printStackTrace(response.getWriter());}
					break;
				}
					
				case "getOffer":
				{
					String StringOfferId = request.getParameter("offerId");
					String tableName = request.getParameter("tableName");

					Offer offer = null;
					try {offer = getOffer(StringOfferId, tableName);}
					catch (OfferExceptionHandler e) {e.printStackTrace(response.getWriter());}
		
					String strOffer = new Gson().toJson(offer).toString();
					response.setContentType("application/json");
		        	response.setCharacterEncoding("utf-8");
					response.getWriter().write(strOffer);
					break;
				}
				
				case "delete":
				{
						try {deleteOffer(request);}
						catch (OfferExceptionHandler e) {e.printStackTrace(response.getWriter());}
						break;
				}
					
				case "getAllOffersUser":
				{
					String stringUserID = request.getParameter("userId");
					if (stringUserID!=null && !stringUserID.isEmpty())
					{
						List<Offer> offers = null;
						try 
						{
							offers = getAllOffersUser(stringUserID);
							String offerArray = new Gson().toJson(offers).toString();
							response.setContentType("application/json");
				        	response.setCharacterEncoding("utf-8");
							response.getWriter().write(offerArray);	
						}
						catch (OfferExceptionHandler e) {e.printStackTrace(response.getWriter());}		
					}
					break;
				}
				
				case "getAllSpecificTable":
				{
					String tableName = request.getParameter("tableName");

					List<Offer> offers = null;
					
					try 
					{
						offers  = getAllSpecificTable(tableName);
						if (offers!=null)
						{
							String applicationArray = new Gson().toJson(offers).toString();
							response.setContentType("application/json");
							response.setCharacterEncoding("utf-8");
							response.getWriter().write(applicationArray);
						}
					} 
					catch (OfferExceptionHandler e) 
					{e.printStackTrace(response.getWriter());}
					break;
				}
					
				case "randomOffers":
				{
					try 
					{
						String strOffersList = getRandomOffer(request);
						response.setContentType("application/json");
			        	response.setCharacterEncoding("utf-8");
						response.getWriter().write(strOffersList);
					}
					catch (OfferExceptionHandler e) {e.printStackTrace(response.getWriter());}
					break;
				}
				case "getUserToInform":
				{
					try
					{
						String strOfferId = request.getParameter("offerId");
						String category = request.getParameter("category");
						if (strOfferId!=null && category!=null)
						{
							int userId = HibernateManualMatchDAO.getInstance().getUserByOfferId(Integer.parseInt(strOfferId), category);
							AppUser user = HibernateUserDAO.getInstance().getUserInfo(userId);
							if (user!=null)
							{
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
							else
							{
								System.out.println("The user was not found");
							}
						}
						
					}
					catch (Exception e)
					{
						try {
							throw new OfferExceptionHandler("Error in get User Information by offer Id: " + e.getMessage());
						} catch (OfferExceptionHandler e1) {
							e1.printStackTrace();
						}
					}
					break;
				}
				
				case "getOffersIntersted":
				{
					String strUserId = request.getParameter("userId");
					
					if (strUserId!=null)
					{
						int userId = Integer.parseInt(strUserId);
						AppUser user = null;
						try {
							user = HibernateUserDAO.getInstance().getUserInfo(userId);
						} catch (UserExceptionHandler e) {
							e.printStackTrace();
						}
						if (user!=null)
						{
							try 
							{
								String strUserOffersPreferences = getUserOffersPreferences(user);
								response.setContentType("application/json");
					        	response.setCharacterEncoding("utf-8");
								response.getWriter().write(strUserOffersPreferences);
							}
							catch (OfferExceptionHandler e) 
							{
								e.printStackTrace();
							}
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

	private String getUserOffersPreferences(AppUser user) throws OfferExceptionHandler
	{
		
		List<Object> offerList = new ArrayList<Object>();
		
		if (user.getRide())
		{
			List<Offer> rideList = HibernateOfferDAO.getInstance().getAllSpecificOfferTable("ride");
			if (rideList!=null)
				offerList.add(rideList);
		}
		if (user.getHandyman())
		{
			List<Offer> handymanList = HibernateOfferDAO.getInstance().getAllSpecificOfferTable("handyman");
			if (handymanList!=null)
				offerList.add(handymanList);
		}
		if (user.getOlders())
		{
			List<Offer> oldersList = HibernateOfferDAO.getInstance().getAllSpecificOfferTable("olders");
			if (oldersList!=null)
				offerList.add(oldersList);
		}
		if (user.getStudent())
		{
			List<Offer> studentList = HibernateOfferDAO.getInstance().getAllSpecificOfferTable("student");
			if (studentList!=null)
				offerList.add(studentList);
		}
					
		return new Gson().toJson(offerList).toString();
	}
	
	
	private List<Offer> getAllOffersUser(String stringUserID) throws OfferExceptionHandler
	{
		int userId = Integer.parseInt(stringUserID);
		return HibernateOfferDAO.getInstance().getUserOffers(userId);
	}

	private void deleteOffer(HttpServletRequest req) throws OfferExceptionHandler
	{
		String offerid = req.getParameter("offerId");
		String tableName = req.getParameter("tableName");
		int OfferIdINT = Integer.parseInt(offerid);
		HibernateOfferDAO.getInstance().deleteOffer(OfferIdINT, tableName);	
	}
	
	private void updateOffer(HttpServletRequest req) throws OfferExceptionHandler
	{
		String stringOfferId = req.getParameter("offerId");
		String tableName = req.getParameter("tableName");

		if (stringOfferId!=null && !stringOfferId.isEmpty())
		{
			int offerId = Integer.parseInt(stringOfferId);
			Offer offer = generateOffer(req);
			if (offer != null)
			{
				HibernateOfferDAO.getInstance().editOffer(offerId, offer, tableName);
			}
		}	
	}
	
	private Offer getOffer(String strOfferId, String tableName) throws OfferExceptionHandler
	{
		Offer offer = null;
		if (strOfferId != null && !strOfferId.isEmpty())
		{
			int offerId = Integer.parseInt(strOfferId);
			offer = HibernateOfferDAO.getInstance().getOffer(offerId, tableName);
			if (offer!=null)
				return offer;
		}
		return null;
	}
	
	private void addNewOffer(HttpServletRequest req) throws OfferExceptionHandler
	{
		Offer offer = generateOffer(req);
		
		if (offer != null)
		{
			HibernateOfferDAO.getInstance().createOffer(offer);
		}
	}
	
	private String getRandomOffer(HttpServletRequest req) throws OfferExceptionHandler
	{
		String strNum = req.getParameter("number");
		String tableName = req.getParameter("tableName");
		int num = Integer.parseInt(strNum);
		
		return HibernateOfferDAO.getInstance().getRandomOffer(num, tableName);
		
	}
	
	private List <Offer> getAllSpecificTable (String tableName) throws OfferExceptionHandler
	{
		return HibernateOfferDAO.getInstance().getAllSpecificOfferTable(tableName);
	}
	
	private Offer generateOffer (HttpServletRequest req) throws OfferExceptionHandler
	{
		String offerString = req.getParameter("offer");
		String tableName = req.getParameter("tableName");
		Class <?> className = HibernateOfferDAO.getInstance().getTableMapping(tableName);
		Offer Deserialization = null;
		Gson gson = new Gson();
		Deserialization = (Offer) gson.fromJson(offerString, className);
		
		if (Deserialization != null){
			return Deserialization;
		}
		
		return null;
	}
}

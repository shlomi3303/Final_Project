package com.shenkar.finalProject.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shenkar.finalProject.model.ApplicationExceptionHandler;
import com.shenkar.finalProject.model.HibernateManualMatchDAO;
import com.shenkar.finalProject.model.ManualMatchUserApplication;
import com.shenkar.finalProject.model.ManualMatchUserOffer;
import com.shenkar.finalProject.model.Match;
import com.shenkar.finalProject.model.OfferExceptionHandler;
import com.shenkar.finalProject.model.UserExceptionHandler;

/**
 * Servlet implementation class ManualServlet
 */
@WebServlet(value="/MatchServlet")
public class MatchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MatchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


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
						try 
						{
							addNewManualMatch(request, response);
							response.getWriter().println(Thread.currentThread().getName());
	
						} 
						catch (Exception e) {e.printStackTrace(response.getWriter());}
						break;
				}
				case "accept":
				{
					
					acceptManualMatch(request, response);
					break;
				}
				case "decline":
				{
					declineManualMatch(response, request);
					
					break;
				}
				default:
					String str = "please insert a valid value into fucntion";
					response.getWriter().write(str);
			}
		}
	}
			
	private void declineManualMatch (HttpServletResponse res, HttpServletRequest req)
	{
		String user = req.getParameter("user");
		String tableName = req.getParameter("tableName");

		if (user!=null && tableName!=null){
			if (user.equals("offer"))
			{
				String strOfferId = req.getParameter("offerId");
				if (strOfferId!=null&&strOfferId.matches("[0-9]"))
				{
					int offerId = Integer.parseInt(strOfferId);
					HibernateManualMatchDAO.getInstance().declineMatch(user, offerId, tableName);
				}
			}
			
			else if (user.equals("application"))
			{
				String strApplicationId = req.getParameter("applicationId");
				if (strApplicationId!=null&&strApplicationId.matches("[0-9]"))
				{
					int applicationId = Integer.parseInt(strApplicationId);
					HibernateManualMatchDAO.getInstance().declineMatch(user, applicationId, tableName);
				}
			}
			else
			{
				System.out.println("please insert the correct values");
			}
		}
	}
	
	
	private void acceptManualMatch (HttpServletRequest req, HttpServletResponse res) throws IOException
	{
		
		String user = req.getParameter("user");
		String tableName = req.getParameter("tableName");

		if (user!=null && tableName!=null){
			if (user.equals("offer"))
			{
				String strOfferId = req.getParameter("offerId");
				int offerId = Integer.parseInt(strOfferId);
				HibernateManualMatchDAO.getInstance().acceptMatch(user, offerId, tableName);
				res.getWriter().println("match was created");
			}
			
			else if (user.equals("application"))
			{
				String strApplicationId = req.getParameter("applicationId");
				int applicationId = Integer.parseInt(strApplicationId);
				HibernateManualMatchDAO.getInstance().acceptMatch(user, applicationId, tableName);
				res.getWriter().println("match was created");
			}
			else
			{
				System.out.println("please insert the correct values");
			}
		}
	}
	
	

	private void addNewManualMatch(HttpServletRequest request, HttpServletResponse response) throws IOException, ApplicationExceptionHandler, OfferExceptionHandler, UserExceptionHandler
	{
		Match match = generateManualMatch(request, response);
		if (match != null)
		{
			String tableName = request.getParameter("tableName");
			HibernateManualMatchDAO.getInstance().createMatch(match, tableName);
			
		}
		
	}	

	
	
	private Match generateManualMatch (HttpServletRequest req, HttpServletResponse response) throws IOException
	{
		String strUserId = req.getParameter("userId");
		String strOfferId = req.getParameter("offerId");
		String strApplicationId = req.getParameter("applicationId");
		String tableName = req.getParameter("tableName");
		
		int offerId;
		int applicationId;
		int userId = Integer.parseInt(strUserId);
		Match manual = null;
		if (strOfferId==null)
		{
			 applicationId = Integer.parseInt(strApplicationId);
			 manual = new ManualMatchUserOffer(applicationId,false, false, userId, tableName); 

		}
		else if (strApplicationId==null)
		{
			offerId = Integer.parseInt(strOfferId);
			manual = new ManualMatchUserApplication(offerId,false, false, userId, tableName); 

		}
			
		return manual;
		
		/*
		String matchString = req.getParameter("matchString");
		response.getWriter().write("The json object is: " + matchString);
		String tableName = req.getParameter("tableName");
		response.getWriter().write("The table name is: " + tableName);
	
		ManualMatch Deserialization = null;
		Gson gson = new Gson();
		
		Deserialization = gson.fromJson(matchString, ManualMatch.class);
			
		response.getWriter().write("i'm after Deserialization");
		if (Deserialization != null)
		{
			return Deserialization;
		}
		*/
	}
	
}


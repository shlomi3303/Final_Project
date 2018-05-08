package com.shenakr.finalProject.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shenkar.finalProject.model.ApplicationExceptionHandler;
import com.shenkar.finalProject.model.HibernateManualMatchDAO;
import com.shenkar.finalProject.model.ManualMatchApplication;
import com.shenkar.finalProject.model.ManualMatchOffer;
import com.shenkar.finalProject.model.Match;
import com.shenkar.finalProject.model.OfferExceptionHandler;
import com.shenkar.finalProject.model.UserExceptionHandler;

/**
 * Servlet implementation class ManualServlet
 */
@WebServlet(value="/ManualServlet")
public class ManualServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManualServlet() {
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
							response.getWriter().println("match was created");
							response.getWriter().println(Thread.currentThread().getName());
	
						} 
						catch (Exception e) {e.printStackTrace(response.getWriter());}
						break;
				}
				case "match":
				{
					
				}
				default:
					String str = "please insert a valid value into fucntion";
					response.getWriter().write(str);
			}
		}
	}
			

	private void addNewManualMatch(HttpServletRequest request, HttpServletResponse response) throws IOException, ApplicationExceptionHandler, OfferExceptionHandler, UserExceptionHandler
	{
		Match match = generateManualMatch(request, response);
		if (match != null)
		{
			String matchInitiator = request.getParameter("matchInitiator");
			String tableName = request.getParameter("tableName");
			HibernateManualMatchDAO.getInstance().createMatch(match, matchInitiator, tableName);
		}
		
	}	

	
	
	private Match generateManualMatch (HttpServletRequest req, HttpServletResponse response) throws IOException
	{
		String strUserId = req.getParameter("userId");
		String strOfferId = req.getParameter("offerId");
		String strApplicationId = req.getParameter("applicationId");
		
		int offerId;
		int applicationId;
		int userId = Integer.parseInt(strUserId);
		Match manual = null;
		if (strOfferId==null)
		{
			 applicationId = Integer.parseInt(strApplicationId);
			 manual = new ManualMatchOffer(applicationId,false, false, userId); 

		}
		else if (strApplicationId==null)
		{
			offerId = Integer.parseInt(strOfferId);
			manual = new ManualMatchApplication(offerId,false, false, userId); 

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


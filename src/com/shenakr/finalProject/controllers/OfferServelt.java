package com.shenakr.finalProject.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.shenkar.finalProject.model.HibernateOfferDAO;
import com.shenkar.finalProject.model.Offer;
import com.shenkar.finalProject.model.OfferExceptionHandler;

/**
 * Servlet implementation class OfferServelt
 */
@WebServlet(value="/OfferServelt")
public class OfferServelt extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OfferServelt() {super();}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    //insert function trigger into doGet()
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		String function = request.getParameter("function");

		if (function!=null && !function.isEmpty()){
	
			if (function.equals("create"))
			{
				try {addNewOffer(request);} 
				catch (OfferExceptionHandler e) {e.printStackTrace(response.getWriter());}
				
			}
			
			else if(function.equals("update"))
			{
				try {updateOffer(request);}
				catch (OfferExceptionHandler e) {e.printStackTrace(response.getWriter());}			
			}
			
			else if(function.equals("getOffer"))
			{
				String StringOfferId = request.getParameter("offerId");
				Offer offer = null;
				try {offer = getOffer(StringOfferId);}
				catch (OfferExceptionHandler e) {e.printStackTrace(response.getWriter());}
				//Type offerListType = new TypeToken<ArrayList<Offer>>(){}.getType();
	
				String strOffer = new Gson().toJson(offer).toString();
				response.setContentType("application/json");
				response.getWriter().write(strOffer);
			}
			
			else if (function.equals("delete"))
			{
				try {deleteOffer(request);}
				catch (OfferExceptionHandler e) {e.printStackTrace(response.getWriter());}	
			}
			else if (function.equals("getAllOffersUser"))
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
						response.getWriter().write(offerArray);	
					}
					catch (OfferExceptionHandler e) {e.printStackTrace(response.getWriter());}		
				}
			}
		}
	}

	
	private List<Offer> getAllOffersUser(String stringUserID) throws OfferExceptionHandler
	{
		int userId = Integer.parseInt(stringUserID);
		return HibernateOfferDAO.getInstance().getOffers(userId);
	}
	

	private void deleteOffer(HttpServletRequest req) throws OfferExceptionHandler
	{
		String offerid = req.getParameter("offerId");
		int OfferIdINT = Integer.parseInt(offerid);
		HibernateOfferDAO.getInstance().deleteOffer(OfferIdINT);	
	}
	
	private void updateOffer(HttpServletRequest req) throws OfferExceptionHandler
	{
		String stringOfferId = req.getParameter("offerId");
		if (stringOfferId!=null && !stringOfferId.isEmpty())
		{
			int offerId = Integer.parseInt(stringOfferId);
			Offer offer = generateOffer(req);
			if (offer != null)
			{
				HibernateOfferDAO.getInstance().editOffer(offerId, offer);
			}
		}	
	}
	
	private Offer getOffer(String strOfferId) throws OfferExceptionHandler
	{
		Offer offer = null;
		if (strOfferId != null && !strOfferId.isEmpty())
		{
			int offerId = Integer.parseInt(strOfferId);
			offer = HibernateOfferDAO.getInstance().getOffer(offerId);
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
	
	private Offer generateOffer (HttpServletRequest req) throws OfferExceptionHandler
	{
		String offerString = req.getParameter("offer");
		Offer Deserialization = null;
		Gson gson = new Gson();
		Deserialization = gson.fromJson(offerString, Offer.class);
		
		if (Deserialization != null){
			return Deserialization;
		}
		
		return null;
	}
	
}

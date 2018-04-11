package com.shenakr.finalProject.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
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
	 * @throws OfferExceptionHandler 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    //insert function trigger into doGet()
	protected void doGet(HttpServletRequest request, HttpServletResponse response, String functionTrigger) throws ServletException, IOException, OfferExceptionHandler 
	{
		if(functionTrigger.equals("update_offer"))
		{
			updateOffer(request);
		}
		
		else if(functionTrigger.equals("get_user"))
		{
			String StringOfferId = request.getParameter("offerId");
			Offer offer = null;
			
			try
			{
				offer = getOffer(StringOfferId);
			}catch (OfferExceptionHandler e){
				e.printStackTrace();
			}
			String strOffer = new Gson().toJson(offer).toString();
			response.getWriter().write(strOffer);
		}
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		try {
			addNewOffer(request);
		} catch (OfferExceptionHandler e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			deleteOffer(req);
		} catch (OfferExceptionHandler e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try 
		{
			updateOffer(req);
		} catch (HibernateException | OfferExceptionHandler e) {
			e.printStackTrace();
		}
	}
	
	private void deleteOffer(HttpServletRequest req) throws OfferExceptionHandler
	{
		String offerid = req.getParameter("offerId");
		int OfferIdINT = Integer.parseInt(offerid);
		HibernateOfferDAO.getInstance().deleteOffer(OfferIdINT);	
	}
	
	private void updateOffer(HttpServletRequest req) throws OfferExceptionHandler
	{
		Offer offer = generateOffer(req);
		if (offer != null)
		{
			//HibernateOfferDAO.getInstance().editOffer(offer);
		}
		
	}
	
	private Offer getOffer(String strOfferId) throws OfferExceptionHandler
	{
		Offer offer = null;
		if (!strOfferId.isEmpty())
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

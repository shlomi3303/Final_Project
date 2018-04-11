package com.shenkar.finalProject.model.interfaces;

import java.util.List;

import com.shenkar.finalProject.model.Offer;
import com.shenkar.finalProject.model.OfferExceptionHandler;

public interface IOfferDAO 
{
	public void createOffer(Offer offer) throws OfferExceptionHandler;
	
	public void editOffer(int offerId, Offer updateOffer) throws OfferExceptionHandler;
	
	public void deleteOffer(int offerId) throws OfferExceptionHandler;
	
	public Offer getOffer(int offerId) throws OfferExceptionHandler;
	
	public int ttlCalc(int ttl) throws OfferExceptionHandler;
	
	public void status (String status, Offer offer) throws OfferExceptionHandler;
	
	public void notification (Offer offer) throws OfferExceptionHandler;
	
	public List<Offer> getOffers(String userId) throws OfferExceptionHandler;

}

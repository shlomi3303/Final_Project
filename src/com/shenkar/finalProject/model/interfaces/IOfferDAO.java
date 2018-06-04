package com.shenkar.finalProject.model.interfaces;

import java.io.IOException;
import java.util.List;

import com.shenkar.finalProject.model.Match;
import com.shenkar.finalProject.model.Offer;
import com.shenkar.finalProject.model.OfferExceptionHandler;
import com.shenkar.finalProject.model.UserExceptionHandler;

public interface IOfferDAO 
{
	public void createOffer(Offer offer) throws OfferExceptionHandler;
	
	public void editOffer(int offerId, Offer updateOffer, String tableName) throws OfferExceptionHandler;
	
	public void deleteOffer(int offerId, String tableName) throws OfferExceptionHandler;
	
	public Offer getOffer(int offerId, String tableName) throws OfferExceptionHandler;
		
	public void status (String status, Offer offer) throws OfferExceptionHandler;
	
	public void notification (int userId, String subject, String body) throws OfferExceptionHandler, UserExceptionHandler, IOException;
	
	public List<Offer> getUserOffers(int userId) throws OfferExceptionHandler;
	
	public List<Offer> getAllSpecificOfferTable(String tableName) throws OfferExceptionHandler;
	
	public String getRandomOffer(int num, String tableName) throws OfferExceptionHandler;

	public List<Offer> getaAllOfferMatches(List<Match> manApp);

	void hardDeleteOffer(int offerId, String tableName) throws OfferExceptionHandler; 

}

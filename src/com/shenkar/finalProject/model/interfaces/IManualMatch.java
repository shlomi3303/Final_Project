package com.shenkar.finalProject.model.interfaces;

import java.io.IOException;
import java.util.List;

import com.shenkar.finalProject.classes.Match;
import com.shenkar.finalProject.model.ApplicationExceptionHandler;
import com.shenkar.finalProject.model.OfferExceptionHandler;
import com.shenkar.finalProject.model.UserExceptionHandler;

public interface IManualMatch 
{	
	
	public void createManualMatch(Match match, String tableName) throws ApplicationExceptionHandler, OfferExceptionHandler, UserExceptionHandler, IOException;
	
	public void acceptMatch (String user, int userRequestId, String tableName);
	
	public void declineMatch (String user, int userRequestId, String tableName);

	public List<Match> getAllUserManualMatches(int userId, String tableName);
	
	public List<Match> getAllUserToInform(int userToInform);

	int getUserByApplicationId(int applicationId, String category);

	int getUserByOfferId(int offerId, String category);


}

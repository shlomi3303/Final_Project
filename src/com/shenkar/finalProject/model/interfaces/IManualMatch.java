package com.shenkar.finalProject.model.interfaces;

import java.io.IOException;
import java.util.List;

import com.shenkar.finalProject.model.ApplicationExceptionHandler;
import com.shenkar.finalProject.model.ManualMatchOffer;
import com.shenkar.finalProject.model.Match;
import com.shenkar.finalProject.model.OfferExceptionHandler;
import com.shenkar.finalProject.model.UserExceptionHandler;

public interface IManualMatch 
{	
	
	void createMatch(Match match, String matchInitiator, String tableName) throws ApplicationExceptionHandler, OfferExceptionHandler, UserExceptionHandler, IOException;
	
	public List<Match> getAllUserToInform(int userToInform);


}

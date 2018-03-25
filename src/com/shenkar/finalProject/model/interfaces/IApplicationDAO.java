package com.shenkar.finalProject.model.interfaces;

import java.util.List;

import com.shenkar.finalProject.model.Application;
import com.shenkar.finalProject.model.ApplicationExceptionHandler;


public interface IApplicationDAO 
{
	public void createApplication(Application application) throws ApplicationExceptionHandler;
	
	public void editApplication(int offerId, Application updateApplication) throws ApplicationExceptionHandler;
	
	public void deleteApplication(int offerId) throws ApplicationExceptionHandler;
	
	public Application getApplication(int offerId) throws ApplicationExceptionHandler;
	
	public int ttlCalc(int ttl) throws ApplicationExceptionHandler;
	
	public void status (String status, Application offer) throws ApplicationExceptionHandler;
	
	public void notification (Application application) throws ApplicationExceptionHandler;
	
	public List<Application> getApplications(String userId) throws ApplicationExceptionHandler;
	
}

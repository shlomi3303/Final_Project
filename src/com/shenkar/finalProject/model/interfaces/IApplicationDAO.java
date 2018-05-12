package com.shenkar.finalProject.model.interfaces;

import java.io.IOException;
import java.util.List;

import com.shenkar.finalProject.model.Application;
import com.shenkar.finalProject.model.ApplicationExceptionHandler;
import com.shenkar.finalProject.model.UserExceptionHandler;


public interface IApplicationDAO 
{
	public void createApplication(Application application) throws ApplicationExceptionHandler;
	
	public void editApplication(int applicationId, Application updateApplication, String tableName) throws ApplicationExceptionHandler;
	
	public void deleteApplication(int applicationId, String tableName) throws ApplicationExceptionHandler;
	
	public Application getApplication(int applicationId, String tableName) throws ApplicationExceptionHandler;
		
	public void status (String status, Application application) throws ApplicationExceptionHandler;
	
	public void notification (int userId, String subject, String body) throws ApplicationExceptionHandler, UserExceptionHandler, IOException;
	
	public List<Application> getUserApplications(int userId) throws ApplicationExceptionHandler;
	
	public List<Application> getAllSpecificApplicationTable(String tableName) throws ApplicationExceptionHandler;
	
	public String getRandomApplication(int num, String tableName) throws ApplicationExceptionHandler;
	
}

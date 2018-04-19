package com.shenakr.finalProject.controllers;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shenkar.finalProject.model.Application;
import com.shenkar.finalProject.model.ApplicationExceptionHandler;
import com.shenkar.finalProject.model.HibernateApplicationDAO;
import com.shenkar.finalProject.model.OfferExceptionHandler;

/**
 * Servlet implementation class ApplicationServelt
 */
@WebServlet(value="/ApplicationServelt")
public class ApplicationServelt extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApplicationServelt() {super();}

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String function = request.getParameter("function");
		
		if (function!=null && !function.isEmpty()){
		
			if (function.equals("create"))
			{
				try {
					addNewApplication(request);
					response.getWriter().println("apllication was created");
				} 
				catch (OfferExceptionHandler | ApplicationExceptionHandler e) {e.printStackTrace(response.getWriter());}
			}
			else if(function.equals("update"))
			{
				try {updateApplication(request);}
				catch (OfferExceptionHandler | ApplicationExceptionHandler e) {e.printStackTrace(response.getWriter());}			
			}
			
			else if(function.equals("getApplication"))
			{
				String StringApplicationId = request.getParameter("applicationId");				
				String tableName = request.getParameter("tableName");
				
				Application application= null;
				try {application = getApplication(StringApplicationId, tableName);} 
				catch (ApplicationExceptionHandler e) {e.printStackTrace(response.getWriter());}
				//Type ApplicationListType = new TypeToken<ArrayList<Application>>(){}.getType();
	
				String strApplication = new Gson().toJson(application).toString();
				response.setContentType("application/json");
				response.getWriter().write(strApplication);
			}
			
			else if (function.equals("delete"))
			{
				try {deleteApplication(request);}
				catch (ApplicationExceptionHandler e) {e.printStackTrace(response.getWriter());}	
			}
			
			else if (function.equals("getAllApplicationsUser"))
			{
				String stringUserID = request.getParameter("userId");
				if (stringUserID!=null && !stringUserID.isEmpty())
				{
					List<Application> applications = null;
					try 
					{
						
						applications  = getAllApplicationsUser(stringUserID);
						String applicationArray = new Gson().toJson(applications).toString();
						response.setContentType("application/json");
						response.getWriter().write(applicationArray);
						
					}
					catch (ApplicationExceptionHandler e) {e.printStackTrace(response.getWriter());}		
				}
				
			}
		}
	}
	
	private List<Application> getAllApplicationsUser(String stringUserID) throws ApplicationExceptionHandler
	{
		int userId = Integer.parseInt(stringUserID);
		return HibernateApplicationDAO.getInstance().getApplications(userId);
	}
	
	private void deleteApplication (HttpServletRequest req) throws ApplicationExceptionHandler
	{
		String applicationId = req.getParameter("applicationId");
		String tableName = req.getParameter("tableName");
		int applicationIdINT = Integer.parseInt(applicationId);
		HibernateApplicationDAO.getInstance().deleteApplication(applicationIdINT, tableName);
	}
	
	private Application getApplication(String strApplicationId, String tableName) throws ApplicationExceptionHandler
	{
		Application application= null;
		if (strApplicationId != null && !strApplicationId.isEmpty())
		{
			int applicationId = Integer.parseInt(strApplicationId);
			application= HibernateApplicationDAO.getInstance().getApplication(applicationId, tableName);
			if (application!=null)
				return application;
		}
		return null;
	}
	
	
	private void addNewApplication(HttpServletRequest req) throws OfferExceptionHandler, ApplicationExceptionHandler
	{
		Application application = generateApplication(req);
		
		if (application  != null)
		{
			HibernateApplicationDAO.getInstance().createApplication(application);
		}
	}
	
	
	private void updateApplication(HttpServletRequest req) throws OfferExceptionHandler, ApplicationExceptionHandler
	{
		String stringApplicationId = req.getParameter("applicationId");
		String tableName = req.getParameter("tableName");
		
		int applicationId = Integer.parseInt(stringApplicationId);
		Application application = generateApplication(req);
		if (application != null)
		{
			HibernateApplicationDAO.getInstance().editApplication(applicationId, application, tableName);
		}

	}
	
	private Application generateApplication (HttpServletRequest req) throws OfferExceptionHandler
	{
		String applicationString = req.getParameter("application");
		Application Deserialization = null;
		Gson gson = new Gson();
		Deserialization = gson.fromJson(applicationString, Application.class);
		
		if (Deserialization != null){
			return Deserialization;
		}
		
		return null;
	}

}

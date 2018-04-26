
package com.shenakr.finalProject.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.shenkar.finalProject.model.Application;
import com.shenkar.finalProject.model.ApplicationExceptionHandler;
import com.shenkar.finalProject.model.HibernateApplicationDAO;

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
		
			switch (function)
			{
			case "create":
			{
					try 
					{
						addNewApplication(request, response);
						response.getWriter().println("apllication was created");
					} 
					catch (ApplicationExceptionHandler e) {e.printStackTrace(response.getWriter());}
					break;
			}	
			case "update":
			{	
					try {updateApplication(request, response);}
					catch (ApplicationExceptionHandler e) {e.printStackTrace(response.getWriter());}
					break;
				
			}	
			case "getApplication":
				{
					String StringApplicationId = request.getParameter("applicationId");				
					String tableName = request.getParameter("tableName");
					
					Application application= null;
					try {application = getApplication(StringApplicationId, tableName);} 
					catch (ApplicationExceptionHandler e) {e.printStackTrace(response.getWriter());}
		
					String strApplication = new Gson().toJson(application).toString();
					response.setContentType("application/json");
					response.getWriter().write(strApplication);
					break;
				}
				
			case "delete":
				{
					try {deleteApplication(request);}
					catch (ApplicationExceptionHandler e) {e.printStackTrace(response.getWriter());}
					break;
				}
				
			case "getAllApplicationsUser":
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
					break;
				}
				
			case "randomApplications":
				{
					try 
					{
						String strApplicationsList = getRandomApplication(request);
						response.setContentType("application/json");
						response.getWriter().write(strApplicationsList);
					}
					catch (ApplicationExceptionHandler e) {e.printStackTrace(response.getWriter());}
					break;
				}
			default:
				String str = "please insert a valid value into fucntion";
				response.getWriter().write(str);
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
	
	private String getRandomApplication(HttpServletRequest req) throws ApplicationExceptionHandler
	{
		String strNum = req.getParameter("number");
		String tableName = req.getParameter("tableName");
		int num = Integer.parseInt(strNum);
		
		return HibernateApplicationDAO.getInstance().getRandomApplication(num, tableName);
		
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
	
	private void addNewApplication(HttpServletRequest req, HttpServletResponse response) throws ApplicationExceptionHandler, IOException
	{
		Application application = generateApplication(req, response);
		
		if (application  != null)
		{
			response.getWriter().write("application is not a null");
			HibernateApplicationDAO.getInstance().createApplication(application);
			response.getWriter().write("after createApplication function");
		}
	}
	
	private void updateApplication(HttpServletRequest req, HttpServletResponse response) throws ApplicationExceptionHandler, IOException
	{
		String stringApplicationId = req.getParameter("applicationId");
		String tableName = req.getParameter("tableName");
		
		int applicationId = Integer.parseInt(stringApplicationId);
		Application updateApplication = generateApplication(req, response);
		if (updateApplication != null)
		{
			HibernateApplicationDAO.getInstance().editApplication(applicationId, updateApplication, tableName);
		}

	}
	
	private Application generateApplication (HttpServletRequest req, HttpServletResponse response) throws ApplicationExceptionHandler, IOException
	{
		String applicationString = req.getParameter("application");
		response.getWriter().write("The json object is: " + applicationString);
		String tableName = req.getParameter("tableName");
		response.getWriter().write("The table name is: " + tableName);
		Class <?> className = HibernateApplicationDAO.getInstance().getTableMapping(tableName);
		Application Deserialization = null;
		Gson gson = new Gson();
		
		Deserialization = (Application) gson.fromJson(applicationString, className);
		response.getWriter().write("i'm after Deserialization");
		if (Deserialization != null){
			System.out.println(Deserialization.getIsAprroved() + Deserialization.getStatus());
			response.getWriter().write(Deserialization.getApplicationID());
			return Deserialization;
		}
		response.getWriter().write("returing null");		
		return null;
	}
	
}

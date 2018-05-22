
package com.shenkar.finalProject.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;

import com.google.gson.Gson;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.shenkar.finalProject.Globals.WebSocket;
import com.shenkar.finalProject.Globals.sendMail;
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
						response.getWriter().println(Thread.currentThread().getName());

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
					try 
					{
						application = getApplication(StringApplicationId, tableName);
						if (application!=null)
						{
							String strApplication = new Gson().toJson(application).toString();
							response.setContentType("application/json");
							response.setCharacterEncoding("utf-8");
							response.getWriter().write(strApplication);
						}
						
					} 
					catch (ApplicationExceptionHandler e) {e.printStackTrace(response.getWriter());}
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
							if (applications!=null)
							{
								String applicationArray = new Gson().toJson(applications).toString();
								response.setContentType("application/json");
								response.setCharacterEncoding("utf-8");
								response.getWriter().write(applicationArray);
							}
						}
						catch (ApplicationExceptionHandler e) {e.printStackTrace(response.getWriter());}		
					}
					break;
				}
				
			case "getAllSpecificTable":
			{
				String tableName = request.getParameter("tableName");

				List<Application> applications = null;
				
				try 
				{
					applications  = getAllSpecificTable(tableName);
					if (applications!=null)
					{
						String applicationArray = new Gson().toJson(applications).toString();
						response.setContentType("application/json");
						response.setCharacterEncoding("utf-8");
						response.getWriter().write(applicationArray);
					}
				} 
				catch (ApplicationExceptionHandler e) 
				{e.printStackTrace(response.getWriter());}
				break;
			}
				
			case "randomApplications":
				{
					try 
					{
						String strApplicationsList = getRandomApplication(request);
						if (strApplicationsList!=null)
						{
							response.setContentType("application/json");
							response.setCharacterEncoding("utf-8");
							response.getWriter().write(strApplicationsList);
						}
					}
					catch (ApplicationExceptionHandler e) {e.printStackTrace(response.getWriter());}
					break;
				}
			case "test":
			{
				WebSocket.onTextMessage("test");
				/*
				String to = request.getParameter("mail");
				sendMail.sendEmail(to, "Hello to you Yossi!!", "sending from Heruko using sendGrid");
				*/
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
		return HibernateApplicationDAO.getInstance().getUserApplications(userId);
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
			response.getWriter().println("application is not a null");
			HibernateApplicationDAO.getInstance().createApplication(application);
			response.getWriter().println("after createApplication function");
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
	
	private List <Application> getAllSpecificTable (String tableName) throws ApplicationExceptionHandler
	{
		
		return HibernateApplicationDAO.getInstance().getAllSpecificApplicationTable(tableName);
		
	}
	
	
	private Application generateApplication (HttpServletRequest req, HttpServletResponse response) throws ApplicationExceptionHandler, IOException
	{
		String applicationString = req.getParameter("application");
		response.getWriter().println("The json object is: " + applicationString);
		String tableName = req.getParameter("tableName");
		response.getWriter().println("The table name is: " + tableName);
		Class <?> className = null;
		try{
			className = HibernateApplicationDAO.getInstance().getTableMapping(tableName);
		}
		catch (Exception e)
		{
			e.printStackTrace(response.getWriter());
		}
		Application Deserialization = null;
		Gson gson = new Gson();
		if (className!=null){
			Deserialization = (Application) gson.fromJson(applicationString, className);
			
			response.getWriter().println("i'm after Deserialization");
			if (Deserialization != null)
			{
				//System.out.println(Deserialization.getIsAprroved() + Deserialization.getStatus());
				response.getWriter().write(Deserialization.getApplicationID());
				return Deserialization;
			}
		
		}
		response.getWriter().println("returing null");
		return null;
	}	
}

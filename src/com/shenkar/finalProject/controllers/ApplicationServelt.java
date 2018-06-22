	
package com.shenkar.finalProject.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shenkar.finalProject.backgroundModules.AutoMatchModules;
import com.shenkar.finalProject.classes.AppUser;
import com.shenkar.finalProject.classes.Application;
import com.shenkar.finalProject.classes.Match;
import com.shenkar.finalProject.model.ApplicationExceptionHandler;
import com.shenkar.finalProject.model.HibernateApplicationDAO;
import com.shenkar.finalProject.model.HibernateMatchDAO;
import com.shenkar.finalProject.model.HibernateUserDAO;
import com.shenkar.finalProject.model.HibernateUserInterestsDAO;
import com.shenkar.finalProject.model.OfferExceptionHandler;

import com.shenkar.finalProject.model.UserExceptionHandler;

import api.CoralogixLogger;

@WebServlet(value="/ApplicationServelt")
public class ApplicationServelt extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    
	private static final String privateKey = System.getenv("CORALOGIX_privateKey");
	private static final String appName = "Final Project";
	private static final String subSystem = "Application Servlet";

    public ApplicationServelt() {super();}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
       // int companyId = 4092;

        CoralogixLogger.configure(privateKey, appName, subSystem);
        CoralogixLogger logger = new CoralogixLogger(ApplicationServelt.class.toString());

        //logger.info("This is my serious test log 1");
        //logger.info("This is my serious test log 2");
		
		String function = request.getParameter("function");
		
		if (function!=null && !function.isEmpty())
		{
		
			switch (function)
			{
				case "create":
				{
					try 
					{
						addNewApplication(request, response);
						response.getWriter().println("apllication was created");
						logger.info("apllication was created");
						response.getWriter().println(Thread.currentThread().getName());

					} 
					catch (ApplicationExceptionHandler | ParseException | OfferExceptionHandler | UserExceptionHandler e) {e.printStackTrace(response.getWriter());}
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
							String strApplication = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(application).toString();
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
						catch (ApplicationExceptionHandler | OfferExceptionHandler | UserExceptionHandler e) {e.printStackTrace(response.getWriter());}
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
								String applicationArray = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(applications).toString();
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
							String applicationArray = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(applications).toString();
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
					catch (ApplicationExceptionHandler | UserExceptionHandler e) {e.printStackTrace(response.getWriter());}
					break;
				}
				
				case "getUserToInform":
				{
					try
					{
						String strAppId = request.getParameter("applicationId");
						String category = request.getParameter("category");
						if (strAppId!=null&&category!=null)
						{
							int applicationId = Integer.parseInt(strAppId);
							int userId = HibernateMatchDAO.getInstance().getUserByApplicationId(applicationId, category);
							System.out.println("the user id is: " + userId);
							if (userId != 0)
							{
								AppUser user = HibernateUserDAO.getInstance().getUserInfo(userId);
								if (user!=null)
								{
									JSONObject json = new JSONObject();
									json.put("firstname", user.getFirstname());
									json.put("lastname", user.getLastname());
									json.put("phone", user.getPhone());
									json.put("mail", user.getMail());
									json.put("age", user.getAge());
									
									String message = json.toString();
									response.setContentType("application/json");
						        	response.setCharacterEncoding("utf-8");
									response.getWriter().write(message);
								}
							}
							else
							{
								System.out.println("The user was not found");
								logger.info("user was not found in application servelt");
							}
						}
						
					}
					catch (Exception e)
					{
						try {
							throw new ApplicationExceptionHandler("Error in get User Information by Application Id: " + e.getMessage());
						}
						catch (ApplicationExceptionHandler e1) 
						{
							e1.printStackTrace();
							logger.warning("problem in getUserToInform " + e1.getMessage());
							
						}
					}
					break;
				}	
				
				case "getApplicationsIntersted":
				{
					String strUserId = request.getParameter("userId");
					
					if (strUserId!=null)
					{
						int userId = Integer.parseInt(strUserId);
						AppUser user = null;
						try {
							user = HibernateUserDAO.getInstance().getUserInfo(userId);
						} catch (UserExceptionHandler e) {
							e.printStackTrace();
						}
						if (user!=null)
						{
							try 
							{
								String strUserApplicationPreferences = getUserApplicationPreferences(user);
								response.setContentType("application/json");
					        	response.setCharacterEncoding("utf-8");
								response.getWriter().write(strUserApplicationPreferences);
							}
							catch (ApplicationExceptionHandler e) 
							{
							
								e.printStackTrace();
							}
						}
					}
					break;
				}
				case "addInterests":
				{
					String strApplicationId = request.getParameter("applicationId");
					String strUserId = request.getParameter("userId");
					String category = request.getParameter("category");
					
					if (strApplicationId!=null && strUserId!=null&&category!=null)
					{
						try{
							addInterests(strApplicationId, strUserId, category);
						} 
						catch (NumberFormatException | ApplicationExceptionHandler e) 
						{
							e.printStackTrace();
							logger.warning("problem in addInterests: " + e.getMessage());
						}
					}
					break;
				}
				
				case "suggestions":
				{
					String strUserId = request.getParameter("userId");
					try 
					{
						List<Application> list = HibernateUserInterestsDAO.getInstance().suggestions(Integer.parseInt(strUserId));
						String strApplicationlist = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(list).toString();
						response.setContentType("application/json");
						response.setCharacterEncoding("utf-8");
						response.getWriter().write(strApplicationlist);
						logger.info("suggestions was sent to client");
						
					}
					catch (NumberFormatException | UserExceptionHandler | ApplicationExceptionHandler e) {
						e.printStackTrace();
					}
							
				}
				
				case "test":
				{
					try {
						//AutoMatchModules.AutoMatchMainModules();
						
//						Application app = HibernateApplicationDAO.getInstance().getApplication(3, "ride");
//						List <Offer> offerO = HibernateOfferDAO.getInstance().getAllSpecificOfferTable("ride");
//						List<RideOffer> handymanOfferList =  (List <RideOffer>) (List <?>) offerO;
//						Ranking.rideRanking((RideApplication)app, handymanOfferList);
						//Application app = HibernateApplicationDAO.getInstance().getApplication(3, "handyman");
						//List <Offer> offer = HibernateOfferDAO.getInstance().getAllSpecificOfferTable("handyman");
						//List<HandymanOffer> handymanOfferList =  (List <HandymanOffer>) (List <?>) offer;
						//Ranking.handymanRanking((HandymanApplication)app, handymanOfferList);
						AutoMatchModules.AutoMatchMainModules();
					} catch (Exception e) {
						e.printStackTrace();
					}
					/*
					String userId = request.getParameter("userId");
					System.out.println("the user id from post man is: " + userId);
					logger.info("The user id is: " + userId);
					//WebSocket.sendMessageToClient("test", userId);
					 
					 */
					break;
				}
				default:
					String str = "please insert a valid value into fucntion";
					response.getWriter().write(str);
			}
		}
	}
	
	private void addInterests (String strApplicationId, String strUserId, String category) throws NumberFormatException, ApplicationExceptionHandler
	{
		Application app = HibernateApplicationDAO.getInstance().getApplication(Integer.valueOf(strApplicationId), category);
		
		if (app!=null)
		{
			HibernateUserInterestsDAO.getInstance().updateUserInterests(app, Integer.valueOf(strUserId));
			HibernateUserInterestsDAO.getInstance().updateCategoryOffersSuggestions(Integer.valueOf(strUserId), app);
		}
		else
		{
			System.out.println("Application not exist!");
		}
		
	}
	
	private String getUserApplicationPreferences(AppUser user) throws ApplicationExceptionHandler
	{
		
		List<Object> applicationList = new ArrayList<Object>();
		
		if (user.getRide())
		{
			List<Application> rideList = HibernateApplicationDAO.getInstance().getAllSpecificApplicationTable("ride");
			if (rideList!=null)
				applicationList.add(rideList);
		}
		if (user.getHandyman())
		{
			List<Application> handymanList = HibernateApplicationDAO.getInstance().getAllSpecificApplicationTable("handyman");
			if (handymanList!=null)
				applicationList.add(handymanList);
		}
		if (user.getOlders())
		{
			List<Application> oldersList = HibernateApplicationDAO.getInstance().getAllSpecificApplicationTable("olders");
			if (oldersList!=null)
				applicationList.add(oldersList);
		}
		if (user.getStudent())
		{
			List<Application> studentList = HibernateApplicationDAO.getInstance().getAllSpecificApplicationTable("student");
			if (studentList!=null)
				applicationList.add(studentList);
		}
					
		return new Gson().toJson(applicationList).toString();
	}
	
	
	private List<Application> getAllApplicationsUser(String stringUserID) throws ApplicationExceptionHandler
	{
		int userId = Integer.parseInt(stringUserID);
		return HibernateApplicationDAO.getInstance().getUserApplications(userId);
	}
	
	private void deleteApplication (HttpServletRequest req) throws ApplicationExceptionHandler, OfferExceptionHandler, UserExceptionHandler, IOException
	{
		String applicationId = req.getParameter("applicationId");
		String tableName = req.getParameter("tableName");
		int applicationIdINT = Integer.parseInt(applicationId);
		//check if the application have a manual match or auto match
		
		//manual match case:
		Match manualMatch = HibernateMatchDAO.getInstance().getManualMatch(applicationIdINT, "User Application", tableName);
		if (manualMatch!=null)
		{
			//ManualMatchUserOffer offer = (ManualMatchUserOffer)manualMatch;
			HibernateMatchDAO.getInstance().declineMatch("application", applicationIdINT, tableName);
			//HibernateOfferDAO.getInstance().notification(offer.getUserId(), ConstantVariables.subjectMailDecline, ConstantVariables.bodyMailApplicationDecline);
		}
		
		//auto match case:
		else
		{
			Match autoMatch = HibernateMatchDAO.getInstance().getAutoMatch(applicationIdINT, "User Application", tableName);
			if (autoMatch!=null)
			{
				HibernateMatchDAO.getInstance().declineMatch("application",applicationIdINT , tableName);
				/*
				AutoMatch match = (AutoMatch)autoMatch;
				Offer offer = HibernateOfferDAO.getInstance().getOffer(match.getOfferId(), tableName);
				HibernateOfferDAO.getInstance().notification(offer.getUserId(), ConstantVariables.subjectMailDecline, ConstantVariables.bodyMailApplicationDecline);
				HibernateOfferDAO.getInstance().status(ConstantVariables.waitingForMatch, offer);
				*/
			}
		}
		HibernateApplicationDAO.getInstance().deleteApplication(applicationIdINT, tableName);
	}
	
	private String getRandomApplication(HttpServletRequest req) throws ApplicationExceptionHandler, UserExceptionHandler
	{
		String strNum = req.getParameter("number");
		String tableName = req.getParameter("tableName");
		int num = Integer.parseInt(strNum);
		String strUserId = req.getParameter("userId");
		int userId;
		if (strUserId==null || strUserId.equals(""))
			userId=0;
		else
			userId = Integer.valueOf(strUserId);
		
		return HibernateApplicationDAO.getInstance().getRandomApplication(num, tableName, userId);
		
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
	
	private void addNewApplication(HttpServletRequest req, HttpServletResponse response) throws ApplicationExceptionHandler, IOException, ParseException, OfferExceptionHandler, UserExceptionHandler
	{
		Application application = generateApplication(req, response);
		
		if (application!=null)
		{
			response.getWriter().println("application is not a null");
			int applicationId = HibernateApplicationDAO.getInstance().createApplication(application);
			if (applicationId>0)
				System.out.println("the application id of the ride is: " + applicationId);
			/*
			//check if the application is a ride
			if (applicationId>0)
			{
				List<Offer> offerRideList = HibernateOfferDAO.getInstance().getAllSpecificOfferTable("ride");
				List<RideOffer> rideOfferList =  (List <RideOffer>) (List <?>) offerRideList;
				RideApplication rideApp =(RideApplication)HibernateApplicationDAO.getInstance().getApplication(applicationId, "ride");
				if (rideOfferList!=null && rideApp!=null)
				{
					List <Integer> listMatchIdOffer = Ranking.rideRanking(rideApp, rideOfferList);
					if (listMatchIdOffer!=null)
					{
						int offerId = listMatchIdOffer.get(0);
						AutoMatchModules.createAutoMatch("ride", offerId, applicationId);
						AutoMatchModules.updateApplicationAndOffer(rideApp, offerId, "ride", listMatchIdOffer);
						System.out.println("Auto match to ride application was created");
					}
				}
			}
			*/
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

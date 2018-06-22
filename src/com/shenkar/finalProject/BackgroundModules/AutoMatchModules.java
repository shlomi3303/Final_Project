package com.shenkar.finalProject.backgroundModules;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.shenkar.finalProject.Globals.ConstantVariables;
import com.shenkar.finalProject.Globals.GlobalsFunctions;
import com.shenkar.finalProject.classes.Application;
import com.shenkar.finalProject.classes.AutoMatch;
import com.shenkar.finalProject.classes.HandymanApplication;
import com.shenkar.finalProject.classes.HandymanOffer;
import com.shenkar.finalProject.classes.Offer;
import com.shenkar.finalProject.classes.OldersApplication;
import com.shenkar.finalProject.classes.OldersOffer;
import com.shenkar.finalProject.classes.RideApplication;
import com.shenkar.finalProject.classes.RideOffer;
import com.shenkar.finalProject.classes.StudentApplication;
import com.shenkar.finalProject.classes.StudentOffer;
import com.shenkar.finalProject.model.ApplicationExceptionHandler;
import com.shenkar.finalProject.model.HibernateApplicationDAO;
import com.shenkar.finalProject.model.HibernateOfferDAO;
import com.shenkar.finalProject.model.OfferExceptionHandler;
import com.shenkar.finalProject.model.Ranking;
import com.shenkar.finalProject.model.UserExceptionHandler;

@SuppressWarnings("unchecked")
@WebListener
public class AutoMatchModules implements ServletContextListener 
{
	private static SessionFactory autoMatchFactory;
	
	public AutoMatchModules() {}
	
	Timer timer = new Timer();
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) 
	{
        System.out.println("Auto Match Module is Closed");	
		if (timer!=null)
			timer.cancel();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) 
	{
		System.out.println("Auto Match Module is On: " + new Date());
		
        timer.scheduleAtFixedRate(new autoMatch(), 0, ConstantVariables.ONCE_PER_DAY/2);
	}
	
	class autoMatch extends TimerTask
	{

		@Override
		public void run() 
		{
			Date date = new Date();
			try
			{
				System.out.println("The current date and time is: " + date);
				AutoMatchMainModules();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
	}
	
	
	public static void AutoMatchMainModules() throws ApplicationExceptionHandler
	{
		List <Application> oldersAppList = HibernateApplicationDAO.getInstance().getAllSpecificApplicationTable("olders");
		
		List <Application> handymanAppList = HibernateApplicationDAO.getInstance().getAllSpecificApplicationTable("handyman");
		
		List <Application> studentAppList = HibernateApplicationDAO.getInstance().getAllSpecificApplicationTable("student");
		
		List <Application> rideAppList = HibernateApplicationDAO.getInstance().getAllSpecificApplicationTable("ride");
		
		Session session = null;
		
		try 
		{
			initAutoMatchFactory();
			session = GlobalsFunctions.getSession(autoMatchFactory);
			if (session!=null)
			{
				
			 //handling all the olders Application List
			  int offerId = 0;
				
			  if (oldersAppList!=null && oldersAppList.size()>0)
			  {
				for (int i=0; i<oldersAppList.size(); i++)
				{
						List<Offer> offerList = HibernateOfferDAO.getInstance().getAllSpecificOfferTable("olders");
						offerId=0;
						if (offerList != null)
						{
							List<OldersOffer> oldersOfferList = (List <OldersOffer>) (List <?>) offerList; 

							Application oldersApp = oldersAppList.get(i);
	
							List <Integer> list = Ranking.oldersRanking((OldersApplication) oldersApp, oldersOfferList);
							
							if (list!=null && list.size()>0)
							{
								offerId = list.get(0);
								if (offerId!=0)
								{
									updateApplicationAndOffer(oldersApp, offerId, "olders", list);
									createAutoMatch("olders", offerId, oldersApp.getApplicationID());
								}
							}
						}
					}
				}
				
				if (handymanAppList!=null && handymanAppList.size()>0){
					for (int i=0; i<handymanAppList.size(); i++)
					{
						List<Offer> offerList = HibernateOfferDAO.getInstance().getAllSpecificOfferTable("handyman");
						offerId =0;
						if (offerList!=null)
						{
							List<HandymanOffer> handymanOfferList =  (List <HandymanOffer>) (List <?>) offerList;
							
							HandymanApplication handymanApp = (HandymanApplication) handymanAppList.get(i);
							
							List <Integer> list = Ranking.handymanRanking(handymanApp, handymanOfferList);
							
							if (list!=null && list.size()>0)
							{
								offerId = list.get(0);
								if (offerId!=0)
								{
									updateApplicationAndOffer(handymanApp, offerId, "handyman", list);
									createAutoMatch("handyman", offerId, handymanApp.getApplicationID());
								}
							}
						}
						
					}
				}
				
				if (studentAppList!=null && studentAppList.size()>0)
				{
					for (int i=0; i<studentAppList.size(); i++)
					{
						List<Offer> offerList = HibernateOfferDAO.getInstance().getAllSpecificOfferTable("student");
						offerId = 0;
						if (offerList!=null)
						{
							List<StudentOffer> studentOfferList =  (List <StudentOffer>) (List <?>) offerList;
							
							StudentApplication studentApp = (StudentApplication) studentAppList.get(i);
							
							List <Integer> list = Ranking.studentRanking(studentApp, studentOfferList);
							
							if (list!=null && list.size()>0)
							{
								offerId = list.get(0);
								if (offerId>0)
								{
									updateApplicationAndOffer(studentApp, offerId, "student", list);
									createAutoMatch("student", offerId, studentApp.getApplicationID());
								}
							}
						}
					}
				}
				
				if (rideAppList!=null && rideAppList.size()>0)
				{
					for (int i=0; i<rideAppList.size(); i++)
					{
						List<Offer> offerList = HibernateOfferDAO.getInstance().getAllSpecificOfferTable("ride");
						offerId=0;
						if (offerList!=null)
						{
							List<RideOffer> rideOfferList =  (List <RideOffer>) (List <?>) offerList;
							
							RideApplication rideApp = (RideApplication) rideAppList.get(i);
							
							List <Integer> list = Ranking.rideRanking(rideApp, rideOfferList);
							
							if (list!=null & list.size()>0)
							{
								offerId = list.get(0);
								if (offerId>0)
								{
									updateApplicationAndOffer(rideApp, offerId, "ride", list);
									createAutoMatch("ride", offerId, rideApp.getApplicationID());
								}
							}
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally 
		{
			try 
			{
				if (session!=null)
					session.close();
			} 
			catch (HibernateException e){
				e.printStackTrace();
			}
		}
	}
	
	public static void updateApplicationAndOffer(Application tempApp, int offerId, String category, List<Integer> offerList) throws OfferExceptionHandler, ApplicationExceptionHandler, UserExceptionHandler, IOException
	{
		if (offerList!=null && offerList.size()>0)
		{
			HibernateApplicationDAO.getInstance().updateOfferListPotentialMatch(offerList, tempApp);
			HibernateApplicationDAO.getInstance().status(ConstantVariables.waitingForbothSideApproval, tempApp);
			HibernateApplicationDAO.getInstance().notification(tempApp.getUserId(), ConstantVariables.subjectMailAutoMatchApplication, ConstantVariables.bodyMailApplication);
	
			Offer offer = HibernateOfferDAO.getInstance().getOffer(offerId, category);
	
			HibernateOfferDAO.getInstance().notification(offer.getUserId(), ConstantVariables.subjectMailAutoMatchOffer, ConstantVariables.bodyMailInterestedInOffer);
			HibernateOfferDAO.getInstance().status(ConstantVariables.waitingForbothSideApproval, offer);
		}
	}
	
	public static void createAutoMatch(String category, int offerId, int appId)
	{
		Session session = null;
		
		try
		{
			initAutoMatchFactory();
			session = GlobalsFunctions.getSession(autoMatchFactory);
			
			if (session!=null)
			{
				AutoMatch match = new AutoMatch (false, false, ConstantVariables.waitingForbothSideApproval, 0, 0,category, offerId, appId);
				session.beginTransaction();
				session.save(match);
				session.getTransaction().commit();
			}
			else
				System.out.println("Auto match: session in null");
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		finally
		{
			try
			{
				if (session!=null)
					session.close();
			}
			catch (HibernateException e)
			{
				e.printStackTrace();
			}
		}
	
	}
	
	private static void initAutoMatchFactory ()
	{
		try
		{
		 if (autoMatchFactory==null)
		  {
			 autoMatchFactory =  GlobalsFunctions.initSessionFactory(autoMatchFactory, "hibernateMatch.cfg.xml");
		  }
		}
		catch (Exception e)
		{
			System.out.println("Error in init Auto Match Modules Factory " + e.getMessage());
		}
	}

	
}

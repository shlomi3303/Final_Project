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
import com.shenkar.finalProject.classes.ManualMatchUserApplication;
import com.shenkar.finalProject.classes.ManualMatchUserOffer;
import com.shenkar.finalProject.classes.Offer;
import com.shenkar.finalProject.classes.RideApplication;
import com.shenkar.finalProject.classes.RideOffer;
import com.shenkar.finalProject.model.ApplicationExceptionHandler;
import com.shenkar.finalProject.model.HibernateApplicationDAO;
import com.shenkar.finalProject.model.HibernateOfferDAO;
import com.shenkar.finalProject.model.OfferExceptionHandler;
import com.shenkar.finalProject.model.UserExceptionHandler;

@SuppressWarnings("unchecked")
@WebListener
public class FollowUp implements ServletContextListener 
{
	
	private static SessionFactory followUpManualMatchFactory;
	
	Timer timer = new Timer();	
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) 
	{
        System.out.println("Follow Up model is Closed");
        if (timer!=null)
        	timer.cancel();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) 
	{
        System.out.println("Follow Up model is On");
        System.out.println("follow up thread: " + Thread.currentThread().getName());
        //timer.scheduleAtFixedRate(new followUpRunner(), 0, ConstantVariables.TEST);

	}
	
	private void reminderScannerAutoMatch()
	{
		Session session = null;
		List <AutoMatch> autoMatchApp = null;
		List <AutoMatch> autoMatchOffer = null;
		
		try
		{
			initFollowUpFactory();
			session = GlobalsFunctions.getSession(followUpManualMatchFactory);
			session.beginTransaction();
			
			//deal with match that the application user still not approval
			autoMatchApp = session.createQuery ("from " + AutoMatch.class.getName() + " as table where table.isArchive = false and table.status like :key").setParameter("key",  "%" + ConstantVariables.waitingForAppApproval + "%").getResultList();
			
			if (autoMatchApp!=null)
			{
				for (int i=0; i<autoMatchApp.size(); i++)
				{
					if (autoMatchApp.get(i).getReminderCount()<3)
					{
						autoMatchApp.get(i).setReminderCount(autoMatchApp.get(i).getReminderCount()+1);
						session.update(autoMatchApp.get(i));
						
						//notify the user application that he needs to approve or decline the match
						Application app = HibernateApplicationDAO.getInstance().getApplication(autoMatchApp.get(i).getApplicationId(), autoMatchApp.get(i).getCategory());
						HibernateApplicationDAO.getInstance().notification(app.getUserId(), ConstantVariables.subjectReminderApplication, ConstantVariables.bodyMailReminderApplication);
					}
					
					//handle with the ttl value?
					else if (autoMatchApp.get(i).getReminderCount()==3)
					{
						//change the match to archive
						autoMatchApp.get(i).setArchive(true);
						
						session.update(autoMatchApp.get(i));
						
						//notify the offer user that the match is not relevant
						Offer offer = HibernateOfferDAO.getInstance().getOffer(autoMatchApp.get(i).getOfferId(), autoMatchApp.get(i).getCategory());
						HibernateOfferDAO.getInstance().notification(offer.getUserId(), ConstantVariables.subjectMailDecline, ConstantVariables.bodyMailApplicationDecline);
						
					}
				}
			}
			
			autoMatchOffer = session.createQuery ("from " + AutoMatch.class.getName() + " as table where table.isArchive = false and table.status like :key").setParameter("key",  "%" + ConstantVariables.waitingForOfferApproval + "%").getResultList();
			
			if (autoMatchOffer!=null)
			{
				
				for (int j=0; j<autoMatchOffer.size(); j++)
				{
					if (autoMatchOffer.get(j).getReminderCount()<3)
					{
						autoMatchOffer.get(j).setReminderCount(autoMatchOffer.get(j).getReminderCount()+1);
						session.update(autoMatchOffer.get(j));
						
						//notify the offer user that he needs to approve or decline the match
						Offer offer = HibernateOfferDAO.getInstance().getOffer(autoMatchOffer.get(j).getOfferId(), autoMatchOffer.get(j).getCategory());
						HibernateOfferDAO.getInstance().notification(offer.getUserId(), ConstantVariables.subjectReminderOffer, ConstantVariables.bodyMailReminderOffer);
						
					}
					else if (autoMatchOffer.get(j).getReminderCount()==3)
					{
						//change the match to the archive
						autoMatchOffer.get(j).setArchive(true);
						session.update(autoMatchOffer.get(j));
						
						Application app = HibernateApplicationDAO.getInstance().getApplication(autoMatchOffer.get(j).getApplicationId(), autoMatchOffer.get(j).getCategory());
						HibernateApplicationDAO.getInstance().notification(app.getUserId(), ConstantVariables.subjectMailDecline, ConstantVariables.bodyMailOfferDecline);
						
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

	private void reminderScannerManualMatch()
	{
		//Date today = new Date();
		Session session = null;
		
		List <ManualMatchUserOffer> mmOffer = null;
		List <ManualMatchUserApplication> mmApp = null;
		
		try
		{
			initFollowUpFactory();
			session = GlobalsFunctions.getSession(followUpManualMatchFactory);
			session.beginTransaction();
			
			mmOffer = session.createQuery ("from " + ManualMatchUserOffer.class.getName() + " as table where table.isArchive = false and table.status like :key").setParameter("key",  "%" + ConstantVariables.waitingForAppApproval + "%").getResultList();
			
			if (mmOffer!= null)
			{
				for (int i=0; i<mmOffer.size(); i++)
				{
					if (mmOffer.get(i).getReminderCount()<3)
					{
						mmOffer.get(i).setReminderCount(mmOffer.get(i).getReminderCount()+1);
						session.update(mmOffer.get(i));
						
						//notify the user application that he needs to approve or decline the match
						HibernateApplicationDAO.getInstance().notification(mmOffer.get(i).getUserToInform(), ConstantVariables.subjectReminderApplication, ConstantVariables.bodyMailReminderApplication);
					}
					//handle with the ttl value?
					else if (mmOffer.get(i).getReminderCount()==3)
					{
						//change the match to archive
						mmOffer.get(i).setArchive(true);
						
						session.update(mmOffer.get(i));
						
						//notify the offer user that the match is not relevant
						HibernateOfferDAO.getInstance().notification(mmOffer.get(i).getUserId(), ConstantVariables.subjectMailDecline, ConstantVariables.bodyMailApplicationDecline);
						
					}
				}
			}
			
			mmApp = session.createQuery ("from " + ManualMatchUserApplication.class.getName() + " as table where table.isArchive = false and table.status like :key").setParameter("key",  "%" + ConstantVariables.waitingForOfferApproval + "%").getResultList();
			
			if (mmApp!=null)
			{
				
				for (int j=0; j<mmApp.size(); j++)
				{
					if (mmApp.get(j).getReminderCount()<3)
					{
						mmApp.get(j).setReminderCount(mmApp.get(j).getReminderCount()+1);
						session.update(mmApp.get(j));
						//notify the offer user that he needs to approve or decline the match
						HibernateOfferDAO.getInstance().notification(mmApp.get(j).getUserToInform(), ConstantVariables.subjectReminderOffer, ConstantVariables.bodyMailReminderOffer);
						
					}
					else if (mmApp.get(j).getReminderCount()==3)
					{
						//change the match to the archive
						mmApp.get(j).setArchive(true);
						session.update(mmApp.get(j));

						HibernateApplicationDAO.getInstance().notification(mmApp.get(j).getUserId(), ConstantVariables.subjectMailDecline, ConstantVariables.bodyMailOfferDecline);
						
					}
				}
			}
			
			if (mmApp != null || mmOffer !=null)
				session.getTransaction().commit();
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
	
	private void sendSurvey() throws ApplicationExceptionHandler, UserExceptionHandler, IOException, OfferExceptionHandler
	{
		Date today = new Date();
		Session session = null;

		System.out.println("In send Survey method. The current date and time is: " + today);
		
		//send survey to all the match in Manual Match offer table
		List <ManualMatchUserOffer> mmOffer = null;
		List <ManualMatchUserApplication> mmApp = null;
		
		try
		{
			initFollowUpFactory();
			session = GlobalsFunctions.getSession(followUpManualMatchFactory);
			session.beginTransaction();
			
			mmOffer = session.createQuery ("from " + ManualMatchUserOffer.class.getName() + " as table where table.isArchive = false and table.status like :key").setParameter("key",  "%" + ConstantVariables.bothSideApproved + "%").getResultList();
			
			if (mmOffer!=null)
			{
				for (int i=0; i<mmOffer.size(); i++)
				{

					if ( mmOffer.get(i).getCategory().equals("ride"))
					{
						RideApplication ride = (RideApplication) HibernateApplicationDAO.getInstance().getApplication( mmOffer.get(i).getApplicationID(), mmOffer.get(i).getCategory());
						if (today.compareTo(ride.getEndPeriod())>0)
						{
							//send mail to the application user
							HibernateApplicationDAO.getInstance().notification(ride.getUserId(), ConstantVariables.subjectMailSurvey, ConstantVariables.bodyMailSurvey);
							//send mail to the offer user
							HibernateOfferDAO.getInstance().notification(mmOffer.get(i).getUserToInform(), ConstantVariables.subjectMailSurvey, ConstantVariables.bodyMailSurvey);
						}
					}
					else
					{
						Application app = HibernateApplicationDAO.getInstance().getApplication( mmOffer.get(i).getApplicationID(), mmOffer.get(i).getCategory());

						if (today.compareTo(app.getPeriod())>0)
						{
							//send mail to the application user
							HibernateApplicationDAO.getInstance().notification(app.getUserId(), ConstantVariables.subjectMailSurvey, ConstantVariables.bodyMailSurvey);
							//send mail to the offer user
							HibernateOfferDAO.getInstance().notification(mmOffer.get(i).getUserToInform(), ConstantVariables.subjectMailSurvey, ConstantVariables.bodyMailSurvey);
						}
					}
				}
			}
			
			mmApp = session.createQuery ("from " + ManualMatchUserApplication.class.getName() + " as table where table.isArchive = false and table.status like :key").setParameter("key",  "%" + ConstantVariables.bothSideApproved + "%").getResultList();
			
			if (mmApp !=null)
			{
				for (int j=0; j<mmApp.size(); j++)
				{
					if (mmApp.get(j).getCategory().equals("ride"))
					{
						RideOffer ride = (RideOffer)HibernateOfferDAO.getInstance().getOffer(mmApp.get(j).getOfferId(), mmApp.get(j).getCategory());
						if (today.compareTo(ride.getEndPeriod()) >0 );
						{
							//send mail to the offer user
							HibernateOfferDAO.getInstance().notification(ride.getOfferId(), ConstantVariables.subjectMailSurvey, ConstantVariables.bodyMailSurvey);
							//send mail to the application user
							HibernateApplicationDAO.getInstance().notification(mmApp.get(j).getUserToInform(), ConstantVariables.subjectMailSurvey, ConstantVariables.bodyMailSurvey);
						}
					}
				}
			}
			if (mmApp != null || mmOffer !=null)
				session.getTransaction().commit();

		
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
	
	class followUpRunner extends TimerTask
	{

		@Override
		public void run() 
		{
			try 
			{
				Date date = new Date();

				System.out.println("Activate Follow Up Methods at: " + date);
				sendSurvey();
				reminderScannerManualMatch();
				reminderScannerAutoMatch();
			} catch (ApplicationExceptionHandler | UserExceptionHandler | IOException | OfferExceptionHandler e) {
				e.printStackTrace();
			}
		}
	}
	
	
	private static void initFollowUpFactory ()
	{
		try
		{
		 if (followUpManualMatchFactory==null)
		  {
			 followUpManualMatchFactory = GlobalsFunctions.initSessionFactory(followUpManualMatchFactory,"hibernateMatch.cfg.xml");
		  }
		}
		catch (Exception e){}
		
	}
}

package com.shenkar.finalProject.Globals;

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

import com.shenkar.finalProject.model.Application;
import com.shenkar.finalProject.model.ApplicationExceptionHandler;
import com.shenkar.finalProject.model.HibernateApplicationDAO;
import com.shenkar.finalProject.model.HibernateOfferDAO;
import com.shenkar.finalProject.model.ManualMatchUserApplication;
import com.shenkar.finalProject.model.ManualMatchUserOffer;
import com.shenkar.finalProject.model.OfferExceptionHandler;
import com.shenkar.finalProject.model.RideApplication;
import com.shenkar.finalProject.model.RideOffer;
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
	
	private void reminderScanner()
	{
		//Date today = new Date();
		Session session = null;
		
		List <ManualMatchUserOffer> mmOffer = null;
		List <ManualMatchUserApplication> mmApp = null;
		
		try
		{
			initFollowUpFactory();
			session = getSessionManualMatch();
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
			if (session!=null)
				session.close();
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
			session = getSessionManualMatch();
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
			if (session!=null)
				session.close();
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
				reminderScanner();
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
	
	private static Session getSessionManualMatch() throws HibernateException {         
		   Session sess = null;       
		   try {         
		       sess = followUpManualMatchFactory.getCurrentSession();  
		   } catch (org.hibernate.HibernateException he) {  
		       sess = followUpManualMatchFactory.openSession();     
		   }             
		   return sess;
	} 
}

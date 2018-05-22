package com.shenkar.finalProject.Globals;

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
import org.hibernate.cfg.Configuration;

import com.shenkar.finalProject.model.Application;
import com.shenkar.finalProject.model.ApplicationExceptionHandler;
import com.shenkar.finalProject.model.HibernateApplicationDAO;
import com.shenkar.finalProject.model.HibernateOfferDAO;
import com.shenkar.finalProject.model.Offer;
import com.shenkar.finalProject.model.OfferExceptionHandler;

@WebListener
public class CleanUp implements ServletContextListener
{

	private static SessionFactory cleanUpFactoryApplication;
	private static SessionFactory cleanUpFactoryOffer;


	Timer timer = new Timer();
	private final static int GET_ALL_DB = -1;
	public CleanUp() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) 
	{
		
        System.out.println("Clean Up model is Closed");	
        if (timer!=null)
        	timer.cancel();

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) 
	{
        System.out.println("Clean Up model is On");
        System.out.println("clean up thread: " +Thread.currentThread().getName());

        //timer.scheduleAtFixedRate(new cleanUp(), 0, ConstantVariables.TEST);
	}
	
	private static void updateTTLApplication () throws ApplicationExceptionHandler
	{
		
		List <Application> list = HibernateApplicationDAO.getInstance().getUserApplications(GET_ALL_DB);
		
		if (list!=null)
		{
			System.out.println("List is not a null");
			initCleanUpFactory();
			Session session = getSessionApplication();
			if (session != null)
			{
				System.out.println("in updateTTLApplication");
				session.beginTransaction();
				for (int i=0; i<list.size(); i++)
				{
					if (list.get(i).getTTL()>0)
					{
						list.get(i).setTTL(list.get(i).getTTL()-1);
						session.update(list.get(i));
					}
					else if (list.get(i).getTTL()==0)
					{
						//session.delete(list.get(i));
					}
				}
				session.getTransaction().commit();
			}	
		}
	}
	
	
	private static void updateTTLOffer () throws OfferExceptionHandler
	{
		
		List <Offer> list = HibernateOfferDAO.getInstance().getUserOffers(GET_ALL_DB);
		
		if (list!=null)
		{
			initCleanUpFactory();
			Session session = getSessionOffer();
			if (session != null)
			{
				session.beginTransaction();
				for (int i=0; i<list.size(); i++)
				{
					if (list.get(i).getTTL()>0)
					{
						list.get(i).setTTL(list.get(i).getTTL()-1);
						session.update(list.get(i));
					}
					else if (list.get(i).getTTL()==0)
					{
						//session.delete(list.get(i));
					}
				}
				session.getTransaction().commit();
			}	
		}
	}
	
	private static void initCleanUpFactory ()
	{
		try{
		 if (cleanUpFactoryApplication==null || cleanUpFactoryOffer==null)
		  {
			 cleanUpFactoryApplication = new Configuration().configure("hibernateApplication.cfg.xml").buildSessionFactory();
			 cleanUpFactoryOffer = new Configuration().configure("hibernateOffer.cfg.xml").buildSessionFactory();
		  }
		}
		catch (Exception e){}
	}
	
	private static Session getSessionApplication() throws HibernateException {         
		   Session sess = null;       
		   try {         
		       sess = cleanUpFactoryApplication.getCurrentSession();  
		   } catch (org.hibernate.HibernateException he) {  
		       sess = cleanUpFactoryApplication.openSession();     
		   }             
		   return sess;
	} 
	
	private static Session getSessionOffer() throws HibernateException {         
		   Session sess = null;       
		   try {         
		       sess = cleanUpFactoryOffer.getCurrentSession();  
		   } 
		   catch (org.hibernate.HibernateException he) {  
		       sess = cleanUpFactoryOffer.openSession();     
		   }             
		   return sess;
	} 
	
	class cleanUp extends TimerTask
	{
		@Override
		public void run() {
		try {
				Date date = new Date();

				System.out.println("The current date and time is: " + date);
				updateTTLApplication();
				updateTTLOffer();
			} catch (ApplicationExceptionHandler | OfferExceptionHandler e) {
				e.printStackTrace();
			}		
		}
	}
}

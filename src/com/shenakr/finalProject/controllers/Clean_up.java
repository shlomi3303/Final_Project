package com.shenakr.finalProject.controllers;

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

@WebListener
public class Clean_up implements ServletContextListener
{

	private static SessionFactory cleanUpFactory;

	Timer timer = new Timer();
   // private final static long ONCE_PER_DAY = 1000*60*60*24;
    private final static long TEST = 1000*30; 
	public Clean_up() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
        System.out.println("On shutdown web app");

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) 
	{
        System.out.println("On start web app");

        timer.scheduleAtFixedRate(new cleanUp(), 0, TEST);
	}
	
	private static void updateTTLApplication () throws ApplicationExceptionHandler
	{
		
		List <Application> list = HibernateApplicationDAO.getInstance().getApplications(-1);
		
		if (list!=null)
		{
			initCleanUpFactory();
			Session session = getSession();
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
						session.delete(list.get(i));
					}
				}
				session.getTransaction().commit();
			}	
		}
	}
	
	private static void initCleanUpFactory ()
	{
		try{
		 if (cleanUpFactory==null)
		  {cleanUpFactory = new Configuration().configure("hibernateU.cfg.xml").buildSessionFactory();}
		}
		catch (Exception e){}
	}
	
	private static Session getSession() throws HibernateException {         
		   Session sess = null;       
		   try {         
		       sess = cleanUpFactory.getCurrentSession();  
		   } catch (org.hibernate.HibernateException he) {  
		       sess = cleanUpFactory.openSession();     
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
			} catch (ApplicationExceptionHandler e) {
				e.printStackTrace();
			}		
		}
	}
}

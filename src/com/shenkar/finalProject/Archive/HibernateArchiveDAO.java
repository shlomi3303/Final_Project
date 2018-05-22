package com.shenkar.finalProject.Archive;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.shenkar.finalProject.Globals.ConstantVariables;
import com.shenkar.finalProject.model.AppUser;
import com.shenkar.finalProject.model.Application;
import com.shenkar.finalProject.model.HibernateApplicationDAO;
import com.shenkar.finalProject.model.Offer;

public class HibernateArchiveDAO {

	private static HibernateArchiveDAO instance;
	
	private static SessionFactory archiveFactory;
	
	private HibernateArchiveDAO () {}
	
	public static HibernateArchiveDAO getInstance() 
	{	
		if (instance == null) {
			instance = new HibernateArchiveDAO();
			initArchiveFactory();
		}
		return instance;
	}
	
	
	public void addNewApplicationArchive (Application app, String tableName)
	{
		Session session = null;
		
		try
		{
			initArchiveFactory();
			session = getSession();
			if (session != null && app!=null)
			{
				session.beginTransaction();

				if (tableName.equals("olders"))
				{
					OldersApplicationArchive oldersArchive;
					oldersArchive = (OldersApplicationArchive) app;
					session.save(oldersArchive);
				}
				else if (tableName.equals("ride"))
				{
					RideApplicationArchive rideArchive = null;
					
					rideArchive.setApplicationID(app.getApplicationID());
					rideArchive.setDescription(app.getDescription());
					//rideArchive.setDestination(app.getde);
					session.save(rideArchive);
				}
				
				session.getTransaction().commit();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if (session.getTransaction() !=null) session.getTransaction().rollback();

		}
		finally
		{
			session.close();
		}
		
	}
	
	public void addNewOfferArchive(Offer offer)
	{
		
	}
	
	public void addNewUserArchive(AppUser user)
	{
		
	}

	
	public static void initArchiveFactory()
	{
		if (archiveFactory==null)
		//{archiveFactory = new Configuration().configure("hibernateArchive.cfg.xml").buildSessionFactory();}
		
		{
			Configuration cfg = new Configuration();
			cfg.configure("hibernateArchive.cfg.xml");
			String userName = null;
			String password = null;
			String url = null;
			
			try
			{
				userName = System.getenv("User_API_KEY");
				password = System.getenv("Password_API_KEY");
				url = System.getenv("URL_API_KEY");
				
				if (userName!=null && password!=null && url!=null)
				{
					System.out.println("In Heruko Server Mode");
					cfg.getProperties().setProperty("hibernate.connection.username",userName);
					cfg.getProperties().setProperty("hibernate.connection.password",password);
					cfg.getProperties().setProperty("hibernate.connection.url",url);
				}
				else
				{
					System.out.println("In Local Host Mode");
					cfg.getProperties().setProperty("hibernate.connection.username",ConstantVariables.userName);
					cfg.getProperties().setProperty("hibernate.connection.password",ConstantVariables.password);
					cfg.getProperties().setProperty("hibernate.connection.url",ConstantVariables.URL);
				}
				archiveFactory = cfg.buildSessionFactory();
			}
			
		
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
	}
	
	private static Session getSession() throws HibernateException {         
		   Session sess = null;       
		   try {         
		       sess = archiveFactory.getCurrentSession();  
		   } catch (org.hibernate.HibernateException he) {  
		       sess = archiveFactory.openSession();     
		   }             
		   return sess;
	} 
	
}

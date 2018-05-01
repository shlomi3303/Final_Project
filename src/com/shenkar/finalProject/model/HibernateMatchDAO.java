package com.shenkar.finalProject.model;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.shenkar.finalProject.model.interfaces.IManualMatch;

public class HibernateMatchDAO implements IManualMatch {

	private static HibernateMatchDAO instance;
	private static SessionFactory matchFactory;

	public HibernateMatchDAO() {}
	
	public static HibernateMatchDAO getInstance() 
	{	
		if (instance == null) 
		{
			instance = new HibernateMatchDAO();
			initMatchFactory();
		}
		return instance;
	}
	
	@Override
	public void createMatch(Match match, String matchInitiator) 
	{
		Session session = null;
		
		try
		{
			initMatchFactory();
			session = getSession();
			if (session != null && match!=null)
			{
				if (match.getClass().equals(ManualMatch.class))
					{
						if (matchInitiator.equals("offer")){
							match.setOfferAproved(true);
							match.setApplicationAproved(false);
							match.setStatus("Waiting for application user approval");
						}
						else{
							match.setApplicationAproved(true);
							match.setOfferAproved(false);
							match.setStatus("Waiting for offer user approval");
						}
						match.setTTL(7);
					
					}
				else if (match.getClass().equals(AutoMatch.class))
					{match.setTTL(5);}
								
				session.beginTransaction();
				session.save(match); 
				session.getTransaction().commit();
			}
		}
		catch (HibernateException e) 
		{
			if (session.getTransaction() != null) session.getTransaction().rollback();
			throw new HibernateException (e);
		}
		finally 
		{
			try 
			{session.close();} 
			catch (HibernateException e)
			{//throw new OfferExceptionHandler("Warnning!! connection did'nt close properly");
				 
			}
		}

	} 
	
	
	
	
	
	private static void initMatchFactory ()
	{
		try{
		 if (matchFactory==null)
		  {matchFactory = new Configuration().configure("hibernateMatch.cfg.xml").buildSessionFactory();}
		}
		catch (Exception e){}
	}
	
	private static Session getSession() throws HibernateException {         
		   Session sess = null;       
		   try {         
		       sess = matchFactory.getCurrentSession();  
		   } catch (org.hibernate.HibernateException he) {  
		       sess = matchFactory.openSession();     
		   }             
		   return sess;
	}

	

}

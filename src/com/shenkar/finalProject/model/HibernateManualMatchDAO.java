package com.shenkar.finalProject.model;

import java.io.IOException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.google.gson.Gson;
import com.shenakr.finalProject.Globals.ConstantVariables;
import com.shenkar.finalProject.model.interfaces.IManualMatch;

public class HibernateManualMatchDAO implements IManualMatch {

	private static HibernateManualMatchDAO instance;
	private static SessionFactory matchFactory;

	public HibernateManualMatchDAO() {}
	
	public static HibernateManualMatchDAO getInstance() 
	{	
		if (instance == null) 
		{
			instance = new HibernateManualMatchDAO();
			initMatchFactory();
		}
		return instance;
	}
	
	@Override
	public void createMatch(Match match, String matchInitiator, String tableName) throws ApplicationExceptionHandler, OfferExceptionHandler, UserExceptionHandler, IOException 
	{
		Session session = null;
		
		try
		{
			initMatchFactory();
			session = getSession();
			if (session != null && match!=null)
			{
				if (match.getClass().equals(ManualMatchOffer.class))
					{
						//if (matchInitiator.equals("offer"))
						
							match.setTTL(7);
							try 
							{
								ManualMatchOffer offer = (ManualMatchOffer)match;
								offer.setOfferAproved(true);
								offer.setApplicationAproved(false);
								offer.setStatus(ConstantVariables.waitingForAppApproval);
								
								Application application = HibernateApplicationDAO.getInstance().getApplication(offer.getApplicationID(), tableName);
								HibernateApplicationDAO.getInstance().status(ConstantVariables.waitingForAppApproval, application);
								offer.setUserToInform(application.getUserId());
								
								//String strApplication = new Gson().toJson(application).toString();
								//WebSocket.sendMessage(strApplication);
								String subject = "!מחוברים לחיים: מישהו/י מוכנ/ה לעזור לך";
								
								String body1 = "!נמצאה התאמה לאחת מבקשות העזרה שלך";
								String body2 = "נא כנס לאלפליקציה בכדי להשלים את תהליך העזרה";
								String body = body1 + System.lineSeparator() + body2;
								
								HibernateApplicationDAO.getInstance().notification(application, subject, body);
								
								session.beginTransaction();
								session.save(offer); 
								session.getTransaction().commit();
							
							} catch (Exception e) 
							{e.printStackTrace();}
					}
						
				else if (match.getClass().equals(ManualMatchApplication.class))
						{
							ManualMatchApplication application = (ManualMatchApplication)match;
							application.setApplicationAproved(true);
							application.setOfferAproved(false);
							application.setStatus(ConstantVariables.waitingForOfferApproval);
							
							Offer offer = HibernateOfferDAO.getInstance().getOffer(application.getOfferId(), tableName);
							HibernateOfferDAO.getInstance().status(ConstantVariables.waitingForOfferApproval, offer);
							application.setUserToInform(offer.getUserId());

							String subject = "!מחוברים לחיים: מישהו מעוניין בעזרה שלך";
							
							String body1 = "מישהו מעוניין בסיוע לאחת מהצעות שהעלאת למחוברים לחיים";
							String body2 = "נא כנס לאלפליקציה בכדי להשלים את תהליך העזרה";
							
							String body = body1 + System.lineSeparator() + body2;
							
							HibernateOfferDAO.getInstance().notification(offer, subject, body);
							
							session.beginTransaction();
							session.save(application); 
							session.getTransaction().commit();
						}
					
					}
				else if (match.getClass().equals(AutoMatch.class))
					{match.setTTL(5);}
								
			
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Match> getAllUserToInform(int userToInform) 
	{
		Session session = null;
		List <Match> matches = null;

		try
		{
			 initMatchFactory();
			 session = getSession();
	    	 session.beginTransaction();
			
	    	 if (userToInform>0)
	    		 matches = session.createQuery("from "+ Match.class.getName() + " manual where manual.userToInform = "+userToInform+"").getResultList();
	    	 else if(userToInform==-1)
	    		 matches = session.createQuery ("from " + Match.class.getName()).getResultList();
	         
	    	 
	         if (matches != null && !matches.isEmpty())
	          {
	        	  session.getTransaction().commit();
	    	      return matches;
	          }
	      }
		catch (HibernateException e) {
			if (session.getTransaction() !=null) session.getTransaction().rollback();
	    }
		finally 
		{
	    	 try {session.close();} 
	    	 catch (HibernateException e){} 
	    }
	    return null;
		
	}

	
}
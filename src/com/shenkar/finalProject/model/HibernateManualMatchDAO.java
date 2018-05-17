package com.shenkar.finalProject.model;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.google.gson.Gson;
import com.shenkar.finalProject.Globals.ConstantVariables;
import com.shenkar.finalProject.model.interfaces.IManualMatch;

public class HibernateManualMatchDAO implements IManualMatch {
//public class HibernateManualMatchDAO{

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
	public void declineMatch(String user, int userRequestId, String tableName) 
	{	
		Session session = null;
		try
		{
				if (user.equals("offer"))
				{
					int offerId = userRequestId;
					ManualMatchUserApplication manualApp = (ManualMatchUserApplication) HibernateManualMatchDAO.getInstance().getManualMatch(offerId, "User Application");
					
					if (manualApp!=null)
					{
						initMatchFactory();
						session = getSession();
						session.beginTransaction();
						
						//notify the both user
						HibernateApplicationDAO.getInstance().notification(manualApp.getUserId(), ConstantVariables.subjectMailDecline, ConstantVariables.bodyMailOfferDecline);
						//HibernateOfferDAO.getInstance().notification(manualApp.getUserToInform(), ConstantVariables.subjectMailBothApproved, ConstantVariables.bodyMailBothSideApproved);
						
						manualApp.setOfferAproved(false);
						manualApp.setApplicationAproved(false);
						manualApp.setStatus(ConstantVariables.offerDeclined);
						manualApp.setUserToInform(0);
						
						//save the match in the archive
						
						if (!session.getTransaction().isActive())
							session.beginTransaction();
						session.update(manualApp);
						//sending the offer to the archive
						//delete the offer from the table 
						//session.delete(manualApp);
						session.getTransaction().commit();
						
						
						Offer offer = HibernateOfferDAO.getInstance().getOffer(offerId, tableName);
						HibernateOfferDAO.getInstance().status(ConstantVariables.waitingForMatch, offer);
					}
				}
				
				else if (user.equals("application"))
				{
					int applicationId = userRequestId;
					ManualMatchUserOffer manualOffer = (ManualMatchUserOffer) getManualMatch(applicationId, "User Offer");
					
					if (manualOffer != null)
					{
						initMatchFactory();
						session = getSession();
						session.beginTransaction();

						if (session!=null){
							
							//notify the both user
							//HibernateApplicationDAO.getInstance().notification(manualOffer.getUserToInform(), ConstantVariables.subjectMailBothApproved, ConstantVariables.bodyMailBothSideApproved);
							HibernateOfferDAO.getInstance().notification(manualOffer.getUserId(), ConstantVariables.subjectMailDecline, ConstantVariables.bodyMailBothSideApproved);
							
							manualOffer.setApplicationAproved(false);
							manualOffer.setOfferAproved(false);
							manualOffer.setStatus(ConstantVariables.applicationDeclined);
							manualOffer.setUserToInform(0);
							
							if (!session.getTransaction().isActive())
								session.beginTransaction();
								
							session.update(manualOffer); 
							//session.delete(manualOffer);
							session.getTransaction().commit();
							
							Application application = HibernateApplicationDAO.getInstance().getApplication(applicationId, tableName);
							HibernateApplicationDAO.getInstance().status(ConstantVariables.waitingForMatch, application);
						}
				}
			}
			
			
		}
		catch(Exception e){
			throw new HibernateException (e);
		}
		
		finally 
		{
			try 
			{session.close();} 
			catch (HibernateException e){}
		}
		
		
	}
	
	
	@Override
	public void acceptMatch(String user, int userRequestId, String tableName) 
	{
		Session session = null;
		try
		{
				if (user.equals("offer"))
				{
					int offerId = userRequestId;
					ManualMatchUserApplication manualApp = (ManualMatchUserApplication) HibernateManualMatchDAO.getInstance().getManualMatch(offerId, "User Application");
					
					if (manualApp!=null)
					{
					
						initMatchFactory();
						session = getSession();
						session.beginTransaction();

						
						//notify the both user
						HibernateApplicationDAO.getInstance().notification(manualApp.getUserId(), ConstantVariables.subjectMailBothApproved, ConstantVariables.bodyMailBothSideApproved);
						HibernateOfferDAO.getInstance().notification(manualApp.getUserToInform(), ConstantVariables.subjectMailBothApproved, ConstantVariables.bodyMailBothSideApproved);
						
						
						manualApp.setOfferAproved(true);
						manualApp.setStatus(ConstantVariables.bothSideApproved);
						manualApp.setUserToInform(0);
						
						if (!session.getTransaction().isActive())
							session.beginTransaction();
						session.update(manualApp); 
						session.getTransaction().commit();
						
						//delete the offer from the table and sending it to the archive
						Offer offer = HibernateOfferDAO.getInstance().getOffer(offerId, tableName);
						HibernateOfferDAO.getInstance().status(ConstantVariables.bothSideApproved, offer);
					}
				}
				
				else if (user.equals("application"))
				{
					int applicationId = userRequestId;
					ManualMatchUserOffer manualOffer = (ManualMatchUserOffer) getManualMatch(applicationId, "User Offer");
					
					if (manualOffer != null)
					{
						initMatchFactory();
						session = getSession();
						session.beginTransaction();

						if (session!=null){
							
							//notify the both user
							HibernateApplicationDAO.getInstance().notification(manualOffer.getUserToInform(), ConstantVariables.subjectMailBothApproved, ConstantVariables.bodyMailBothSideApproved);
							HibernateOfferDAO.getInstance().notification(manualOffer.getUserId(), ConstantVariables.subjectMailBothApproved, ConstantVariables.bodyMailBothSideApproved);
							
							manualOffer.setApplicationAproved(true);
							manualOffer.setStatus(ConstantVariables.bothSideApproved);
							manualOffer.setUserToInform(0);
							
							if (!session.getTransaction().isActive())
								session.beginTransaction();
								
							session.update(manualOffer); 
							session.getTransaction().commit();
							
							Application application = HibernateApplicationDAO.getInstance().getApplication(applicationId, tableName);
							HibernateApplicationDAO.getInstance().status(ConstantVariables.bothSideApproved, application);
						}
				}
			}
			
			
		}
		catch(Exception e){
			throw new HibernateException (e);
		}
		
		finally 
		{
			try 
			{session.close();} 
			catch (HibernateException e){}
		}
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
				if (match.getClass().equals(ManualMatchUserOffer.class))
					{
						//if (matchInitiator.equals("offer"))
						
							match.setTTL(7);
							try 
							{
								ManualMatchUserOffer offer = (ManualMatchUserOffer)match;
								offer.setOfferAproved(true);
								offer.setApplicationAproved(false);
								offer.setStatus(ConstantVariables.waitingForAppApproval);
								
								Application application = HibernateApplicationDAO.getInstance().getApplication(offer.getApplicationID(), tableName);
								
								HibernateApplicationDAO.getInstance().status(ConstantVariables.waitingForAppApproval, application);
								offer.setUserToInform(application.getUserId());
								
								//String strApplication = new Gson().toJson(application).toString();
								//WebSocket.sendMessage(strApplication);
								
								HibernateApplicationDAO.getInstance().notification(application.getUserId(), ConstantVariables.subjectMailApplication, ConstantVariables.bodyMailApplication);
								
								session.beginTransaction();
								session.save(offer); 
								session.getTransaction().commit();
							
							} catch (Exception e) 
							{e.printStackTrace();}
					}
						
				else if (match.getClass().equals(ManualMatchUserApplication.class))
						{
							ManualMatchUserApplication application = (ManualMatchUserApplication)match;
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
							
							HibernateOfferDAO.getInstance().notification(offer.getUserId(), subject, body);
							
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

	@SuppressWarnings("unchecked")
	public Match getManualMatch(int Id, String tableName) {
		
		 Session session = null;
		 List<Match> manualMatch = null;
		 
		 initMatchFactory();
		 session = getSession();
		 
		 if (!session.getTransaction().isActive())
			 session.beginTransaction();

		 try{
	    	  if (tableName.equals("User Application"))
	    	  {
		          manualMatch = session.createQuery("from " + getTableName(tableName) + " where matchId = " + Id +"").getResultList();
	    	  }
	    	  else if (tableName.equals("User Offer"))
	    	  {
	    		  manualMatch = session.createQuery("from " + getTableName(tableName) + " where applicationID = " + Id +"").getResultList();
	    	  }
	    	  
	    	  if (manualMatch != null && !manualMatch.isEmpty())
	          {
	        	  session.getTransaction().commit();
	        	  if (manualMatch.size() > 0) {
	    		      return manualMatch.get(0);	
	    	      }
	          }

	      }catch (HibernateException e) {
	         if (session.getTransaction() != null) session.getTransaction().rollback();
	         	
	      }
	     finally 
	     {
	    	 try {session.close();} 
	    	 catch(HibernateException e) {}
	     }
	     return null;
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
	    		 matches = session.createQuery ("from " + Match.class.getName() + " as table where table.status like :key").setParameter("key",  "%" + ConstantVariables.waitingForMatch + "%").getResultList();
	         
	    	 
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

	private String getTableName(String tableName) 
	{
		if (tableName.equals("User Application"))
			return ManualMatchUserApplication.class.getName();
		else if (tableName.equals("User Offer"))
			return ManualMatchUserOffer.class.getName();
		
		
		return "";
	}

	
	
}
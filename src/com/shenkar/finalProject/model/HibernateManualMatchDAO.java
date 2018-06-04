package com.shenkar.finalProject.model;

import java.io.IOException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.shenkar.finalProject.Globals.ConstantVariables;
import com.shenkar.finalProject.Globals.GlobalsFunctions;
import com.shenkar.finalProject.model.interfaces.IManualMatch;

@SuppressWarnings("unchecked")
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
						//change the match to the archive
						manualApp.setArchive(true);
						
						if (!session.getTransaction().isActive())
							session.beginTransaction();
						session.update(manualApp);
						
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
							HibernateOfferDAO.getInstance().notification(manualOffer.getUserId(), ConstantVariables.subjectMailDecline, ConstantVariables.bodyMailApplicationDecline);
							
							manualOffer.setApplicationAproved(false);
							manualOffer.setOfferAproved(false);
							manualOffer.setStatus(ConstantVariables.applicationDeclined);
							manualOffer.setUserToInform(0);
							manualOffer.setArchive(true);
							
							if (!session.getTransaction().isActive())
								session.beginTransaction();
								
							session.update(manualOffer); 
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
			{
				if (session!=null)
					session.close();
			} 
			catch (HibernateException e){
				e.printStackTrace();
			}
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
			{
				if (session!=null)
					session.close();
			} 
			catch (HibernateException e){}
		}
	}
	
	@Override
	public void createMatch(Match match, String tableName) throws ApplicationExceptionHandler, OfferExceptionHandler, UserExceptionHandler, IOException 
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
						try 
						{
							ManualMatchUserOffer offer = (ManualMatchUserOffer)match;
							offer.setOfferAproved(true);
							offer.setApplicationAproved(false);
							offer.setStatus(ConstantVariables.waitingForAppApproval);
							
							Application application = HibernateApplicationDAO.getInstance().getApplication(offer.getApplicationID(), tableName);
							
							HibernateApplicationDAO.getInstance().status(ConstantVariables.waitingForYourApproval, application);
							
							if (application.getCategory().equals("ride"))
							{
								RideApplication ride = (RideApplication) application;
								offer.setTTL(GlobalsFunctions.calculateTTL(ride.getEndPeriod()));
							}
							
							offer.setTTL(GlobalsFunctions.calculateTTL(application.getPeriod()));
							offer.setUserToInform(application.getUserId());
							
							HibernateApplicationDAO.getInstance().notification(application.getUserId(), ConstantVariables.subjectMailInterestedInApplication, ConstantVariables.bodyMailApplication);
							
							session.beginTransaction();
							session.save(offer); 
							session.getTransaction().commit();
						
						} 
						catch (Exception e) 
						{e.printStackTrace();}
				}
						
				else if (match.getClass().equals(ManualMatchUserApplication.class))
				{
					ManualMatchUserApplication application = (ManualMatchUserApplication)match;
					application.setApplicationAproved(true);
					application.setOfferAproved(false);
					application.setStatus(ConstantVariables.waitingForOfferApproval);
					
					Offer offer = HibernateOfferDAO.getInstance().getOffer(application.getOfferId(), tableName);
					HibernateOfferDAO.getInstance().status(ConstantVariables.waitingForYourApproval, offer);
					
					if (offer.getCategory().equals("ride"))
					{	
						RideOffer ride = (RideOffer) offer;
						application.setTTL(GlobalsFunctions.calculateTTL(ride.getEndPeriod()));
					}
					application.setTTL(GlobalsFunctions.calculateTTL(offer.getPeriod()));
					application.setUserToInform(offer.getUserId());
					
					HibernateOfferDAO.getInstance().notification(offer.getUserId(), ConstantVariables.subjectMailInterestedInOffer, ConstantVariables.bodyMailInterestedInOffer);
					
					session.beginTransaction();
					session.save(application); 
					session.getTransaction().commit();
				}
				
				else if (match.getClass().equals(AutoMatch.class))
				{match.setTTL(5);}
			}
				
		}
		catch (HibernateException e) 
		{
			if (session.getTransaction() != null) 
				session.getTransaction().rollback();
			throw new HibernateException (e);
		}
		finally 
		{
			try 
			{
				if (session!=null)
					session.close();
			} 
			catch (HibernateException e)
			{//throw new OfferExceptionHandler("Warnning!! connection did'nt close properly");
				 
			}
		}
	}
	
	public Match getManualMatch(int Id, String tableName) 
	{
		 Session session = null;
		 List<Match> manualMatch = null;
		 
		 initMatchFactory();
		 session = getSession();
		 
		 if (!session.getTransaction().isActive())
			 session.beginTransaction();

		 try
		 {
	    	  if (tableName.equals("User Application"))
	    	  {
		          manualMatch = session.createQuery("from " + getTableName(tableName) + " where offerId = " + Id + " and isArchive = false").getResultList();
	    	  }
	    	  else if (tableName.equals("User Offer"))
	    	  {
	    		  manualMatch = session.createQuery("from " + getTableName(tableName) + " where applicationID = " + Id + " and isArchive = false").getResultList();
	    	  }
	    	  
	    	  if (manualMatch != null && !manualMatch.isEmpty())
	          {
	        	  session.getTransaction().commit();
	        	  if (manualMatch.size() > 0) {
	    		      return manualMatch.get(0);	
	    	      }
	          }
	     }
		 catch (HibernateException e) 
		 {
	         if (session.getTransaction() != null) 
	        	 session.getTransaction().rollback();
	         	
	      }
	     finally 
	     {
	    	 try 
	    	 {
	    		 if (session!=null)
	    			 session.close();
	    	 } 
	    	 catch(Exception e) 
	    	 {
	    		 e.printStackTrace();
	    	 }
	     }
	     return null;
	}
	
	public List<Match> getAllUserManualMatches(int userId, String tableName) 
	{
		 Session session = null;
		 List<Match> manualMatch = null;
		 
		 initMatchFactory();
		 session = getSession();
		 
		 if (!session.getTransaction().isActive())
			 session.beginTransaction();

		 try
		 {
	    	  if (tableName.equals("User Application"))
	    	  {
		          manualMatch = session.createQuery("from " + getTableName(tableName) + " where userId = " + userId +"").getResultList();
	    	  }
	    	  else if (tableName.equals("User Offer"))
	    	  {
	    		  manualMatch = session.createQuery("from " + getTableName(tableName) + " where userId = " + userId +"").getResultList();
	    	  }
	    	  
	    	  if (manualMatch != null && !manualMatch.isEmpty())
	          {
	        	  session.getTransaction().commit();
	        	  if (manualMatch.size() > 0) {
	    		      return manualMatch;	
	    	      }
	          }

	     }
		 catch (HibernateException e) 
		 {
	         if (session.getTransaction() != null) session.getTransaction().rollback();
	     }
	     finally 
	     {
	    	 try 
	    	 {
	    		 if (session!=null)
					session.close();
	    	 } 
	    	 catch(Exception e) {
	    		 e.printStackTrace();
	    	 }
	     }
	     return null;
	}
	 
	private static void initMatchFactory ()
	{
		try
		{
			if (matchFactory==null)
			{
				matchFactory = GlobalsFunctions.initSessionFactory(matchFactory,"hibernateMatch.cfg.xml");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private static Session getSession() throws HibernateException 
	{         
	   Session sess = null;       
	   try {         
	       sess = matchFactory.getCurrentSession();  
	   } catch (HibernateException he) {  
	       sess = matchFactory.openSession();     
	   }             
	   return sess;
	}

	//return the user id that need to be informed about the match
	@Override
	public int getUserByOfferId (int offerId)
	{
		Session session = null;
		List<ManualMatchUserApplication> match = null;
		
		try
		{
			 initMatchFactory();
			 session = getSession();
	    	 session.beginTransaction();
	    	 
	    	 match = session.createQuery("from "+ ManualMatchUserApplication.class.getName() + " where offerId = " + offerId+"").getResultList();
	    	 if (match != null && !match.isEmpty())
	         {
	        	session.getTransaction().commit();
	    	    return match.get(0).getUserToInform();
	         }
	    }
		catch (Exception e) 
		{
			if (session.getTransaction() !=null) 
				session.getTransaction().rollback();
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
		return 0;
		
	}
	
	@Override
	public int getUserByApplicationId (int applicationId)
	{
		Session session = null;
		List<ManualMatchUserOffer> match = null;
		
		try
		{
			 initMatchFactory();
			 session = getSession();
	    	 session.beginTransaction();
	    	 
	    	 match = session.createQuery("from "+ ManualMatchUserOffer.class.getName() + " where applicationID = " + applicationId+"").getResultList();
	    	 if (match != null && !match.isEmpty())
	         {
	        	session.getTransaction().commit();
	    	    return match.get(0).getUserToInform();
	         }
	      }
		catch (Exception e) 
		{
			if (session.getTransaction() !=null) 
				session.getTransaction().rollback();
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
		return 0;
	}
		
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
	    	 try 
	    	 {
	    		 if (session!=null)
						session.close();
	    	 } 
	    	 catch (HibernateException e){
	    		 e.printStackTrace();
	    	 } 
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
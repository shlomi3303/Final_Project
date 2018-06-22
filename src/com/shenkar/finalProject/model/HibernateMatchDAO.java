package com.shenkar.finalProject.model;

import java.io.IOException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.shenkar.finalProject.Globals.ConstantVariables;
import com.shenkar.finalProject.Globals.GlobalsFunctions;
import com.shenkar.finalProject.backgroundModules.AutoMatchModules;
import com.shenkar.finalProject.classes.Application;
import com.shenkar.finalProject.classes.AutoMatch;
import com.shenkar.finalProject.classes.ManualMatchUserApplication;
import com.shenkar.finalProject.classes.ManualMatchUserOffer;
import com.shenkar.finalProject.classes.Match;
import com.shenkar.finalProject.classes.Offer;
import com.shenkar.finalProject.classes.RideApplication;
import com.shenkar.finalProject.classes.RideOffer;
import com.shenkar.finalProject.model.interfaces.IMatch;

@SuppressWarnings("unchecked")
public class HibernateMatchDAO implements IMatch {
//public class HibernateManualMatchDAO{

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
	public void declineMatch(String user, int userRequestId, String tableName) 
	{	
		Session session = null;
		try
		{
			if (user.equals("offer"))
			{
				int offerId = userRequestId;
				ManualMatchUserApplication manualApp = (ManualMatchUserApplication) HibernateMatchDAO.getInstance().getManualMatch(offerId, "User Application", tableName);
				//manual match case
				if (manualApp!=null)
				{
					initMatchFactory();
					session = GlobalsFunctions.getSession(matchFactory);
					if (session!=null)
					{
						session.beginTransaction();
					
						//notify the application user
						HibernateApplicationDAO.getInstance().notification(manualApp.getUserId(), ConstantVariables.subjectMailDecline, ConstantVariables.bodyMailOfferDecline);
						//HibernateOfferDAO.getInstance().notification(manualApp.getUserToInform(), ConstantVariables.subjectMailBothApproved, ConstantVariables.bodyMailBothSideApproved);
						
						manualApp.setOfferAproved(false);
						manualApp.setApplicationAproved(false);
						manualApp.setStatus(ConstantVariables.offerDeclined);
						//change the match to the archive
						manualApp.setArchive(true);
						
						Offer offer = HibernateOfferDAO.getInstance().getOffer(offerId, tableName);
						HibernateOfferDAO.getInstance().status(ConstantVariables.waitingForMatch, offer);
												
						if (!session.getTransaction().isActive())
							session.beginTransaction();
						session.update(manualApp);
						
						session.getTransaction().commit();
					}
				}
				//auto match case
				else
				{
					Match autoMatch = getAutoMatch(offerId, "User Offer", tableName);
					
					if (autoMatch!=null)
					{
						initMatchFactory();
						session = GlobalsFunctions.getSession(matchFactory);
						
						if (session!=null)
						{
							session.beginTransaction();

							autoMatch.setOfferAproved(false);
							autoMatch.setArchive(true);
							autoMatch.setStatus(ConstantVariables.offerDeclined);
							
							AutoMatch auto = (AutoMatch) autoMatch;
							
							Application app = HibernateApplicationDAO.getInstance().getApplication(auto.getApplicationId(), auto.getCategory());
							Offer offer = HibernateOfferDAO.getInstance().getOffer(auto.getOfferId(), auto.getCategory());
							
							//change the status for the application and offer
							HibernateApplicationDAO.getInstance().status(ConstantVariables.waitingForMatch, app);
							HibernateOfferDAO.getInstance().status(ConstantVariables.waitingForMatch, offer);
							
							//update the refuse list and the offer list in the application
							HibernateApplicationDAO.getInstance().updateRefuseList(auto.getOfferId(), app);
							HibernateApplicationDAO.getInstance().removeOfferFromOfferListPotential(app, auto.getOfferId());
							
							HibernateApplicationDAO.getInstance().notification(app.getUserId(), ConstantVariables.subjectMailDecline, ConstantVariables.bodyMailOfferDecline);
						
							if (!session.getTransaction().isActive())
								session.beginTransaction();
							session.update(autoMatch);
							
							session.getTransaction().commit();
							
							//try to find another match to the application user from the offer list potential
							findAnotherMatch(app, tableName);
						}
					}
				}
			}
			
			else if (user.equals("application"))
			{
				int applicationId = userRequestId;
				ManualMatchUserOffer manualOffer = (ManualMatchUserOffer) getManualMatch(applicationId, "User Offer", tableName);
				
				if (manualOffer != null)
				{
					initMatchFactory();
					session = GlobalsFunctions.getSession(matchFactory);
					session.beginTransaction();

					if (session!=null){
						
						//notify the both user
						//HibernateApplicationDAO.getInstance().notification(manualOffer.getUserToInform(), ConstantVariables.subjectMailBothApproved, ConstantVariables.bodyMailBothSideApproved);
						HibernateOfferDAO.getInstance().notification(manualOffer.getUserId(), ConstantVariables.subjectMailDecline, ConstantVariables.bodyMailApplicationDecline);
						
						manualOffer.setApplicationAproved(false);
						manualOffer.setOfferAproved(false);
						manualOffer.setStatus(ConstantVariables.applicationDeclined);
						//manualOffer.setUserToInform(0);
						manualOffer.setArchive(true);
						
						Application application = HibernateApplicationDAO.getInstance().getApplication(applicationId, tableName);
						HibernateApplicationDAO.getInstance().status(ConstantVariables.waitingForMatch, application);
						
						if (!session.getTransaction().isActive())
							session.beginTransaction();
							
						session.update(manualOffer); 
						session.getTransaction().commit();
					}
				}
				else
				{
					Match autoMatch = getAutoMatch(applicationId, "User Application", tableName);
					
					if (autoMatch!=null)
					{
						initMatchFactory();
						session = GlobalsFunctions.getSession(matchFactory);
						
						if (session!=null)
						{
							session.beginTransaction();
							
							autoMatch.setApplicationAproved(false);
							autoMatch.setArchive(true);
							autoMatch.setStatus(ConstantVariables.applicationDeclined);
							
							AutoMatch auto = (AutoMatch) autoMatch;
							
							Application app = HibernateApplicationDAO.getInstance().getApplication(auto.getApplicationId(), auto.getCategory());
							Offer offer = HibernateOfferDAO.getInstance().getOffer(auto.getOfferId(), auto.getCategory());
							
							if (app!=null)
							{
								HibernateApplicationDAO.getInstance().status(ConstantVariables.waitingForMatch, app);
								//update the refuse list and the offer list in the application
								HibernateApplicationDAO.getInstance().updateRefuseList(auto.getOfferId(), app);
								HibernateApplicationDAO.getInstance().removeOfferFromOfferListPotential(app, auto.getOfferId());
							}
							else
								System.out.println("application is null in auto match decline function");
							if (offer!=null)
							{
								HibernateOfferDAO.getInstance().status(ConstantVariables.waitingForMatch, offer);
								HibernateOfferDAO.getInstance().notification(offer.getUserId(), ConstantVariables.subjectMailDecline, ConstantVariables.bodyMailApplicationDecline);
							}
							else
								System.out.println("offer is null in auto match decline function");
							
							
							if (!session.getTransaction().isActive())
								session.beginTransaction();
							session.update(autoMatch);
							
							session.getTransaction().commit();
							
							//try to find another match to the application user from the offer list potential
							findAnotherMatch(app, tableName);
						}
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
				ManualMatchUserApplication manualApp = (ManualMatchUserApplication) getManualMatch(offerId, "User Application", tableName);
				
				if (manualApp!=null)
				{
				
					initMatchFactory();
					session = GlobalsFunctions.getSession(matchFactory);
					session.beginTransaction();

					
					//notify the both user
					HibernateApplicationDAO.getInstance().notification(manualApp.getUserId(), ConstantVariables.subjectMailBothApproved, ConstantVariables.bodyMailBothSideApproved);
					HibernateOfferDAO.getInstance().notification(manualApp.getUserToInform(), ConstantVariables.subjectMailBothApproved, ConstantVariables.bodyMailBothSideApproved);
					
					
					manualApp.setOfferAproved(true);
					manualApp.setStatus(ConstantVariables.bothSideApproved);
					//manualApp.setUserToInform(0);
					
					if (!session.getTransaction().isActive())
						session.beginTransaction();
					session.update(manualApp); 
					session.getTransaction().commit();
					
					Offer offer = HibernateOfferDAO.getInstance().getOffer(offerId, tableName);
					HibernateOfferDAO.getInstance().status(ConstantVariables.bothSideApproved, offer);
				}
				else 
				{
					Match autoMatch = getAutoMatch(offerId, "User Offer", tableName);
					
					if (autoMatch!=null)
					{
						initMatchFactory();
						session = GlobalsFunctions.getSession(matchFactory);
						
						autoMatch.setOfferAproved(true);
						
						AutoMatch auto = (AutoMatch) autoMatch;
						
						Application app = HibernateApplicationDAO.getInstance().getApplication(auto.getApplicationId(), auto.getCategory());
						Offer offer = HibernateOfferDAO.getInstance().getOffer(auto.getOfferId(), auto.getCategory());
						
						//check if there is complete auto match
						if (autoMatch.getApplicationAproved())
						{
							HibernateApplicationDAO.getInstance().notification(app.getUserId(), ConstantVariables.subjectMailBothApproved, ConstantVariables.bodyMailBothSideApproved);
							HibernateApplicationDAO.getInstance().status(ConstantVariables.bothSideApproved, app);
							
							HibernateOfferDAO.getInstance().notification(offer.getUserId(), ConstantVariables.subjectMailBothApproved, ConstantVariables.bodyMailBothSideApproved);
							HibernateOfferDAO.getInstance().status(ConstantVariables.bothSideApproved, offer);
							
							autoMatch.setStatus(ConstantVariables.bothSideApproved);
						}
						else
						{
							HibernateApplicationDAO.getInstance().notification(app.getUserId(), ConstantVariables.subjectReminderApplication, ConstantVariables.bodyMailReminderApplication);
							HibernateApplicationDAO.getInstance().status(ConstantVariables.waitingForAppApproval, app);
							
							HibernateOfferDAO.getInstance().status(ConstantVariables.waitingForAppApproval, offer);
							
							autoMatch.setStatus(ConstantVariables.waitingForAppApproval);
							
						}
						
						if (!session.getTransaction().isActive())
							session.beginTransaction();
						session.update(autoMatch);
						session.getTransaction().commit();
					}
				}
			}
			
			else if (user.equals("application"))
			{
				int applicationId = userRequestId;
				ManualMatchUserOffer manualOffer = (ManualMatchUserOffer) getManualMatch(applicationId, "User Offer", tableName);
				
				if (manualOffer != null)
				{
					initMatchFactory();
					session = GlobalsFunctions.getSession(matchFactory);
					session.beginTransaction();

					if (session!=null){
						
						//notify the both user
						HibernateApplicationDAO.getInstance().notification(manualOffer.getUserToInform(), ConstantVariables.subjectMailBothApproved, ConstantVariables.bodyMailBothSideApproved);
						HibernateOfferDAO.getInstance().notification(manualOffer.getUserId(), ConstantVariables.subjectMailBothApproved, ConstantVariables.bodyMailBothSideApproved);
						
						manualOffer.setApplicationAproved(true);
						manualOffer.setStatus(ConstantVariables.bothSideApproved);
						//manualOffer.setUserToInform(0);
						
						if (!session.getTransaction().isActive())
							session.beginTransaction();
							
						session.update(manualOffer); 
						session.getTransaction().commit();
						
						Application application = HibernateApplicationDAO.getInstance().getApplication(applicationId, tableName);
						HibernateApplicationDAO.getInstance().status(ConstantVariables.bothSideApproved, application);
					}
				}
				//auto match case:
				else
				{
					Match autoMatch = getAutoMatch(applicationId, "User Application", tableName);
					
					if (autoMatch!=null)
					{
					
						initMatchFactory();
						session = GlobalsFunctions.getSession(matchFactory);
						
						if (session!=null)
						{
							autoMatch.setApplicationAproved(true);
							
							AutoMatch auto = (AutoMatch) autoMatch;
							
							Application app = HibernateApplicationDAO.getInstance().getApplication(auto.getApplicationId(), auto.getCategory());
							Offer offer = HibernateOfferDAO.getInstance().getOffer(auto.getOfferId(), auto.getCategory());
							
							if (autoMatch.getOfferAproved())
							{
								HibernateApplicationDAO.getInstance().notification(app.getUserId(), ConstantVariables.subjectMailBothApproved, ConstantVariables.bodyMailBothSideApproved);
								HibernateApplicationDAO.getInstance().status(ConstantVariables.bothSideApproved, app);
								
								HibernateOfferDAO.getInstance().notification(offer.getUserId(), ConstantVariables.subjectMailBothApproved, ConstantVariables.bodyMailBothSideApproved);
								HibernateOfferDAO.getInstance().status(ConstantVariables.bothSideApproved, offer);
								
								autoMatch.setStatus(ConstantVariables.bothSideApproved);
							}
							else
							{
								HibernateOfferDAO.getInstance().notification(offer.getUserId(), ConstantVariables.subjectReminderApplication, ConstantVariables.bodyMailReminderApplication);
								HibernateOfferDAO.getInstance().status(ConstantVariables.waitingForOfferApproval, offer);
								
								HibernateApplicationDAO.getInstance().status(ConstantVariables.waitingForOfferApproval, app);
								
								autoMatch.setStatus(ConstantVariables.waitingForOfferApproval);
								
							}
							
							if (!session.getTransaction().isActive())
								session.beginTransaction();
							session.update(autoMatch);
							session.getTransaction().commit();
						}
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
	public void createManualMatch(Match match, String tableName) throws ApplicationExceptionHandler, OfferExceptionHandler, UserExceptionHandler, IOException 
	{
		Session session = null;
		
		try
		{
			initMatchFactory();
			session = GlobalsFunctions.getSession(matchFactory);
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
						
						HibernateUserInterestsDAO.getInstance().updateUserInterests(application, offer.getUserId());
						
						if (application.getCategory().equals("ride"))
						{
							RideApplication ride = (RideApplication) application;
							offer.setTTL(GlobalsFunctions.calculateTTL(ride.getEndPeriod()));
						}
						else
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
					
					HibernateUserInterestsDAO.getInstance().updateUserInterests(offer, application.getUserId());
					
					if (offer.getCategory().equals("ride"))
					{	
						RideOffer ride = (RideOffer) offer;
						application.setTTL(GlobalsFunctions.calculateTTL(ride.getEndPeriod()));
					}
					else
						application.setTTL(GlobalsFunctions.calculateTTL(offer.getPeriod()));
					application.setUserToInform(offer.getUserId());
					
					HibernateOfferDAO.getInstance().notification(offer.getUserId(), ConstantVariables.subjectMailInterestedInOffer, ConstantVariables.bodyMailInterestedInOffer);
					
					session.beginTransaction();
					session.save(application); 
					session.getTransaction().commit();
				}
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
	
	public Match getAutoMatch(int Id, String user, String tableName) 
	{
		 Session session = null;
		 List<Match> autoMatch = null;
		 
		 initMatchFactory();
		 session = GlobalsFunctions.getSession(matchFactory);
		 
		 if (!session.getTransaction().isActive())
			 session.beginTransaction();
		 try
		 {
	    	  if (user.equals("User Offer"))
	    	  {
		          autoMatch = session.createQuery("from " + AutoMatch.class.getName() + " as table where table.offerId = " + Id + " and table.isArchive = false and table.category like :key").setParameter("key",  "%" + tableName + "%").getResultList();
	    	  }
	    	  else if (user.equals("User Application"))
	    	  {
	    		  System.out.println("the applicationId is: ");
	    		  System.out.println("the category is: " + tableName);
	    		  autoMatch = session.createQuery("from " + AutoMatch.class.getName() + " as table where table.applicationId = " + Id + " and table.isArchive = false and table.category like :key").setParameter("key",  "%" + tableName + "%").getResultList();
	    		  System.out.println("the auto match by applicationId is: " + autoMatch);
	    	  }
	    	  
	    	  if (autoMatch != null && !autoMatch.isEmpty())
	          {
	        	  session.getTransaction().commit();
	        	  if (autoMatch.size() > 0) {
	        		  System.out.println("returning auto match: " + autoMatch.get(0).getMatchId());
	    		      return autoMatch.get(0);	
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
	
	public Match getManualMatch(int Id, String user, String tableName) 
	{
		 Session session = null;
		 List<Match> manualMatch = null;
		 
		 initMatchFactory();
		 session = GlobalsFunctions.getSession(matchFactory);
		 
		 if (!session.getTransaction().isActive())
			 session.beginTransaction();

		 try
		 {
	    	  if (user.equals("User Application"))
	    	  {
		          manualMatch = session.createQuery("from " + getTableName(user) + " where offerId = " + Id + " and isArchive = false and category = '" + tableName + "'").getResultList();
	    	  }
	    	  else if (user.equals("User Offer"))
	    	  {
	    		  manualMatch = session.createQuery("from " + getTableName(user) + " where applicationID = " + Id + " and isArchive = false and category = '" + tableName + "'").getResultList();
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
		 session = GlobalsFunctions.getSession(matchFactory);
		 
		 if (!session.getTransaction().isActive())
			 session.beginTransaction();

		 try
		 {
	    	  if (tableName.equals("User Application"))
	    	  {
		          manualMatch = session.createQuery("from " + getTableName(tableName) + " where userId = " + userId +" and isArchive = false").getResultList();
	    	  }
	    	  else if (tableName.equals("User Offer"))
	    	  {
	    		  manualMatch = session.createQuery("from " + getTableName(tableName) + " where userId = " + userId +" and isArchive = false").getResultList();
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
	 
	@Override
	public int getUserByOfferId (int offerId, String category)
	{
		Session session = null;
		List<ManualMatchUserApplication> match = null;
		
		try
		{
			 initMatchFactory();
			 session = GlobalsFunctions.getSession(matchFactory);
	    	 session.beginTransaction();

	    	 match = session.createQuery("from "+ ManualMatchUserApplication.class.getName() + " as table where table.offerId = " + offerId + " and table.isArchive = false and table.category like :key").setParameter("key",  "%" + category + "%").getResultList();
	    	 System.out.println("manual match in getUserByOfferId is: " + match);

	    	 if (match != null && !match.isEmpty())
	         {
	        	session.getTransaction().commit();
	    	    return match.get(0).getUserToInform();
	         }
	    	 else if (match.size()==0)
	    	 {
	    		 List<AutoMatch> autoMatch = session.createQuery("from "+ AutoMatch.class.getName() + " as table where table.offerId = " + offerId + " and table.isArchive = false and table.category like :key").setParameter("key",  "%" + category + "%").getResultList();
	    		 System.out.println("auto match in get all by offer id: " + autoMatch);
	    		 if (autoMatch!=null && autoMatch.size()>0)
	    		 {
	    			 System.out.println("autoMatch application Id: " + autoMatch.get(0).getApplicationId() );
	    			 Application app = HibernateApplicationDAO.getInstance().getApplication(autoMatch.get(0).getApplicationId(), category);
	    			 
	    			 if (app!=null)
	    			 {
	    				System.out.println("in auto match condition, the application user id is: " + app.getUserId());
	    				return app.getUserId();
	    			 }
	    			 else
	    			 {
	    				 System.out.println("the application is null, returing 0");
	    				 return 0;
	    			 }
	    		 }
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
	public int getUserByApplicationId (int applicationId, String category)
	{
		Session session = null;
		List<ManualMatchUserOffer> match = null;
		
		try
		{
			 initMatchFactory();
			 session = GlobalsFunctions.getSession(matchFactory);
	    	 session.beginTransaction();

	    	 match = session.createQuery("from "+ ManualMatchUserOffer.class.getName() + " as table where table.applicationID = " + applicationId + " and table.isArchive = false and table.category like :key").setParameter("key",  "%" + category + "%").getResultList();
	    	 System.out.println("manual match in getUserByApplicationId is: " + match);

	    	 if (match != null && match.size()>0)
	         {
	        	session.getTransaction().commit();
	    	    return match.get(0).getUserToInform();
	         }
	    	 else if (match !=null && match.size()==0)
	    	 {
	    		 List<AutoMatch> autoMatch = session.createQuery("from "+ AutoMatch.class.getName() + " as table where table.applicationId = " + applicationId + " and table.isArchive = false and table.category like :key").setParameter("key",  "%" + category + "%").getResultList();
		    	 System.out.println("auto match is: " + autoMatch);
	    		 if (autoMatch!=null && autoMatch.size()>0)
	    		 {
	    			 Offer offer = HibernateOfferDAO.getInstance().getOffer(autoMatch.get(0).getOfferId(), category);
	    			 if (offer!=null){
	    				 return offer.getUserId();
	    			 }
	    			 else
	    			 {
	    				 System.out.println("the offer is null, returning 0");
	    				 return 0;
	    			 }
	    		 }
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
			 session = GlobalsFunctions.getSession(matchFactory);
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
	
	private String getTableName(String user) 
	{
		if (user.equals("User Application"))
			return ManualMatchUserApplication.class.getName();
		else if (user.equals("User Offer"))
			return ManualMatchUserOffer.class.getName();
		
		return "";
	}

	@Override
	public void findAnotherMatch(Application app, String tableName)
			throws OfferExceptionHandler, ApplicationExceptionHandler, UserExceptionHandler, IOException 
	{
		for (int j=0; j<app.getList().size(); j++)
		{
			Offer tempOffer = HibernateOfferDAO.getInstance().getOffer(app.getList().get(j), tableName);
			if (tempOffer!=null && tempOffer.getStatus().equals(ConstantVariables.waitingForMatch))
			{
				AutoMatchModules.createAutoMatch(app.getCategory(), tempOffer.getOfferId(), app.getApplicationID());
				AutoMatchModules.updateApplicationAndOffer(app, tempOffer.getOfferId(), tableName, null);
				break;
			}
			else
				HibernateApplicationDAO.getInstance().removeOfferFromOfferListPotential(app, tempOffer.getOfferId());
		}
	}
}
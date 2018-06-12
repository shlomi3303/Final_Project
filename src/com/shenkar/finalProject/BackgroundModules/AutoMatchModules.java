package com.shenkar.finalProject.BackgroundModules;

import java.io.IOException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.shenkar.finalProject.Globals.ConstantVariables;
import com.shenkar.finalProject.Globals.GlobalsFunctions;
import com.shenkar.finalProject.classes.AutoMatch;
import com.shenkar.finalProject.model.Application;
import com.shenkar.finalProject.model.ApplicationExceptionHandler;
import com.shenkar.finalProject.model.HandymanApplication;
import com.shenkar.finalProject.model.HandymanOffer;
import com.shenkar.finalProject.model.HibernateApplicationDAO;
import com.shenkar.finalProject.model.HibernateOfferDAO;
import com.shenkar.finalProject.model.Offer;
import com.shenkar.finalProject.model.OfferExceptionHandler;
import com.shenkar.finalProject.model.OldersApplication;
import com.shenkar.finalProject.model.OldersOffer;
import com.shenkar.finalProject.model.Ranking;
import com.shenkar.finalProject.model.RideApplication;
import com.shenkar.finalProject.model.RideOffer;
import com.shenkar.finalProject.model.StudentApplication;
import com.shenkar.finalProject.model.StudentOffer;
import com.shenkar.finalProject.model.UserExceptionHandler;

public class AutoMatchModules {

	private static SessionFactory autoMatchFactory;
	
	public AutoMatchModules() {}
	
	
	@SuppressWarnings("unchecked")
	public void AutoMatchMainModules() throws ApplicationExceptionHandler
	{
		List <Application> oldersAppList = HibernateApplicationDAO.getInstance().getAllSpecificApplicationTable("olders");
		
		List <Application> handymanAppList = HibernateApplicationDAO.getInstance().getAllSpecificApplicationTable("handyman");
		
		List <Application> studentAppList = HibernateApplicationDAO.getInstance().getAllSpecificApplicationTable("student");
		
		List <Application> rideAppList = HibernateApplicationDAO.getInstance().getAllSpecificApplicationTable("ride");
		
		Session session = null;
		
		try 
		{
			initAutoMatchFactory();
			session = GlobalsFunctions.getSession(autoMatchFactory);
			if (session!=null)
			{
				
				//handling all the olders Application List
				int offerId = 0;
				if (oldersAppList!=null)
				{
					for (int i=0; i<oldersAppList.size(); i++)
					{
						List<Offer> offerList = HibernateOfferDAO.getInstance().getAllSpecificOfferTable("olders");
						
						if (offerList != null)
						{
							List<OldersOffer> oldersOfferList = (List <OldersOffer>) (List <?>) offerList; 

							Application oldersApp = oldersAppList.get(i);
	
							List <Integer> list = Ranking.oldersRanking((OldersApplication) oldersApp, oldersOfferList);
							
							//change to list.get(0);
							offerId = oldersAppList.get(i).getList().get(0);
							updateApplicationAndOffer(oldersApp, offerId, "olders", list);
							createAutoMatch("olders", offerId, oldersApp.getUserId());
							//HibernateApplicationDAO.getInstance().updateOfferListPotentialMatch(Ranking.oldersRanking((OldersApplication) oldersApp, oldersOfferList), oldersApp);
							//HibernateApplicationDAO.getInstance().status(ConstantVariables.waitingForbothSideApproval, oldersApp);
							//HibernateApplicationDAO.getInstance().notification(oldersAppList.get(i).getUserId(), ConstantVariables.subjectMailAutoMatchApplication, ConstantVariables.bodyMailApplication);
							//Offer offer = HibernateOfferDAO.getInstance().getOffer(oldersAppList.get(i).getList().get(0), "olders");
							//HibernateOfferDAO.getInstance().notification(offer.getUserId(), ConstantVariables.subjectMailAutoMatchOffer, ConstantVariables.bodyMailInterestedInOffer);
							//HibernateOfferDAO.getInstance().status(ConstantVariables.waitingForbothSideApproval, offer);
						}
					}
				}
				
				for (int i=0; i<handymanAppList.size(); i++)
				{
					List<Offer> offerList = HibernateOfferDAO.getInstance().getAllSpecificOfferTable("handyman");

					if (offerList!=null)
					{
						List<HandymanOffer> handymanOfferList =  (List <HandymanOffer>) (List <?>) offerList;
						
						HandymanApplication handymanApp = (HandymanApplication) handymanAppList.get(i);
						
						List <Integer> list = Ranking.handymanRanking(handymanApp, handymanOfferList);
						
						offerId = list.get(0);
						
						updateApplicationAndOffer(handymanApp, offerId, "handyman", list);
						
						createAutoMatch("handyman", offerId, handymanApp.getUserId());
					}
					
				}
				
				for (int i=0; i<studentAppList.size(); i++)
				{
					List<Offer> offerList = HibernateOfferDAO.getInstance().getAllSpecificOfferTable("student");

					if (offerList!=null)
					{
						List<StudentOffer> studentOfferList =  (List <StudentOffer>) (List <?>) offerList;
						
						StudentApplication studentApp = (StudentApplication) studentAppList.get(i);
						
						List <Integer> list = Ranking.studentRanking(studentApp, studentOfferList);
						
						offerId = list.get(0);
						
						updateApplicationAndOffer(studentApp, offerId, "student", list);
						
						createAutoMatch("student", offerId, studentApp.getUserId());
					}
					
				}
				
				for (int i=0; i<rideAppList.size(); i++)
				{
					List<Offer> offerList = HibernateOfferDAO.getInstance().getAllSpecificOfferTable("ride");

					if (offerList!=null)
					{
						List<RideOffer> rideOfferList =  (List <RideOffer>) (List <?>) offerList;
						
						RideApplication rideApp = (RideApplication) rideAppList.get(i);
						
						List <Integer> list = Ranking.rideRanking(rideApp, rideOfferList);
						
						offerId = list.get(0);
						
						updateApplicationAndOffer(rideApp, offerId, "ride", list);
						
						createAutoMatch("ride", offerId, rideApp.getUserId());
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
			if (session!=null)
				session.close();
		}
		//handle in all the oldersAppList
		
	}
	
	private void updateApplicationAndOffer(Application tempApp, int offerId, String category, List<Integer> offerList) throws OfferExceptionHandler, ApplicationExceptionHandler, UserExceptionHandler, IOException
	{
		HibernateApplicationDAO.getInstance().updateOfferListPotentialMatch(offerList, tempApp);
		HibernateApplicationDAO.getInstance().status(ConstantVariables.waitingForbothSideApproval, tempApp);
		HibernateApplicationDAO.getInstance().notification(tempApp.getUserId(), ConstantVariables.subjectMailAutoMatchApplication, ConstantVariables.bodyMailApplication);
	
		Offer offer = HibernateOfferDAO.getInstance().getOffer(offerId, "olders");
	
		HibernateOfferDAO.getInstance().notification(offer.getUserId(), ConstantVariables.subjectMailAutoMatchOffer, ConstantVariables.bodyMailInterestedInOffer);
		HibernateOfferDAO.getInstance().status(ConstantVariables.waitingForbothSideApproval, offer);
	}
	
	private void createAutoMatch(String category, int offerId,int appId)
	{
		Session session = null;
		
		try
		{
			initAutoMatchFactory();
			session = GlobalsFunctions.getSession(autoMatchFactory);
			
			if (session!=null)
			{
				AutoMatch match = new AutoMatch (false, false, ConstantVariables.waitingForbothSideApproval, 0, 0,category, offerId, appId);
				session.beginTransaction();
				session.save(match);
				session.getTransaction().commit();
			}
			else
				System.out.println("Auto match: session in null");
			
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
			catch (HibernateException e)
			{
				e.printStackTrace();
			}
		}
	
	}
	
	private static void initAutoMatchFactory ()
	{
		try
		{
		 if (autoMatchFactory==null)
		  {
			 autoMatchFactory =  GlobalsFunctions.initSessionFactory(autoMatchFactory, "hibernateMatch.cfg.xml");
		  }
		}
		catch (Exception e)
		{
			System.out.println("Error in init Auto Match Modules Factory " + e.getMessage());
		}
	}
}

package com.shenkar.finalProject.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.shenkar.finalProject.Globals.GlobalsFunctions;
import com.shenkar.finalProject.classes.CategoryOffersSuggestions;
import com.shenkar.finalProject.classes.UserSubcategoryApplicationsInterests;
import com.shenkar.finalProject.classes.UserSubcategoryOffersInterests;

@SuppressWarnings("unchecked")
public class HibernateUserInterestsDAO {

	private static HibernateUserInterestsDAO instance;
	private static SessionFactory interestsFactory;
	private HibernateUserInterestsDAO() {}
	
	public static HibernateUserInterestsDAO getInstance() 
	{	
		if (instance == null) {
			instance = new HibernateUserInterestsDAO();
			initInterestsFactory();
			
		}
		return instance;
	}
	
	public void createUserSubCategoryInterests(int userId, Object obj) 
	{
		UserSubcategoryApplicationsInterests appInterests = null;
		UserSubcategoryOffersInterests offerInterests = null;
		Session session = null;

		try
		{
			initInterestsFactory();
			session=GlobalsFunctions.getSession(interestsFactory);
			
			if (session!=null)
			{	
				if (obj instanceof Application)
				{
					Application app = (Application) obj;
						
					if (app.getCategory().equals("ride"))
					{
						appInterests = new UserSubcategoryApplicationsInterests (userId, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0); 
					}
					else if (app.getCategory().equals("student"))
					{
						StudentApplication stuApp = (StudentApplication)app;
						int homeWorks=0, practice = 0, testStudy=0;
						if (stuApp.getHomeWorks())
							homeWorks=1;
						if (stuApp.getPractice())
							practice=1;
						if (stuApp.getTestStudy())
							testStudy=1;
						appInterests = new UserSubcategoryApplicationsInterests (userId, homeWorks, testStudy, practice, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0); 
					}
					else if (app.getCategory().equals("handyman"))
					{
						
						HandymanApplication handyApp = (HandymanApplication) app;
						
						int colorCorrections=0, furniture=0, generalHangingWorks=0, hangingOfLightFixtures=0, treatmentSocketsAndPowerPoints=0;
						
						if (handyApp.getColorCorrections())
							colorCorrections=1;
						if (handyApp.getFurniture())
							furniture=1;
						if (handyApp.getGeneralHangingWorks())
							generalHangingWorks=1;
						if (handyApp.getHangingOfLightFixtures())
							hangingOfLightFixtures=1;
						if (handyApp.getTreatmentSocketsAndPowerPoints())
							treatmentSocketsAndPowerPoints=1;
						appInterests = new UserSubcategoryApplicationsInterests (userId, 0, 0, 0, 0, 0, 0, 0, 0, colorCorrections, furniture,
								generalHangingWorks, hangingOfLightFixtures, treatmentSocketsAndPowerPoints); 
			
					}
					
					else if (app.getCategory().equals("olders"))
					{
						OldersApplication oldersApp = (OldersApplication) app;
						
						int shopping=0, cooking=0, escortedAged=0, conversation=0;
						
						if (oldersApp.getConversation())
							conversation=1;
						if (oldersApp.getCooking())
							cooking=1;
						if (oldersApp.getEscortedAged())
							escortedAged=1;
						if (oldersApp.getShopping())
							shopping=1;
						
						appInterests = new UserSubcategoryApplicationsInterests (userId, 0, 0, 0, 0,shopping,cooking, escortedAged, conversation, 0, 0, 0, 0, 0); 
					}
					session.beginTransaction();
					session.save(appInterests);
					session.getTransaction().commit();
				}
				
				else if (obj instanceof Offer)
				{
					Offer offer = (Offer) obj;
					
					if (offer.getCategory().equals("ride"))
					{
						offerInterests = new UserSubcategoryOffersInterests (userId, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0); 
					}
					else if (offer.getCategory().equals("student"))
					{
						StudentOffer stuOffer = (StudentOffer)offer;
						int homeWorks=0, practice = 0, testStudy=0;
						if (stuOffer.getHomeWorks())
							homeWorks=1;
						if (stuOffer.getPractice())
							practice=1;
						if (stuOffer.getTestStudy())
							testStudy=1;
						offerInterests = new UserSubcategoryOffersInterests (userId, homeWorks, testStudy, practice, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0); 
					}
					else if (offer.getCategory().equals("handyman"))
					{
						
						HandymanOffer handyOffer= (HandymanOffer) offer;
						
						int colorCorrections=0, furniture=0, generalHangingWorks=0, hangingOfLightFixtures=0, treatmentSocketsAndPowerPoints=0;
						
						if (handyOffer.getColorCorrections())
							colorCorrections=1;
						if (handyOffer.getFurniture())
							furniture=1;
						if (handyOffer.getGeneralHangingWorks())
							generalHangingWorks=1;
						if (handyOffer.getHangingOfLightFixtures())
							hangingOfLightFixtures=1;
						if (handyOffer.getTreatmentSocketsAndPowerPoints())
							treatmentSocketsAndPowerPoints=1;
						offerInterests = new UserSubcategoryOffersInterests  (userId, 0, 0, 0, 0, 0, 0, 0, 0, colorCorrections, furniture,
								generalHangingWorks, hangingOfLightFixtures, treatmentSocketsAndPowerPoints); 
			
					}
					
					else if (offer.getCategory().equals("olders"))
					{
						OldersOffer oldersOffer = (OldersOffer)offer;
						
						int shopping=0, cooking=0, escortedAged=0, conversation=0;
						
						if (oldersOffer.getConversation())
							conversation=1;
						if (oldersOffer.getCooking())
							cooking=1;
						if (oldersOffer.getEscortedAged())
							escortedAged=1;
						if (oldersOffer.getShopping())
							shopping=1;
						
						offerInterests  = new UserSubcategoryOffersInterests (userId, 0, 0, 0, 0,shopping,cooking, escortedAged, conversation, 0, 0, 0, 0, 0); 
					}
					session.beginTransaction();
					session.save(offerInterests);
					session.getTransaction().commit();
				}
			}
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
			{e.printStackTrace();} 
		}
	}
	
	public void addInterests(int userId ,String category,String interestCategory, List<Integer> interests)
	{
		
		Session session = null;
		try
		{
			initInterestsFactory();
			session=GlobalsFunctions.getSession(interestsFactory);
		
			if (session!=null)
			{
				if (interestCategory.equals("application"))
				{
					UserSubcategoryApplicationsInterests appInterests = getApplicationsInterests(userId);
		
					if (appInterests!=null)
					{
			
						if (category.equals("ride"))
						{
							for (Integer interest:interests)
							{
								appInterests.setRide(appInterests.getRide()+interest); 
							}
						}
						else if (category.equals("handyman"))
						{
							appInterests.setColorCorrections(appInterests.getColorCorrections()+interests.get(0));
							appInterests.setFurniture(appInterests.getFurniture()+interests.get(1));
							appInterests.setGeneralHangingWorks(appInterests.getGeneralHangingWorks()+interests.get(2));
							appInterests.setHangingOfLightFixtures(appInterests.getHangingOfLightFixtures()+interests.get(3));
						}
						else if (category.equals("student"))
						{
							appInterests.setHomeWorks(appInterests.getHomeWorks()+interests.get(0));
							appInterests.setPractice(appInterests.getPractice()+interests.get(1));
							appInterests.setTestStudy(appInterests.getTestStudy()+interests.get(2));
						}
						else if (category.equals("olders"))
						{
							appInterests.setConversation(appInterests.getColorCorrections()+interests.get(0));
							appInterests.setCooking(appInterests.getCooking()+interests.get(1));
							appInterests.setEscortedAged(appInterests.getEscortedAged()+interests.get(2));
							appInterests.setShopping(appInterests.getShopping()+interests.get(3));
						}
					
						session.beginTransaction();
						session.update(appInterests);
						session.getTransaction().commit();
					}
				}
				
				else if (interestCategory.equals("offer"))
				{
					UserSubcategoryOffersInterests offerInterests = getOffersInterests(userId);
					
					if (offerInterests!=null)
					{
						if (category.equals("ride"))
						{
							for (Integer interest:interests)
							{
								offerInterests.setRide(offerInterests.getRide()+interest); 
							}
						}
						else if (category.equals("handyman"))
						{
							offerInterests.setColorCorrections(offerInterests.getColorCorrections()+interests.get(0));
							offerInterests.setFurniture(offerInterests.getFurniture()+interests.get(1));
							offerInterests.setGeneralHangingWorks(offerInterests.getGeneralHangingWorks()+interests.get(2));
							offerInterests.setHangingOfLightFixtures(offerInterests.getHangingOfLightFixtures()+interests.get(3));
						}
						else if (category.equals("student"))
						{
							offerInterests.setHomeWorks(offerInterests.getHomeWorks()+interests.get(0));
							offerInterests.setPractice(offerInterests.getPractice()+interests.get(1));
							offerInterests.setTestStudy(offerInterests.getTestStudy()+interests.get(2));
						}
						else if (category.equals("olders"))
						{
							offerInterests.setConversation(offerInterests.getColorCorrections()+interests.get(0));
							offerInterests.setCooking(offerInterests.getCooking()+interests.get(1));
							offerInterests.setEscortedAged(offerInterests.getEscortedAged()+interests.get(2));
							offerInterests.setShopping(offerInterests.getShopping()+interests.get(3));
						}
					
						session.beginTransaction();
						session.update(offerInterests);
						session.getTransaction().commit();
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
			try 
			{
				if (session!=null)
					session.close();
			} 
			catch (HibernateException e)
			{e.printStackTrace();} 
		}
		
		System.out.println("user was not found");
	}
	
	public UserSubcategoryApplicationsInterests getApplicationsInterests(int userId)
	{
		 Session session = null;
		 List<UserSubcategoryApplicationsInterests> app = null;
		 
	     try
	     {
	    	  
	    	 initInterestsFactory();
	    	 session = GlobalsFunctions.getSession(interestsFactory);
	    	 session.beginTransaction();
		     app = session.createQuery("from " + UserSubcategoryApplicationsInterests.class.getName() + " where userId = " + userId +"").getResultList();
		          
		          if (app != null && !app.isEmpty())
		          {
		        	  session.getTransaction().commit();
		        	  if (app.size() > 0) {
		    		      return app.get(0);	
		    	      }
		          }
	    	 	
	     }
	     catch (HibernateException e) {
	         if (session.getTransaction() != null) 
	        	 session.getTransaction().rollback();
	         	e.printStackTrace();
	      }
	     finally 
	     {
	    	 try 
	    	 {
	    		 if (session!=null)
					session.close();
	    	 } 
	    	 catch(HibernateException e) {e.printStackTrace();}
	     }
	     return null;
		
	}
	
	public UserSubcategoryOffersInterests getOffersInterests(int userId)
	{
		 Session session = null;
		 List<UserSubcategoryOffersInterests> offer = null;
		 
	     try
	     {
	    	  
	    	 initInterestsFactory();
	    	 session = GlobalsFunctions.getSession(interestsFactory);
	    	 session.beginTransaction();
		     offer = session.createQuery("from " + UserSubcategoryOffersInterests.class.getName() + " where userId = " + userId +"").getResultList();
		          
		          if (offer != null && !offer.isEmpty())
		          {
		        	  session.getTransaction().commit();
		        	  if (offer.size() > 0) {
		    		      return offer.get(0);	
		    	      }
		          }
	    	 	
	     }
	     catch (HibernateException e) {
	         if (session.getTransaction() != null) 
	        	 session.getTransaction().rollback();
	         	e.printStackTrace();
	      }
	     finally 
	     {
	    	 try 
	    	 {
	    		 if (session!=null)
					session.close();
	    	 } 
	    	 catch(HibernateException e) {e.printStackTrace();}
	     }
	     return null;
		
	}
	
	public void updateCategoryOffersSuggestions(int userId, Application app)
	{
		Session session = null;
		List<Integer> idList = null;

		try
		{
			 initInterestsFactory();
			 session = GlobalsFunctions.getSession(interestsFactory);
			 if (session!=null)
			 {
		    	 session.beginTransaction();
				 CriteriaBuilder builder = session.getCriteriaBuilder();
			   	 CriteriaQuery<Integer> criteriaQuery = builder.createQuery(Integer.class);
			     Root<?> root2 = criteriaQuery.from(CategoryOffersSuggestions.class);
			     criteriaQuery.select(builder.max(root2.get("id")));
			     idList = session.createQuery(criteriaQuery).getResultList();
			     session.getTransaction().commit();
			     int id = idList.get(0);
			     
			     List <CategoryOffersSuggestions> list = session.createQuery("from "+ CategoryOffersSuggestions.class.getName() + " where id = " + id).getResultList();
			     CategoryOffersSuggestions obj=null;
			     
			     if (list!=null && !list.isEmpty())
			     {
			    	 obj = list.get(0);
			     }
			     else
			     {
			    	 obj=new CategoryOffersSuggestions(0, 0, 0, 0, 0, 0);
			     }
			     
		    	 AppUser user = HibernateUserDAO.getInstance().getUserInfo(userId);
		    	 String category = app.getCategory();
		    	 if (!user.getHandyman()&&category.equals("handyman"))
		    	 {
		    		 if (user.getOlders())
		    			 obj.setHandyman_olders(obj.getHandyman_olders()+1);
		    		 if (user.getRide())
		    			 obj.setRide_handyman(obj.getRide_handyman()+1);
		    		 if (user.getStudent())
		    			 obj.setHandyman_student(obj.getHandyman_student()+1);
		    	 }
		    	 if (!user.getOlders()&&category.equals("olders"))
		    	 {
		    		if (user.getHandyman())
		    			obj.setHandyman_olders(obj.getHandyman_olders()+1);
		    		if (user.getRide())
		    			obj.setRide_olders(obj.getRide_olders()+1);
		    		 if (user.getStudent())
		    			 obj.setOlders_student(obj.getOlders_student()+1);
		    	 }
		    	 if (!user.getStudent()&&category.equals("student"))
		    	 {
		    		 if (user.getOlders())
		    			 obj.setOlders_student(obj.getOlders_student()+1);
		    		 if (user.getRide())
		    			 obj.setRide_student(obj.getRide_student()+1);
		    		 if (user.getHandyman())
		    			 obj.setHandyman_student(obj.getHandyman_student()+1);
		    	 }
		    	 if (!user.getRide()&&category.equals("ride"))
		    	 {
		    		 if (user.getStudent())
		    			 obj.setRide_student(obj.getRide_student()+1);
		    		 if (user.getOlders())
		    			 obj.setRide_olders(obj.getRide_olders()+1);
		    		 if (user.getHandyman())
		    			 obj.setRide_handyman(obj.getRide_handyman()+1);
		    	 }
			    	 
		    	 if (list!=null && !list.isEmpty())
		    		 session.update(obj);
		    	 else
		    		 session.save(obj);
		    	 
			     session.getTransaction().commit();
			 }
		     
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		finally
		{
			try{ 	
				if (session!=null)
					session.close();
			} 
			catch (HibernateException e1){
				e1.printStackTrace();
			}
		 }
	}
	
	
	public void updateUserInterests(Object obj, int userId)
	{
		List<Integer> list = new ArrayList<Integer>();

		if (obj instanceof Application)
		{
			Application application = (Application) obj;

			if (HibernateUserInterestsDAO.getInstance().getApplicationsInterests(userId)==null)
			{
				HibernateUserInterestsDAO.getInstance().createUserSubCategoryInterests(userId, application);
			}
			
			else 
			{
				if (application.getCategory().equals("ride"))
				{
					list.add(1);
					HibernateUserInterestsDAO.getInstance().addInterests(userId, application.getCategory(),"application", list);
		
				}
				else if (application.getCategory().equals("handyman"))
				{
					HandymanApplication handymanApp = (HandymanApplication)application;
					if (handymanApp.getColorCorrections())
						list.add(1);
					else
						list.add(0);
					
					if (handymanApp.getFurniture())
						list.add(1);
					else
						list.add(0);
					
					if (handymanApp.getGeneralHangingWorks())
						list.add(1);
					else
						list.add(0);
					
					if (handymanApp.getHangingOfLightFixtures())
						list.add(1);
					else
						list.add(0);
					
					HibernateUserInterestsDAO.getInstance().addInterests(userId, application.getCategory(),"application", list);
				}
				else if (application.getCategory().equals("student"))
				{
		
					StudentApplication studentApp =(StudentApplication) application;
					
					if (studentApp.getHomeWorks())
						list.add(1);
					else
						list.add(0);
					
					if (studentApp.getPractice())
						list.add(1);
					else
						list.add(0);
					
					if (studentApp.getTestStudy())
						list.add(1);
					else
						list.add(0);
					HibernateUserInterestsDAO.getInstance().addInterests(userId, application.getCategory(),"application", list);
				}
				else if (application.getCategory().equals("olders"))
				{
					OldersApplication oldersApp = (OldersApplication) application;
					
					if (oldersApp.getConversation())
						list.add(1);
					else
						list.add(0);
					
					if (oldersApp.getCooking())
						list.add(1);
					else
						list.add(0);
					
					if (oldersApp.getEscortedAged())
						list.add(1);
					else
						list.add(0);
					
					if (oldersApp.getShopping())
						list.add(1);
					else
						list.add(0);
					
					HibernateUserInterestsDAO.getInstance().addInterests(userId, application.getCategory(),"application", list);
				}
			}
		}
		else if (obj instanceof Offer)
		{
			Offer offer = (Offer) obj;

			if (HibernateUserInterestsDAO.getInstance().getOffersInterests(userId)==null)
			{
				HibernateUserInterestsDAO.getInstance().createUserSubCategoryInterests(userId, offer);
			}
			
			else 
			{
				if (offer.getCategory().equals("ride"))
				{
					list.add(1);
					HibernateUserInterestsDAO.getInstance().addInterests(userId, offer.getCategory(),"offer", list);
		
				}
				else if (offer.getCategory().equals("handyman"))
				{
					HandymanOffer handymanOffer = (HandymanOffer)offer;
					if (handymanOffer.getColorCorrections())
						list.add(1);
					else
						list.add(0);
					
					if (handymanOffer.getFurniture())
						list.add(1);
					else
						list.add(0);
					
					if (handymanOffer.getGeneralHangingWorks())
						list.add(1);
					else
						list.add(0);
					
					if (handymanOffer.getHangingOfLightFixtures())
						list.add(1);
					else
						list.add(0);
					
					HibernateUserInterestsDAO.getInstance().addInterests(userId, offer.getCategory(),"offer", list);
				}
				else if (offer.getCategory().equals("student"))
				{
		
					StudentOffer studentOffer =(StudentOffer) offer;
					
					if (studentOffer.getHomeWorks())
						list.add(1);
					else
						list.add(0);
					
					if (studentOffer.getPractice())
						list.add(1);
					else
						list.add(0);
					
					if (studentOffer.getTestStudy())
						list.add(1);
					else
						list.add(0);
					HibernateUserInterestsDAO.getInstance().addInterests(userId, offer.getCategory(),"offer", list);
				}
				else if (offer.getCategory().equals("olders"))
				{
					OldersOffer oldersOffer = (OldersOffer) offer;
					
					if (oldersOffer.getConversation())
						list.add(1);
					else
						list.add(0);
					
					if (oldersOffer.getCooking())
						list.add(1);
					else
						list.add(0);
					
					if (oldersOffer.getEscortedAged())
						list.add(1);
					else
						list.add(0);
					
					if (oldersOffer.getShopping())
						list.add(1);
					else
						list.add(0);
					
					HibernateUserInterestsDAO.getInstance().addInterests(userId, offer.getCategory(),"offer", list);
				}
			}
		}
	}
	
	public List<Application> suggestions (int userId) throws UserExceptionHandler, ApplicationExceptionHandler
	{
		
		AppUser user = HibernateUserDAO.getInstance().getUserInfo(userId);
		List<Integer> idList = null;
	     String suggestions = "";

		if (( (user.getHandyman()?1:0) + (user.getRide()?1:0) + (user.getOlders()?1:0) +  (user.getStudent()?1:0) )<3)
		{
			try
			{
				Session session = GlobalsFunctions.getSession(interestsFactory);
				if (session!=null)
				{
					 session.beginTransaction();
					 CriteriaBuilder builder = session.getCriteriaBuilder();
				   	 CriteriaQuery<Integer> criteriaQuery = builder.createQuery(Integer.class);
				     Root<?> root2 = criteriaQuery.from(CategoryOffersSuggestions.class);
				     criteriaQuery.select(builder.max(root2.get("id")));
				     idList = session.createQuery(criteriaQuery).getResultList();
				     session.getTransaction().commit();
				     int id = idList.get(0);
				     
				     List <CategoryOffersSuggestions> list = session.createQuery("from "+ CategoryOffersSuggestions.class.getName() + " where id = " + id).getResultList();
				     CategoryOffersSuggestions obj= list.get(0);
				     
				     int max=0;
				     
				     if ( (user.getStudent()&&!user.getOlders()) || (!user.getStudent()&&user.getOlders()) )
				     {
				    	 if (obj.getOlders_student()>max)
				    	 {
				    		 max=obj.getOlders_student();
				    		 if (user.getOlders())
				    			 suggestions="student";
				    		 else
				    			 suggestions="olders";
				    	 }
				     }
				     
				     if ( (user.getRide()&&!user.getOlders()) || (!user.getRide()&&user.getOlders()) )
				     {
				    	 if (obj.getRide_olders()>max)
				    	 {
				    		 max=obj.getRide_olders();
				    		 if (user.getOlders())
				    			 suggestions="ride";
				    		 else
				    			 suggestions="olders";
				    	 }				    	 
				     }
				     
				     if ( (user.getRide()&& !user.getStudent()) || (!user.getRide()&& user.getStudent()))
				     {
				    	 if (obj.getRide_student()>max)
				    	 {
				    		 max=obj.getRide_student();
				    		 if (user.getRide())
				    			 suggestions = "student";
				    		 else
				    			 suggestions="ride";
				    	 }
				    	 
				     }
				     
				     if ( (user.getRide() && !user.getHandyman()) || (!user.getRide() && user.getHandyman()) )
				     {
				    	if (obj.getRide_handyman()>max)
				    	{
				    		max=obj.getRide_handyman();
				    		if (user.getRide())
				    			suggestions = "handyman";
				    		else
				    			suggestions = "ride";
				    	} 
				     }
				     
				     if ( (user.getHandyman() && !user.getOlders()) && (!user.getHandyman() && user.getOlders()) )
				     {
				    	 if (obj.getHandyman_olders()>max)
				    	 {
				    		 max=obj.getHandyman_olders();
				    		 if (user.getHandyman())
				    			 suggestions="olders";
				    		 else
				    			 suggestions="handyman";
				    	 }
				     }
				     
				     if ( (user.getHandyman() && !user.getStudent()) && (!user.getHandyman() && user.getStudent()) )
				     {
				    	 if (obj.getHandyman_student()>max)
				    	 {
				    		 max=obj.getHandyman_student();
				    		 if (user.getHandyman())
				    			 suggestions="student";
				    		 else
				    			 suggestions="handyman";
				    	 }
				     }
				
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
			
		}
		List<Application> list = HibernateApplicationDAO.getInstance().getAllSpecificApplicationTable(suggestions);
		return list;
		
	}
	
	private static void initInterestsFactory()
	{
		try
		{
			if (interestsFactory==null)
			{
				interestsFactory = GlobalsFunctions.initSessionFactory(interestsFactory, "hibernateUserInterests.cfg.xml");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
}

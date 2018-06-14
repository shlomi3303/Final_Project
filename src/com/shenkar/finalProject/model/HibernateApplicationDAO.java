package com.shenkar.finalProject.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.gson.Gson;
import com.shenkar.finalProject.Globals.ConstantVariables;
import com.shenkar.finalProject.Globals.GlobalsFunctions;
import com.shenkar.finalProject.Globals.WebSocket;
import com.shenkar.finalProject.classes.AppUser;
import com.shenkar.finalProject.classes.ManualMatchUserOffer;
import com.shenkar.finalProject.classes.Match;
import com.shenkar.finalProject.model.interfaces.IApplicationDAO;

@SuppressWarnings("unchecked")
public class HibernateApplicationDAO implements IApplicationDAO 
{
	private static HibernateApplicationDAO instance;
	private static SessionFactory applicationFactory;
	private HibernateApplicationDAO () {}
	
	public static HibernateApplicationDAO getInstance() 
	{	
		if (instance == null) {
			instance = new HibernateApplicationDAO();
			initApplicationFactory();
			
		}
		return instance;
	}
	
	@Override
	public int createApplication(Application application) throws ApplicationExceptionHandler 
	{
		Session session =null;
		
		try
		{
			initApplicationFactory();
			session = getSession();
			if (session!=null && application!=null)
			{
				if (application.getClass().equals(HandymanApplication.class))
				{
					application.setTTL(GlobalsFunctions.calculateTTL(application.getPeriod()));
					application.setCategory("handyman");
				}
				else if (application.getClass().equals(RideApplication.class))
				{
					application.setTTL(-1);
					application.setCategory("ride");
				}
				else if (application.getClass().equals(OldersApplication.class))
				{
					application.setTTL(GlobalsFunctions.calculateTTL(application.getPeriod()));
					application.setCategory("olders");
				}
				else if (application.getClass().equals(StudentApplication.class))
				{
					application.setTTL(GlobalsFunctions.calculateTTL(application.getPeriod()));
					application.setCategory("student");
				}
				application.setIsAprroved(false);
				if (application.getStatus().equals(""))
					application.setStatus(ConstantVariables.waitingForMatch);
				
				session.beginTransaction();
				int id = (Integer)session.save(application); 
				session.getTransaction().commit();
				
				if (application.equals("ride"))
					return id;
				else
					return 0;
			}
			
		}catch (HibernateException e) {
			if (session.getTransaction() !=null) session.getTransaction().rollback();
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
			{throw new ApplicationExceptionHandler("Warnning!! connection did'nt close properly");} 
		}
		return 0;
	}

	@Override
	public void editApplication(int applicationId, Application updateApplication, String tableName) throws ApplicationExceptionHandler 
	{
		Session session = null;
		try
		{
			initApplicationFactory();
			session = getSession();
			
			if (session !=null)
			{
				session.beginTransaction();
				if (tableName.equals("olders"))
				{
					OldersApplication olders = (OldersApplication)session.get(OldersApplication.class, applicationId);
					if (olders!=null){
						updateSameVarAppliction(olders, updateApplication);
						editOlders(olders, (OldersApplication) updateApplication);
						session.update(olders);
					}
				}
				else if (tableName.equals("ride"))
				{
					RideApplication ride = (RideApplication)session.get(RideApplication.class, applicationId);
					if (ride!=null)
					{
						updateSameVarAppliction(ride, updateApplication);
						editRide(ride,(RideApplication) updateApplication);
						session.update(ride);
					}
				}
				else if (tableName.equals("handyman"))
				{
					HandymanApplication handyman = (HandymanApplication)session.get(HandymanApplication.class, applicationId);
					if (handyman != null)
					{
						updateSameVarAppliction(handyman, updateApplication);
						editHandyman(handyman, (HandymanApplication) updateApplication);
						session.update(handyman);
					}
				}
				else if (tableName.equals("student"))
				{
					StudentApplication student= (StudentApplication)session.get(StudentApplication.class, applicationId);
					if (student!=null)
					{
						updateSameVarAppliction(student, updateApplication);
						editStudent(student, (StudentApplication) updateApplication);
						session.update(student);
					}
				}
				
				session.getTransaction().commit();
			}
		}
		catch (Exception e)
		{
			if (session.getTransaction() !=null) 
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
			{throw new ApplicationExceptionHandler("Warnning!! connection did'nt close properly");} 
		}

	}
	
	@Override
	public void hardDeleteApplication(int applicationId, String tableName) throws ApplicationExceptionHandler 
	{
		Application application = null;
		Session session = null;
		application = HibernateApplicationDAO.getInstance().getApplication(applicationId, tableName);
		if (application != null)
		{
			try 
			{
				initApplicationFactory();
				session = getSession();
				if (session != null){
					session.beginTransaction();
					session.createQuery("delete from " + getTableName(tableName) + " where id = " + applicationId).executeUpdate();
					session.getTransaction().commit();	
				}
			} 
			
			catch (Exception e) {if (session.getTransaction() !=null) session.getTransaction().rollback();}
			
			finally
			{
				 try 
		    	 {
		    		 if (session!=null)
		    			 session.close();
		    	 } 
		    	 catch(HibernateException e){throw new ApplicationExceptionHandler("Warnning!! connection did'nt close properly");}
			}
		}
	}
	
	@Override
	public void deleteApplication(int applicationId, String tableName) throws ApplicationExceptionHandler 
	{
		Application application = null;
		Session session = null;
		application = HibernateApplicationDAO.getInstance().getApplication(applicationId, tableName);
		if (application != null)
		{
			try 
			{
				initApplicationFactory();
				session = getSession();
				if (session != null)
				{
					session.beginTransaction();
					application.setArchive(true);
					session.update(application);
					session.getTransaction().commit();	
				}
			} 
			
			catch (Exception e) {if (session.getTransaction() !=null) session.getTransaction().rollback();}
			
			finally
			{
				 try 
		    	 {
		    		 if (session!=null)
		    			 session.close();
		    	 } 
		    	 catch(HibernateException e)
				 {throw new ApplicationExceptionHandler("Warnning!! connection did'nt close properly");}
			}
		}
	}

	@Override
	public List<Application> getaAllApplicationMatches (List<Match> manApp)
	{
		Session session =null;
		
		List<Application> applicationList = new ArrayList<Application>();
		
		if (manApp!=null && manApp.size()>0)
		try
		{
			for (int i=0; i<manApp.size(); i++)
			{
				ManualMatchUserOffer manMatchOffer = (ManualMatchUserOffer) manApp.get(i);
				Application app = HibernateApplicationDAO.getInstance().getApplication(manMatchOffer.getApplicationID(), manMatchOffer.getCategory());
				if (app!=null&&app.getUserId()!=manMatchOffer.getUserId())
					applicationList.add(app);
			}
			return applicationList;
		}
		catch(Exception e)
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
	    	 catch(HibernateException e)
			 {
	    		 try {
	    			 throw new ApplicationExceptionHandler("Warnning!! connection did'nt close properly");
	    		 } 
	    		 catch (ApplicationExceptionHandler e1){ 
	    			 e1.printStackTrace();
	    		 }
	    	 }
		}
		return null;
	}
	
	@Override
	public Application getApplication(int applicationId, String tableName) throws ApplicationExceptionHandler 
	{
		Session session = null;
		List<Application> application = null;
	     
	    try
	    {
	    	String strTableName = getTableName(tableName);
	    	if (!strTableName.equals(""))
	    	{
	    		
		    	  initApplicationFactory();
				  session = getSession();
		    	  session.beginTransaction();
		    	  
		    	  application = session.createQuery("from " + strTableName + " where applicationID = " + applicationId +" and isArchive = false").getResultList();
			      if (application != null && !application.isEmpty())
			      {
			        session.getTransaction().commit();
			        if (application.size() > 0) {
			    		return application.get(0);	
			    	 }
			      }
	    	  }
	    }
	    catch (HibernateException e) 
	    {
	         if (session.getTransaction() != null) 
	        	 session.getTransaction().rollback();
	         throw new ApplicationExceptionHandler("Error in get application: " + e.getMessage());
	    }
	    finally 
	    {
	    	 try 
	    	 {
	    		 if (session!=null)
	    			 session.close();
	    	 } 
	    	 catch(HibernateException e){throw new ApplicationExceptionHandler("Warnning!! connection did'nt close properly");}
	    } 
	    return null;
	}

	@Override
	public List<Application> getUserApplications(int userId) throws ApplicationExceptionHandler 
	{
		Session session = null;
		List <Application> applications = null;
		
		try
		{
			 initApplicationFactory();
			 session = getSession();
	    	 session.beginTransaction();
		
	    	 if (userId>0)
	    		 applications = session.createQuery("from "+ Application.class.getName() + " applications where applications.userId = " + userId+ " and isArchive = false").getResultList();
	    	 else if (userId==-1)
	    		 applications = session.createQuery ("from " + Application.class.getName() + " as table where table.isArchive = false and table.status like :key").setParameter("key",  "%" + ConstantVariables.waitingForMatch + "%").getResultList();
	    	 	
	    	 System.out.println(applications);

	         if (applications != null && !applications.isEmpty())
	          {
	        	  session.getTransaction().commit();
	        	  if (applications.size() > 0) {
	    		      return applications;	
	    	      }
	          }
			 
	    }
		catch (HibernateException e) 
		{
		     if (session.getTransaction() != null) 
		    	 session.getTransaction().rollback();
	         throw new ApplicationExceptionHandler("Error in get User applications list: " + e.getMessage());
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
	    		 throw new ApplicationExceptionHandler("Warnning!! connection did'nt close properly" + e.getMessage());
	    	} 
	    }
	    return null;
	}
	
	@Override
	public String getRandomApplication(int num, String tableName) throws ApplicationExceptionHandler 
	{	
		Session session = null;
		List<Integer> idList = null;
		
		List <Application> applicationsList = new ArrayList<>();
			
		try
		{	
			initApplicationFactory();
			session = getSession();
	   	  	session.beginTransaction();
	   	  	   	  	
	        CriteriaBuilder builder = session.getCriteriaBuilder();
	   	  	CriteriaQuery<Integer> criteriaQuery = builder.createQuery(Integer.class);
	        Root<?> root2 = criteriaQuery.from(getTableMapping(tableName));
	        criteriaQuery.select(builder.max(root2.get("applicationID")));
	        idList = session.createQuery(criteriaQuery).getResultList();
	        session.getTransaction().commit();
	        int maxId = idList.get(0);
	        /*    
	        int maxId;
	        if (Id<num)
	        	maxId=Id;
	        else
	        	maxId=num;
	     	*/
	        while (applicationsList.size() !=num )
	        {
	        	Application app = getApplication(new Random().nextInt(maxId)+1, tableName);
	        	if (app != null)
	        	{
	        		applicationsList.add(app);
	        		if (applicationsList.size()==num)
	        			break;
	        	}
	        }
			return new Gson().toJson(applicationsList).toString();
		}
		catch (Exception e)
		{
			throw new ApplicationExceptionHandler("Error in get random applications: " + e.getMessage());
		}
	        
		finally
		{
			try 
			{	
				if (session!=null)
					session.close();
			} 
			catch (HibernateException e1)
			{
				throw new ApplicationExceptionHandler("Warnning!! connection did'nt close properly " + e1.getMessage());
			} 
		}
	}

	
	private String getTableName(String tableName) 
	{
		if (tableName.equals("olders"))
			return OldersApplication.class.getName();
		else if (tableName.equals("ride"))
			return RideApplication.class.getName();
		else if (tableName.equals("handyman"))
			return HandymanApplication.class.getName();
		else if (tableName.equals("student"))
			return StudentApplication.class.getName();
		
		return "";
	}
	
	private void updateSameVarAppliction(Application application, Application updateApplication)
	{
		//application.setLocation(updateApplication.getLocation());
		application.setPeriod(updateApplication.getPeriod());
		application.setPeriodic(updateApplication.getPeriodic());
		application.setUrgency(updateApplication.getUrgency());
		//application.setUserLocation(updateApplication.getUserLocation());
		application.setGender(updateApplication.getGender());
		application.setLanguage(updateApplication.getLanguage());
		application.setImg(updateApplication.getImg());
		application.setDescription(updateApplication.getDescription());
		application.setTitle(updateApplication.getTitle());
		//change into status function?
		application.setStatus(updateApplication.getStatus());
		application.setTTL(updateApplication.getTTL());
		application.setIsAprroved(updateApplication.getIsAprroved());
	}
	
	private void editStudent (StudentApplication student, StudentApplication updateStudent)
	{
		student.setEducationLevel(updateStudent.getEducationLevel());
		student.setFieldOfStudy(updateStudent.getFieldOfStudy());
		student.setHomeWorks(updateStudent.getHomeWorks());
		student.setPractice(updateStudent.getPractice());
		student.setTestStudy(updateStudent.getTestStudy());
	}
	
	private void editOlders (OldersApplication olders, OldersApplication updateOlders) 
	{
		olders.setConversation(updateOlders.getConversation());
		olders.setCooking(updateOlders.getCooking());
		olders.setEscortedAged(updateOlders.getEscortedAged());
		olders.setShopping(updateOlders.getShopping());
	}
	
	private void editRide (RideApplication ride, RideApplication updateRide)
	{
		//ride.setDestination(updateRide.getDestination());
		//ride.setSource(updateRide.getSource());
		ride.setEndPeriod(updateRide.getEndPeriod());
	}
	
	private void editHandyman (HandymanApplication handyman, HandymanApplication updateHandyman)
	{
		handyman.setColorCorrections(updateHandyman.getColorCorrections());
		handyman.setFurniture(updateHandyman.getFurniture());
		handyman.setHangingOfLightFixtures(updateHandyman.getHangingOfLightFixtures());
		handyman.setTreatmentSocketsAndPowerPoints(updateHandyman.getTreatmentSocketsAndPowerPoints());
		handyman.setGeneralHangingWorks(updateHandyman.getGeneralHangingWorks());
	}

	private static void initApplicationFactory ()
	{
		try
		{
			if (applicationFactory==null)
			{
				applicationFactory = GlobalsFunctions.initSessionFactory(applicationFactory, "hibernateApplication.cfg.xml");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private static Session getSession() throws HibernateException {         
		   Session sess = null;       
		   try {         
		       sess = applicationFactory.getCurrentSession();  
		   } catch (org.hibernate.HibernateException he) {  
		       sess = applicationFactory.openSession();     
		   }             
		   return sess;
	} 
	
	@Override
	public void status(String status, Application application) throws ApplicationExceptionHandler 
	{
		application.setStatus(status);
		Session session = null;

		try
		{
			initApplicationFactory();
			session = getSession();
			if (session!=null)
			{
	   	  		session.beginTransaction();
	   	  		session.update(application);
	   	  		session.getTransaction().commit();
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
				if (session !=null)
					session.close();
			}
			catch (HibernateException e)
			{
				e.printStackTrace();
			}
		}
	}
	@Override
	public void updateOfferListPotentialMatch(List <Integer> list, Application application) throws ApplicationExceptionHandler 
	{
		application.setList(list);
	
		Session session = null;
		
		try{
			initApplicationFactory();
			session = getSession();
			if (session!=null){
	   	  		session.beginTransaction();
	   	  		session.update(application);
	   	  		session.getTransaction().commit();
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
				if (session !=null)
					session.close();
			}
			catch (HibernateException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void updateRefuseList(int offerId, Application application) 
	{
		application.getRefuseList().add(offerId);
		
		Session session = null;
		
		try{
			initApplicationFactory();
			session = GlobalsFunctions.getSession(applicationFactory);
			if (session!=null){
	   	  		session.beginTransaction();
	   	  		session.update(application);
	   	  		session.getTransaction().commit();
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
				if (session !=null)
					session.close();
			}
			catch (HibernateException e)
			{
				e.printStackTrace();
			}
		}		
	}
	
	@Override
	public void notification(int userId, String subject, String body) throws ApplicationExceptionHandler, UserExceptionHandler, IOException 
	{
		
		AppUser user = HibernateUserDAO.getInstance().getUserInfo(userId);
		
		if (user!=null)
		{
			GlobalsFunctions.sendEmail(user.getMail(), subject, body);
			String strUserId = String.valueOf(userId);
			WebSocket.sendMessageToClient(ConstantVariables.newMsgInPrivateZone, strUserId);
		}
		
	}

	@Override
	public List<Application> getAllSpecificApplicationTable(String tableName) throws ApplicationExceptionHandler 
	{
		
		Session session = null;
		List <Application> applications = null;
		
		try
		{
	    	String strTableName = getTableName(tableName);
			initApplicationFactory();
			session = getSession();
	    	session.beginTransaction();
		
	    	applications = session.createQuery ("from " + strTableName + " as table where table.isArchive = false and table.status like :key").setParameter("key",  "%" + ConstantVariables.waitingForMatch + "%").getResultList();
				
	         if (applications != null && !applications.isEmpty())
	          {
	        	  session.getTransaction().commit();
	        	  if (applications.size() > 0) {
	    		      return applications;	
	    	      }
	          }
	    }
		catch (HibernateException e) 
		{
		     if (session.getTransaction() != null) 
		    	 session.getTransaction().rollback();
	         throw new ApplicationExceptionHandler("Error in get All Specific Application Table function " + e.getMessage());
	    }
		finally 
		{
	    	 try 
	    	 {	
	    		 if (session!=null)
	    			 session.close();
	    	 } 
	    	 catch (HibernateException e1)
	    		 {throw new ApplicationExceptionHandler("Warnning!! connection did'nt close properly " + e1.getMessage());} 
	    }
		
	    return null;
		
	}

	public Class<?>  getTableMapping (String tableName)
	{
		
		Class<?> className = null;
		
		if (tableName.equals("olders"))
			className = OldersApplication.class;
		else if (tableName.equals("ride"))
			className = RideApplication.class;
		else if (tableName.equals("handyman"))
			className = HandymanApplication.class;
		else if (tableName.equals("student"))
			className = StudentApplication.class;
		
		return className;
		
	}
}

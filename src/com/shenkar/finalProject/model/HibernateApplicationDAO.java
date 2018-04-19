package com.shenkar.finalProject.model;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.shenkar.finalProject.model.interfaces.IApplicationDAO;

public class HibernateApplicationDAO implements IApplicationDAO 
{
	
	private static HibernateApplicationDAO instance;
	
	private static SessionFactory applicationFactory;
	
	private HibernateApplicationDAO () {}
	
	public static HibernateApplicationDAO getInstance() 
	{	
		if (instance == null) {
			instance = new HibernateApplicationDAO();
			applicationFactory = new Configuration().configure("hibernateU.cfg.xml").buildSessionFactory();
		}
		return instance;
	}
	
	public static Session getSession() throws HibernateException {         
		   Session sess = null;       
		   try {         
		       sess = applicationFactory.getCurrentSession();  
		   } catch (org.hibernate.HibernateException he) {  
		       sess = applicationFactory.openSession();     
		   }             
		   return sess;
	} 
	
	@Override
	public void createApplication(Application application) throws ApplicationExceptionHandler 
	{
		Session session =null;
		
		try
		{
			if (applicationFactory==null)
			{
				applicationFactory = new Configuration().configure("hibernateApplication.cfg.xml").buildSessionFactory();
			}
			session = getSession();
			
			session.beginTransaction();
			session.save(application); 
			session.getTransaction().commit();
			
		}catch (HibernateException e) {
			if (session.beginTransaction() !=null) session.beginTransaction().rollback();
			throw new HibernateException (e);
		}
		finally 
		{
			try 
			{
				session.close();
			} 
			catch (HibernateException e)
			{
				throw new ApplicationExceptionHandler("Warnning!! connection did'nt close properly");
			} 
		}
	}

	
	@Override
	public void editApplication(int applicationId, Application updateApplication, String tableName) throws ApplicationExceptionHandler {
		Session session = null;

		try
		{
			if (applicationFactory==null)
			{
				applicationFactory = new Configuration().configure("hibernateApplication.cfg.xml").buildSessionFactory();
			}
			session = getSession();
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
		catch (HibernateException e)
		{
			if (session.beginTransaction() !=null) session.beginTransaction().rollback();
			throw new HibernateException (e);
		}
		finally 
		{
			try 
			{session.close();}
			catch (HibernateException e)
			{throw new ApplicationExceptionHandler("Warnning!! connection did'nt close properly");} 
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
				if (applicationFactory==null)
				{
					applicationFactory = new Configuration().configure("hibernateApplication.cfg.xml").buildSessionFactory();
				}
				
				{
					session = getSession();
					session.beginTransaction();
					session.createQuery("delete from " + Application.class.getName() + " where id = " + applicationId).executeUpdate();
					session.getTransaction().commit();					
				}
			} 
			
			catch (Exception e1) {
				if (session.beginTransaction() !=null) session.beginTransaction().rollback();
				}
			
			finally
			{
				session.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Application getApplication(int applicationId, String tableName) throws ApplicationExceptionHandler 
	{
		Session session = null;
		List<Application> application = null;
	     
	    try
	    {
	    	String strTableName = getTableName(tableName);
	    	if (!strTableName.equals("")){
	    		
		    	if (applicationFactory==null)
		    	  {
		    			applicationFactory = new Configuration().configure("hibernateApplication.cfg.xml").buildSessionFactory();
				  }
				  session = getSession();
		    	  session.beginTransaction();
		    	  
		    	  application = session.createQuery("from " + getTableName(tableName) + " where applicationID = " + applicationId +"").getResultList();
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
	         if (session.beginTransaction() != null) session.beginTransaction().rollback();
	         	throw new ApplicationExceptionHandler("Sorry, connection problem was detected");
	      }finally {
	    	 try {
	    		session.close();
	    	 } catch(HibernateException e){
	    		 throw new ApplicationExceptionHandler("Warnning!! connection did'nt close properly");
	    	 }
	      }
	      
	      return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Application> getApplications(int userId) throws ApplicationExceptionHandler 
	{
		Session session = null;
		List <Application> applications = null;
		
		try
		{
			if (applicationFactory==null)
	    	  {
	    			applicationFactory = new Configuration().configure("hibernateApplication.cfg.xml").buildSessionFactory();
			  }
			  session = getSession();
	    	  session.beginTransaction();
			
	         applications = session.createQuery("from "+ Application.class.getName() + " applications where applications.userId=" + userId+"").getResultList(); 
			 
	         if (applications != null && !applications.isEmpty())
	          {
	        	  session.getTransaction().commit();
	        	  if (applications.size() > 0) {
	    		      return applications;	
	    	      }
	          }
			 
	      }catch (HibernateException e) {
		         if (session.beginTransaction() != null) session.beginTransaction().rollback();
	         throw new ApplicationExceptionHandler("Applications list not avilable at the moment" + e.getMessage());
	      }
		finally {
	    	 try {
	    		 session.close();
	    	 } catch (HibernateException e){
	    		 throw new ApplicationExceptionHandler("Warnning!! connection did'nt close properly" + e.getMessage());
	    	 } 
	      }
	      return null;
	}
	
	@Override
	public int ttlCalc(int ttl, String tableName) throws ApplicationExceptionHandler 
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void status(String status, Application application) throws ApplicationExceptionHandler 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void notification(Application application) throws ApplicationExceptionHandler 
	{
		// TODO Auto-generated method stub

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
		application.setLocation(updateApplication.getLocation());
		application.setPeriod(updateApplication.getPeriod());
		application.setPeriodic(updateApplication.getPeriodic());
		application.setUrgency(updateApplication.getUrgency());
		application.setUserLocation(updateApplication.getUserLocation());
		application.setGender(updateApplication.getGender());
		application.setLanguage(updateApplication.getLanguage());
		application.setImg(updateApplication.getImg());
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
		ride.setDestination(updateRide.getDestination());
		ride.setSource(updateRide.getSource());
		ride.setTimeRange(updateRide.getTimeRange());
	}
	
	private void editHandyman (HandymanApplication handyman, HandymanApplication updateHandyman)
	{
		handyman.setColorCorrections(updateHandyman.getColorCorrections());
		handyman.setFurniture(updateHandyman.getFurniture());
		handyman.setHangingOfLightFixtures(updateHandyman.getHangingOfLightFixtures());
		handyman.setTreatmentSocketsAndPowerPoints(updateHandyman.getTreatmentSocketsAndPowerPoints());
		handyman.setGeneralHangingWorks(updateHandyman.getGeneralHangingWorks());
	}
}

package com.shenkar.finalProject.model;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

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
			//applicationFactory = new AnnotationConfiguration().configure("hibernateApplication.cfg.xml").buildSessionFactory();
		}
		return instance;
	}
	
	
	@Override
	public void createApplication(Application application) throws ApplicationExceptionHandler {
		Session session = applicationFactory.openSession();
		Transaction tx = null;
		int id = 0;
		
		try
		{
			tx = session.beginTransaction();
			id = (Integer) session.save(application); 
			tx.commit();
		}catch (HibernateException e) {
			if (tx !=null) tx.rollback();
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
		if (id != 0) 
			System.out.println("Offer created successfully");

	}

	@Override
	public void editApplication(int applicationId, Application updateApplication) throws ApplicationExceptionHandler {
		Session session = applicationFactory.openSession();
		System.out.println(applicationId);
		Transaction tx = null;
		try
		{
			tx = session.beginTransaction();
			Application application = (Application)session.get(Application.class, new Integer(applicationId));
			
				 application.setLocation(updateApplication.getLocation());
				 application.setPeriod(updateApplication.getPeriod());
				 application.setPeriodic(updateApplication.getPeriodic());
				 application.setUrgency(updateApplication.getUrgency());
				 application.setTTL(updateApplication.getTTL());
				 application.setIsAprroved(updateApplication.getIsAprroved());
				 application.setDescription(updateApplication.getDescription());
				 application.setUserLocation(updateApplication.getUserLocation());
				 
				 session.update(application); 
		    	 tx.commit();
			 
		}
		catch (HibernateException e)
		{
			if (tx !=null) tx.rollback();
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
	public void deleteApplication(int applicationId) throws ApplicationExceptionHandler 
	{
		Application application = null;
		Transaction tx = null;
		try 
		{
			application = HibernateApplicationDAO.getInstance().getApplication(applicationId);
		} catch (ApplicationExceptionHandler e1) {e1.printStackTrace();}
		Session session = applicationFactory.openSession();
		
		try
		{
			if (application!=null){
				tx = session.beginTransaction();
				Object ob = session.get(Offer.class, new Integer(application.getApplicationID()));
				tx = session.beginTransaction();
				session.delete(ob);
				tx.commit();
			}
		}
		catch  (HibernateException e) 
		{
			if (tx !=null) tx.rollback();
		}
		finally
		{
			session.close();
		}

	}

	@Override
	public Application getApplication(int applicationId) throws ApplicationExceptionHandler 
	{
		Session session = applicationFactory.openSession();
		 List<Application> application = null;
	     Transaction tx = null;
	      
	     try{
	    	  tx = session.beginTransaction();
	    	  application = session.createQuery("from " + Application.class.getName() + " application where application.applicationID ='" + applicationId +"'").list();
	          tx.commit();
	      }catch (HibernateException e) {
	         if (session.beginTransaction() != null) session.beginTransaction().rollback();
	         	throw new ApplicationExceptionHandler("Sorry, connection problem was detected");
	      }finally {
	    	 try {
	    		session.close();
	    	 } catch(HibernateException e){
	    		 throw new ApplicationExceptionHandler("Warnning!! connection did'nt close properly");
	    	 }
	      }
	      if (application.size() > 0) {
		      return application.get(0);	
	      }
	      return null;
	}

	@Override
	public int ttlCalc(int ttl) throws ApplicationExceptionHandler {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void status(String status, Application application) throws ApplicationExceptionHandler {
		// TODO Auto-generated method stub

	}

	@Override
	public void notification(Application application) throws ApplicationExceptionHandler {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Application> getApplications(String userId) throws ApplicationExceptionHandler 
	{
		Session session = applicationFactory.openSession();
	    Transaction tx = null;

		List <Application> applications = null;
		try{
	         session.beginTransaction();
	         applications = session.createQuery("from "+ Application.class.getName() + " applications where applications.userId='" + userId+"'").list(); 
			 tx.commit();
			 
	      }catch (HibernateException e) {
	         if (tx != null) tx.rollback();
	         throw new ApplicationExceptionHandler("Applications list not avilable at the moment" + e.getMessage());
	      }finally {
	    	 try {
	    		 session.close();
	    	 } catch (HibernateException e){
	    		 throw new ApplicationExceptionHandler("Warnning!! connection did'nt close properly" + e.getMessage());
	    	 } 
	      }
	      return applications;
	}

}

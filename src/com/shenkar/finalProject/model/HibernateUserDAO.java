package com.shenkar.finalProject.model;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.shenkar.finalProject.model.interfaces.IUserDAO;

public class HibernateUserDAO implements IUserDAO 
{
	
	private static HibernateUserDAO instance;
	private static SessionFactory userFactory;	

	private HibernateUserDAO () {}
		
	public static HibernateUserDAO getInstance() throws IOException 
	{	
		instance = new HibernateUserDAO();
		if (instance == null)
		{
			try
			{
				userFactory = new Configuration().configure("hibernateUser.cfg.xml").buildSessionFactory();
			}
			catch (Exception e)
			{}
			
	}
		return instance;
	}
	
	public void addNewUser(AppUser user, HttpServletResponse response) throws UserExceptionHandler, IOException 
	{	

		Session session = null; 
		try
		{
			initUserFactory();
			session = getSession();
			if (session != null)
			{
				response.getWriter().println("in the try stament");
				session.beginTransaction();
				session.save(user);
				session.getTransaction().commit();
			}
			
		}catch (Exception e) 
		{
			e.printStackTrace(response.getWriter());
			if (session.getTransaction() != null) session.getTransaction().rollback();
			throw new HibernateException ("in add newUser: " + e);
		}finally 
		{
			try 
			{session.close();} 
			catch (HibernateException e)
			{
				throw new UserExceptionHandler("Warnning!! connection did'nt close properly");
			} 
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public AppUser getUser(String mail, String password, HttpServletResponse response) throws UserExceptionHandler, IOException 
	{
		  Session session = null; 
	      List<AppUser> user = null;
	      try
	      {
	    	  if (userFactory==null)
	    	  {
					userFactory = new Configuration().configure("hibernateUser.cfg.xml").buildSessionFactory();
			  }
			  session = getSession();
			  
	    	  session.beginTransaction();
	          user = session.createQuery("from " + AppUser.class.getName() + " user where user.mail ='" + mail +"'").getResultList();
	          if (user != null && !user.isEmpty())
	          {
	        	  session.getTransaction().commit();
	        	  if (user.size() > 0) 
	        	  {
	    		      return user.get(0);
	    	      }
	        	  
	          }
	        	  
	      }catch (HibernateException e) 
	      {
				if (session.beginTransaction() !=null) session.beginTransaction().rollback();
	         	throw new UserExceptionHandler("Sorry, connection problem was detected, login denied");
	      }finally 
	      {
	    	 if (session != null){
	    		 session.close();
	    	 }
	      }
	      if (user.size() > 0) {
		      return user.get(0);	
	      }
	      return null;
	}
	
	@Override
	public void updateUser(int id, AppUser updateUser, HttpServletResponse response) throws UserExceptionHandler, IOException 
	{
		  Session session = null;

	      try
	      {
	    	  if (userFactory==null)
			  {
					userFactory = new Configuration().configure("hibernateUser.cfg.xml").buildSessionFactory();
			  }
	    	  session =  getSession();
	    	  session.beginTransaction();
	    	  AppUser user = (AppUser)session.get(AppUser.class, new Integer(id)); 
	    	  
    		 user.setAge(updateUser.getAge()); 
    		 user.setFamilyStatus(updateUser.getFamilyStatus());
    		 user.setFirstname(updateUser.getFirstname());
    		 user.setLastname(updateUser.getLastname());
    		 user.setPhone(updateUser.getPhone());
    		 user.setKids(updateUser.getKids());
    		 user.setPassword(updateUser.getPassword());
    		 user.setUserLocation(updateUser.getUserLocation());
    		 user.setHandyman(updateUser.getHandyman());
    		 user.setStudent(updateUser.getStudent());
    		 user.setOlders(updateUser.getOlders());
    		 user.setRide(updateUser.getRide());
    		 
			 session.update(user); 
			 session.getTransaction().commit();
	      }
	      catch (HibernateException e) 
	      {
				if (session.beginTransaction() != null) session.beginTransaction().rollback();
	         	throw new UserExceptionHandler("Can'nt update user details at the moment, please check your connection");
	      }finally {
	    	 try {
	    		 session.close();
	    	 } catch (HibernateException e){
	    		 throw new UserExceptionHandler("Warnning!! connection did'nt close properly");
	    	 } 
	      }
	}
	
	@Override
	public void deleteUser(String mail, String password, HttpServletResponse response) throws IOException 
	{
		AppUser user = null;
		Session session = null;
		try 
		{
			user = HibernateUserDAO.getInstance().getUser(mail, password, response);
			if (user!=null && !user.getMail().isEmpty() )
			{
				if (userFactory==null)
				{
					userFactory = new Configuration().configure("hibernateUser.cfg.xml").buildSessionFactory();
				}
				
				session = getSession();
		    	session.beginTransaction();
	
				session.createQuery("delete from " + AppUser.class.getName() + " where id = " + user.getId()).executeUpdate();
				session.getTransaction().commit();
			}
		} catch (Exception e1) {
			if (session.beginTransaction() !=null) session.beginTransaction().rollback();
			e1.printStackTrace(response.getWriter());
			}
		
		finally
		{
			session.close();
		}
	}

	@Override
	public void setUserToAdmin(int id, boolean bool) throws UserExceptionHandler 
	{
		  Session session = null;

	      try
	      {
	    	  initUserFactory();
	    	  session =  getSession();
	    	  session.beginTransaction();
	    	  AppUser user = (AppUser)session.get(AppUser.class, new Integer(id)); 
	    	  
	    	  user.setAdmin(bool);
	    	  
	    	  session.update(user); 
	    	  session.getTransaction().commit();
		  }
		  catch (HibernateException e) 
		      {
					if (session.beginTransaction() != null) session.beginTransaction().rollback();
		         	throw new UserExceptionHandler("Can'nt update user details at the moment, please check your connection");
		      }finally {
		    	 try {
		    		 session.close();
		    	 } catch (HibernateException e){
		    		 throw new UserExceptionHandler("Warnning!! connection did'nt close properly");
		    	 } 
		      }	  
	}
	
	private void initUserFactory ()
	{
		try
		{
		 if (userFactory==null)
		  {userFactory = new Configuration().configure("hibernateUser.cfg.xml").buildSessionFactory();}
		}
		catch (Exception e){}
	}
	
	private static Session getSession() throws HibernateException {         
		   Session sess = null;       
		   try {         
		       sess = userFactory.getCurrentSession();  
		   } catch (org.hibernate.HibernateException he) {  
		       sess = userFactory.openSession();     
		   }             
		   return sess;
		}

	@SuppressWarnings("unchecked") 
	public AppUser getUserInfo(int userId) throws UserExceptionHandler 
	{
		 Session session = null; 
	      List<AppUser> user = null;
	      try
	      {
	    	  initUserFactory();
			  session = getSession();
			  
	    	  session.beginTransaction();
	          user = session.createQuery("from " + AppUser.class.getName() + " user where user.id = " + userId +"").getResultList();
	          if (user != null && !user.isEmpty())
	          {
	        	  session.getTransaction().commit();
	        	  if (user.size() > 0) 
	        	  {
	    		      return user.get(0);
	    	      }
	        	  
	          }
	        	  
	      }catch (HibernateException e) 
	      {
				if (session.beginTransaction() !=null) session.beginTransaction().rollback();
	         	throw new UserExceptionHandler("Sorry, connection problem was detected, login denied");
	      }finally 
	      {
	    	 if (session != null){
	    		 session.close();
	    	 }
	      }
	      if (user.size() > 0) {
		      return user.get(0);	
	      }
		
		
		return null;
	} 
	
}

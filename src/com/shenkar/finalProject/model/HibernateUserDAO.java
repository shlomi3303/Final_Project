package com.shenkar.finalProject.model;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.shenkar.finalProject.Globals.GlobalsFunctions;
import com.shenkar.finalProject.classes.AppUser;
import com.shenkar.finalProject.model.interfaces.IUserDAO;

@SuppressWarnings("unchecked")
public class HibernateUserDAO implements IUserDAO 
{
	
	private static HibernateUserDAO instance;
	private static SessionFactory userFactory;	

	private HibernateUserDAO () {}
		
	public static HibernateUserDAO getInstance()
	{	
		if (instance == null)
		{
			instance = new HibernateUserDAO();
			initUserFactory();
		}
		return instance;
	}
	
	public void addNewUser(AppUser user) throws UserExceptionHandler 
	{	

		Session session = null; 
		try
		{
			initUserFactory();
			session = GlobalsFunctions.getSession(userFactory);
			if (session != null)
			{
				session.beginTransaction();
				session.save(user);
				session.getTransaction().commit();
			}
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			if (session.getTransaction() != null) session.getTransaction().rollback();
			throw new HibernateException ("in add newUser: " + e);
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
				throw new UserExceptionHandler("Warnning!! connection did'nt close properly");
			} 
		}
	}
	
	@Override
	public List <AppUser> getUser(String mail, String password) throws UserExceptionHandler
	{
		  Session session = null; 
	      List<AppUser> user = null;
	      try
	      {
	    	  initUserFactory();
	    	  session = GlobalsFunctions.getSession(userFactory);
			  
	    	  session.beginTransaction();
	          user = session.createQuery("from " + AppUser.class.getName() + " user where user.mail ='" + mail +"'").getResultList();
	          if (user != null && !user.isEmpty())
	          {
	        	  session.getTransaction().commit();
	        	  if (user.size() > 0) 
	        	  {
	    		      return user;
	    	      }
	        	  
	          }
	        	  
	      }
	      catch (HibernateException e) 
	      {
				if (session.getTransaction() !=null) session.getTransaction().rollback();
	         	throw new UserExceptionHandler("Sorry, connection problem was detected, login denied");
	      }
	      finally 
	      {
	    	 if (session != null)
	    		 session.close();
	      }
	     
	      return null;
	}
	
	@Override
	public void updateUser(int id, AppUser updateUser) throws UserExceptionHandler
	{
		  Session session = null;

	      try
	      {
	    	 initUserFactory();
	    	 session = GlobalsFunctions.getSession(userFactory);
	    	 session.beginTransaction();
	    	 AppUser user = (AppUser)session.get(AppUser.class, new Integer(id)); 
	    	  
    		 user.setAge(updateUser.getAge()); 
    		 user.setFamilyStatus(updateUser.getFamilyStatus());
    		 user.setFirstname(updateUser.getFirstname());
    		 user.setLastname(updateUser.getLastname());
    		 user.setPhone(updateUser.getPhone());
    		 user.setKids(updateUser.getKids());
    		 user.setPassword(updateUser.getPassword());
    		 user.setCity(updateUser.getCity());
    		 user.setStreet(updateUser.getStreet());
    		 user.setHouseNumber(updateUser.getHouseNumber());
    		 user.setLatitude(updateUser.getLatitude());
    		 user.setLongitude(updateUser.getLongitude());
    		 user.setHandyman(updateUser.getHandyman());
    		 user.setStudent(updateUser.getStudent());
    		 user.setOlders(updateUser.getOlders());
    		 user.setRide(updateUser.getRide());
    		 
			 session.update(user); 
			 session.getTransaction().commit();
	      }
	      catch (HibernateException e) 
	      {
				if (session.getTransaction() != null) session.getTransaction().rollback();
	         	throw new UserExceptionHandler("Can'nt update user details at the moment, please check your connection");
	      }finally {
	    	 try 
	    	 {
	    		 if (session!=null)
	    			 session.close();
	    	 } 
	    	 catch (HibernateException e){
	    		 throw new UserExceptionHandler("Warnning!! connection did'nt close properly");
	    	 } 
	      }
	}
	
	@Override
	public void deleteUser(String mail, String password)
	{
		List <AppUser> user = null;
		Session session = null;
		try 
		{
			user = HibernateUserDAO.getInstance().getUser(mail, password);
			if (user!=null && !user.get(0).getMail().isEmpty() )
			{
				initUserFactory();
				session = GlobalsFunctions.getSession(userFactory);
				
		    	session.beginTransaction();
				session.createQuery("delete from " + AppUser.class.getName() + " where id = " + user.get(0).getId()).executeUpdate();
				session.getTransaction().commit();
			}
		} 
		catch (Exception e1) {
			if (session.getTransaction() !=null) session.getTransaction().rollback();
			e1.printStackTrace();
			}
		
		finally
		{
			 if (session!=null)
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
	    	  session = GlobalsFunctions.getSession(userFactory);
	    	  session.beginTransaction();
	    	  AppUser user = (AppUser)session.get(AppUser.class, new Integer(id)); 
	    	  
	    	  user.setAdmin(bool);
	    	  
	    	  session.update(user); 
	    	  session.getTransaction().commit();
		  }
		  catch (HibernateException e) 
		  {
			  if (session.getTransaction() != null) 
				session.getTransaction().rollback();
			  throw new UserExceptionHandler("Can'nt update user details at the moment, please check your connection");
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
	    		 throw new UserExceptionHandler("Warnning!! connection did'nt close properly");
	    	 } 
		  }	  
	}
	
	private static void initUserFactory ()
	{
		try
		{
		 if (userFactory==null)
		  {
			 userFactory = GlobalsFunctions.initSessionFactory(userFactory, "hibernateUser.cfg.xml");
		  }
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public AppUser getUserInfo(int userId) throws UserExceptionHandler 
	{
		 Session session = null; 
	      List<AppUser> user = null;
	      
	      //List<Integer> mylist = null;
	      //mylist.get(new Random().nextInt(mylist.size()));
	      try
	      {
	    	  initUserFactory();
	    	  session = GlobalsFunctions.getSession(userFactory);
			  
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
				if (session.getTransaction() !=null) session.getTransaction().rollback();
	         	throw new UserExceptionHandler("Sorry, connection problem was detected, login denied");
	      }finally 
	      {
	    	 if (session != null)
	    		 session.close();
	      }	
	      
	      return null;
	}

	@Override
	public void updateLocation(int userId, String lat, String longt, String city, String street, int houseNumber) throws UserExceptionHandler {
		
		AppUser user = getUserInfo(userId);
		
		Session session =null;
		
		if (user!=null)
		{
			try
			{
				initUserFactory();
				session=GlobalsFunctions.getSession(userFactory);
				
				if (session!=null)
				{
					user.setLatitude(lat);
					user.setLongitude(longt);
					user.setCity(city);
					user.setStreet(street);
					user.setHouseNumber(houseNumber);
					
					session.beginTransaction();
					session.update(user);
					session.getTransaction().commit();
				}
			}
			catch (HibernateException e) 
			{
			  if (session.getTransaction() != null) 
				session.getTransaction().rollback();
			  throw new UserExceptionHandler("Can'nt update user details at the moment, please check your connection");
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
		    		 throw new UserExceptionHandler("Warnning!! connection did'nt close properly");
		    	 } 
			  }	  
		}
	}	
	
}

package com.shenkar.finalProject.model;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.fasterxml.classmate.AnnotationConfiguration;
import com.shenkar.finalProject.model.interfaces.IUserDAO;

public class HibernateUserDAO implements IUserDAO 
{
	
	private static HibernateUserDAO instance;
	
	private static SessionFactory userFactory;
	

	private HibernateUserDAO () {}
		
	public static HibernateUserDAO getInstance(HttpServletResponse response) throws IOException 
	{	
		response.getWriter().println("in get instance");
		
		response.getWriter().println("instance is a null");
		instance = new HibernateUserDAO();
		response.getWriter().println("instance is not null now" + instance);
		if (instance == null){
			
			try{
				userFactory = new AnnotationConfiguration.configure("hibernateUser.cfg.xml").addAnnotatedClass(AppUser.class).buildSessionFactory();
				response.getWriter().println("in get instance: " + userFactory);
	
			}
			catch (Exception e)
			{
				response.getWriter().println("in get instance: " + e);
				response.getWriter().println("in get instance: " + e.toString());
			}
			response.getWriter().println("user factory was created");
			
			response.getWriter().println("returning the instance");
	}
		return instance;
	}
	
	public static Session getSession() throws HibernateException {         
		   Session sess = null;       
		   try {         
		       sess = userFactory.getCurrentSession();  
		   } catch (org.hibernate.HibernateException he) {  
		       sess = userFactory.openSession();     
		   }             
		   return sess;
		} 
	
	
	public void addNewUser(AppUser user, HttpServletResponse response) throws UserExceptionHandler, IOException 
	{	

		response.getWriter().println("userfactory: " + userFactory);
		Session session = null; 
		response.getWriter().println("session: ");	

		int id = 0;
		try
		{
			response.getWriter().println("Before session");
			if (userFactory==null)
			{
				userFactory = new Configuration().configure("hibernateUser.cfg.xml").buildSessionFactory();
				response.getWriter().println("not null");
			}
			session = getSession();
			if (session != null)
			{
				response.getWriter().println("in the try stament");
			}
			
			session.beginTransaction();
			session.save(user);
			session.getTransaction().commit();
			
			response.getWriter().println("the user is in the DB");

		}catch (Exception e) 
		{
			response.getWriter().println("in add newUser: "+ e);
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
		if (id != 0) 
			System.out.println("User created successfully");  	
	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public AppUser getUser(String mail, String password) throws UserExceptionHandler 
	{
		 Session session = userFactory.getCurrentSession();
	      List<AppUser> user = null;
	      try{
	    	  session.beginTransaction();
	          user = session.createQuery("from " + AppUser.class.getName() + " user where user.mail ='" + mail +"'").list();
	          if (user != null)
	        	  session.getTransaction().commit();
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
	
/*
	@Override
	public void deleteUser(String mail, String password) 
	{
		User user = null;
		try 
		{
			user = HibernateUserDAO.getInstance().getUser(mail, password);
		} catch (UserExceptionHandler e1) {e1.printStackTrace();}
		
		Session session = userFactory.getCurrentSession();

		try
		{
			if (user!=null)
			{
				session.beginTransaction();
				Object ob = session.get(User.class, new Integer(user.getId()));
				session.beginTransaction();
				session.delete(ob);
				session.getTransaction().commit();
			}
		}
		catch  (HibernateException e) 
		{
			if (session.beginTransaction() !=null) session.beginTransaction().rollback();
		}
		finally
		{
			session.close();
		}
	}
	

	

	@Override
	public void updateUser(int id, User updateUser) throws UserExceptionHandler 
	{
		  Session session = userFactory.getCurrentSession();

		  System.out.println("im here!!!!!!!");
	      try
	      {
	    	  session.beginTransaction();
	    	  User user = (User)session.get(User.class, new Integer(id)); 
	    	  System.out.println("im here");
	    	  
	    		 user.setAge(updateUser.getAge()); 
	    		 user.setFamilyStatus(updateUser.getFamilyStatus());
	    		 user.setFirstname(updateUser.getFirstname());
	    		 user.setLastname(updateUser.getLastname());
	    		 user.setInterests(updateUser.getInterests());
	    		 user.setPhone(updateUser.getPhone());
	    		 user.setKids(updateUser.getKids());
	    		 user.setPassword(updateUser.getPassword());
	    		 user.setUserLocation(updateUser.getUserLocation());
	    		 
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
*/

	@Override
	public void deleteUser(String mail, String password) throws UserExceptionHandler {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void updateUser(int id, AppUser updateUser) throws UserExceptionHandler {
		// TODO Auto-generated method stub
		
	}
}
	


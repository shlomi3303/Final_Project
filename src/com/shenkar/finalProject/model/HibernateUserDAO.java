package com.shenkar.finalProject.model;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.registry.Registry;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.fasterxml.classmate.AnnotationConfiguration;
import com.shenkar.finalProject.model.interfaces.IUserDAO;

public class HibernateUserDAO implements IUserDAO 
{
	
	private static HibernateUserDAO instance;
	
	private static SessionFactory userFactory;

	private static StandardServiceRegistry registry;
	

	private HibernateUserDAO () {}
		
	public static HibernateUserDAO getInstance(HttpServletResponse response) throws IOException 
	{	
		response.getWriter().println("in get instance");
		
		response.getWriter().println("instance is a null");
		instance = new HibernateUserDAO();
		response.getWriter().println("instance is not null now" + instance);
		if (instance == null)
		{
			try
			{
				userFactory = new Configuration().configure("hibernateUser.cfg.xml").buildSessionFactory();
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
			e.printStackTrace(response.getWriter());
			response.getWriter().println(e);
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
	public AppUser getUser(String mail, String password, HttpServletResponse response) throws UserExceptionHandler, IOException 
	{
		  Session session = null; 
	      List<AppUser> user = null;
	      try
	      {
	    	  if (userFactory==null)
	    	  {
					userFactory = new Configuration().configure("hibernateUser.cfg.xml").buildSessionFactory();
					response.getWriter().println("not null");
			  }
			  session = getSession();

	    	  session.beginTransaction();
	    	  response.getWriter().println("in get, after tx");
	          user = session.createQuery("from " + AppUser.class.getName() + " user where user.mail ='" + mail +"'").getResultList();
	          if (user != null && !user.isEmpty())
	          {
	        	  response.getWriter().println(user.get(0).getMail());
		    	  response.getWriter().println("user is not a null");
	        	  session.getTransaction().commit();
	        	  response.getWriter().println("after commit");
	        	  if (user.size() > 0) 
	        	  {
		        	  response.getWriter().println("in user.size >0 ");
		        	  response.getWriter().println("mail: " + user.get(0).getMail());
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
    	  response.getWriter().println("IN UPDATE, before try");

	      try
	      {
	    	  if (userFactory==null)
				{
					userFactory = new Configuration().configure("hibernateUser.cfg.xml").buildSessionFactory();
					response.getWriter().println("not null");
				}
	    	  session =  getSession();
	    	  session.beginTransaction();
	    	  AppUser user = (AppUser)session.get(AppUser.class, new Integer(id)); 
	    	  response.getWriter().println("IN UPDATE");
	    	  
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
	      catch (HibernateException | IOException e) 
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
			user = HibernateUserDAO.getInstance(response).getUser(mail, password, response);
			response.getWriter().println("returning from get: " + user);
			response.getWriter().println("returning from get: " + user.getId());
			session = getSession();
	    	session.beginTransaction();

			session.createQuery("delete from " + AppUser.class.getName() + " where id = " + user.getId()).executeUpdate();
			session.getTransaction().commit();
	    	response.getWriter().println("IN DELETE");

		} catch (Exception e1) {e1.printStackTrace(response.getWriter());}
		
		 
/*
		try
		{
			if (user!=null && !user.getMail().isEmpty() )
			{
				if (userFactory==null)
				{
					userFactory = new Configuration().configure("hibernateUser.cfg.xml").buildSessionFactory();
					response.getWriter().println("not null");
				}
				
		
				session = getSession();
				
				if (session != null)
				{
					session.beginTransaction();
					Object ob = session.get(AppUser.class, new Integer(user.getId()));
					response.getWriter().println ("Ob class: " + ob.getClass());
					session.beginTransaction();
					session.delete(ob);
					session.getTransaction().commit();
			    	response.getWriter().println("IN DELETE");
				}
			}
		}
		catch  (HibernateException | IOException e) 
		{
			e.printStackTrace(response.getWriter());
			if (session.beginTransaction() !=null) session.beginTransaction().rollback();
		}
		*/
		finally
		{
			session.close();
		}
	}

}
	


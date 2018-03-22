package com.shenkar.finalProject.model.HibernateHandels;

import java.util.List;

import javax.transaction.Transaction;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;


import com.shenkar.finalProject.model.User;
import com.shenkar.finalProject.model.UserExceptionHandler;
import com.shenkar.finalProject.model.interfaces.IUserDAO;

public class HibernateUserDAO implements IUserDAO 
{
	
	private static HibernateUserDAO instance;
	
	private static SessionFactory userFactory;
	
	private HibernateUserDAO () {}
	
	public static HibernateUserDAO getInstance() 
	{	
		if (instance == null) {
			instance = new HibernateUserDAO();
			/*
			Configuration cfg = new Configuration().configure("hibernateUser.cfg.xml"); 
			ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(cfg.getProperties()).buildServiceRegistry();

			userFactory = cfg.buildSessionFactory(serviceRegistry);
			*/
			userFactory = new AnnotationConfiguration().configure("hibernateUser.cfg.xml").buildSessionFactory();
		}
		return instance;
	}
	
	public void addNewUser(User user) throws UserExceptionHandler 
	{	
		Session session = userFactory.openSession();
		org.hibernate.Transaction tx = null;
		int id = 0;
		try{
			tx = session.beginTransaction();
			List <Object> users = (List <Object>) session.createSQLQuery("select * from users").list();
			java.util.Iterator i = users.iterator();
			
			while(i.hasNext())
			{
				Object obj[] = (Object[])i.next();
				String mail = (String) obj[5];
				if (mail.equals(user.getMail()))
					throw new UserExceptionHandler("Unable to signup with that User name because it is already exist");
			}			
			tx = session.beginTransaction();
			id = (Integer) session.save(user); 
			tx.commit();
		}catch (HibernateException e) {
			if (tx !=null) tx.rollback();
			throw new HibernateException (e);
			//("Unable to signup, duplicate User name or no network connection"); 
		}finally 
		{
			try 
			{
				session.close();
			} 
			catch (HibernateException e)
			{
				throw new UserExceptionHandler("Warnning!! connection did'nt close properly");
			} 
		}
		if (id != 0) 
		{
			System.out.println("User created successfully");  	
		}
		
	}

	

	@Override
	public void deleteUser(String mail, String password) 
	{
		User user = null;
		try 
		{
			user = HibernateUserDAO.getInstance().getUser(mail, password);
		} catch (UserExceptionHandler e1) {e1.printStackTrace();}
		Session session = userFactory.openSession();
		org.hibernate.Transaction tx = null;

		try
		{
			if (user!=null){
			tx = session.beginTransaction();
			Object ob = session.get(User.class, new Integer(user.getId()));
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
	public User getUser(String mail, String password) throws UserExceptionHandler 
	{
		  Session session = userFactory.openSession();
	      List<User> user = null;
	      try{
	    	  session.beginTransaction();
	          user = session.createQuery("from " + User.class.getName() + " user where user.mail ='" + mail +"'").list();
	          session.getTransaction().commit();
	      }catch (HibernateException e) {
	         if (session.beginTransaction() != null) session.beginTransaction().rollback();
	         	throw new UserExceptionHandler("Sorry, connection problem was detected, login denied");
	      }finally {
	    	 try {
	    		session.close();
	    	 } catch(HibernateException e){
	    		 throw new UserExceptionHandler("Warnning!! connection did'nt close properly");
	    	 }
	      }
	      if (user.size() > 0) {
		      return user.get(0);	
	      }
	      return null;
	}

	@Override
	public void updateUser(String userId, User updateUser) throws UserExceptionHandler 
	{
		  Session session = userFactory.openSession();
		  System.out.println("im here!!!!!!!");
	      try
	      {
	    	  org.hibernate.Transaction tx = session.beginTransaction();
	    	  User user = (User)session.get(User.class, new Integer(updateUser.getId())); 
	    	  System.out.println("im here");
	    	  if (updateUser.getUserId().equals(user.getUserId()) ) 
	    	  {
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
	         
	      }
	      catch (HibernateException e) {
	         if (session.getTransaction()!=null) session.getTransaction().rollback();
	         	throw new UserExceptionHandler("Can'nt update user details at the moment, please check your connection");
	      }finally {
	    	 try {
	    		 session.close();
	    	 } catch (HibernateException e){
	    		 throw new UserExceptionHandler("Warnning!! connection did'nt close properly");
	    	 } 
	      }
	
	}
	
}
	


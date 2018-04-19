package com.shenkar.finalProject.model;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.shenkar.finalProject.model.interfaces.IOfferDAO;

public class HibernateOfferDAO implements IOfferDAO
{
	private static HibernateOfferDAO instance;
	
	private static SessionFactory offerFactory;
	
	private HibernateOfferDAO () {}
	
	public static HibernateOfferDAO getInstance() 
	{	
		if (instance == null) 
		{
			instance = new HibernateOfferDAO();
			try
			{
				offerFactory = new Configuration().configure("hibernateOffer.cfg.xml").buildSessionFactory();
			}
			catch (Exception e){}
		}
		return instance;
	}

	public static Session getSession() throws HibernateException {         
		   Session sess = null;       
		   try {         
		       sess = offerFactory.getCurrentSession();  
		   } catch (org.hibernate.HibernateException he) {  
		       sess = offerFactory.openSession();     
		   }             
		   return sess;
	} 
	
	@Override
	public void createOffer(Offer offer) throws OfferExceptionHandler 
	{
		Session session = null;
		try
		{
			if (offerFactory==null)
			{
				offerFactory = new Configuration().configure("hibernateOffer.cfg.xml").buildSessionFactory();
			}
			session = getSession();
			if (session != null)
			{
				session.beginTransaction();
				session.save(offer); 
				session.getTransaction().commit();
			}
		}catch (HibernateException e) 
		{
			if (session.getTransaction() != null) session.getTransaction().rollback();
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
				throw new OfferExceptionHandler("Warnning!! connection did'nt close properly");
			} 
		}
	}

	@Override
	public void editOffer(int offerId, Offer updateOffer) throws OfferExceptionHandler 
	{
		Session session = null;
		try
		{
			if (offerFactory==null)
			{
				offerFactory = new Configuration().configure("hibernateOffer.cfg.xml").buildSessionFactory();
			}
			session = getSession();
			if (session != null)
			{
				
				session.beginTransaction();
				Offer offer = (Offer)session.get(Offer.class, new Integer(offerId));
		
				 offer.setLocation(updateOffer.getLocation());
				 offer.setPeriod(updateOffer.getPeriod());
				 offer.setPeriodic(updateOffer.getPeriodic());
				 offer.setUrgency(updateOffer.getUrgency());
				 offer.setIsAprroved(updateOffer.getIsAprroved());
				 offer.setDescription(updateOffer.getDescription());
				 offer.setUserLocation(updateOffer.getUserLocation());
				 offer.setGender(updateOffer.getGender());
				 offer.setLanguage(updateOffer.getLanguage());
				 offer.setEducationLevel(updateOffer.getEducationLevel());
				 offer.setFieldOfStudy(updateOffer.getFieldOfStudy());
				 offer.setImg(updateOffer.getImg());
				 
				 session.update(offer); 
				 session.getTransaction().commit();
			}
		}
		catch (HibernateException e)
		{
			if (session.getTransaction() != null) session.getTransaction().rollback();
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
				throw new OfferExceptionHandler("Warnning!! connection did'nt close properly");
			} 
		}
	}

	@Override
	public void deleteOffer(int offerId) throws OfferExceptionHandler 
	{
		Session session = null;
		
		try
		{
				if (offerFactory==null)
		    	{
					offerFactory = new Configuration().configure("hibernateOffer.cfg.xml").buildSessionFactory();
				}
				session = getSession();
				  
		    	session.beginTransaction();
				
				Object ob = session.get(Offer.class, offerId);
				session.delete(ob);
	        	session.getTransaction().commit();
		}
		catch  (HibernateException e) 
		{
	         if (session.beginTransaction() != null) session.beginTransaction().rollback();
		}
		finally
		{
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Offer getOffer(int offerId) throws OfferExceptionHandler {
		
		 Session session = null;
		 List<Offer> offer = null;
		 
	     try{
	    	 if (offerFactory==null)
	    	  {
					offerFactory = new Configuration().configure("hibernateOffer.cfg.xml").buildSessionFactory();
			  }
			  session = getSession();
			  
	    	  session.beginTransaction();
	    	  
	          offer = session.createQuery("from " + Offer.class.getName() + " offer where offer.offerId ='" + offerId +"'").getResultList();
	          
	          if (offer != null && !offer.isEmpty())
	          {
	        	  session.getTransaction().commit();
	        	  if (offer.size() > 0) {
	    		      return offer.get(0);	
	    	      }
	          }

	      }catch (HibernateException e) {
	         if (session.beginTransaction() != null) session.beginTransaction().rollback();
	         	throw new OfferExceptionHandler("Sorry, connection problem was detected");
	      }finally 
	     {
	    	 try 
	    	 {	    		 
	    		session.close();
	    		
	    	 } catch(HibernateException e)
	    	 {
	    		 
	    		 throw new OfferExceptionHandler("Warnning!! connection did'nt close properly");
	    		 
	    	 }
	      }
	      
	      return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Offer> getOffers(int userId) throws OfferExceptionHandler {
		Session session = null;
		List <Offer> offers = null;

		try
		{
			if (offerFactory==null)
	    	  {
					offerFactory = new Configuration().configure("hibernateOffer.cfg.xml").buildSessionFactory();
			  }
			  session = getSession();
			  
	    	  session.beginTransaction();
			
	         offers = session.createQuery("from "+ Offer.class.getName() + " offers where offers.userId = "+userId+"").getResultList();
	         
	         if (offers != null && !offers.isEmpty())
	          {
	        	  session.getTransaction().commit();
	    	      return offers;
	          }
	         
	      }catch (HibernateException e) {
				if (session.beginTransaction() !=null) session.beginTransaction().rollback();
	         throw new OfferExceptionHandler("Offers list not avilable at the moment" + e.getMessage());
	      }finally {
	    	 try {
	    		 session.close();
	    	 } catch (HibernateException e){
	    		 throw new OfferExceptionHandler("Warnning!! connection did'nt close properly" + e.getMessage());
	    	 } 
	      }
	      return null;
	}
	
	@Override
	public int ttlCalc(int ttl) throws OfferExceptionHandler {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void status(String status, Offer offer) throws OfferExceptionHandler {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notification(Offer offer) throws OfferExceptionHandler {
		// TODO Auto-generated method stub
		
	}
}

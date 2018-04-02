package com.shenkar.finalProject.model;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

import com.shenkar.finalProject.model.interfaces.IOfferDAO;

public class HibernateOfferDAO implements IOfferDAO
{
	private static HibernateOfferDAO instance;
	
	private static SessionFactory offerFactory;
	
	private HibernateOfferDAO () {}
	
	public static HibernateOfferDAO getInstance() 
	{	
		if (instance == null) {
			instance = new HibernateOfferDAO();
			offerFactory = new AnnotationConfiguration().configure("hibernateOffer.cfg.xml").buildSessionFactory();
		}
		return instance;
	}

	@Override
	public void createOffer(Offer offer) throws OfferExceptionHandler {
		Session session = offerFactory.openSession();
		Transaction tx = null;
		int id = 0;
		
		try
		{
			tx = session.beginTransaction();
			id = (Integer) session.save(offer); 
			tx.commit();
		}catch (HibernateException e) {
			if (tx !=null) tx.rollback();
			throw new HibernateException (e);
			//("Unable to signup, duplicate User name or no network connection"); 
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
		if (id != 0) 
			System.out.println("Offer created successfully");  	
	}

	@Override
	public void editOffer(int offerId, Offer updateOffer) throws OfferExceptionHandler 
	{
		Session session = offerFactory.openSession();
		System.out.println(offerId);
		Transaction tx = null;
		try
		{
			tx = session.beginTransaction();
			Offer offer = (Offer)session.get(Offer.class, new Integer(offerId));
			if (updateOffer.getUserId().equals(offer.getUserId()))
			{
				 offer.setLocation(updateOffer.getLocation());
				 offer.setPeriod(updateOffer.getPeriod());
				 offer.setPeriodic(updateOffer.getPeriodic());
				 offer.setUrgency(updateOffer.getUrgency());
				 offer.setTTL(updateOffer.getTTL());
				 offer.setIsAprroved(updateOffer.getIsAprroved());
				 offer.setDescription(updateOffer.getDescription());
				 offer.setUserLocation(updateOffer.getUserLocation());
				 
				 session.update(offer); 
		    	 tx.commit();
			 }
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
				throw new OfferExceptionHandler("Warnning!! connection did'nt close properly");
			} 
		}
	}

	@Override
	public void deleteOffer(int offerId) throws OfferExceptionHandler 
	{
		Offer offer = null;
		Transaction tx = null;
		try 
		{
			offer = HibernateOfferDAO.getInstance().getOffer(offerId);
		} catch (OfferExceptionHandler e1) {e1.printStackTrace();}
		Session session = offerFactory.openSession();
		
		try
		{
			if (offer!=null){
				tx = session.beginTransaction();
				Object ob = session.get(Offer.class, new Integer(offer.getOfferId()));
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
	public Offer getOffer(int offerId) throws OfferExceptionHandler {
		
		 Session session = offerFactory.openSession();
		 List<Offer> offer = null;
	     Transaction tx = null;
	      
	     try{
	    	  tx = session.beginTransaction();
	          offer = session.createQuery("from " + Offer.class.getName() + " offer where offer.offerId ='" + offerId +"'").list();
	          tx.commit();
	      }catch (HibernateException e) {
	         if (session.beginTransaction() != null) session.beginTransaction().rollback();
	         	throw new OfferExceptionHandler("Sorry, connection problem was detected");
	      }finally {
	    	 try {
	    		session.close();
	    	 } catch(HibernateException e){
	    		 throw new OfferExceptionHandler("Warnning!! connection did'nt close properly");
	    	 }
	      }
	      if (offer.size() > 0) {
		      return offer.get(0);	
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

	@Override
	public List<Offer> getOffers(String userId) throws OfferExceptionHandler {
		Session session = offerFactory.openSession();
		List <Offer> offers = null;
	    Transaction tx = null;

		try{
	         session.beginTransaction();
	         offers = session.createQuery("from "+ Offer.class.getName() + " offers where offers.userId='" + userId+"'").list(); 
			 tx.commit();
			 
	      }catch (HibernateException e) {
	         if (tx != null) tx.rollback();
	         throw new OfferExceptionHandler("Offers list not avilable at the moment" + e.getMessage());
	      }finally {
	    	 try {
	    		 session.close();
	    	 } catch (HibernateException e){
	    		 throw new OfferExceptionHandler("Warnning!! connection did'nt close properly" + e.getMessage());
	    	 } 
	      }
	      return offers;
	}
}

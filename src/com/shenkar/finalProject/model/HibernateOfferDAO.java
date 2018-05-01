package com.shenkar.finalProject.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.google.gson.Gson;
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
			initOfferFactory();
		}
		return instance;
	}

	@Override
	public void createOffer(Offer offer) throws OfferExceptionHandler 
	{
		Session session = null;
		try
		{
			initOfferFactory();
			session = getSession();
			if (session != null && offer!=null)
			{
				if (offer.getClass().equals(HandymanOffer.class))
					{offer.setTTL(5);}
				else if (offer.getClass().equals(RideOffer.class))
					{offer.setTTL(-1);}
				else if (offer.getClass().equals(OldersOffer.class))
					{offer.setTTL(4);}
				else if (offer.getClass().equals(StudentOffer.class))
					{offer.setTTL(6);}
				offer.setIsAprroved(false);
				offer.setStatus("Waiting for approval");
				
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
			{session.close();} 
			catch (HibernateException e)
			{throw new OfferExceptionHandler("Warnning!! connection did'nt close properly");} 
		}
	}

	@Override
	public void editOffer(int offerId, Offer updateOffer, String tableName) throws OfferExceptionHandler 
	{
		Session session = null;
		try
		{
			initOfferFactory();
			session = getSession();
			if (session != null)
			{
				
				session.beginTransaction();
				if (tableName.equals("olders"))
				{
					OldersOffer olders = (OldersOffer)session.get(OldersOffer.class, offerId);
					if (olders!=null){
						updateSameVarOffer(olders, updateOffer);
						editOlders(olders, (OldersOffer) updateOffer);
						session.update(olders);
					}
				}
				else if (tableName.equals("ride"))
				{
					RideOffer ride = (RideOffer)session.get(RideOffer.class, offerId);
					if (ride!=null)
					{
						updateSameVarOffer(ride, updateOffer);
						editRide(ride,(RideOffer) updateOffer);
						session.update(ride);
					}
				}
				else if (tableName.equals("handyman"))
				{
					HandymanOffer handyman = (HandymanOffer)session.get(HandymanOffer.class, offerId);
					if (handyman != null)
					{
						updateSameVarOffer(handyman, updateOffer);
						editHandyman(handyman, (HandymanOffer) updateOffer);
						session.update(handyman);
					}
				}
				else if (tableName.equals("student"))
				{
					StudentOffer student= (StudentOffer)session.get(StudentOffer.class, offerId);
					if (student!=null)
					{
						updateSameVarOffer(student, updateOffer);
						editStudent(student, (StudentOffer) updateOffer);
						session.update(student);
					}
				}
			
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
			{session.close();} 
			catch (HibernateException e)
			{throw new OfferExceptionHandler("Warnning!! connection did'nt close properly");} 
		}
	}

	@Override
	public void deleteOffer(int offerId, String tableName) throws OfferExceptionHandler 
	{
		Offer offer = null;
		Session session = null;
		offer = HibernateOfferDAO.getInstance().getOffer(offerId, tableName);
		if (offer !=null){
			try
			{
					initOfferFactory();
					session = getSession();
					if (session!=null)
					{  
				    	session.beginTransaction();
						session.createQuery("delete from " + getTableName(tableName) + " where id = " + offerId).executeUpdate();
			        	session.getTransaction().commit();
					}
			}
			catch  (HibernateException e) 
			{if (session.getTransaction() != null) session.getTransaction().rollback();}
			finally
			{session.close();}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Offer getOffer(int offerId, String tableName) throws OfferExceptionHandler {
		
		 Session session = null;
		 List<Offer> offer = null;
		 
	     try{
	    	  String strTableName = getTableName(tableName);
	    	  if (!strTableName.equals(""))
	    	  {
		    	  initOfferFactory();
				  session = getSession();
		    	  session.beginTransaction();
		          offer = session.createQuery("from " + getTableName(tableName) + " where offerId = " + offerId +"").getResultList();
		          
		          if (offer != null && !offer.isEmpty())
		          {
		        	  session.getTransaction().commit();
		        	  if (offer.size() > 0) {
		    		      return offer.get(0);	
		    	      }
		          }
	    	  }

	      }catch (HibernateException e) {
	         if (session.getTransaction() != null) session.getTransaction().rollback();
	         	throw new OfferExceptionHandler("Sorry, connection problem was detected");
	      }
	     finally 
	     {
	    	 try {session.close();} 
	    	 catch(HibernateException e) {throw new OfferExceptionHandler("Warnning!! connection did'nt close properly");}
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
			 initOfferFactory();
			 session = getSession();
	    	 session.beginTransaction();
			
	    	 if (userId>0)
	    		 offers = session.createQuery("from "+ Offer.class.getName() + " offers where offers.userId = "+userId+"").getResultList();
	    	 else if(userId==-1)
	    		 offers = session.createQuery ("from " + Offer.class.getName()).getResultList();
	         
	         if (offers != null && !offers.isEmpty())
	          {
	        	  session.getTransaction().commit();
	    	      return offers;
	          }
	      }
		catch (HibernateException e) {
			if (session.getTransaction() !=null) session.getTransaction().rollback();
	        throw new OfferExceptionHandler("Offers list not avilable at the moment" + e.getMessage());
	    }
		finally 
		{
	    	 try {session.close();} 
	    	 catch (HibernateException e){throw new OfferExceptionHandler("Warnning!! connection did'nt close properly" + e.getMessage());} 
	    }
	    return null;
	}
	
	public String getRandomOffer(int num, String tableName) throws OfferExceptionHandler 
	{	
		Session session = null;
		List<Integer> idList = null;
		
		List <Offer> offersList = new ArrayList<>();
		
		initOfferFactory();
		session = getSession();
   	  	session.beginTransaction();
   	  	   	  	
        CriteriaBuilder builder = session.getCriteriaBuilder();
   	  	CriteriaQuery<Integer> criteriaQuery = builder.createQuery(Integer.class);
        Root<?> root = criteriaQuery.from(getTableMapping(tableName));
        criteriaQuery.select(builder.max(root.get("offerId")));
        idList = session.createQuery(criteriaQuery).getResultList();
        session.getTransaction().commit();
        int maxId = idList.get(0);
        //System.out.println("Max Id = " + maxId);

        while (offersList.size() !=num )
        {
        	Offer offer= getOffer(new Random().nextInt(maxId)+1, tableName);
        	if (offer != null)
        	{
        		offersList.add(offer);
        		if (offersList.size()==num)
        			break;
        	}
        }
        
		return new Gson().toJson(offersList).toString();
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
	private static void initOfferFactory ()
	{
		try{
		 if (offerFactory==null)
		  {offerFactory = new Configuration().configure("hibernateOffer.cfg.xml").buildSessionFactory();}
		}
		catch (Exception e){}
	}
	private static Session getSession() throws HibernateException {         
		   Session sess = null;       
		   try {         
		       sess = offerFactory.getCurrentSession();  
		   } catch (org.hibernate.HibernateException he) {  
		       sess = offerFactory.openSession();     
		   }             
		   return sess;
	} 
	private void updateSameVarOffer(Offer offer, Offer updateOffer)
	{
		offer.setLocation(updateOffer.getLocation());
		offer.setPeriod(updateOffer.getPeriod());
		offer.setPeriodic(updateOffer.getPeriodic());
		offer.setUrgency(updateOffer.getUrgency());
		offer.setUserLocation(updateOffer.getUserLocation());
		offer.setGender(updateOffer.getGender());
		offer.setLanguage(updateOffer.getLanguage());
		offer.setImg(updateOffer.getImg());
		offer.setDescription(updateOffer.getDescription());
		offer.setTitle(updateOffer.getTitle());
		//change into function?
		offer.setStatus(updateOffer.getStatus());
		offer.setTTL(updateOffer.getTTL());
		offer.setIsAprroved(updateOffer.getIsAprroved());
	}
	
	private void editStudent (StudentOffer student, StudentOffer updateStudent)
	{
		student.setEducationLevel(updateStudent.getEducationLevel());
		student.setFieldOfStudy(updateStudent.getFieldOfStudy());
		student.setHomeWorks(updateStudent.getHomeWorks());
		student.setPractice(updateStudent.getPractice());
		student.setTestStudy(updateStudent.getTestStudy());
	}
	
	private void editOlders (OldersOffer olders, OldersOffer updateOlders) 
	{
		olders.setConversation(updateOlders.getConversation());
		olders.setCooking(updateOlders.getCooking());
		olders.setEscortedAged(updateOlders.getEscortedAged());
		olders.setShopping(updateOlders.getShopping());
	}
	
	private void editRide (RideOffer ride, RideOffer updateRide)
	{
		ride.setDestination(updateRide.getDestination());
		ride.setSource(updateRide.getSource());
		ride.setEndPeriod(updateRide.getEndPeriod());
	}
	
	private void editHandyman (HandymanOffer handyman, HandymanOffer updateHandyman)
	{
		handyman.setColorCorrections(updateHandyman.getColorCorrections());
		handyman.setFurniture(updateHandyman.getFurniture());
		handyman.setHangingOfLightFixtures(updateHandyman.getHangingOfLightFixtures());
		handyman.setTreatmentSocketsAndPowerPoints(updateHandyman.getTreatmentSocketsAndPowerPoints());
		handyman.setGeneralHangingWorks(updateHandyman.getGeneralHangingWorks());
	}
	private String getTableName(String tableName) 
	{
		if (tableName.equals("olders"))
			return OldersOffer.class.getName();
		else if (tableName.equals("ride"))
			return RideOffer.class.getName();
		else if (tableName.equals("handyman"))
			return HandymanOffer.class.getName();
		else if (tableName.equals("student"))
			return StudentOffer.class.getName();
		
		return "";
	}
	
	public Class<?>  getTableMapping (String tableName)
	{
		
		Class<?> className = null;
		
		if (tableName.equals("olders"))
			className = OldersOffer.class;
		else if (tableName.equals("ride"))
			className = RideOffer.class;
		else if (tableName.equals("handyman"))
			className = HandymanOffer.class;
		else if (tableName.equals("student"))
			className = StudentOffer.class;
		
		return className;
		
	}
	
}

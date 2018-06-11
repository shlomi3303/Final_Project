package com.shenkar.finalProject.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.gson.Gson;
import com.shenkar.finalProject.Globals.ConstantVariables;
import com.shenkar.finalProject.Globals.GlobalsFunctions;
import com.shenkar.finalProject.Globals.WebSocket;
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
				{
					offer.setTTL(GlobalsFunctions.calculateTTL(offer.getPeriod()));
					offer.setCategory("handyman");
				}
				else if (offer.getClass().equals(RideOffer.class))
				{
					offer.setTTL(-1);
					offer.setCategory("ride");
				}
				else if (offer.getClass().equals(OldersOffer.class))
				{
					offer.setTTL(GlobalsFunctions.calculateTTL(offer.getPeriod()));
					offer.setCategory("olders");
				}
				else if (offer.getClass().equals(StudentOffer.class))
				{
					offer.setTTL(GlobalsFunctions.calculateTTL(offer.getPeriod()));
					offer.setCategory("student");
				}
				offer.setIsAprroved(false);
				if (offer.getStatus().equals(""))
					offer.setStatus(ConstantVariables.waitingForMatch);
				
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
				if (session!=null)
					session.close();
			} 
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
			{
				if (session!=null)
				session.close();
			} 
			catch (HibernateException e)
			{throw new OfferExceptionHandler("Warnning!! connection did'nt close properly");} 
		}
	}

	@Override
	public void hardDeleteOffer(int offerId, String tableName) throws OfferExceptionHandler 
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
			{
				if (session!=null)
				session.close();
			}
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
			    	offer.setArchive(true);
			    	session.update(offer);
		        	session.getTransaction().commit();
				}
			}
			catch  (HibernateException e) 
			{if (session.getTransaction() != null) session.getTransaction().rollback();}
			finally
			{
				if (session!=null)
				session.close();
			}
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
		          offer = session.createQuery("from " + getTableName(tableName) + " where offerId = " + offerId +" and isArchive = false").getResultList();
		          
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
	    	 try 
	    	 {
	    		 if (session!=null)
					session.close();
	    	 } 
	    	 catch(HibernateException e) {throw new OfferExceptionHandler("Warnning!! connection did'nt close properly");}
	     }
	     return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Offer> getUserOffers(int userId) throws OfferExceptionHandler 
	{
		Session session = null;
		List <Offer> offers = null;

		try
		{
			 initOfferFactory();
			 session = getSession();
	    	 session.beginTransaction();
			
	    	 if (userId>0)
	    		 offers = session.createQuery("from "+ Offer.class.getName() + " offers where offers.userId = " +userId+ " and isArchive = false").getResultList();
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
	    	 try 
	    	 {
	    		 if (session!=null)
					session.close();
	    	 } 
	    	 catch (HibernateException e){throw new OfferExceptionHandler("Warnning!! connection did'nt close properly" + e.getMessage());} 
	    }
	    return null;
	}
	
	public String getRandomOffer(int num, String tableName) throws OfferExceptionHandler 
	{	
		Session session = null;
		List<Integer> idList = null;
		
		List <Offer> offersList = new ArrayList<>();
		
	try{	
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
        /*
        int maxId;
        
        if (Id<num)
        	maxId=Id;
        else
        	maxId=num;
        */
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
	catch (Exception e)
	{
		e.printStackTrace();
	}
	finally {
		if (session!=null)
			session.close();
	}
	return null;
}

	@Override
	public void status(String status, Offer offer) throws OfferExceptionHandler {
		offer.setStatus(status);
		
		Session session = null;
		initOfferFactory();
		session = getSession();
		
		try
		{
			if (session!=null){
	   	  		session.beginTransaction();
	   	  		session.update(offer);
	   	  		session.getTransaction().commit();
			}
		}
		catch (Exception e)
		{
			if (session.getTransaction() !=null) session.getTransaction().rollback();
		}
		finally
		{
			if (session!=null)
				session.close();
		}
			
	}

	@Override
	public void notification(int userId, String subject, String body) throws OfferExceptionHandler, UserExceptionHandler, IOException 
	{
		AppUser user = HibernateUserDAO.getInstance().getUserInfo(userId);
		
		if (user!=null)
		{
			GlobalsFunctions.sendEmail(user.getMail(), subject, body);
			String strUserId = String.valueOf(userId);
			WebSocket.sendMessageToClient(ConstantVariables.newMsgInPrivateZone, strUserId);
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Offer> getAllSpecificOfferTable(String tableName) throws OfferExceptionHandler 
	{
		Session session = null;
		List <Offer> offers = null;
		
		try
		{
	    	String strTableName = getTableName(tableName);
			initOfferFactory();
			session = getSession();
	    	session.beginTransaction();
		
	    	offers = session.createQuery ("from " + strTableName + " as table where table.isArchive = false and table.status like :searchkey").setParameter("searchkey",  "%" + ConstantVariables.waitingForMatch + "%").getResultList();
				
	         if (offers != null && !offers.isEmpty())
	          {
	        	  session.getTransaction().commit();
	        	  if (offers.size() > 0) {
	    		      return offers;	
	    	      }
	          }
	    }
		catch (HibernateException e) 
		  {
		         if (session.getTransaction() != null) session.getTransaction().rollback();
	         throw new OfferExceptionHandler("Offers list not avilable at the moment" + e.getMessage());
	      }
		finally {
	    	 try 
	    	 {
	    		 if (session!=null)
					session.close();
	    	 }
	    	 catch (HibernateException e){
	    		 throw new OfferExceptionHandler("Warnning!! connection did'nt close properly" + e.getMessage());
	    	 } 
	      }
		
	    return null;
	}
	
	@Override
	public List<Offer> getaAllOfferMatches (List<Match> manOffer)
	{
		Session session =null;
		
		List<Offer> offerList = new ArrayList<Offer>();
		
		if (manOffer!=null && manOffer.size()>0)
		try
		{
			for (int i=0; i<manOffer.size(); i++)
			{
				ManualMatchUserApplication manMatchApp = (ManualMatchUserApplication) manOffer.get(i);
				Offer offer = HibernateOfferDAO.getInstance().getOffer(manMatchApp.getOfferId(), manMatchApp.getCategory());
				if (offer!=null && offer.getUserId()!=manMatchApp.getUserId())
				{
					offerList.add(offer);
				}
			}
			return offerList;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{

   		 	if (session!=null)
   			 session.close();
		}
		return null;
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
	
	public Class<?> getTableMapping (String tableName)
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
	
	private static void initOfferFactory ()
	{
		try
		{
			if (offerFactory==null)
			{
				offerFactory = GlobalsFunctions.initSessionFactory(offerFactory, "hibernateOffer.cfg.xml");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
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
		offer.setPeriod(updateOffer.getPeriod());
		offer.setPeriodic(updateOffer.getPeriodic());
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
	
}

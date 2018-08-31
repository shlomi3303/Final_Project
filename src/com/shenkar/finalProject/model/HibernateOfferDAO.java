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
import com.shenkar.finalProject.classes.AppUser;
import com.shenkar.finalProject.classes.HandymanOffer;
import com.shenkar.finalProject.classes.ManualMatchUserApplication;
import com.shenkar.finalProject.classes.Match;
import com.shenkar.finalProject.classes.Offer;
import com.shenkar.finalProject.classes.OldersOffer;
import com.shenkar.finalProject.classes.RideOffer;
import com.shenkar.finalProject.classes.StudentOffer;
import com.shenkar.finalProject.globals.ConstantVariables;
import com.shenkar.finalProject.globals.GlobalsFunctions;
import com.shenkar.finalProject.globals.WebSocket;
import com.shenkar.finalProject.model.interfaces.IOfferDAO;

@SuppressWarnings("unchecked")
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
			session = GlobalsFunctions.getSession(offerFactory);
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
			session = GlobalsFunctions.getSession(offerFactory);
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
				session = GlobalsFunctions.getSession(offerFactory);
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
		    	 try 
		    	 {
		    		 if (session!=null)
						session.close();
		    	 } 
		    	 catch (HibernateException e){throw new OfferExceptionHandler("Warnning!! connection did'nt close properly" + e.getMessage());} 
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
				session = GlobalsFunctions.getSession(offerFactory);
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
		    	 try 
		    	 {
		    		 if (session!=null)
						session.close();
		    	 } 
		    	 catch (HibernateException e){throw new OfferExceptionHandler("Warnning!! connection did'nt close properly" + e.getMessage());} 
		    }
		}
	}

	@Override
	public Offer getOffer(int offerId, String tableName) throws OfferExceptionHandler 
	{
		 Session session = null;
		 List<Offer> offer = null;
		 
	     try
	     {
	    	  String strTableName = getTableName(tableName);
	    	  if (!strTableName.equals(""))
	    	  {
		    	  initOfferFactory();
		    	  session = GlobalsFunctions.getSession(offerFactory);
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
	     }
	     catch (HibernateException e) 
	     {
	         if (session.getTransaction() != null) 
	        	 session.getTransaction().rollback();
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

	@Override
	public List<Offer> getUserOffers(int userId) throws OfferExceptionHandler 
	{
		Session session = null;
		List <Offer> offers = null;

		try
		{
			 initOfferFactory();
			 session = GlobalsFunctions.getSession(offerFactory);
	    	 session.beginTransaction();
			
	    	 if (userId>0)
	    		 offers = session.createQuery("from "+ Offer.class.getName() + " offers where offers.userId = " +userId+ " and isArchive = false").getResultList();
	    	 else if(userId==-1)
	    		 offers = session.createQuery ("from " + Offer.class.getName() + " as table where table.isArchive = false and table.status like :key").setParameter("key",  "%" + ConstantVariables.waitingForMatch + "%").getResultList();
	    	 
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
	
	@SuppressWarnings("deprecation")
	public String getRandomOffer(int num, String tableName, int userId) throws OfferExceptionHandler 
	{	
		Session session = null;
		List<Integer> idList = null;
		List <Offer> offersList = new ArrayList<>();
	    List <Integer> list = new ArrayList<Integer>();
		
	    CriteriaBuilder builder;
		Root<?> root;
		int maxId=0;
		CriteriaQuery<Integer> criteriaQuery;
	    
		try
		{	
			initOfferFactory();
			session = GlobalsFunctions.getSession(offerFactory);
	   	  	session.beginTransaction();
	   	  	
			int count = ((Long)session.createQuery("select count(*) from " +  getTableName(tableName) + " as table where table.isArchive = false and table.status = '" + ConstantVariables.waitingForMatch + "'").uniqueResult()).intValue();
			
			if (userId>0)
			{
				AppUser user = HibernateUserDAO.getInstance().getUserInfo(userId);
				if (user!=null)
				{
					String city = user.getCity();
					List <Offer> offerByUserLocation = session.createQuery("from " + getTableName(tableName) + " as table where table.city = '" + city +"' and table.isArchive = false and table.status = '" + ConstantVariables.waitingForMatch + "'").getResultList();
					
					if (offerByUserLocation.size()<num)
					{
						builder = session.getCriteriaBuilder();
				   	  	criteriaQuery = builder.createQuery(Integer.class);
				   	  	root = criteriaQuery.from(getTableMapping(tableName));
				        criteriaQuery.select(builder.max(root.get("offerId")));
				        idList = session.createQuery(criteriaQuery).getResultList();
				        session.getTransaction().commit();
				        if (idList!=null && idList.size()>0)
				        {
				        	maxId = idList.get(0);
					        list = new ArrayList<Integer>();
							
							for (Offer offer: offerByUserLocation)
								list.add(offer.getOfferId());
							
							int tempCount = count;
							
							while(offerByUserLocation.size()!=num)
							{
								int index = new Random().nextInt(maxId+1);
					        	if (list.contains(Integer.valueOf(index)) && (tempCount/count)>1/2)
					        		continue;
					        	else
					        	{
						        	Offer offer = getOffer(index, tableName);
						        	if (offer != null && offer.getStatus().equals(ConstantVariables.waitingForMatch))
						        	{
						        		offerByUserLocation.add(offer);
						        		list.add(offer.getOfferId());
						        		tempCount--;
						        		if (offerByUserLocation.size()==num)
						        			break;
						        	}
						        }
							}
					        return new Gson().toJson(offerByUserLocation);
				        }
					}
					else
					{
						Random random = new Random();
			   	    	list = new ArrayList<Integer>();
			   	    	List<Offer> offerlist = new ArrayList<Offer>();
			   	    	int i=0;
			   	    	while (i!=offerByUserLocation.size())
			   	    	{
			   	    		int index = random.nextInt(offerByUserLocation.size());
			   	    		if (list.contains(Integer.valueOf(index)))
			   	    			continue;
			   	    		else
			   	    		{
			   	    			offerlist.add(offerByUserLocation.get(index));
			   	    			list.add(index);
			   	    			i++;
			   	    		}
			   	    	}
			   	    	return new Gson().toJson(offerlist);
					}
				}
			}
			
			else if (userId==0)
			{
				Random random = new Random();
	   	    	list = new ArrayList<Integer>();
				builder = session.getCriteriaBuilder();
			  	criteriaQuery = builder.createQuery(Integer.class);
			  	root = criteriaQuery.from(getTableMapping(tableName));
			  	criteriaQuery.select(builder.max(root.get("offerId")));
			  	idList = session.createQuery(criteriaQuery).getResultList();
			  	session.getTransaction().commit();
			  	maxId = idList.get(0);
			  	
			  	if (count>num)
			  	{
			  		int i=0;
				    while (i!=num)
				    {
				    	int index = random.nextInt(maxId+1);
				    	if (list.contains(Integer.valueOf(index)) && list.size()!=count)
				    		continue;
				    	else
				    	{
					        Offer offer = getOffer(index, tableName);
					        if (offer != null && offer.getStatus().equals(ConstantVariables.waitingForMatch))
					        {
				        		offersList.add(offer);
				        		list.add(index);
				        		i++;
					        }
				    	}
				    }
				    return new Gson().toJson(offersList);
			  	}
			  	else
			  	{
			  		offersList = getAllSpecificOfferTable(tableName);
			  		if (offersList!=null)
			  		{
				  		List<Offer> toSend = new ArrayList<Offer>();
				  		outloop:
				  		while (toSend.size()!=num)
				  		{
				  			for (int i=0;i<offersList.size();i++)
				  			{
				  				toSend.add(offersList.get(i));
				  				if (toSend.size()==num)
				  					break outloop;
				  			}
				  		}
					    return new Gson().toJson(toSend);
			  		}
			  		else
			  			return null;
			  	}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
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

	@Override
	public void status(String status, Offer offer) throws OfferExceptionHandler 
	{
		offer.setStatus(status);
		
		Session session = null;
		initOfferFactory();
		session = GlobalsFunctions.getSession(offerFactory);
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
	    	 try 
	    	 {
	    		 if (session!=null)
					session.close();
	    	 } 
	    	 catch (HibernateException e){throw new OfferExceptionHandler("Warnning!! connection did'nt close properly" + e.getMessage());} 
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

	@Override
	public List<Offer> getAllSpecificOfferTable(String tableName) throws OfferExceptionHandler 
	{
		Session session = null;
		List <Offer> offers = null;
		
		try
		{
	    	String strTableName = getTableName(tableName);
			initOfferFactory();
			session = GlobalsFunctions.getSession(offerFactory);
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
		    if (session.getTransaction() != null) 
		    	session.getTransaction().rollback();
	        throw new OfferExceptionHandler("Offers list not avilable at the moment" + e.getMessage());
		}
		finally 
		{
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
	public List<Offer> getaAllOfferMatches (List<Match> manOffer) throws OfferExceptionHandler
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
	    	 try 
	    	 {
	    		 if (session!=null)
					session.close();
	    	 } 
	    	 catch (HibernateException e){throw new OfferExceptionHandler("Warnning!! connection did'nt close properly" + e.getMessage());} 
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
	
	private void updateSameVarOffer(Offer offer, Offer updateOffer)
	{
		offer.setPeriod(updateOffer.getPeriod());
		offer.setGender(updateOffer.getGender());
		offer.setLanguage(updateOffer.getLanguage());
		offer.setImg(updateOffer.getImg());
		offer.setDescription(updateOffer.getDescription());
		offer.setTitle(updateOffer.getTitle());
		offer.setCity(updateOffer.getCity());
		offer.setStreet(updateOffer.getStreet());
		offer.setHouseNumber(updateOffer.getHouseNumber());
		offer.setLatitude(updateOffer.getLatitude());
		offer.setLongitude(updateOffer.getLongitude());
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
		ride.setDestCity(updateRide.getDestCity());
		ride.setDestStreet(updateRide.getDestStreet());
		ride.setDestHouseNum(updateRide.getDestHouseNum());
		
		ride.setDestlatitude(updateRide.getDestlatitude());
		ride.setDestLongitude(updateRide.getDestLongitude());
		
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

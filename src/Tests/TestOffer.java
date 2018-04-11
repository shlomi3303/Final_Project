package Tests;

import java.util.Date;

import com.shenkar.finalProject.model.Application;
import com.shenkar.finalProject.model.HibernateOfferDAO;
import com.shenkar.finalProject.model.Offer;
import com.shenkar.finalProject.model.OfferExceptionHandler;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TestOffer {

	public static void main(String[] args) throws ParseException, OfferExceptionHandler 
	{
		//offer #1
		//int offerId =234;
		int userId = 207689323;
		//SimpleDateFormat formatter = new SimpleDateFormat("d-MMM-yyyy ,HH:mm:ss aaa");
		//String testDate = "09-May-2015,23:10:14 PM";
		//java.util.Date period =  formatter.parse(testDate);
		//System.out.println(period);
	    String testDateString2 = "02/04/2014 23:37";
	    DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	    
	    Date period  = (Date) df.parse(testDateString2);
	    //Date sd = (Date) df.parseObject(testDateString2);
        //System.out.println("Date: " + d2);
        //System.out.println("Date in dd/MM/yyyy HH:mm format is: " + df.format(d2));

		String location = "ראשון לציון";
		String category = "קשישים";
		Date periodic = period;
		boolean urgency = true;
		int TTL = 5;
		String description = "להגיע מהר";
		String userLocation = "תל אביב";
		boolean isAprroved = false;
		
		
		Offer offer1 = new Offer(userId, period, location, category, periodic,
				urgency, TTL, description, "waiting", userLocation, isAprroved,"male", "english", "high", "math");
		HibernateOfferDAO.getInstance().createOffer(offer1);
		/*
		/
		//Offer upda = new Offer(offer1.getId(),offerId, userId, period, "רחובות", category, periodic,
				//urgency, TTL, description, userLocation, isAprroved);
		//HibernateOfferDAO.getInstance().editOffer(upda);
		
		
		String userId1 = "9076893234";
		//SimpleDateFormat formatter = new SimpleDateFormat("d-MMM-yyyy ,HH:mm:ss aaa");
		//String testDate = "09-May-2015,23:10:14 PM";
		//java.util.Date period =  formatter.parse(testDate);
		//System.out.println(period);
	    String testDateString21 = "02/04/2014 23:37";
	    DateFormat df1 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	    
	    Date period1  = (Date) df1.parse(testDateString21);
	    //Date sd = (Date) df.parseObject(testDateString2);
        //System.out.println("Date: " + d2);
        //System.out.println("Date in dd/MM/yyyy HH:mm format is: " + df.format(d2));

		String location1 = "ראשון לציון";
		String category1 = "קשישים";
		Date periodic1 = period1;
		boolean urgency1 = true;
		int TTL1 = 5;
		String description1 = "להגיע מהר";
		String userLocation1 = "רמת גן";
		boolean isAprroved1 = false;
		/*
		Offer offer2 = new Offer(userId, period1, location1, category1, periodic1,
				urgency1, TTL1, description1, userLocation1, isAprroved1);
		Offer upoffer2 = new Offer(userId, period1, "רמת גן", "טרמפ", periodic1,
				urgency1, TTL1, description1, userLocation1, isAprroved1);
		 */
		//HibernateOfferDAO.getInstance().createOffer(offer2);
		
		//System.out.println("offer 1 id: " + offer1.getId());
		//System.out.println("offer 2 id: " + offer1.getId());
		//HibernateOfferDAO.getInstance().deleteOffer(2);
		//java.util.List<Offer> offers = null;
		//offers =  HibernateOfferDAO.getInstance().getOffers(userId);
		//System.out.println(offers.get(0).getLocation());
		//System.out.println(offers.get(1).getOfferId());
	}
	
	
	
	private String modifyDateLayout(String inputDate) throws ParseException{
	    java.util.Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a").parse(inputDate);
	    return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(date);
	}

}

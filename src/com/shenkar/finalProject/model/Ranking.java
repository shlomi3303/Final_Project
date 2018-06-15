package com.shenkar.finalProject.model;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.*;

@SuppressWarnings("unchecked")
public class Ranking {
		
	enum student {homeWorks, testStudy, practice;}
	enum olders {shooping, cooking, conversation, escort;}
	enum handyman {colorCorrections, furniture, generalHangingWorks, hangingOfLightFixtures, treatmentSocketsAndPowerPoints;}
	enum fieldOfStudy {check;}
	enum educationLevel {check;}

	
	public static List <Integer> rideRanking (RideApplication app, List <RideOffer> listOffers) throws ParseException
	{
		HashMap<Integer, Double> ranking = new HashMap<Integer, Double>();
		
		for (int i=0; i<listOffers.size(); i++)
		{
			double tempRanking=0;
			RideOffer offer = listOffers.get(i);
			
			//check if the offer is in the refuse list of the application
			if (app.getRefuseList().contains(offer.getOfferId()))
				continue;
			
			int con=0;
			Date appStart = app.getPeriod();
			Date appEnd = app.getEndPeriod();
			Date offerStart = listOffers.get(i).getPeriod();
			Date offerEnd = listOffers.get(i).getEndPeriod();
			
			DateTime datatime = new DateTime(appStart);
			
			if (con==0)
				continue;
			
			if ( (appStart.compareTo(offerStart)==0) && (appEnd.compareTo(offerEnd)==0))
			{
				tempRanking+=1*0.3;
			}
			else if (!(offerStart.before(appStart) || offerStart.after(appEnd)))
			{
				tempRanking+=0.9*0.3;
			}
			else if (con==3)
			{
				tempRanking+=0.7*0.3;
			}
			
			//break point to check if there is a point to continue
			if (tempRanking==0)
				continue;
			
		}
		
		return sortList(ranking);		
	}
	
	
	public static List <Integer> oldersRanking (OldersApplication app, List <OldersOffer> listOffers)
	{
		HashMap<Integer, Double> ranking = new HashMap<Integer, Double>();
		
		olders oldersEnum = null;
		
		if ( (app.getShopping()))
			oldersEnum=olders.shooping;
		else if (app.getCooking())
			oldersEnum=olders.cooking;
		else if (app.getConversation())
			oldersEnum=olders.conversation;
		else if (app.getEscortedAged())
			oldersEnum=olders.escort;
		
		for (int i=0; i>listOffers.size(); i++)
		{
			double tempRanking = 0;
			OldersOffer offer = listOffers.get(i);
			boolean flag = false;
			
			//check if the offer is in the refuse list of the application
			if (app.getRefuseList().contains(offer.getOfferId()))
				continue;
			
			switch(oldersEnum)
			{
				case shooping:
					if ((!offer.getShopping()))
						flag=true;
					break;
				case cooking:
					if ((!offer.getCooking()))
						flag=true;
					break;
				case conversation:
					if (!offer.getConversation() && app.getLanguage().equals(offer.getLanguage()))
						flag=true;
					break;
				case escort:
					if (!offer.getEscortedAged() && app.getLanguage().equals(offer.getLanguage()))
						flag=true;
					break;
					default: flag=false;
			}
			if(!flag)
				continue;
			
			//check that the gender is the same or nevermind
			if (!app.getGender().equals(offer.getGender()))
				continue;
			
			//ranking time
			LocalDate localApp = app.getPeriod().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate localOffer = offer.getPeriod().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			 
			int days = Period.between(localApp, localOffer).getDays();
			int mon = Period.between(localApp,localOffer).getMonths();			
			int year = Period.between(localApp,localOffer).getYears();
			
			if ((days==0 && mon==0 && year==0))
			{
				DateTime datatimeApp = new DateTime(app.getPeriod());
				DateTime datatimeOffer = new DateTime(offer.getPeriod());

				org.joda.time.Period p = new org.joda.time.Period(datatimeApp, datatimeOffer);
				int hours = Math.abs(p.getHours());
				int minutes = p.getMinutes();
				
				double sum = ((hours*60) + minutes)/60;
				
				double check = 0.5-(0.1)*sum;
				
				if (check>0)
					tempRanking += check;

			}
			else
				continue;
				 
			//ranking language
			if (app.getLanguage().equals(offer.getLanguage()))
					tempRanking += 0.1;
			
			//ranking distance
			double dist = distance(Double.parseDouble(app.getLatitude()), Double.parseDouble(offer.getLatitude()), Double.parseDouble(app.getLongitude()), Double.parseDouble(offer.getLongitude()), 1, 1);
			
			if (dist<=0.5)
				tempRanking+=0.4;
			else
			{
				double check = 0.4-((dist/0.3)*0.005);
				if (check>0)
					tempRanking += check;
			}
			
			if (tempRanking>=0.50)
				ranking.put(offer.getOfferId(), tempRanking);
		}
		
		return sortList(ranking);		
	}
	
	public static List <Integer> studentRanking (StudentApplication app, List <StudentOffer> listOffers)
	{
		HashMap<Integer, Double> ranking = new HashMap<Integer, Double>();
		
		student studentEnum = null;
		
		if ( (app.getHomeWorks()))
			studentEnum=student.homeWorks;
		else if (app.getPractice())
			studentEnum=student.practice;
		else if (app.getTestStudy())
			studentEnum=student.testStudy;
		
		for (int i=0; i>listOffers.size(); i++)
		{
			boolean flag = false;
			double tempRanking = 0;
			StudentOffer offer = listOffers.get(i);
			
			//check if the offer is in the refuse list of the application
			if (app.getRefuseList().contains(offer.getOfferId()))
				continue;
			
			if (!app.getGender().equals(offer.getGender()))
				continue;
			
			switch(studentEnum)
			{
				case homeWorks:
					if ((!offer.getHomeWorks()))
						flag=true;
					break;
				case practice:
					if ((!offer.getPractice()))
						flag=true;
					break;
				case testStudy:
					if (!offer.getTestStudy())
						flag=true;
					break;
								
				default: flag=false;
			}
			if(!flag)
				continue;
		
			//check that the gender is the same never mind
			if (!app.getGender().equals(offer.getGender()))
				tempRanking+=0.1;
			
			//ranking time
			LocalDate localApp = app.getPeriod().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate localOffer = offer.getPeriod().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			 
			int days = Period.between(localApp, localOffer).getDays();
			int mon = Period.between(localApp,localOffer).getMonths();			
			int year = Period.between(localApp,localOffer).getYears();
			
			if ((days==0 && mon==0 && year==0))
			{
				DateTime datatimeApp = new DateTime(app.getPeriod());
				DateTime datatimeOffer = new DateTime(offer.getPeriod());

				org.joda.time.Period p = new org.joda.time.Period(datatimeApp, datatimeOffer);
				int hours = Math.abs(p.getHours());
				int minutes = p.getMinutes();
				
				double sum = ((hours*60) + minutes)/60;
				
				double check = 0.3-(0.1)*sum;
				
				if (check>0)
					tempRanking += check;

			}
			else
				continue;
			
			//ranking distance
			double dist = distance(Double.parseDouble(app.getLatitude()), Double.parseDouble(offer.getLatitude()), Double.parseDouble(app.getLongitude()), Double.parseDouble(offer.getLongitude()), 1, 1);
			
			if (dist<=0.5)
				tempRanking+=0.3;
			else
			{
				double check = 0.3-((dist/0.3)*0.005);
				if (check>0)
					tempRanking += check;
			}
			
			if (tempRanking>=0.35)
				ranking.put(offer.getOfferId(), tempRanking);
			
		}	
		
		return sortList(ranking);
	}
	

	public static List <Integer> handymanRanking (HandymanApplication app, List <HandymanOffer> listOffers)
	{
		HashMap<Integer, Double> ranking = new HashMap<Integer, Double>();

		handyman handymanEnum = null;
		
		if ( (app.getColorCorrections()))
			handymanEnum =handyman.colorCorrections;
		else if (app.getFurniture())
			handymanEnum=handyman.furniture;
		else if (app.getGeneralHangingWorks())
			handymanEnum=handyman.generalHangingWorks;
		else if (app.getHangingOfLightFixtures())
			handymanEnum = handyman.hangingOfLightFixtures;
		else if (app.getTreatmentSocketsAndPowerPoints())
			handymanEnum = handyman.treatmentSocketsAndPowerPoints;
		
		for (int i=0; i>listOffers.size(); i++)
		{
			boolean flag = false;
			HandymanOffer offer = listOffers.get(i);
			double tempRanking = 0;
			
			//check if the offer is in the refuse list of the application
			if (app.getRefuseList().contains(offer.getOfferId()))
				continue;
			
			switch(handymanEnum)
			{
				case colorCorrections:
					if ((!offer.getColorCorrections()))
						flag=true;
					break;
				case furniture:
					if ((!offer.getFurniture()))
						flag=true;
					break;
				case generalHangingWorks:
					if (!offer.getGeneralHangingWorks())
						flag=true;
					break;
				case hangingOfLightFixtures:
					if (!offer.getHangingOfLightFixtures())
						flag=true;
					break;
							
				case treatmentSocketsAndPowerPoints:
					if (!offer.getTreatmentSocketsAndPowerPoints())
						flag=true;
					break;
					
					
				default: flag=false;
			}
			if(!flag)
				continue;
			
			//check that the gender is the same never mind
			if (!app.getGender().equals(offer.getGender()))
				tempRanking+=0.1;
			
			//ranking time
			LocalDate localApp = app.getPeriod().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate localOffer = offer.getPeriod().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			 
			int days = Period.between(localApp, localOffer).getDays();
			int mon = Period.between(localApp,localOffer).getMonths();			
			int year = Period.between(localApp,localOffer).getYears();
			
			if ((days==0 && mon==0 && year==0))
			{
				DateTime datatimeApp = new DateTime(app.getPeriod());
				DateTime datatimeOffer = new DateTime(offer.getPeriod());

				org.joda.time.Period p = new org.joda.time.Period(datatimeApp, datatimeOffer);
				int hours = Math.abs(p.getHours());
				int minutes = p.getMinutes();
				
				double sum = ((hours*60) + minutes)/60;
				
				double check = 0.4-(0.1)*sum;
				
				if (check>0)
					tempRanking += check;

			}
			else
				continue;
				 
			//ranking language
			if (app.getLanguage().equals(offer.getLanguage()))
				tempRanking += 0.05;
			
			//ranking distance
			double dist = distance(Double.parseDouble(app.getLatitude()), Double.parseDouble(offer.getLatitude()), Double.parseDouble(app.getLongitude()), Double.parseDouble(offer.getLongitude()), 1, 1);
			
			if (dist<=0.5)
				tempRanking+=0.45;
			else
			{
				double check = 0.45-((dist/0.3)*0.005);
				if (check>0)
					tempRanking += check;
			}
			
			if (tempRanking>=0.45)
				ranking.put(offer.getOfferId(), tempRanking);
		}
		
		return sortList(ranking);		
	}

	private static List<Integer> sortList(HashMap<Integer, Double> ranking)
	{
		
		Object[] a = ranking.entrySet().toArray();
			
		Arrays.sort(a, new Comparator() 
		{
			public int compare(Object o1, Object o2) 
			{
			        return ((Map.Entry<Integer, Double>) o2).getValue()
			                   .compareTo(((Map.Entry<Integer, Double>) o1).getValue());
			}
		});

		List<Integer> list = new ArrayList<Integer>();
		
		for (int i=0; i<10; i++) {
		    list.add(( (Map.Entry<Integer, Double>) a[i]).getKey());
		}
		
		return list;		
	}
	
	public static double distance(double lat1, double lat2, double lon1,
	        double lon2, double el1, double el2) 
	{

	    final int R = 6371; // Radius of the earth

	    double latDistance = Math.toRadians(lat2 - lat1);
	    double lonDistance = Math.toRadians(lon2 - lon1);
	    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    
	    double distance = R * c; // convert to kilometers

	    double height = el1 - el2;

	    distance = Math.pow(distance, 2) + Math.pow(height, 2);

	    return Math.sqrt(distance);
	}

}

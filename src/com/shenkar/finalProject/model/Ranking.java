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

import com.shenkar.finalProject.classes.HandymanApplication;
import com.shenkar.finalProject.classes.HandymanOffer;
import com.shenkar.finalProject.classes.OldersApplication;
import com.shenkar.finalProject.classes.OldersOffer;
import com.shenkar.finalProject.classes.RideApplication;
import com.shenkar.finalProject.classes.RideOffer;
import com.shenkar.finalProject.classes.StudentApplication;
import com.shenkar.finalProject.classes.StudentOffer;

@SuppressWarnings("unchecked")
public class Ranking {
	
	//const strings of education level
	public static final String ELEMENTRY= "ביה\"ס יסודי";
	public static final String MIDDLE= "חטיבת ביניים ";
	public static final String HIGH= "חטיבה עליונה";
	public static final String COLLEGE= "סטודנט";
	public static final String GRADUATE= "בוגר תואר";
	//const string of field of studies
	public static final String CIVICS= "אזרחות";
	public static final String ENGLISH= "אנגלית";
	public static final String HISTORY= "היסטוריה";
	public static final String LANGUAGE= "לשון";
	public static final String MATH= "מתמטיקה";
	public static final String LITERATURE="ספרות";
	public static final String BIBLE= "תנ\"ך";
	//const string for never mind case
	public static final String NEVERMIND = "לא משנה";
	
	enum student {homeWorks, testStudy, practice;}
	enum olders {shooping, cooking, conversation, escort;}
	enum handyman {colorCorrections, furniture, generalHangingWorks, hangingOfLightFixtures, treatmentSocketsAndPowerPoints;}
	enum educationLevel {elementary, middle, high, college, graduate;}
	enum fieldOfStudy {civics, english, history, language, math, literature, bible;}
	
	public static List <Integer> rideRanking (RideApplication app, List <RideOffer> listOffers) throws ParseException
	{
		HashMap<Integer, Double> ranking = new HashMap<Integer, Double>();
		
		System.out.println("handkling with application ride number: " + app.getApplicationID());
		
		for (int i=0; i<listOffers.size(); i++)
		{
			double tempRanking=0;
			RideOffer offer = listOffers.get(i);
			System.out.println("handling with ride offer number: " + offer.getOfferId());

			//check if the offer is in the refuse list of the application
			if (app.getRefuseList().contains(Integer.valueOf(offer.getOfferId())))
				continue;
			
			Date appStart = app.getPeriod();
			Date appEnd = app.getEndPeriod();
			Date offerStart = listOffers.get(i).getPeriod();
			Date offerEnd = listOffers.get(i).getEndPeriod();
			
			DateTime appBegin = new DateTime(appStart);
			DateTime appFinish = new DateTime(appEnd);
			DateTime offerBegin = new DateTime(offerStart);
			DateTime offerFinish = new DateTime(offerEnd);
			
			Interval interval = new Interval( appBegin, appFinish );
			Interval interval2 = new Interval( offerBegin, offerFinish );
			
			if ( (appStart.compareTo(offerStart)==0) && (appEnd.compareTo(offerEnd)==0))
			{
				tempRanking+=1*0.48;	
				System.out.println("the same time, time ranking is: " + tempRanking);
			}
			
			else if ((interval.overlaps( interval2 ))||( interval2.overlaps( interval)))
			{
				tempRanking+=1*0.48;
				System.out.println("over lap, temp ranking is: " + tempRanking);
			}
			
			else if (offerEnd.before(appStart))
			{
				org.joda.time.Period p = new org.joda.time.Period(offerFinish, appBegin);
				int hours = Math.abs(p.getHours());
				int minutes = p.getMinutes();
				int days = p.getDays();
				
				if (days==0)
				{
					double sum = ((hours*60) + minutes)/60;
				
					double check = 0.48-(0.1)*sum;
				
					if (check>0)
					{
						tempRanking += check;
					}
				}
				else
					continue;
			}
			else if (appEnd.before(offerStart))
			{
				org.joda.time.Period p = new org.joda.time.Period(appFinish, offerBegin);
				int hours = Math.abs(p.getHours());
				int minutes = p.getMinutes();
				int days = p.getDays();
				
				if (days==0)
				{
					double sum = ((hours*60) + minutes)/60;
					double check = 0.48-(0.1)*sum;
				
					if (check>0)
					{
						tempRanking += check;
					}
				}
				else
					continue;
			}
			//break point to check if there is no match
			if (tempRanking==0)
			{
				System.out.println("break out, the offer is not relevent in the time");
				continue;
			}
			
			//ranking language
			if (app.getLanguage().equals(offer.getLanguage())||app.getLanguage().equals(NEVERMIND))
					tempRanking += 0.02;
			
			//check that the gender is the same or nevermind
			if (app.getGender().equals(offer.getGender())||app.getGender().equals(NEVERMIND))
					tempRanking += 0.02;
			
			//ranking distance between pickup points.
			double dist = distance(Double.parseDouble(app.getLatitude()), Double.parseDouble(offer.getLatitude()), Double.parseDouble(app.getLongitude()), Double.parseDouble(offer.getLongitude()), 1, 1);
			
			if (dist<=2)
			{
				tempRanking+=0.27;
				System.out.println("full temp ranking in pickup distance, temp ranking is: " + tempRanking);
			}
			else
			{
				double check = 0.27-(((dist-2)/0.5)*0.04);
				if (check>0){
					tempRanking += check;
					System.out.println("partial ranking in pickup distance: " + check + " the sum of temp ranking is: " + tempRanking);
				}
			}
			
			//ranking distance between drop off points.
			double dist2 = distance(Double.parseDouble(app.getDestlatitude()), Double.parseDouble(offer.getDestlatitude()), Double.parseDouble(app.getDestLongitude()), Double.parseDouble(offer.getDestLongitude()), 1, 1);
			
			if (dist2<=4){
				tempRanking+=0.19;
				System.out.println("full temp ranking in drop off distance, temp ranking is: " + tempRanking);
			}
			else
			{
				double check = 0.19-(((dist2-4)/0.5)*0.028);
				if (check>0){
					tempRanking += check;
					System.out.println("partial ranking in drop off distance, temp ranking is: " + tempRanking);
				}
			}
			
			System.out.println("the ranking for offer id: " + offer.getOfferId() + " is: " + tempRanking);
			if (tempRanking>=0.58)
				ranking.put(offer.getOfferId(), tempRanking);
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
		
		for (int i=0; i<listOffers.size(); i++)
		{
			double tempRanking = 0;
			OldersOffer offer = listOffers.get(i);
			boolean flag = false;
			
			//check if the offer is in the refuse list of the application
			if (app.getRefuseList().contains(Integer.valueOf(offer.getOfferId())))
				continue;
			
			switch(oldersEnum)
			{
				case shooping:
					if ((offer.getShopping()==false)){
						flag=true;
					}
					break;
				case cooking:
					if ((!offer.getCooking())){
						flag=true;
					}
					break;
				case conversation:
					if (offer.getConversation()==false || (app.getLanguage().equals(offer.getLanguage())==false && !app.getLanguage().equals(NEVERMIND)))
					{
						flag=true;
					}
					break;
				case escort:
					if (offer.getEscortedAged()==false || (app.getLanguage().equals(offer.getLanguage())==false && !app.getLanguage().equals(NEVERMIND))){
						flag=true;
					}
					break;
					default: flag=false;
			}
			if(flag)
				continue;
			
			//check that the gender is the same or nevermind
			if (!(app.getGender().equals(offer.getGender())||app.getGender().equals(NEVERMIND)))
			{	
				continue;
			}
			
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
				
				if (check>0){
					System.out.println("time for temp ranking is: " + check);
					tempRanking += check;
				}
			}
			else
			{
				System.out.println("not the same day");
				continue;
			}
			//ranking language
			if (app.getLanguage().equals(offer.getLanguage()))
					tempRanking += 0.1;
			
			//ranking distance
			if (app.getLatitude()!=null && app.getLongitude()!=null && offer.getLatitude()!=null && offer.getLongitude()!=null)
			{
				double dist = distance(Double.parseDouble(app.getLatitude()), Double.parseDouble(offer.getLatitude()), Double.parseDouble(app.getLongitude()), Double.parseDouble(offer.getLongitude()), 1, 1);
				
				if (dist<=1.5)
				{
					System.out.println("full distance, temp ranking is: " + 0.4);
					tempRanking+=0.4;
				}
				else
				{
					double check = 0.4-(((dist-1.5)/0.3)*0.005);
					if (check>0)
					{	System.out.println("temp ranking is: " + check);
						tempRanking += check;
					}
				}
			}
			else
				continue;
			System.out.println("the rank for offer: " + offer.getOfferId() + " is: " + tempRanking);

			if (tempRanking>=0.50){
				System.out.println("the rank for offer: " + offer.getOfferId() + " is: " + tempRanking);
				ranking.put(offer.getOfferId(), tempRanking);
			}
		}
		return sortList(ranking);		
	}
	
	public static List <Integer> studentRanking (StudentApplication app, List <StudentOffer> listOffers)
	{
		HashMap<Integer, Double> ranking = new HashMap<Integer, Double>();
		fieldOfStudy appField=null;
		educationLevel appLevel=null;
		
		//assign the enum of the application education level	
		if (app.getEducationLevel().equals(ELEMENTRY)){
			appLevel= educationLevel.elementary;
		}
		else if (app.getEducationLevel().equals(MIDDLE)){
			appLevel=educationLevel.middle;
		}
		
		if (app.getEducationLevel().equals(HIGH)){
			appLevel= educationLevel.high;
		}
		
		else if (app.getEducationLevel().equals(COLLEGE)){
			appLevel=educationLevel.college;
		}
		
		if (app.getEducationLevel().equals(GRADUATE)){
			appLevel= educationLevel.graduate;
		}
		
		System.out.println("app field before the assign:" + appField);
		//assign the enum of the application field of study		
		if (app.getFieldOfStudy().equals(CIVICS)){
			appField= fieldOfStudy.civics;
		}
		
		else if (app.getFieldOfStudy().equals(ENGLISH)){
			appField=fieldOfStudy.english;
		}
		
		if (app.getFieldOfStudy().equals(HISTORY)){
			appField= fieldOfStudy.history;
		}
		else if (app.getFieldOfStudy().equals(LANGUAGE)){
			appField=fieldOfStudy.language;
		}
		
		if (app.getFieldOfStudy().equals(MATH)){
			appField= fieldOfStudy.math;
		}
		else if (app.getFieldOfStudy().equals(LITERATURE)){
			appField=fieldOfStudy.literature;
		}
		else if (app.getFieldOfStudy().equals(BIBLE)){
			appField=fieldOfStudy.bible;
		}

		student studentEnum = null;
		
		if ( (app.getHomeWorks()))
			studentEnum=student.homeWorks;
		else if (app.getPractice())
			studentEnum=student.practice;
		else if (app.getTestStudy())
			studentEnum=student.testStudy;
		
		for (int i=0; i<listOffers.size(); i++)
		{
			boolean flag = false;
			double tempRanking = 0;
			StudentOffer offer = listOffers.get(i);
			fieldOfStudy offerField = null;
			educationLevel offerLevel = null;
			
			//check if the offer is in the refuse list of the application
			if (app.getRefuseList().contains(Integer.valueOf(offer.getOfferId())))
				continue;
			
			if (!(app.getGender().equals(offer.getGender())||app.getGender().equals(NEVERMIND)))
				continue;
			
			if (!(app.getLanguage().equals(offer.getLanguage())||app.getLanguage().equals(NEVERMIND)))
				continue;
			
			//assign the enum of the offer field of study		
			if (offer.getFieldOfStudy().equals(CIVICS))
				offerField=fieldOfStudy.civics;
			else if (app.getFieldOfStudy().equals(ENGLISH))
				offerField=fieldOfStudy.english;
			
			else if (app.getFieldOfStudy().equals(HISTORY))
				offerField= fieldOfStudy.history;
			else if (offer.getFieldOfStudy().equals(LANGUAGE))
				offerField=fieldOfStudy.language;
			
			else if (offer.getFieldOfStudy().equals(MATH)){
				offerField= fieldOfStudy.math;
				System.out.println("the field of study in offer math " + offerField);
			}
			else if (offer.getFieldOfStudy().equals(LITERATURE))
				offerField=fieldOfStudy.literature;
			else if (offer.getFieldOfStudy().equals(BIBLE))
				offerField=fieldOfStudy.bible;
			
			//assign the enum of the Offer education level	
			if (offer.getEducationLevel().equals(ELEMENTRY)){
				offerLevel= educationLevel.elementary;
			}
			else if (offer.getEducationLevel().equals(MIDDLE)){
				offerLevel=educationLevel.middle;
			}
			else if (offer.getEducationLevel().equals(HIGH))
				offerLevel= educationLevel.high;
			else if (offer.getEducationLevel().equals(COLLEGE))
				offerLevel=educationLevel.college;
			else if (offer.getEducationLevel().equals(GRADUATE))
				offerLevel= educationLevel.graduate;
			
			if(appField!=offerField)
				continue;
			
			int appnum = appLevel.ordinal();
			int offernum= offerLevel.ordinal();
			
			if (offernum>=appnum)
			{
				int temp= offernum-appnum;
				tempRanking+=0.3-(temp*0.070);
				System.out.println("temp ranking after enums is " + tempRanking);
			}
			else
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
			
			if(flag)
				continue;
		
			//check that the gender is the same never mind
			if (app.getGender().equals(offer.getGender()))
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
				
				if (check>0){
					tempRanking += check;
					System.out.println("temp rankig after time ranking is: " + tempRanking);
				}
			}
			else
				continue;
			
			//ranking distance
			if (app.getLatitude()!=null && app.getLongitude()!=null && offer.getLatitude()!=null && offer.getLongitude()!=null)
			{
				double dist = distance(Double.parseDouble(app.getLatitude()), Double.parseDouble(offer.getLatitude()), Double.parseDouble(app.getLongitude()), Double.parseDouble(offer.getLongitude()), 1, 1);
			
				if (dist<=2.5)
				{
					tempRanking+=0.3;
					System.out.println("full ranking in distance, the temp ranking is: " + tempRanking);
				}
				else
				{
					double check = 0.3-(((dist-2.5)/0.3)*0.005);
					if (check>0){
						tempRanking+=check;
						System.out.println("not full ranking in distance, the temp ranking is: " + tempRanking);

					}
				}
			}
			else
				continue;
			System.out.println("sum of temp ranking: " + tempRanking);
			if (tempRanking>=0.65)
			{
				ranking.put(offer.getOfferId(), tempRanking);
				System.out.println("offer id: " + offer.getOfferId() + " temp ranking: " + tempRanking);
			}
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
		
		for (int i=0; i<listOffers.size(); i++)
		{
			boolean flag = false;
			HandymanOffer offer = listOffers.get(i);
			double tempRanking = 0;
			
			//check if the offer is in the refuse list of the application
			if (app.getRefuseList().contains(Integer.valueOf(offer.getOfferId())))
			{
				continue;
			}
						
			if (handymanEnum!=null)
			{
				switch(handymanEnum)
				{
					case colorCorrections:
						if ((!offer.getColorCorrections())){
							flag=true;
						}
						break;
					case furniture:
						if ((!offer.getFurniture())){
							flag=true;
						}
						break;
					case generalHangingWorks:
						if (!offer.getGeneralHangingWorks())
						{
							flag=true;
						}
						break;
					case hangingOfLightFixtures:
						if (!offer.getHangingOfLightFixtures())
						{
							flag=true;
						}
						break;
					case treatmentSocketsAndPowerPoints:
						if (!offer.getTreatmentSocketsAndPowerPoints())
						{
							flag=true;
						}
						break;
					default: flag=false;
				}
			}
			else
				continue;
			
			if(flag)
				continue;
			
			//check that the gender is the same never mind
			if (app.getGender().equals(offer.getGender()))
			{
				tempRanking+=0.1;
			}
			
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
				{	
					tempRanking += check;
				}
			}
			else{
				System.out.println("days are not match");
				continue;
			}
			//ranking language
			if (app.getLanguage().equals(offer.getLanguage())){				
				tempRanking += 0.05;
			}
			//ranking distance
			if (app.getLatitude()!=null && app.getLongitude()!=null && offer.getLatitude()!=null && offer.getLongitude()!=null)
			{

				double dist = distance(Double.parseDouble(app.getLatitude()), Double.parseDouble(offer.getLatitude()), Double.parseDouble(app.getLongitude()), Double.parseDouble(offer.getLongitude()), 1, 1);
				if (dist<=2)
				{	
					tempRanking+=0.45;
				}
				else
				{
					double check = 0.45-(((dist-2)/0.3)*0.005);
					if (check>0)
					{
						System.out.println("paraitl distance ranking, the ranking is: " + check);
						tempRanking += check;
					}
				}
			}
			else{
				System.out.println("longitude or latitude is null");
				continue;
			}
			if (tempRanking>=0.65){
				ranking.put(offer.getOfferId(), tempRanking);
			}
		}
		
		return sortList(ranking);		
	}

	//sort the offer list and return the top 10 offers
	@SuppressWarnings("rawtypes")
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
		
		for (int i=0; i<10; i++) 
		{	
			//check if there is last from 10 offers
		    if (i==ranking.size())
		    	break;
			list.add(( (Map.Entry<Integer, Double>) a[i]).getKey());
		}		
		return list;		
	}
	
	//ranking the distance between the application and the offer
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

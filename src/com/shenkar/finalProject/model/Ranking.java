package com.shenkar.finalProject.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Ranking {
		
	@SuppressWarnings("unchecked")
	public static List <Integer> rideRanking (RideApplication app, List <RideOffer> listOffers)
	{
		HashMap<Integer, Double> ranking = new HashMap<Integer, Double>();
		
		for (int i=0; i<listOffers.size(); i++)
		{
			double temp=0;
			int con=0;
			Date appStart = app.getPeriod();
			Date appEnd = app.getEndPeriod();
			Date offerStart = listOffers.get(i).getPeriod();
			Date offerEnd = listOffers.get(i).getEndPeriod();
			
			
			if (con==0)
				continue;
			
			if ( (appStart.compareTo(offerStart)==0) && (appEnd.compareTo(offerEnd)==0))
			{
				temp+=1*0.3;
			}
			else if (!(offerStart.before(appStart) || offerStart.after(appEnd)))
			{
				temp+=0.9*0.3;
			}
			else if (con==3)
			{
				temp+=0.7*0.3;
			}
			
			//break point to check if there is a point to continue
			if (temp==0)
				continue;
			
			
			
		}
		
		
		
		Object[] a = ranking.entrySet().toArray();
		Arrays.sort(a, new Comparator() {
		    public int compare(Object o1, Object o2) {
		        return ((Map.Entry<Integer, Double>) o2).getValue()
		                   .compareTo(((Map.Entry<Integer, Double>) o1).getValue());
		    }
		});

		List<Integer> list = new ArrayList<Integer>();
		
		
		for (int i=0; i<a.length; i++) {
		    
		    list.add(( (Map.Entry<Integer, Double>) a[i]).getKey());

		}
		
		return list;
		
	}
	
	public static List <Integer> oldersRanking (OldersApplication app, List <OldersOffer> listOffers)
	{
		return null;		
	}
	
	public static List <Integer> studentRanking (StudentApplication app, List <StudentOffer> listOffers)
	{
		return null;		
	}

	public static List <Integer> handymanRanking (HandymanApplication app, List <HandymanOffer> listOffers)
	{
		return null;		
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
	    
	    double distance = R * c * 1000; // convert to meters

	    double height = el1 - el2;

	    distance = Math.pow(distance, 2) + Math.pow(height, 2);

	    return Math.sqrt(distance);
	}

}

package Tests;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.shenkar.finalProject.classes.Application;
import com.shenkar.finalProject.classes.HandymanApplication;
import com.shenkar.finalProject.classes.Offer;
import com.shenkar.finalProject.classes.OldersApplication;
import com.shenkar.finalProject.classes.RideApplication;
import com.shenkar.finalProject.classes.StudentApplication;
import com.shenkar.finalProject.globals.GlobalsFunctions;
import com.shenkar.finalProject.model.ApplicationExceptionHandler;
import com.shenkar.finalProject.model.HibernateApplicationDAO;
import com.shenkar.finalProject.model.HibernateMatchDAO;
import com.shenkar.finalProject.model.Ranking;
import com.shenkar.finalProject.model.UserExceptionHandler;

public class TestApplication {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws ParseException, ApplicationExceptionHandler, UserExceptionHandler 
	{
		
		Long midnight=LocalDateTime.now().until(LocalDate.now().plusDays(1).atTime(4, 0), ChronoUnit.MINUTES);
		System.out.println(LocalDate.now().plusDays(1).atTime(5, 0));

		String regex = "\\d+";
		
		String check ="12";
		
		if (check.matches(regex))
			System.out.println(true);
			
		
//		HashMap<Integer, Double> ranking = new HashMap<Integer, Double>();
//		
//		ranking.put(4, 0.5);
//		ranking.put(3, 0.8);
//		ranking.put(2, 0.7);
//		
//		Object[] a = ranking.entrySet().toArray();
//		Arrays.sort(a, new Comparator() {
//		    public int compare(Object o1, Object o2) {
//		        return ((Map.Entry<Integer, Double>) o2).getValue()
//		                   .compareTo(((Map.Entry<Integer, Double>) o1).getValue());
//		    }
//		});
//		
//		List<Integer> list = new ArrayList<Integer>();
//
//		for (int i=0; i<10; i++) {
//		    if (i==ranking.size())
//		    	break;
//			list.add(( (Map.Entry<Integer, Double>) a[i]).getKey());
//		}
//	
//		for (Object l:list)
//		{
//			System.out.println(l);
//		}
//		
//		String sd = "check";
//		List <Integer> listint = new ArrayList<Integer>();
//		
//		System.out.println(listint.size()==0);
		//System.out.println(HibernateApplicationDAO.getInstance().getRandomApplication(12, "ride", 1));
		//System.out.println(sd.by);
		
		//System.out.println("dist is: " +  Ranking.distance(Double.parseDouble("31.8913829"), Double.parseDouble("31.88711869999999"), Double.parseDouble("34.813065"), Double.parseDouble("34.831326"), 1, 1));
		/*
		list.remove(Integer.valueOf(4));
		
		for (Object l:list)
		{
			System.out.println(l);
		}
		*/
		/*
//		List <Application> list = HibernateApplicationDAO.getInstance().getAllSpecificApplicationTable("handyman");
//		
//		int ttl = GlobalsFunctions.calculateTTL(period2);
//		
//		System.out.println("the ttl is: " + ttl);
//		*/
		
		//Application app = HibernateApplicationDAO.getInstance().getApplication(1, "student");
		
//		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//
//		//the application day
//		String testDateString2 = "2018-07-27 12:50";
//	    Date period2  = (Date) df.parse(testDateString2);
//	    
//	    //today
//		String testDateString3 = "2018-07-27 11:40";
//	    Date period3 = (Date) df.parse(testDateString3);
//	    	    
//		DateTime datatimeApp = new DateTime(period2);
//		DateTime datatimeOffer = new DateTime(period3);
//
//		org.joda.time.Period p = new org.joda.time.Period(datatimeOffer, datatimeApp);
//		int hours = p.getHours();
//		int minutes = p.getMinutes();
//		
//		System.out.println("hours: " + hours);
//		System.out.println("min: " + minutes);
//
//	    DateFormat outputFormatter = new SimpleDateFormat("MM/dd/yyyy");
//	    String output = outputFormatter.format(period2); // Output : 01/20/2012
//	    
//	    Date newDate = (Date) outputFormatter.parse(output);
//    
//	    System.out.println(newDate);
//	    RideApplication ob = new RideApplication(1, period2, "sdf", "asda", 1, "asda", "asdasd", false, "sadas", "asdad", "asdad", "sdfsf", "asdas", "asdad", "asda", "asdas", 1, "asdad", "asdsa", period2);
//
//	    ob.setList(list);
//
//	    int i = HibernateApplicationDAO.getInstance().createApplication(ob);
//	    System.out.println("the app id is: " + i);
//	    
//	    Application app =  HibernateApplicationDAO.getInstance().getApplication(1, "ride");
//	    
//	    
//	    
//	    System.out.println(new Gson().toJson(HibernateApplicationDAO.getInstance().getApplication(1, "ride")));
//	    
	    
	    
	    //System.out.println(app.getPeriodic().compareTo(app.getPeriod()));
	    
		//GlobalsFunctions.calculateTTL(app.getPeriod());
//	
//	    System.out.println(today.compareTo(period2));
//	    
//	    LocalDate todayy = today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//	    LocalDate date2 = period2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//	    //Duration daysBetween = Duration.between(date1, date2);
//
//	    int daus = Period.between( date2,todayy).getDays();
//	    int mon = Period.between(date2,todayy).getMonths();
//	    
//	    System.out.println("days: "+daus);
//	    System.out.println("month: "+ mon);
//	    
//	    HandymanApplication ob = new HandymanApplication(1, period2, "wsedrs", period2, true, "sd", "ssd", "male",
//				"asd", "check", "check", true, true, true, true, true, "");
//	    //check(ob);
	    
	    //System.out.println(HibernateManualMatchDAO.getInstance().getUserByOfferId(3, "handyman"));
	    
		//System.out.println("list is " + HibernateApplicationDAO.getInstance().getApplication(8, "handyman"));

	    
	   /* 
	    Days d = Days.daysBetween(period2, period1);
	    int days = d.getDays();
	    //int day = Period.between(period1, period2);
	    
		HandymanApplication ob = new HandymanApplication(1, period1, "wsedrs", period1, true, "sd", "ssd", "male",
				"asd", "check", "check", true, true, true, true, true, "");
		System.out.println("list is " + HibernateApplicationDAO.getInstance().getApplication(8, "handyman"));
		//HibernateApplicationDAO.getInstance().createApplication(ob);
		
		StudentApplication ob4 = new StudentApplication(1, period, "wsedrs", period, true, "sd", "male", "hebrew",
				"2", "math", "sports","check", "check", true, true, true);
		
		String strApplication = new Gson().toJson(ob4).toString();
		System.out.println(strApplication);
		JsonElement je = new JsonParser().parse(strApplication);
		//JsonArray myArray = je.getAsJsonArray();
	    JsonObject jo = je.getAsJsonObject();
	    JsonPrimitive tsPrimitive = jo.getAsJsonPrimitive("period");
	    
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.ROOT);

	    String strLocalDate = "2015-10-23T03:34:40";

	    LocalDateTime localDate = LocalDateTime.parse(strLocalDate, formatter);
	    
	    
	    System.out.println("Timestamp: " + tsPrimitive);
*/


		
		//Application Deserialization = null;
		//Gson gson = new Gson();
		//Deserialization = (Application)gson.fromJson(new Gson().toJson(ob).toString(),HandymanApplication.class);
		
		//System.out.println(new Gson().toJson(ob).toString());

		//System.out.println(new Gson().toJson(ob).toString());
		
		//OldersApplication ob2 = new OldersApplication(1, period, "rs", period, true, "sd", "ssd", "male",
			//	"test", true, true, true, true);
		//System.out.println(new Gson().toJson(ob2).toString());

		/*
		RideApplication ob3 = new RideApplication(1, period, period, true, "sd", "ssd", "male",
				"test", "test", "rishon", period, "test", "test");
		System.out.println(new Gson().toJson(ob3).toString());

		*/
		//HibernateApplicationDAO.getInstance().createApplication(ob);
		//HibernateApplicationDAO.getInstance().createApplication(ob2);
		//HibernateApplicationDAO.getInstance().createApplication(ob3);
		
		//System.out.println(HibernateApplicationDAO.getInstance().getApplication(2, "olders").getUserId());
		//System.out.println(HibernateApplicationDAO.getInstance().getApplications(1));
		
		//OldersApplication ob31 = new OldersApplication(1, period, "rs", period, true, 5, "sd", "ssd", true, "male",
				//"test", "test", true, false, true,false);
		//HibernateApplicationDAO.getInstance().editApplication(2, ob31, "olders");
		//HibernateApplicationDAO.getInstance().deleteApplication(3, "handyman");
		//System.out.println(new Random().nextInt(50)+1);
		//List <Application> list = new ArrayList<>();
		//HibernateApplicationDAO.getInstance().getRandomApplication(2, "olders");
		//System.out.println(HibernateApplicationDAO.getInstance().getRandomApplication(3, "olders"));
		//System.out.println(HibernateApplicationDAO.getInstance().getApplications(-1).toString());
	    
//	    private static double calcTime(LocalDate localApp, LocalDate localOffer, Application app, Offer offer)
//		{
//			int days = Period.between(localApp, localOffer).getDays();
//			int mon = Period.between(localApp,localOffer).getMonths();			
//			int year = Period.between(localApp,localOffer).getYears();
//			
//			if ((days==0 && mon==0 && year==0))
//			{
//				DateTime datatimeApp = new DateTime(app.getPeriod());
//				DateTime datatimeOffer = new DateTime(offer.getPeriod());
//
//				org.joda.time.Period p = new org.joda.time.Period(datatimeApp, datatimeOffer);
//				int hours = Math.abs(p.getHours());
//				int minutes = p.getMinutes();
//				
//				double sum = ((hours*60) + minutes)/60;
//				
//				double check = 0.5-(0.1)*sum;
//				
//				return check;
//			}
//			return 0;
//			
//		}
	    
	}
	
	public static void check (Object obj)
	{
		if (obj instanceof Application){
			System.out.println("is application");
		}
		
	}
	
}

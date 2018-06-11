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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.shenkar.finalProject.Globals.GlobalsFunctions;
import com.shenkar.finalProject.model.Application;
import com.shenkar.finalProject.model.ApplicationExceptionHandler;
import com.shenkar.finalProject.model.HandymanApplication;
import com.shenkar.finalProject.model.HibernateApplicationDAO;
import com.shenkar.finalProject.model.HibernateManualMatchDAO;
import com.shenkar.finalProject.model.OldersApplication;
import com.shenkar.finalProject.model.Ranking;
import com.shenkar.finalProject.model.RideApplication;
import com.shenkar.finalProject.model.StudentApplication;

public class TestApplication {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws ParseException, ApplicationExceptionHandler 
	{
		
		HashMap<Integer, Double> ranking = new HashMap<Integer, Double>();
		
		ranking.put(1, 0.5);
		ranking.put(3, 0.8);
		ranking.put(2, 0.7);
		
		Object[] a = ranking.entrySet().toArray();
		Arrays.sort(a, new Comparator() {
		    public int compare(Object o1, Object o2) {
		        return ((Map.Entry<Integer, Double>) o2).getValue()
		                   .compareTo(((Map.Entry<Integer, Double>) o1).getValue());
		    }
		});
		
		List<Integer> list = new ArrayList<Integer>();

		for (Object e : a) {
		    
		    list.add(( (Map.Entry<Integer, Double>) e).getKey());

		}
		
//		for (Object l:list)
//		{
//			System.out.println(l);
//		}
		
		//System.out.println(Ranking.distance(31.922210, 31.936648, 34.785425, 34.799984, 1, 1));
		 
		/*
//		List <Application> list = HibernateApplicationDAO.getInstance().getAllSpecificApplicationTable("handyman");
//		
//		int ttl = GlobalsFunctions.calculateTTL(period2);
//		
//		System.out.println("the ttl is: " + ttl);
//		*/
		
		//Application app = HibernateApplicationDAO.getInstance().getApplication(1, "student");
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		//the application day
		String testDateString2 = "2018-07-27";
	    Date period2  = (Date) df.parse(testDateString2);
	    
	    //today
		String testDateString3 = "2018-07-27";
	    Date today  = (Date) df.parse(testDateString3);

//	RideApplication ob = new RideApplication(1, period2, "sdf", "asda", 1, "asda", "asdasd", period2, false, "sadas", "asdad", "asdad", "sdfsf", "asdas", "asdad", "asda", "asdas", "asdasd", 1, "asdad", "asdsa", period2);
//
//  	ob.setList(list);
//
//  	HibernateApplicationDAO.getInstance().createApplication(ob);
	    
	//Application app =  HibernateApplicationDAO.getInstance().getApplication(1, "student");
	    
	System.out.println(new Gson().toJson(HibernateApplicationDAO.getInstance().getApplication(1, "ride")));
	    
	    
	    
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

	}
	
	public static void check (Object obj)
	{
		if (obj instanceof Application){
			System.out.println("is application");
		}
		
	}
	
}

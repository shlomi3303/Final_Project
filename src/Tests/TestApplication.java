package Tests;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.shenkar.finalProject.model.Application;
import com.shenkar.finalProject.model.ApplicationExceptionHandler;
import com.shenkar.finalProject.model.HandymanApplication;
import com.shenkar.finalProject.model.HibernateApplicationDAO;
import com.shenkar.finalProject.model.OldersApplication;
import com.shenkar.finalProject.model.RideApplication;
import com.shenkar.finalProject.model.StudentApplication;

public class TestApplication {

	public static void main(String[] args) throws ParseException, ApplicationExceptionHandler 
	{
		
		List <Application> list = HibernateApplicationDAO.getInstance().getAllSpecificApplicationTable("handyman");
		
		
		String testDateString2 = "2018-04-30 18:00";
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	    //DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	    
	    Date period  = (Date) df.parse(testDateString2);
		
		HandymanApplication ob = new HandymanApplication(1, period, "wsedrs", period, true, "sd", "ssd", "male",
				"asd", "check", "check", true, true, true, true, true);
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



		
		Application Deserialization = null;
		//Gson gson = new Gson();
		//Deserialization = (Application)gson.fromJson(new Gson().toJson(ob).toString(),HandymanApplication.class);
		
		System.out.println(new Gson().toJson(ob).toString());

		//System.out.println(new Gson().toJson(ob).toString());
		
		//OldersApplication ob2 = new OldersApplication(1, period, "rs", period, true, "sd", "ssd", "male",
			//	"test", true, true, true, true);
		//System.out.println(new Gson().toJson(ob2).toString());

		
		RideApplication ob3 = new RideApplication(1, period, period, true, "sd", "ssd", "male",
				"test", "test", "rishon", period, "test", "test");
		System.out.println(new Gson().toJson(ob3).toString());

		
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

}

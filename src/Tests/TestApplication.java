package Tests;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.shenkar.finalProject.model.ApplicationExceptionHandler;
import com.shenkar.finalProject.model.HandymanApplication;
import com.shenkar.finalProject.model.HibernateApplicationDAO;
import com.shenkar.finalProject.model.OldersApplication;
import com.shenkar.finalProject.model.RideApplication;

public class TestApplication {

	public static void main(String[] args) throws ParseException, ApplicationExceptionHandler 
	{
		String testDateString2 = "02/04/2014 23:37";
	    DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	    
	    Date period  = (Date) df.parse(testDateString2);
		
		HandymanApplication ob = new HandymanApplication(1, period, "wsedrs", period, true, 3, "sd", "ssd", true, "male",
				"asd", "asdad", true, true, true, true, true);
		
		OldersApplication ob2 = new OldersApplication(1, period, "rs", period, true, 5, "sd", "ssd", true, "male",
				"test", "test", true, true, true, true);

		RideApplication ob3 = new RideApplication(1, period, period, true, "sd", "ssd", "male",
				"test", "test", "rishon", "tel aviv", period);
		
		//HibernateApplicationDAO.getInstance().createApplication(ob);
		//HibernateApplicationDAO.getInstance().createApplication(ob2);
		//HibernateApplicationDAO.getInstance().createApplication(ob3);
		
		//System.out.println(HibernateApplicationDAO.getInstance().getApplication(2, "olders").getUserId());
		//System.out.println(HibernateApplicationDAO.getInstance().getApplications(1));
		
		OldersApplication ob31 = new OldersApplication(1, period, "rs", period, true, 5, "sd", "ssd", true, "male",
				"test", "test", true, false, true,false);
		HibernateApplicationDAO.getInstance().editApplication(2, ob31, "olders");

	}

}

package Tests;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.shenkar.finalProject.Archive.HibernateArchiveDAO;
import com.shenkar.finalProject.model.RideApplication;

public class TestArchive {

	public static void main(String[] args) throws ParseException
	{
    //Date period = null;
	    
	    try {
	    	String testDateString2 = "2018-04-30 18:00";
	        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	    	Date period = (Date) df.parse(testDateString2);
	    	RideApplication ob3 = new RideApplication(1, period, period, true, "sd", "ssd", "male",
	    			"test", "test", "rishon", period, "test", "test");
	    	
	    	HibernateArchiveDAO.getInstance().addNewApplicationArchive(ob3, "ride");
	    }
	    catch (Exception e)
	    {
	    	e.printStackTrace();
	    }
    
	}
}

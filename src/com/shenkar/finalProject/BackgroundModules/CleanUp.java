package com.shenkar.finalProject.backgroundModules;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.shenkar.finalProject.classes.Application;
import com.shenkar.finalProject.classes.Offer;
import com.shenkar.finalProject.classes.RideApplication;
import com.shenkar.finalProject.classes.RideOffer;
import com.shenkar.finalProject.globals.ConstantVariables;
import com.shenkar.finalProject.globals.GlobalsFunctions;
import com.shenkar.finalProject.model.ApplicationExceptionHandler;
import com.shenkar.finalProject.model.HibernateApplicationDAO;
import com.shenkar.finalProject.model.HibernateOfferDAO;
import com.shenkar.finalProject.model.OfferExceptionHandler;
import com.shenkar.finalProject.model.UserExceptionHandler;

import api.CoralogixLogger;

@WebListener
public class CleanUp implements ServletContextListener
{

	private static SessionFactory cleanUpFactoryApplication;
	private static SessionFactory cleanUpFactoryOffer;
	private static CoralogixLogger logger;
	private static final String subSystem = "Clean Up";
	
	public CleanUp()
	{
		String privateKey = GlobalsFunctions.getCoralogixPrivateKey();
		System.out.println("In Clean Up const, Coralogix private key is: " + privateKey);
		if (!privateKey.equals("")){
			CoralogixLogger.configure(privateKey, ConstantVariables.appName, subSystem);
	        logger = new CoralogixLogger(CleanUp.class.toString());
		}
	}

	Timer timer = new Timer();
	private final static int GET_ALL_DB = -1;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) 
	{
		if (logger!=null)
        	logger.info("Closing Clean Up....");
		
        if (timer!=null){
        	timer.cancel();
        	if (logger!=null)
        		logger.info("Clean Up is closed");
        }
        else if (logger!=null)
        {
        	logger.info("Timer Task in null in Clean Up");
        }
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) 
	{
        if (logger!=null)
        	logger.info("Clean Up Module is On " + new Date());
        
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Long midnight = LocalDateTime.now().until(LocalDate.now().plusDays(0).atTime(23, 45), ChronoUnit.MINUTES);
        System.out.println("the now from clean up is: " + LocalDateTime.now());
        System.out.println("the now minutes clean up is: " + LocalDateTime.now().getMinute());
        scheduler.scheduleAtFixedRate(new cleanUpRunner(), midnight, TimeUnit.DAYS.toMinutes(1), TimeUnit.MINUTES);

        timer.scheduleAtFixedRate(new cleanUpRunner(), 0, ConstantVariables.ONCE_PER_DAY);
	}
	
	private static void updateTTLApplication () throws ApplicationExceptionHandler, UserExceptionHandler, IOException
	{
		List <Application> list = HibernateApplicationDAO.getInstance().getUserApplications(GET_ALL_DB);
		Session session = null;
		if (list!=null)
		{
			logger.info("The list of application is not empty");
			
			try
			{
				initCleanUpFactory();
				session = GlobalsFunctions.getSession(cleanUpFactoryApplication);
				
				if (session != null)
				{					
					session.beginTransaction();
					for (int i=0; i<list.size(); i++)
					{
						if (list.get(i).getTTL()>0)
						{
							list.get(i).setTTL(list.get(i).getTTL()-1);
							session.update(list.get(i));
						}
						else if (list.get(i).getTTL()==0)
						{
							Application app = list.get(i);
							HibernateApplicationDAO.getInstance().notification(app.getUserId(),  ConstantVariables.subjectYourApplicationIsexpired, ConstantVariables.bodyYourApplicationIsexpired);
							list.get(i).setArchive(true);
						}
						//check if the Application is a type of ride
						else if (list.get(i).getTTL()==-1)
						{
							Date today = new Date();
							RideApplication ride = (RideApplication) list.get(i);
							if (today.compareTo(ride.getEndPeriod())>0)
							{
								HibernateApplicationDAO.getInstance().notification(ride.getUserId(),  ConstantVariables.subjectYourApplicationIsexpired, ConstantVariables.bodyYourApplicationIsexpired);
								list.get(i).setArchive(true);
							}
						}
					}
					session.getTransaction().commit();
					logger.info("updateTTLApplication function finished");
				}
			}
			catch (Exception e)
			{
				logger.error("Error in updateTTLApplication: " + e.getMessage());
				e.printStackTrace();
			}
			finally 
			{
				try
				{
					if (session!=null)
						session.close();
				}
				catch (Exception e)
				{
					logger.error("Error on closing session in updateTTLApplication function: " + e.getMessage());
					e.printStackTrace();
				}
			}
		}	
	}
	
	private static void updateTTLOffer () throws OfferExceptionHandler, UserExceptionHandler, IOException
	{
		List <Offer> list = HibernateOfferDAO.getInstance().getUserOffers(GET_ALL_DB);
		Session session=null;
		if (list!=null)
		{
			try{
				initCleanUpFactory();
				session = GlobalsFunctions.getSession(cleanUpFactoryOffer);
				if (session != null)
				{
					session.beginTransaction();
					for (int i=0; i<list.size(); i++)
					{
						if (list.get(i).getTTL()>0)
						{
							list.get(i).setTTL(list.get(i).getTTL()-1);
						}
						else if (list.get(i).getTTL()==0)
						{
							Offer offer =list.get(i);
							HibernateOfferDAO.getInstance().notification(offer.getUserId(), ConstantVariables.subjectYourOfferIsexpired, ConstantVariables.bodyYourOfferIsexpired);
							list.get(i).setArchive(true);
						}
						//check if the offer is a type of ride
						else if (list.get(i).getTTL()==-1)
						{
							Date today = new Date();
							RideOffer ride = (RideOffer) list.get(i);
							if (today.compareTo(ride.getEndPeriod())>0)
							{
								HibernateOfferDAO.getInstance().notification(ride.getUserId(), ConstantVariables.subjectYourOfferIsexpired, ConstantVariables.bodyYourOfferIsexpired);
								list.get(i).setArchive(true);
							}
						}
						session.update(list.get(i));
					}
					session.getTransaction().commit();
				}
			}
			catch (Exception e)
			{
				logger.error("Error on updateTTLOffer: " + e.getMessage());
				e.printStackTrace();
			}
			finally 
			{
				try
				{
					if (session!=null)
						session.close();
				}
				catch (Exception e)
				{
					logger.error("Error on closing session in updateTTLOffer function: " + e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}
	
	private static void initCleanUpFactory ()
	{
		try
		{
		 if (cleanUpFactoryApplication==null || cleanUpFactoryOffer==null)
		  {
			 cleanUpFactoryApplication =  GlobalsFunctions.initSessionFactory(cleanUpFactoryApplication, "hibernateApplication.cfg.xml");
			 cleanUpFactoryOffer = GlobalsFunctions.initSessionFactory(cleanUpFactoryOffer, "hibernateOffer.cfg.xml");
		  }
		}
		catch (Exception e)
		{
			System.out.println("Error in init Clean Up Factory " + e.getMessage());
		}
	}
	
	class cleanUpRunner extends TimerTask
	{
		@Override
		public void run() 
		{
			try 
			{
				System.out.println("clean Up Task start on: " + new Date());
				logger.info("clean Up Task start on: " + new Date());
				updateTTLApplication();
				updateTTLOffer();
			} 
			catch (ApplicationExceptionHandler | OfferExceptionHandler | UserExceptionHandler | IOException e) 
			{
				logger.error("Error in Clean Up Runner: " + e.getMessage());	
				e.printStackTrace();
			}		
		}
	}
}

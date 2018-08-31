package com.shenkar.finalProject.globals;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

public class GlobalsFunctions 
{
	
	public static String getCoralogixPrivateKey()
	{
		String privateKey = System.getenv("CORALOGIX_privateKey");
		
		if (privateKey!=null && !privateKey.isEmpty())
			return privateKey;
		
		return "";
	}
	
	
	public static SessionFactory initSessionFactory(SessionFactory sessionFactory, String hibernatFile)
	{
		Configuration cfg = new Configuration();
		cfg.configure(hibernatFile);
		
		String userName = null;
		String password = null;
		String url = null;
		
		try
		{
			userName = System.getenv("User_API_KEY");
			password = System.getenv("Password_API_KEY");
			url = System.getenv("URL_API_KEY");
			
			if (userName!=null && password!=null && url!=null)
			{
				System.out.println("In Heruko Server Mode");
				cfg.getProperties().setProperty("hibernate.connection.username",userName);
				cfg.getProperties().setProperty("hibernate.connection.password",password);
				cfg.getProperties().setProperty("hibernate.connection.url",url);
			}
			else
			{
				System.out.println("In Local Host Mode");
				cfg.getProperties().setProperty("hibernate.connection.username",ConstantVariables.userName);
				cfg.getProperties().setProperty("hibernate.connection.password",ConstantVariables.password);
				cfg.getProperties().setProperty("hibernate.connection.url",ConstantVariables.URL);
			}
			
			sessionFactory = cfg.buildSessionFactory();
			
			return sessionFactory;
		}
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("returning null for hibernat file: " + hibernatFile);
		return null;
	}

	public static Session getSession(SessionFactory sessionFactory) throws HibernateException {         
		   Session sess = null;       
		   try 
		   {
			   if (sessionFactory!=null)
				   {sess = sessionFactory.openSession();}
			   else
				   System.out.println("the session factory: " + sessionFactory + " is null");
		   } catch (org.hibernate.HibernateException he) {  
		       sess = sessionFactory.getCurrentSession();  

		   }             
		   return sess;
	} 
	
	public static void sendEmail(String toEmail,String subject , String body)
	{
		Email from = new Email("shlomi3303@gmail.com"); 
		Email to = new Email(toEmail);
		String link = "<a href=\"https://final-project-eef76.firebaseapp.com\"> לחץ כאן בכדי לעבור למחוברים לחיים </a>";
		body = body + "<br>" + link;
		Content content = new Content("text/html", body);
		Mail mail = new Mail(from, subject, to, content);
		SendGrid sg = null;
	
		try 
		{
			String sendGridApi = System.getenv("SENDGRID_API_KEY");
			if (sendGridApi!=null)
				sg = new SendGrid(sendGridApi);
			
			if (sg!=null)
			{
				  Request request = new Request();
				  request.setMethod(Method.POST);
				  request.setEndpoint("mail/send");
				  request.setBody(mail.build());
				  Response response = sg.api(request);
				  System.out.println(response.getStatusCode());
				  System.out.println(response.getBody());
				  System.out.println(response.getHeaders());
			}
	   } 
	   catch (IOException ex) 
	   {
		   ex.printStackTrace();
	   }
	}
	
	public static int calculateTTL(Date date)
	{
		 Date todayDate = new Date();
		
		 LocalDate date1 = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		 LocalDate today = todayDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	
		 int days = Period.between(today, date1).getDays();
		 int mon = Period.between(today,date1).getMonths();
		 
		 int ttl = days + mon*30;
		 if (ttl>0)
			 return ttl;
		 return 0;
	}
}

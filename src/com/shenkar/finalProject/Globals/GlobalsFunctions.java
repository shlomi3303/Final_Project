package com.shenkar.finalProject.Globals;

import java.io.IOException;

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
		
		
		return null;
		
	}
	
	public static void sendEmail(String toEmail,String subject , String body)
	{
		Email from = new Email("shlomi3303@gmail.com"); 
		Email to = new Email(toEmail);
		Content content = new Content("text/plain", body);
		Mail mail = new Mail(from, subject, to, content);
	
	    SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));

		Request request = new Request();
		try 
		{
	      request.method = Method.POST;
	      request.endpoint = "mail/send";
	      request.body = mail.build();
	      Response response = sg.api(request);
	      System.out.println(response.statusCode);
	      System.out.println(response.body);
	      System.out.println(response.headers);
	   } 
		catch (IOException ex) 
		{
			ex.printStackTrace();
		}
	}

}
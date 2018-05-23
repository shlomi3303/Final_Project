package com.shenkar.finalProject.Globals;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

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
	
	

}

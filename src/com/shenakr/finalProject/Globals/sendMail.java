package com.shenakr.finalProject.Globals;

import java.io.IOException;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

public class sendMail {

	public static void sendEmail(String toEmail,String subject , String body)
	{
		Email from = new Email("shlomi3303@gmail.com"); 
		Email to = new Email(toEmail);
		Content content = new Content("text/plain", body);
		Mail mail = new Mail(from, subject, to, content);
	
		Request request = new Request();
		try {
		      request.method = Method.POST;
		      request.endpoint = "mail/send";
		      request.body = mail.build();
		    
	   } 
		catch (IOException ex) 
		{}
	}
}

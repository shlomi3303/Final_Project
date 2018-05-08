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
	
		SendGrid sg = new SendGrid("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");
		Request request = new Request();
		try {
		      request.method = Method.POST;
		      request.endpoint = "mail/send";
		      request.body = mail.build();
		      Response response = sg.api(request);
		      System.out.println(response.statusCode);
		      System.out.println(response.body);
		      System.out.println(response.headers);
	   } 
		catch (IOException ex) 
		{}
	}
}

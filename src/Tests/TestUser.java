package Tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.gson.Gson;
import com.shenkar.finalProject.model.HibernateUserDAO;
import com.shenkar.finalProject.model.AppUser;
import com.shenkar.finalProject.model.Application;
import com.shenkar.finalProject.model.ApplicationExceptionHandler;
import com.shenkar.finalProject.model.HibernateApplicationDAO;
import com.shenkar.finalProject.model.UserExceptionHandler;
import com.sun.istack.internal.NotNull;
import com.sun.xml.internal.fastinfoset.util.StringArray;

public class TestUser {

	public static void main(String[] args) throws UserExceptionHandler, ApplicationExceptionHandler
	{
		 
		String firstname1 = "�����";
		String lastname1 = "������";
		String mail1 = "shlomi3303@walla.com";
		String password1 = "123456";
		String phone1 = "0642342313";
		int age1 = 12;
		String familyStatus1 = "����";
		int kids1 = 3;
		String userLocation1 = "�� ����";
		
		List <AppUser> user = HibernateUserDAO.getInstance().getUser(mail1, password1);
		 
		List <Application> app = HibernateApplicationDAO.getInstance().getUserApplications(1);
		
		List <Object> obj = new ArrayList<>();
		
		obj.add(user);
		obj.add(app);
		
		String json = new Gson().toJson(obj);
		
		System.out.println(json);
		
		 //HibernateUserDAO.getInstance().addNewUser(user1);
		 /*
		 
		 String arr =  new Gson().toJson(user1);
		 System.out.println(arr);
		 String newArr = arr;
		 Gson gson = new Gson();
		 User DeserializationString = gson.fromJson(newArr, User.class);
		 System.out.println(DeserializationString.getFamilyStatus());
		// HibernateUserDAO.getInstance().addNewUser(DeserializationString);
		 HibernateUserDAO.getInstance().getUser("check1@gmail.com", "test124");
		 //HibernateUserDAO.getInstance().addNewUser(user1);
		 
		 */
		 
		/* 
		 String userId2 = "999999";
		 <mapping class="com.shenkar.finalProject.model.User"/>
		 String firstname2 = "����";
		 String lastname2 = "�����";
			String mail2 = "TestMyApp@gmail.com";
			String password2 = "checkNumber2";
			String phone2 = "123400000";
			int age2 = 18;
			String familyStatus2 = "����";
			int kids2 = 3;
			 String userLocation2 = "��� ���";
			 String[] interests2 = new String[3];
			 String interestString2 = "";
			 
			 for (int z=0; z<interests2.length; z++)
			 {
				interestString2 += interests2[z] + ";"; 
			 }
			
			 User user3 = new User (userId2,firstname2, lastname2, mail2, password2, phone2, age2,
						familyStatus2,kids2, userLocation2, interestString2);
			// String us = user3.getJsonString(user3);
		//	 System.out.println(new Gson().toJson(user3));
			 
			 HibernateUserDAO.getInstance().addNewUser(user3);
		 
		 
		 User updateUser = new User(user1.getId(), userId1,"�����", lastname1, mail1, password1, phone1, age1,
					//familyStatus1,kids1, userLocation1, interests1);
		 
		 //HibernateUserDAO.getInstance().updateUser(userId1, updateUser);
	/*	 
		 User user2 = HibernateUserDAO.getInstance().getUser(mail1, password1);
		 System.out.println(user2.getMail() +" " + user2.getFirstname());
		 
		String userId2 = "999999";
			
		String firstname2 = "����";
	    
		String lastname2 = "�����";
	    
		String mail2 = "TestMyApp@gmail.com";
	    
		String password2 = "checkNumber2";
	    
		String phone2 = "123400000";
		 
		int age2 = 18;
	    
		String familyStatus2 = "����";
		 
		int kids2 = 3;
		 
		 String userLocation2 = "��� ���";
		 
		 String[] interests2 = new String[3];
		 
		 for (int i=0; i<3; i++)
		 {
			 interests1[i] ="����� 2" + ";";
			 System.out.println(interests1[i] );
		 }
		
		 User user3 = new User(userId2,firstname2, lastname2, mail2, password2, phone2, age2,
					familyStatus2,kids2, userLocation2, interests2);
		 
		 HibernateUserDAO.getInstance().addNewUser(user3);
		 */
		 //HibernateUserDAO.getInstance().deleteUser("TestMyApp@gmail.com", "checkNumber2");
		 
		 //HibernateUserDAO.getInstance().deleteUser
					
		/*			
					
					String[] interests1 = new String[3];
					 for (int i=0; i<3; i++)
					 {
						 interests1[i] = "�����";
						// System.out.println(interests1[i] );
					 }
					 
					 String interestString1 = "";
					 for (int j=0; j<interests1.length; j++)
					 {
						interestString1 += interests1[j] + ";"; 
					 }
					 //System.out.println(interestString1);
		 
		*/ 
	}

}

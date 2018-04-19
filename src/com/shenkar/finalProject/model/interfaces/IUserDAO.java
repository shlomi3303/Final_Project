/**
 * 
 */
package com.shenkar.finalProject.model.interfaces;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.shenkar.finalProject.model.AppUser;
import com.shenkar.finalProject.model.UserExceptionHandler;

public interface IUserDAO
{
	
	public void addNewUser(AppUser user, HttpServletResponse response) throws UserExceptionHandler, IOException; 
	
	public void deleteUser (String mail, String password, HttpServletResponse response) throws UserExceptionHandler, IOException;
	
	public AppUser getUser(String mail, String password, HttpServletResponse response) throws UserExceptionHandler, IOException;
	
	public void updateUser(int id, AppUser updateUser, HttpServletResponse response) throws UserExceptionHandler, IOException;
	
	public void setUserToAdmin (int id, boolean bool) throws UserExceptionHandler;

}

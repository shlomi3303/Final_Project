/**
 * 
 */
package com.shenkar.finalProject.model.interfaces;

import java.util.List;

import com.shenkar.finalProject.model.AppUser;
import com.shenkar.finalProject.model.UserExceptionHandler;

public interface IUserDAO
{
	
	public void addNewUser(AppUser user) throws UserExceptionHandler; 
	
	public void deleteUser (String mail, String password) throws UserExceptionHandler;
	
	public List<AppUser> getUser(String mail, String password) throws UserExceptionHandler;
	
	public void updateUser(int id, AppUser updateUser) throws UserExceptionHandler;
	
	public void setUserToAdmin (int id, boolean bool) throws UserExceptionHandler;
	
	public AppUser getUserInfo(int userId) throws UserExceptionHandler;
	
	public void updateLocation(int userId, String lat, String longt, String city, String street, int houseNumber) throws UserExceptionHandler;

}

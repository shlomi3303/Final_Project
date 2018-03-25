/**
 * 
 */
package com.shenkar.finalProject.model.interfaces;

import com.shenkar.finalProject.model.User;
import com.shenkar.finalProject.model.UserExceptionHandler;

public interface IUserDAO
{
	
	public void addNewUser(User user) throws UserExceptionHandler; 
	
	public void deleteUser (String mail, String password) throws UserExceptionHandler;
	
	public User getUser(String mail, String password) throws UserExceptionHandler;
	
	public void updateUser(String userId, User updateUser) throws UserExceptionHandler;

}

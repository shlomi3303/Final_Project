package com.shenkar.finalProject.classes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table (name="manualMatchOffer")
public class ManualMatchUserOffer extends Match {

	@Column (name="User_Id")
	int userId;
	
	@Column (name="User_ToInform")
	int userToInform;
	
	@Column (name="Application_Id")
	int applicationID;
	
	public ManualMatchUserOffer() {}

	public ManualMatchUserOffer(int applicationID, boolean offerAproved, boolean applicationAproved, int userId, String category) 
	{
		super(offerAproved, applicationAproved, "", 0, 0, category);
		this.applicationID = applicationID;
		this.userId = userId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getUserToInform() {
		return userToInform;
	}

	public void setUserToInform(int userToInform) {
		this.userToInform = userToInform;
	}

	public int getApplicationID() {
		return applicationID;
	}

	public void setApplicationID(int applicationID) {
		this.applicationID = applicationID;
	}
	
	

}

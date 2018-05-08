package com.shenkar.finalProject.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table (name="manualMatchApplication")
public class ManualMatchApplication extends Match {

	@Column (name="User_Id")
	int userId;
	
	@Column (name="User_ToInform")
	int userToInform;
	
	@Column (name="Offer_Id")
	int offerId;
	
	public ManualMatchApplication() {}

	public ManualMatchApplication(int offerId, boolean offerAproved, boolean applicationAproved, int userId) 
	{
		super(offerAproved, applicationAproved, "", 0);
		this.offerId = offerId;
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

	public int getOfferId() {
		return offerId;
	}

	public void setOfferId(int offerId) {
		this.offerId = offerId;
	}
	
	

}

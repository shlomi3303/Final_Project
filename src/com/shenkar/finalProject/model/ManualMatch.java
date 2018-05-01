package com.shenkar.finalProject.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table (name="manualMatch")
public class ManualMatch extends Match {

	@Column (name="User_Id")
	int userId;
	
	
	public ManualMatch() {}

	public ManualMatch(int offerId, int applicationID, boolean offerAproved, boolean applicationAproved, int userId) 
	{
		super(offerId, applicationID, offerAproved, applicationAproved, "", 0);
		this.userId = userId;
	}

}

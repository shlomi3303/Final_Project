package com.shenkar.finalProject.classes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table (name="AutoMatch")
public class AutoMatch extends Match 
{
	
	@Column (name="Offer_Id")
	int offerId;
	
	@Column (name="Application_Id")
	int applicationId;
	
	public AutoMatch() {}

	public AutoMatch(boolean offerAproved, boolean applicationAproved, String status, int tTL, int reminderCount,
			String category, int offerId, int applicationId) 
	{
		super(offerAproved, applicationAproved, status, tTL, reminderCount, category);
		this.offerId=offerId;
		this.applicationId=applicationId;
	}

	public int getOfferId() {
		return offerId;
	}

	public void setOfferId(int offerId) {
		this.offerId = offerId;
	}

	public int getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}
	
	

}

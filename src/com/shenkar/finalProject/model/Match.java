package com.shenkar.finalProject.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Match 
{	
	@Id
    @Column (name="Match_Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int matchId;
	
   
	
	@Column (name="Offer_Aproved")
	boolean offerAproved;
	
	@Column (name="Application_Aproved")
	boolean applicationAproved;
	
	@Column (name="Status")
	String status;
	
	@Column (name="TTL")
	int TTL;
	
	@Column (name = "Reminder_Counter")
	int reminderCount;
	
	public Match(){}
	
	public Match(boolean offerAproved, boolean applicationAproved, String status,
			int tTL, int reminderCount) 
	{
		this.offerAproved = offerAproved;
		this.applicationAproved = applicationAproved;
		this.status = status;
		this.reminderCount = reminderCount;
		TTL = tTL;
	}

	/**
	 * @return the matchId
	 */
	public int getMatchId() {
		return matchId;
	}

	/**
	 * @param matchId the matchId to set
	 */
	public void setMatchId(int matchId) {
		this.matchId = matchId;
	}

	/**
	 * @return the offerAproved
	 */
	public boolean getOfferAproved() {
		return offerAproved;
	}

	/**
	 * @param offerAproved the offerAproved to set
	 */
	public void setOfferAproved(boolean offerAproved) {
		this.offerAproved = offerAproved;
	}

	/**
	 * @return the applicationAproved
	 */
	public boolean getApplicationAproved() {
		return applicationAproved;
	}

	/**
	 * @param applicationAproved the applicationAproved to set
	 */
	public void setApplicationAproved(boolean applicationAproved) {
		this.applicationAproved = applicationAproved;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public int getTTL() {
		return TTL;
	}

	
	public void setTTL(int tTL) {
		TTL = tTL;
	}

	public int getReminderCount() {
		return reminderCount;
	}

	public void setReminderCount(int reminderCount) {
		this.reminderCount = reminderCount;
	}
	
	
	
}

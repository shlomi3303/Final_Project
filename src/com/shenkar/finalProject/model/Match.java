package com.shenkar.finalProject.model;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public abstract class Match 
{	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int matchId;
	
	int offerId;
	int applicationID;
	boolean offerAproved;
	boolean applicationAproved;
	String status;
	int TTL;
	
	public Match(){}
	
	public Match(int offerId, int applicationID, boolean offerAproved, boolean applicationAproved, String status,
			int tTL) 
	{
		this.offerId = offerId;
		this.applicationID = applicationID;
		this.offerAproved = offerAproved;
		this.applicationAproved = applicationAproved;
		this.status = status;
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
	 * @return the offerId
	 */
	public int getOfferId() {
		return offerId;
	}

	/**
	 * @param offerId the offerId to set
	 */
	public void setOfferId(int offerId) {
		this.offerId = offerId;
	}

	/**
	 * @return the applicationID
	 */
	public int getApplicationID() {
		return applicationID;
	}

	/**
	 * @param applicationID the applicationID to set
	 */
	public void setApplicationID(int applicationID) {
		this.applicationID = applicationID;
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

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the tTL
	 */
	public int getTTL() {
		return TTL;
	}

	/**
	 * @param tTL the tTL to set
	 */
	public void setTTL(int tTL) {
		TTL = tTL;
	}

}

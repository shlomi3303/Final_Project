package com.shenkar.finalProject.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import java.util.Date;

import javax.persistence.*;
import com.sun.istack.internal.NotNull;

public class Offer 
{
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int offerId;
	String userId;
	
	@Column(name="CREATION_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date period;
	
	private String location;
	private String category;
	
	@Column(name="CREATION_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date periodic;
	
	private boolean urgency;
	private int TTL;
	private String description;
	private String userLocation;
	private boolean isAprroved;
	
	public Offer(){}
	
	public Offer(String userId, java.util.Date period2, String location, String category, java.util.Date periodic2,
			boolean urgency, int tTL, String description, String userLocation, boolean isAprroved) 
	{
		super();
	
		this.userId = userId;
		this.period = period2;
		this.location = location;
		this.category = category;
		this.periodic = periodic2;
		this.urgency = urgency;
		TTL = tTL;
		this.description = description;
		this.userLocation = userLocation;
		this.isAprroved = isAprroved;
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
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the period
	 */
	public Date getPeriod() {
		return period;
	}
	/**
	 * @param period the period to set
	 */
	public void setPeriod(Date period) {
		this.period = period;
	}
	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	/**
	 * @return the periodic
	 */
	public Date getPeriodic() {
		return periodic;
	}
	/**
	 * @param periodic the periodic to set
	 */
	public void setPeriodic(Date periodic) {
		this.periodic = periodic;
	}
	/**
	 * @return the urgency
	 */
	public boolean getUrgency() {
		return urgency;
	}
	/**
	 * @param urgency the urgency to set
	 */
	public void setUrgency(boolean urgency) {
		this.urgency = urgency;
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
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the userLocation
	 */
	public String getUserLocation() {
		return userLocation;
	}
	/**
	 * @param userLocation the userLocation to set
	 */
	public void setUserLocation(String userLocation) {
		this.userLocation = userLocation;
	}
	/**
	 * @return the isAprroved
	 */
	public boolean getIsAprroved() {
		return isAprroved;
	}
	/**
	 * @param isAprroved the isAprroved to set
	 */
	public void setIsAprroved(boolean isAprroved) {
		this.isAprroved = isAprroved;
	}
	
	
	
	
	
	
	
	
	
	
	
	

}

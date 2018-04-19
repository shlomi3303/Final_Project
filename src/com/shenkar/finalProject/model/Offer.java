package com.shenkar.finalProject.model;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table (name="offers")
public class Offer 
{
	
	@Id
    @Column (name="Offer_Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	int offerId;
	
	@Column (name="User_Id")
	int userId;
	
	@Column(name="CREATION_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	Date period;
	
	@Column(name="Location")
	String location;
	
	@Column(name="Category")
	String category;
	
	@Column(name="Periodic")
	@Temporal(TemporalType.TIMESTAMP)
	Date periodic;
	
	@Column(name="Urgency")
	boolean urgency;
	
	@Column(name="TTL")
	int TTL;
	
	@Column(name="Description")
	String description;
	
	@Column(name="Status")
	String status;
	
	@Column(name="User_Location")
	String userLocation;
	
	@Column(name="Is_Aprroved")
	boolean isAprroved;
	
	@Column(name="Gender")
	String gender;
	
	@Column(name="Language")
	String language;
	
	@Column(name="Image")
	String img;
	
	//Student Attribute
	@Column(name="Education_Level")
	String educationLevel;
	
	@Column(name="field_Of_Study")
	String fieldOfStudy;
	
	public Offer(){}
	
	public Offer(int userId, Date period, String location, String category, Date periodic, boolean urgency, int tTL,
			String description, String status, String userLocation, boolean isAprroved, String gender, String language,
			String educationLevel, String fieldOfStudy, String img) 
	{
		super();
		this.userId = userId;
		this.period = period;
		this.location = location;
		this.category = category;
		this.periodic = periodic;
		this.urgency = urgency;
		TTL = tTL;
		this.description = description;
		this.status = status;
		this.userLocation = userLocation;
		this.isAprroved = isAprroved;
		this.gender = gender;
		this.language = language;
		this.educationLevel = educationLevel;
		this.fieldOfStudy = fieldOfStudy;
		this.img = img;
	}

	public Offer(int offerid, int userId, Date period, String location, String category, Date periodic, boolean urgency, int tTL,
			String description, String status, String userLocation, boolean isAprroved, String gender, String language,
			String educationLevel, String fieldOfStudy, String img) 
	{
		super();
		this.offerId = offerid;
		this.userId = userId;
		this.period = period;
		this.location = location;
		this.category = category;
		this.periodic = periodic;
		this.urgency = urgency;
		TTL = tTL;
		this.description = description;
		this.status = status;
		this.userLocation = userLocation;
		this.isAprroved = isAprroved;
		this.gender = gender;
		this.language = language;
		this.educationLevel = educationLevel;
		this.fieldOfStudy = fieldOfStudy;
		this.img = img;
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
	public int getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
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
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * @return the educationLevel
	 */
	public String getEducationLevel() {
		return educationLevel;
	}

	/**
	 * @param educationLevel the educationLevel to set
	 */
	public void setEducationLevel(String educationLevel) {
		this.educationLevel = educationLevel;
	}

	/**
	 * @return the fieldOfStudy
	 */
	public String getFieldOfStudy() {
		return fieldOfStudy;
	}

	/**
	 * @param fieldOfStudy the fieldOfStudy to set
	 */
	public void setFieldOfStudy(String fieldOfStudy) {
		this.fieldOfStudy = fieldOfStudy;
	}

	/**
	 * @return the img
	 */
	public String getImg() {
		return img;
	}

	/**
	 * @param img the img to set
	 */
	public void setImg(String img) {
		this.img = img;
	}
	


}

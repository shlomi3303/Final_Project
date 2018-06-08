package com.shenkar.finalProject.model;

import java.util.Date;

import javax.persistence.*;

@MappedSuperclass
public class Offer 
{
	
	@Id
    @Column (name="Offer_Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	int offerId;
	
	@Column (name="User_Id")
	int userId;
	
	@Column(name="Period")
	@Temporal(TemporalType.TIMESTAMP)
	Date period;
	
	@Column(name="Location")
	String location;
	
	@Column(name="Periodic")
	@Temporal(TemporalType.TIMESTAMP)
	Date periodic;
	
	@Column(name="TTL")
	protected int TTL;
	
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
	
	@Column(name="Title")
	String title;
	
	@Column(name="Description")
	String description;
	
	@Column (name = "Is_Archive")
	boolean isArchive;
	
	@Column(name="Category")
	String category;

	public Offer(){}
	
	public Offer(int userId, Date period, String location, Date periodic,
			String status, String userLocation, boolean isAprroved, 
			String gender, String language, String img, String title, String description) 
	{
		super();
		this.userId = userId;
		this.period = period;
		this.location = location;
		this.periodic = periodic;
		this.status = status;
		this.userLocation = userLocation;
		this.isAprroved = isAprroved;
		this.gender = gender;
		this.language = language;
		this.img = img;
		this.title = title;
		this.description = description;
		this.isArchive = false;
		this.category = "";
	}

	public int getOfferId() {
		return offerId;
	}

	public void setOfferId(int offerId) {
		this.offerId = offerId;
	}

	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getPeriod() {
		return period;
	}

	public void setPeriod(Date period) {
		this.period = period;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	public Date getPeriodic() {
		return periodic;
	}

	public void setPeriodic(Date periodic) {
		this.periodic = periodic;
	}

	public int getTTL() {
		return TTL;
	}

	public void setTTL(int tTL) {
		TTL = tTL;
	}

	public String getUserLocation() {
		return userLocation;
	}

	public void setUserLocation(String userLocation) {
		this.userLocation = userLocation;
	}

	public boolean getIsAprroved() {
		return isAprroved;
	}

	public void setIsAprroved(boolean isAprroved) {
		this.isAprroved = isAprroved;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean getArchive() {
		return isArchive;
	}

	public void setArchive(boolean isArchive) {
		this.isArchive = isArchive;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	
	
}

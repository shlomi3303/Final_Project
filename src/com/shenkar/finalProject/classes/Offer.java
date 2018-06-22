package com.shenkar.finalProject.classes;

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
	
	@Column(name="City")
	String city;
	
	@Column(name="Street")
	String street;
	
	@Column(name="House_Number")
	int houseNumber;
	
	@Column(name="Latitude")
	String latitude;
	
	@Column(name="Longitude")
	String Longitude;
	
	@Column(name="TTL")
	protected int TTL;
	
	@Column(name="Status")
	String status;
	
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
	
	public Offer(int userId, Date period, String city, String street, int houseNumber, String latitude,
			String longitude, String status, 
			String gender, String language, String img, String title, String description) 
	{
		this.userId = userId;
		this.period = period;
		this.city = city;
		this.street = street;
		this.houseNumber = houseNumber;
		this.latitude = latitude;
		Longitude = longitude;
		TTL = 0;
		this.status = status;
		this.isAprroved = true;
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

	public int getTTL() {
		return TTL;
	}

	public void setTTL(int tTL) {
		TTL = tTL;
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

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return Longitude;
	}

	public void setLongitude(String longitude) {
		Longitude = longitude;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(int houseNumber) {
		this.houseNumber = houseNumber;
	}
}

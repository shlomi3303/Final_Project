package com.shenkar.finalProject.classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.google.gson.annotations.Expose;

@MappedSuperclass
public class Application 
{
		protected
		@Expose
		@Id
		@Column (name="Application_Id")
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		int applicationID;
	
		@Expose
		@Column (name="User_Id")
		protected
		int userId;

		@Expose
		@Column(name="Period")
		@Temporal(TemporalType.TIMESTAMP)
		Date period;

		@Expose
		@Column(name="City")
		String city;
		
		@Expose
		@Column(name="Street")
		String street;
		
		@Expose
		@Column(name="House_Number")
		int houseNumber;
		
		@Expose
		@Column(name="Latitude")
		String latitude;
		
		@Expose
		@Column(name="Longitude")
		String Longitude;
		
		@Expose
		@Column(name="Urgency")
		boolean urgency;
		
		@Expose
		@Column(name="TTL")
		protected int TTL;
		
		@Expose
		@Column(name="Status")
		String status;
		
		@Expose
		@Column(name="Is_Aprroved")
		boolean isAprroved;
		
		@Expose
		@Column(name="Gender")
		String gender;
		
		@Expose
		@Column(name="Language")
		String language;
		
		@Expose
		@Column(name="Image")
		String img;
		
		@Expose
		@Column(name="Title")
		String title;
		
		@Expose
		@Column(name="Description")
		String description;
		
		@Expose
		@Column (name = "Is_Archive")
		boolean isArchive;
		
		@Expose
		@Column(name="Category")
		String category;
		
		@Expose(serialize=false)
		@ElementCollection(fetch=FetchType.EAGER)
		List<Integer> list;
		
		@Expose(serialize=false)
		@ElementCollection(fetch=FetchType.EAGER)
		Set <Integer> refuseList;
	
		public Application(){}
		
		public Application(int userId, Date period, String city, String street, int houseNumber, String latitude,
				String longitude, boolean urgency, String status,
				String gender, String language, String img, String title, String description) 
		{
			this.userId = userId;
			this.period = period;
			this.city = city;
			this.street = street;
			this.houseNumber = houseNumber;
			this.latitude = latitude;
			Longitude = longitude;
			this.urgency = urgency;
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
			this.list=new ArrayList<Integer>();
			this.refuseList = Collections.synchronizedSet(new HashSet<Integer>());
		
		}
		
		public int getApplicationID() {
			return applicationID;
		}

		public void setApplicationID(int applicationID) {
			this.applicationID = applicationID;
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

		public boolean getUrgency() {
			return urgency;
		}

		public void setUrgency(boolean urgency) {
			this.urgency = urgency;
		}

		public int getTTL() {
			return TTL;
		}

		public void setTTL(int tTL) {
			TTL = tTL;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public boolean getIsAprroved() {
			return isAprroved;
		}

		public void setIsAprroved(boolean isAprroved) {
			this.isAprroved = isAprroved;
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

		public List<Integer> getList() {
			return list;
		}

		public void setList(List<Integer> list) {
			this.list = list;
		}

		public Set<Integer> getRefuseList() {
			return refuseList;
		}

		public void setRefuseList(Set<Integer> refuseList) {
			this.refuseList = refuseList;
		}
}

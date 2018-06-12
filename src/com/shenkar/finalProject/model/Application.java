package com.shenkar.finalProject.model;

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

@MappedSuperclass
public class Application 
{
		protected
		@Id
		@Column (name="Application_Id")
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		int applicationID;
	
		@Column (name="User_Id")
		protected
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
		
		@Column(name="Periodic")
		@Temporal(TemporalType.TIMESTAMP)
		Date periodic;
		
		@Column(name="Urgency")
		boolean urgency;
		
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
		
		@ElementCollection(fetch=FetchType.EAGER)
		List<Integer> list;
		
		@ElementCollection(fetch=FetchType.EAGER)
		Set <Integer> refuseList;
	
		public Application(){}
		
		public Application(int userId, Date period, String city, String street, int houseNumber, String latitude,
				String longitude, Date periodic, boolean urgency, String status,
				String gender, String language, String img, String title, String description) 
		{
			this.userId = userId;
			this.period = period;
			this.city = city;
			this.street = street;
			this.houseNumber = houseNumber;
			this.latitude = latitude;
			Longitude = longitude;
			this.periodic = periodic;
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

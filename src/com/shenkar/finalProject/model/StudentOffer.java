package com.shenkar.finalProject.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table (name="offerStudent")
public class StudentOffer extends Offer 
{
	@Column(name="Education_Level")
	String educationLevel;
	
	@Column(name="field_Of_Study")
	String fieldOfStudy;
	
	@Column (name="Desh_homeWorks")
	boolean homeWorks;
	
	@Column (name="Des_testStudy")
	boolean testStudy;
	
	@Column (name="Des_practice")
	boolean practice;
	
	public StudentOffer() {super();}

	
	
	public StudentOffer(int userId, Date period, String city, String street, int houseNumber, String latitude,
			String longitude, Date periodic, String status,  String gender, String language,
			String img, String title, String description, String educationLevel, String fieldOfStudy,
			boolean homeWorks,boolean testStudy, boolean practice) 
	{
		super(userId, period, city, street, houseNumber, latitude, longitude, periodic, status, gender, language,
				img, title, description);
		this.educationLevel = educationLevel;
		this.fieldOfStudy = fieldOfStudy;
		this.homeWorks = homeWorks;
		this.testStudy = testStudy;
		this.practice = practice;
	}



	public String getEducationLevel() {
		return educationLevel;
	}


	
	public void setEducationLevel(String educationLevel) {
		this.educationLevel = educationLevel;
	}


	
	public String getFieldOfStudy() {
		return fieldOfStudy;
	}


	
	public void setFieldOfStudy(String fieldOfStudy) {
		this.fieldOfStudy = fieldOfStudy;
	}


	
	public boolean getHomeWorks() {
		return homeWorks;
	}

	public void setHomeWorks(boolean homeWorks) {
		this.homeWorks = homeWorks;
	}

	public boolean getTestStudy() {
		return testStudy;
	}

	public void setTestStudy(boolean testStudy) {
		this.testStudy = testStudy;
	}


	public boolean getPractice() {
		return practice;
	}

	public void setPractice(boolean practice) {
		this.practice = practice;
	}
	
}

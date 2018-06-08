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

	public StudentOffer(int userId, Date period, String location, Date periodic, boolean urgency,
			String userLocation,String gender, String language, String img, String title, String description,
			String educationLevel, String fieldOfStudy, boolean homeWorks, boolean testStudy, boolean practice, String status) 
	{
		super(userId, period, location, periodic, status,userLocation, false, gender, language, img, title, description);
		this.educationLevel = educationLevel;
		this.fieldOfStudy = fieldOfStudy;
		this.homeWorks = homeWorks;
		this.testStudy = testStudy;
		this.practice = practice;
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
	 * @return the homeWorks
	 */
	public boolean getHomeWorks() {
		return homeWorks;
	}


	/**
	 * @param homeWorks the homeWorks to set
	 */
	public void setHomeWorks(boolean homeWorks) {
		this.homeWorks = homeWorks;
	}


	/**
	 * @return the testStudy
	 */
	public boolean getTestStudy() {
		return testStudy;
	}


	/**
	 * @param testStudy the testStudy to set
	 */
	public void setTestStudy(boolean testStudy) {
		this.testStudy = testStudy;
	}


	/**
	 * @return the practice
	 */
	public boolean getPractice() {
		return practice;
	}


	/**
	 * @param practice the practice to set
	 */
	public void setPractice(boolean practice) {
		this.practice = practice;
	}
	
}

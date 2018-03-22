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

import javax.persistence.*;
import com.google.gson.*;
import com.google.gson.annotations.Expose;
import com.sun.istack.internal.NotNull;

public class User 
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private int id;
    
    @Id
    @NotNull
    //@Expose
	 private String userId;
	 
    @NotNull
	 private String firstname;
    
    @NotNull
	 private String lastname;
    
    //@Column(unique = true)
    @NotNull
	 private String mail;
    
    @NotNull
	 private String password;
    
    @NotNull
	 private String phone;
	 
    @NotNull
	 private int age;
    
    @NotNull
	 private String familyStatus;
	 
    @NotNull
	 private int kids;
	 
	 private String userLocation;
	 
	 private String interests;
	 
	 
	public User() {} 

	
	/**
	 * @param userID
	 * @param firstname
	 * @param lastname
	 * @param mail
	 * @param password
	 * @param phone
	 * @param age
	 * @param familyStatus
	 * @param kids
	 * @param userLocation
	 * @param interests
	 */
	public User(String userId, String firstname, String lastname, String mail, String password, String phone, int age,
			String familyStatus, int kids, String userLocation, String interests) {
		super();
		//this.setId(id);
		this.setUserId(userId);
		this.setFirstname(firstname);
		this.setLastname(lastname);
		this.setMail(mail);
		this.setPassword(password);
		this.setPhone(phone);
		this.setAge(age);
		this.setFamilyStatus(familyStatus);
		this.setKids(kids);
		this.setUserLocation(userLocation);
		this.setInterests(interests);
	}
	
	public User(int id, String userId, String firstname, String lastname, String mail, String password, String phone, int age,
			String familyStatus, int kids, String userLocation, String interests) {
		super();
		this.setId(id);
		this.setUserId(userId);
		this.setFirstname(firstname);
		this.setLastname(lastname);
		this.setMail(mail);
		this.setPassword(password);
		this.setPhone(phone);
		this.setAge(age);
		this.setFamilyStatus(familyStatus);
		this.setKids(kids);
		this.setUserLocation(userLocation);
		this.setInterests(interests);
	}

	public int getId() {
		return id;
	}

	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUserId()
	{
		return userId;
	}
	
	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getFamilyStatus() {
		return familyStatus;
	}

	public void setFamilyStatus(String familyStatus) {
		this.familyStatus = familyStatus;
	}

	public int getKids() {
		return kids;
	}

	public void setKids(int kids) {
		this.kids = kids;
	}

	public String getUserLocation() {
		return userLocation;
	}

	public void setUserLocation(String userLocation) {
		this.userLocation = userLocation;
	}

	public String getInterests() {
		return this.interests ;
	}

	public void setInterests(String interests) {
		this.interests = interests;
	}
	
	public String getJsonString(User user) {
	    // Before converting to GSON check value of id
	    Gson gson = null;
	    gson = new Gson();
	    
	    return gson.toJson(user);
	}
	
}
	 
	 
	 
	

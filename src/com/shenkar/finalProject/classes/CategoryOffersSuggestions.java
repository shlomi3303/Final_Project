package com.shenkar.finalProject.classes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name="CategoryOffersSuggestions")
public class CategoryOffersSuggestions {
	
	@Id
    @Column (name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column (name="Ride_Olders")
	int ride_olders;
	
	@Column (name="Ride_Student")
	int ride_student;
	
	@Column (name="Ride_Handyman")
	int ride_handyman;
	
	@Column (name="Handyman_Olders")
	int handyman_olders;
	
	@Column (name="Handyman_Student")
	int handyman_student;
	
	@Column (name="Olders_Student")
	int olders_student;

	public CategoryOffersSuggestions(){}
	
	public CategoryOffersSuggestions(int ride_olders, int ride_student, int ride_handyman,
			int handyman_olders, int handyman_student, int olders_student) {
		this.ride_olders = ride_olders;
		this.ride_student = ride_student;
		this.ride_handyman = ride_handyman;
		this.handyman_olders = handyman_olders;
		this.handyman_student = handyman_student;
		this.olders_student = olders_student;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRide_olders() {
		return ride_olders;
	}

	public void setRide_olders(int ride_olders) {
		this.ride_olders = ride_olders;
	}

	public int getRide_student() {
		return ride_student;
	}

	public void setRide_student(int ride_student) {
		this.ride_student = ride_student;
	}

	public int getRide_handyman() {
		return ride_handyman;
	}

	public void setRide_handyman(int ride_handyman) {
		this.ride_handyman = ride_handyman;
	}

	public int getHandyman_olders() {
		return handyman_olders;
	}

	public void setHandyman_olders(int handyman_olders) {
		this.handyman_olders = handyman_olders;
	}

	public int getHandyman_student() {
		return handyman_student;
	}

	public void setHandyman_student(int handyman_student) {
		this.handyman_student = handyman_student;
	}

	public int getOlders_student() {
		return olders_student;
	}

	public void setOlders_student(int olders_student) {
		this.olders_student = olders_student;
	}
	
	
	
	
}

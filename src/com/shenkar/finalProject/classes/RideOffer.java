package com.shenkar.finalProject.classes;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table (name="offerRide")
public class RideOffer extends Offer 
{

	@Column (name="Destination_City")
	String destCity;
	
	@Column (name="Destination_Street")
	String destStreet;
	
	@Column (name="Destination_HouseNum")
	int destHouseNum;
	
	@Column(name="Destination_Latitude")
	String destlatitude;
	
	@Column(name="Destination_Longitude")
	String destLongitude;
	
	@Column(name="End_Period")
	@Temporal(TemporalType.TIMESTAMP)
	Date endPeriod;

	public RideOffer() {super();}

	public RideOffer(int userId, Date period, String city, String street, int houseNumber, String latitude,
			String longitude, String status, String gender, String language,
			String img, String title, String description, String destCity, String destStreet, int destHouseNum, String destlatitude,
			String destLongitude, Date endPeriod) 
	{
		super(userId, period, city, street, houseNumber, latitude, longitude, status, gender, language,
				img, title, description);
		this.destCity = destCity;
		this.destStreet = destStreet;
		this.destHouseNum = destHouseNum;
		this.destlatitude = destlatitude;
		this.destLongitude = destLongitude;
		this.endPeriod = endPeriod;
	}

	public String getDestCity() {
		return destCity;
	}

	public void setDestCity(String destCity) {
		this.destCity = destCity;
	}

	public String getDestStreet() {
		return destStreet;
	}

	public void setDestStreet(String destStreet) {
		this.destStreet = destStreet;
	}

	public int getDestHouseNum() {
		return destHouseNum;
	}

	public void setDestHouseNum(int destHouseNum) {
		this.destHouseNum = destHouseNum;
	}

	public String getDestlatitude() {
		return destlatitude;
	}

	public void setDestlatitude(String destlatitude) {
		this.destlatitude = destlatitude;
	}

	public String getDestLongitude() {
		return destLongitude;
	}

	public void setDestLongitude(String destLongitude) {
		this.destLongitude = destLongitude;
	}

	public Date getEndPeriod() {
		return endPeriod;
	}

	public void setEndPeriod(Date endPeriod) {
		this.endPeriod = endPeriod;
	}

	
}

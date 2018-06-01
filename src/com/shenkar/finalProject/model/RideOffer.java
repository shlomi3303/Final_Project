package com.shenkar.finalProject.model;

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

	@Column (name="Source")
	String source;
	
	@Column (name="Destination")
	String destination;
	
	@Column(name="End_Period")
	@Temporal(TemporalType.TIMESTAMP)
	Date endPeriod;

	public RideOffer() {super();}

	public RideOffer(int userId, Date period, Date periodic, boolean urgency,
			String userLocation, String gender, String language, String img, String title, String description,
			String source, String destination, Date endPeriod, String status) 
	{
		super(userId, period, "", periodic, urgency, status, userLocation, false, gender, language, img, title, description);
		this.source = source;
		this.destination = destination;
		this.endPeriod = endPeriod;
	}


	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}


	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * @return the destination
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * @param destination the destination to set
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}


	/**
	 * @return the timeRange
	 */
	public Date getEndPeriod() {
		return endPeriod;
	}

	/**
	 * @param timeRange the timeRange to set
	 */
	public void setEndPeriod(Date endPeriod) {
		this.endPeriod = endPeriod;
	}
}

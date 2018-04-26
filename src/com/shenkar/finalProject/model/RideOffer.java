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
	
	@Column(name="Time_Range")
	@Temporal(TemporalType.TIMESTAMP)
	Date timeRange;

	public RideOffer() {super();}

	public RideOffer(int userId, Date period, Date periodic, boolean urgency,
			String userLocation, String gender, String language, String img, String title, String description,
			String source, String destination, Date timeRange) 
	{
		super(userId, period, "", periodic, urgency, "Waiting for approval", userLocation, false, gender, language, img, title, description);
		this.TTL = -1;
		this.source = source;
		this.destination = destination;
		this.timeRange = timeRange;
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
	public Date getTimeRange() {
		return timeRange;
	}


	/**
	 * @param timeRange the timeRange to set
	 */
	public void setTimeRange(Date timeRange) {
		this.timeRange = timeRange;
	}
}

package com.shenkar.finalProject.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table (name="applicationsOlders")
public class OldersApplication extends Application 
{

	@Column (name="Des_Shopping")
	boolean shopping;
	
	@Column (name="Des_Cooking")
	boolean cooking;
	
	@Column (name="Des_EscortedAged")
	boolean escortedAged;
	
	@Column (name="Des_Conversation")
	boolean conversation;
	
	public OldersApplication() {
		super();
	}

	public OldersApplication(int userId, Date period, String city, String street, int houseNumber, String latitude,
			String longitude, Date periodic, boolean urgency, String status, String gender, String language, String img,
			String title, String description, boolean shopping, boolean cooking, boolean escortedAged,
			boolean conversation) 
	{
		super(userId, period, city, street, houseNumber, latitude, longitude, periodic, urgency, status, gender, language, img,
		title, description);
		this.shopping = shopping;
		this.cooking = cooking;
		this.escortedAged = escortedAged;
		this.conversation  = conversation;
				
	}

	public boolean getShopping() {
		return shopping;
	}

	public void setShopping(boolean shopping) {
		this.shopping = shopping;
	}

	public boolean getCooking() {
		return cooking;
	}

	public void setCooking(boolean cooking) {
		this.cooking = cooking;
	}

	public boolean getEscortedAged() {
		return escortedAged;
	}

	public void setEscortedAged(boolean escortedAged) {
		this.escortedAged = escortedAged;
	}

	public boolean getConversation() {
		return conversation;
	}

	public void setConversation(boolean conversation) {
		this.conversation = conversation;
	}


	
}

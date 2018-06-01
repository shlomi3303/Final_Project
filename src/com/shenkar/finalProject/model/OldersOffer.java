package com.shenkar.finalProject.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table (name="offerOlders")
public class OldersOffer extends Offer
{

	@Column (name="Des_Shopping")
	boolean shopping;
	
	@Column (name="Des_Cooking")
	boolean cooking;
	
	@Column (name="Des_EscortedAged")
	boolean escortedAged;
	
	@Column (name="Des_Conversation")
	boolean conversation;
	
	public OldersOffer() {super();}

	public OldersOffer(int userId, Date period, String location, Date periodic, boolean urgency,
			String userLocation, String gender, String language, String img,String title, String description,
			boolean shopping, boolean cooking, boolean escortedAged,
			boolean conversation, String status) 
	{
		super(userId, period, location, periodic, urgency, status, userLocation, false, gender, language, img, title, description);
		this.shopping = shopping;
		this.cooking = cooking;
		this.escortedAged = escortedAged;
		this.conversation  = conversation;
	}


	/**
	 * @return the shopping
	 */
	public boolean getShopping() {
		return shopping;
	}


	/**
	 * @param shopping the shopping to set
	 */
	public void setShopping(boolean shopping) {
		this.shopping = shopping;
	}


	/**
	 * @return the cooking
	 */
	public boolean getCooking() {
		return cooking;
	}


	/**
	 * @param cooking the cooking to set
	 */
	public void setCooking(boolean cooking) {
		this.cooking = cooking;
	}


	/**
	 * @return the escortedAged
	 */
	public boolean getEscortedAged() {
		return escortedAged;
	}


	/**
	 * @param escortedAged the escortedAged to set
	 */
	public void setEscortedAged(boolean escortedAged) {
		this.escortedAged = escortedAged;
	}


	/**
	 * @return the conversation
	 */
	public boolean getConversation() {
		return conversation;
	}


	/**
	 * @param conversation the conversation to set
	 */
	public void setConversation(boolean conversation) {
		this.conversation = conversation;
	}


	
}

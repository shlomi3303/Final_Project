package com.shenkar.finalProject.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table (name="offerHandyman")
public class HandymanOffer extends Offer
{

	@Column (name="Des_colorCorrections")
	boolean colorCorrections;
	
	@Column (name="Des_furniture")
	boolean furniture;
	
	@Column (name="Des_generalHangingWorks")
	boolean generalHangingWorks;
	
	@Column (name="Des_hangingOfLightFixtures")
	boolean hangingOfLightFixtures;
	
	@Column (name="Des_treatmentSocketsAndPowerPoints")
	boolean treatmentSocketsAndPowerPoints;

	public HandymanOffer() {super();}




	public HandymanOffer(int userId, Date period, String city, String street, int houseNumber, String latitude,
			String longitude, Date periodic, String status, String gender, String language,
			String img, String title, String description,
			boolean colorCorrections,boolean furniture,  boolean generalHangingWorks, boolean hangingOfLightFixtures, boolean treatmentSocketsAndPowerPoints) 
 
	{
		super(userId, period, city, street, houseNumber, latitude, longitude, periodic, status, gender, language,
				img, title, description);
		this.colorCorrections = colorCorrections;
		this.furniture = furniture;
		this.generalHangingWorks = generalHangingWorks;
		this.hangingOfLightFixtures = hangingOfLightFixtures;
		this.treatmentSocketsAndPowerPoints = treatmentSocketsAndPowerPoints;
	}




	/**
	 * @return the colorCorrections
	 */
	public boolean getColorCorrections() {
		return colorCorrections;
	}


	/**
	 * @param colorCorrections the colorCorrections to set
	 */
	public void setColorCorrections(boolean colorCorrections) {
		this.colorCorrections = colorCorrections;
	}


	/**
	 * @return the furniture
	 */
	public boolean getFurniture() {
		return furniture;
	}


	/**
	 * @param furniture the furniture to set
	 */
	public void setFurniture(boolean furniture) {
		this.furniture = furniture;
	}


	/**
	 * @return the generalHangingWorks
	 */
	public boolean getGeneralHangingWorks() {
		return generalHangingWorks;
	}


	/**
	 * @param generalHangingWorks the generalHangingWorks to set
	 */
	public void setGeneralHangingWorks(boolean generalHangingWorks) {
		this.generalHangingWorks = generalHangingWorks;
	}


	/**
	 * @return the hangingOfLightFixtures
	 */
	public boolean getHangingOfLightFixtures() {
		return hangingOfLightFixtures;
	}


	/**
	 * @param hangingOfLightFixtures the hangingOfLightFixtures to set
	 */
	public void setHangingOfLightFixtures(boolean hangingOfLightFixtures) {
		this.hangingOfLightFixtures = hangingOfLightFixtures;
	}


	/**
	 * @return the treatmentSocketsAndPowerPoints
	 */
	public boolean getTreatmentSocketsAndPowerPoints() {
		return treatmentSocketsAndPowerPoints;
	}


	/**
	 * @param treatmentSocketsAndPowerPoints the treatmentSocketsAndPowerPoints to set
	 */
	public void setTreatmentSocketsAndPowerPoints(boolean treatmentSocketsAndPowerPoints) {
		this.treatmentSocketsAndPowerPoints = treatmentSocketsAndPowerPoints;
	}

}

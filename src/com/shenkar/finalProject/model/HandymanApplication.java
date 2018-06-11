package com.shenkar.finalProject.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table (name="applicationsHandyman")
public class HandymanApplication extends Application 
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

	public HandymanApplication() {
		super();
	}

	public HandymanApplication(int userId, Date period, String city, String street, int houseNumber, String latitude,
			String longitude, Date periodic, boolean urgency, String status, String gender, String language, String img,
			String title, String description,
			boolean colorCorrections,boolean furniture,  boolean generalHangingWorks, boolean hangingOfLightFixtures, boolean treatmentSocketsAndPowerPoints) 
	{
		super(userId, period, city, street, houseNumber, latitude, longitude, periodic, urgency, status, gender, language, img,
				title, description);
		
		this.colorCorrections = colorCorrections;
		this.furniture = furniture;
		this.generalHangingWorks = generalHangingWorks;
		this.hangingOfLightFixtures = hangingOfLightFixtures;
		this.treatmentSocketsAndPowerPoints = treatmentSocketsAndPowerPoints;
	}


	public boolean getColorCorrections() {
		return colorCorrections;
	}

	void setColorCorrections(boolean colorCorrections) {
		this.colorCorrections = colorCorrections;
	}


	public boolean getFurniture() {
		return furniture;
	}

	public void setFurniture(boolean furniture) {
		this.furniture = furniture;
	}

	public boolean getGeneralHangingWorks() {
		return generalHangingWorks;
	}

	public void setGeneralHangingWorks(boolean generalHangingWorks) {
		this.generalHangingWorks = generalHangingWorks;
	}

	public boolean getHangingOfLightFixtures() {
		return hangingOfLightFixtures;
	}

	public void setHangingOfLightFixtures(boolean hangingOfLightFixtures) {
		this.hangingOfLightFixtures = hangingOfLightFixtures;
	}

	public boolean getTreatmentSocketsAndPowerPoints() {
		return treatmentSocketsAndPowerPoints;
	}

	public void setTreatmentSocketsAndPowerPoints(boolean treatmentSocketsAndPowerPoints) {
		this.treatmentSocketsAndPowerPoints = treatmentSocketsAndPowerPoints;
	}

}

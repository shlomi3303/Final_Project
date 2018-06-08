package com.shenkar.finalProject.classes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name="UserApplicationsInterests")
public class UserSubcategoryApplicationsInterests {
	
	@Id
    @Column (name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column (name="User_Id", unique=true)
	int userId;
	
	//sub category of student 
	@Column (name="Home_Works")
	int homeWorks;
	
	@Column (name="Test_Study")
	int testStudy;
	
	@Column (name="Practice")
	int practice;
	
	@Column (name="Ride")
	int ride;
	
	@Column (name="Des_Shopping")
	int shopping;
	
	@Column (name="Cooking")
	int cooking;
	
	@Column (name="EscortedAged")
	int escortedAged;
	
	@Column (name="Conversation")
	int conversation;
	
	@Column (name="Color_Corrections")
	int colorCorrections;
	
	@Column (name="Furniture")
	int furniture;
	
	@Column (name="General_Hanging_Works")
	int generalHangingWorks;
	
	@Column (name="Hanging_Of_LightFixtures")
	int hangingOfLightFixtures;
	
	@Column (name="Treatment_SocketsAndPowerPoints")
	int treatmentSocketsAndPowerPoints;
	
	public UserSubcategoryApplicationsInterests() {}

	public UserSubcategoryApplicationsInterests(int userId, int homeWorks, int testStudy, int practice, int ride, int shopping,
			int cooking, int escortedAged, int conversation, int colorCorrections, int furniture,
			int generalHangingWorks, int hangingOfLightFixtures, int treatmentSocketsAndPowerPoints) 
	{
		this.userId = userId;
		this.homeWorks = homeWorks;
		this.testStudy = testStudy;
		this.practice = practice;
		this.ride = ride;
		this.shopping = shopping;
		this.cooking = cooking;
		this.escortedAged = escortedAged;
		this.conversation = conversation;
		this.colorCorrections = colorCorrections;
		this.furniture = furniture;
		this.generalHangingWorks = generalHangingWorks;
		this.hangingOfLightFixtures = hangingOfLightFixtures;
		this.treatmentSocketsAndPowerPoints = treatmentSocketsAndPowerPoints;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getHomeWorks() {
		return homeWorks;
	}

	public void setHomeWorks(int homeWorks) {
		this.homeWorks = homeWorks;
	}

	public int getTestStudy() {
		return testStudy;
	}

	public void setTestStudy(int testStudy) {
		this.testStudy = testStudy;
	}

	public int getPractice() {
		return practice;
	}

	public void setPractice(int practice) {
		this.practice = practice;
	}

	public int getRide() {
		return ride;
	}

	public void setRide(int ride) {
		this.ride = ride;
	}

	public int getShopping() {
		return shopping;
	}

	public void setShopping(int shopping) {
		this.shopping = shopping;
	}

	public int getCooking() {
		return cooking;
	}

	public void setCooking(int cooking) {
		this.cooking = cooking;
	}

	public int getEscortedAged() {
		return escortedAged;
	}

	public void setEscortedAged(int escortedAged) {
		this.escortedAged = escortedAged;
	}

	public int getConversation() {
		return conversation;
	}

	public void setConversation(int conversation) {
		this.conversation = conversation;
	}

	public int getColorCorrections() {
		return colorCorrections;
	}

	public void setColorCorrections(int colorCorrections) {
		this.colorCorrections = colorCorrections;
	}

	public int getFurniture() {
		return furniture;
	}

	public void setFurniture(int furniture) {
		this.furniture = furniture;
	}

	public int getGeneralHangingWorks() {
		return generalHangingWorks;
	}

	public void setGeneralHangingWorks(int generalHangingWorks) {
		this.generalHangingWorks = generalHangingWorks;
	}

	public int getHangingOfLightFixtures() {
		return hangingOfLightFixtures;
	}

	public void setHangingOfLightFixtures(int hangingOfLightFixtures) {
		this.hangingOfLightFixtures = hangingOfLightFixtures;
	}

	public int getTreatmentSocketsAndPowerPoints() {
		return treatmentSocketsAndPowerPoints;
	}

	public void setTreatmentSocketsAndPowerPoints(int treatmentSocketsAndPowerPoints) {
		this.treatmentSocketsAndPowerPoints = treatmentSocketsAndPowerPoints;
	}
	
}

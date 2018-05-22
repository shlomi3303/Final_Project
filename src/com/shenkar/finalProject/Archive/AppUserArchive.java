package com.shenkar.finalProject.Archive;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Table (name="Archive_Users")
public class AppUserArchive 
{
    @Id
    @Column (name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private int id;
    	 
    @Column (name="First_Name")
	 private String firstname;
    
    @Column (name="Last_Name")
	 private String lastname;
    
    @Column(name = "Email", unique=true)
	 private String mail;
    
    @Column (name="Password")
	 private String password;
    
    @Column (name="Phone")
	 private String phone;
	 
    @Column (name="Age")
	 private int age;
    
    @Column (name="Family_Status")
	 private String familyStatus;
	 
    @Column (name="Kids")
	 private int kids;
    
    @Column (name="User_Location")
	 private String userLocation;
	 
    @Column (name="Help_For_Student")
	 private boolean student;
    
    @Column (name="Help_For_Olders")
	 private boolean olders;
  
    @Column (name="Handyman")
  	 private boolean handyman;
	 
    @Column (name="Ride")
 	 private boolean ride;
    
    @Column (name="Admin")
	 private boolean admin;
	 
	public AppUserArchive() {} 

	/**
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
	public AppUserArchive(String firstname, String lastname, String mail, String password, String phone, int age,
			String familyStatus, int kids, String userLocation, boolean student, boolean olders, boolean handyman,
			boolean ride) 
	{
		super();
		this.setFirstname(firstname);
		this.setLastname(lastname);
		this.setMail(mail);
		this.setPassword(password);
		this.setPhone(phone);
		this.setAge(age);
		this.setFamilyStatus(familyStatus);
		this.setKids(kids);
		this.setUserLocation(userLocation);
		this.student = student;
		this.olders = olders;
		this.handyman = handyman;
		this.ride = ride;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public boolean getStudent() {
		return student;
	}

	public void setStudent(boolean student) {
		this.student = student;
	}

	public boolean getOlders() {
		return olders;
	}


	public void setOlders(boolean olders) {
		this.olders = olders;
	}


	public boolean getHandyman() {
		return handyman;
	}

	public void setHandyman(boolean handyman) {
		this.handyman = handyman;
	}


	public boolean getRide() {
		return ride;
	}


	public void setRide(boolean ride) {
		this.ride = ride;
	}

	public boolean getAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
}

package com.shenkar.finalProject.classes;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Table (name="users")
public class AppUser 
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
   
    @Column(name="City")
	String city;
	
	@Column(name="Street")
	String street;
	
	@Column(name="House_Number")
	int houseNumber;
	
	@Column(name="Latitude")
	String latitude;
	
	@Column(name="Longitude")
	String longitude;
	 
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
	 
	public AppUser() {} 

	
	
	public AppUser(String firstname, String lastname, String mail, String password, String phone, int age,
			String familyStatus, int kids, String city, String street, int houseNumber, String latitude,
			String longitude, boolean student, boolean olders, boolean handyman, boolean ride) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.mail = mail;
		this.password = password;
		this.phone = phone;
		this.age = age;
		this.familyStatus = familyStatus;
		this.kids = kids;
		this.city = city;
		this.street = street;
		this.houseNumber = houseNumber;
		this.latitude = latitude;
		this.longitude = longitude;
		this.student = student;
		this.olders = olders;
		this.handyman = handyman;
		this.ride = ride;
		this.admin = false;
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



	public String getCity() {
		return city;
	}



	public void setCity(String city) {
		this.city = city;
	}



	public String getStreet() {
		return street;
	}



	public void setStreet(String street) {
		this.street = street;
	}



	public int getHouseNumber() {
		return houseNumber;
	}



	public void setHouseNumber(int houseNumber) {
		this.houseNumber = houseNumber;
	}



	public String getLatitude() {
		return latitude;
	}



	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}



	public String getLongitude() {
		return longitude;
	}



	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	
}

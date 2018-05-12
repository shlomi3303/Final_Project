package com.shenkar.finalProject.Globals;

public class ConstantVariables 
{
	
	public final static String waitingForAppApproval = "ממתין לאישור מבקש העזרה";
	
	public final static String waitingForOfferApproval = "ממתין לאישור מציע העזרה";
	
	public final static String bothSideApproved = "שני הצדדים אישרו";
		
	public final static String waitingForMatch = "ממתין למציאת התאמה";
	
	//variable for Email
	public final static String subjectMailApplication = "מחוברים לחיים: מישהו/י מוכנ/ה לעזור לך!";
	
	static String body1MailApplication = "נמצאה התאמה לאחת מבקשות העזרה שלך!";
	static String body2MailApplication = "נא כנס לאלפליקציה בכדי להשלים את תהליך העזרה";
	public final static String bodyMailApplication = body1MailApplication + System.lineSeparator() + body2MailApplication;
	
	//subject for case that both side approved
	public final static String subjectMailBothApproved = "מחוברים לחיים: שני הצדדים אישרו את ההתאמה!";
	
	static String body1MailBothApproved = "איזה כיף! שני הצדדים אישרו את ההתאמה!";
	static String body2MailBothApproved = "נבקש למלא סקר קצר על אופן ביצוע העזרה באתר בסיום בכדי להמשיך ולעזור לנו לטייב את תהליך ההתאמה";

	public final static String bodyMailBothSideApproved = body1MailBothApproved + System.lineSeparator() + body2MailBothApproved;



	
}

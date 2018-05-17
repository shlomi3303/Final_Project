package com.shenkar.finalProject.Globals;

public class ConstantVariables 
{
	
    public final static long TEST = 1000*60; 
	public final static long ONCE_PER_DAY = 1000*60*60*24;

	
	public final static String waitingForAppApproval = "ממתין לאישור מבקש העזרה";	
	public final static String waitingForOfferApproval = "ממתין לאישור מציע העזרה";
	public final static String waitingForMatch = "ממתין למציאת התאמה";
	
	public final static String bothSideApproved = "שני הצדדים אישרו";
		
	
	public final static String offerDeclined = "מציע העזרה דחה את ההתאמה";
	public final static String applicationDeclined = "מבקש העזרה דחה את ההתאמה";

	
	//variable for Email
	public final static String subjectMailApplication = "מחוברים לחיים: מישהו/י מוכנ/ה לעזור לך!";
	
	private static String body1MailApplication = "נמצאה התאמה לאחת מבקשות העזרה שלך!";
	private static String body2MailApplication = "נא כנס לאלפליקציה בכדי להשלים את תהליך העזרה";
	public final static String bodyMailApplication = body1MailApplication + System.lineSeparator() + body2MailApplication;
	
	//subject and body for case that both side approved
	public final static String subjectMailBothApproved = "מחוברים לחיים: שני הצדדים אישרו את ההתאמה!";
	
	private static String body1MailBothApproved = "איזה כיף! שני הצדדים אישרו את ההתאמה!";
	private static String body2MailBothApproved = "נבקש למלא סקר קצר על אופן ביצוע העזרה באתר בסיום בכדי להמשיך ולעזור לנו לטייב את תהליך ההתאמה";

	public final static String bodyMailBothSideApproved = body1MailBothApproved + System.lineSeparator() + body2MailBothApproved;
	
	//subject and body for case that one of the side decline
	public final static String subjectMailDecline= "מחוברים לחיים: ההתאמה נדחתה!";
	
	private static String body1MailOfferDecline = "מתנצלים, מציע העזרה דחה את ההתאמה";
	private static String body2MailOfferDecline = "אנו מציעים לך לנסות ולמצוא התאמה לבקשת העזרה דרך האתר";

	public final static String bodyMailOfferDecline = body1MailOfferDecline + System.lineSeparator() + body2MailOfferDecline;
	
	//subject and body for survey form
	public final static String subjectMailSurvey= "מחוברים לחיים: סקר קצר על חווית העזרה!";
	
	private static String body1MailSurvey = "נודה לך אם תשיב/י על שאלון קצר על חווית העזרה שבוצעה";
	private static String body2MailSurvey = "יש לציין שלא יעשה כל שימוש בנתונים, מלבד לצרכי הסקר ושיפור השירות";
	private static String body3MailSurvey = "תשובותיך יסייעו לנו רבות בהמשך שיפור ושימור שאר צרכי המתמשים";

	public final static String bodyMailSurvey = body1MailSurvey + System.lineSeparator() + body2MailSurvey + body3MailSurvey;
	
	//subject and body for send reminder to the application user that did'nt approve the match yet

	

	
}

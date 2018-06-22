package com.shenkar.finalProject.Globals;

public class ConstantVariables 
{
	
    public final static long TEST = 60000*60; 
    public final static long sessionMaxIdle =10*1000*60; 
	public final static long ONCE_PER_DAY = 1000*60*60*24;
	
	public final static String userName = "root";
	public final static String password = "root";
	public final static String URL = "jdbc:mysql://localhost:3306/finalproject?useSSL=false";
	
	public final static String newMsgInPrivateZone = "הודעות מחדשות ממתינות לך באזור האישי";
	
	public final static String waitingForYourApproval = "ממתין לאישורך";

	public final static String waitingForAppApproval = "ממתין לאישור מבקש העזרה";	
	public final static String waitingForOfferApproval = "ממתין לאישור מציע העזרה";
	
	public final static String waitingForMatch = "ממתין למציאת התאמה";
	public final static String waitingForbothSideApproval = "ממתין לאישור שני הצדדים";
	
	public final static String bothSideApproved = "שני הצדדים אישרו את ההתאמה";
		
	public final static String offerDeclined = "מציע העזרה דחה את ההתאמה";
	public final static String applicationDeclined = "מבקש העזרה דחה את ההתאמה";

	//////variable for Email///////
	
	public final static String subjectMailInterestedInApplication = "מחוברים לחיים: מישהו/י מוכנ/ה לעזור לך!";
	public final static String subjectMailInterestedInOffer = "מחוברים לחיים: מישהו/י מעוניינ/ת בעזרה שלך!";
	
	public final static String subjectMailAutoMatchApplication = "מחוברים לחיים: נמצאה התאמה לאחת מבקשות העזרה שלך!";
	public final static String subjectMailAutoMatchOffer = "מחוברים לחיים: נמצאה התאמה לאחת מהצעות העזרה שלך!";

	private static String body1MailApplication = "נמצאה התאמה לאחת מבקשות העזרה שלך!";
	private static String body2MailApplication = "נא כנס לאלפליקציה בכדי להשלים את תהליך העזרה";
	public final static String bodyMailApplication = body1MailApplication + System.lineSeparator() + body2MailApplication;
	
	
	private static String body1InterestedInOffer = "מישהו/י מעוניינ/ת בקבלת סיוע מאחת הצעות העזרה שהעלאת ליישומן מחוברים לחיים";
	private static String body2InterestedInOffer = "נא כנס/י לאלפליקציה בכדי להשלים את תהליך העזרה";
	public final static String bodyMailInterestedInOffer = body1InterestedInOffer + System.lineSeparator() + body2InterestedInOffer;
	
	
	//subject and body for case that both side approved
	public final static String subjectMailBothApproved = "מחוברים לחיים: שני הצדדים אישרו את ההתאמה!";
	
	private static String body1MailBothApproved = "איזה כיף! שני הצדדים אישרו את ההתאמה!";
	private static String body2MailBothApproved = "נבקש למלא סקר קצר על אופן ביצוע העזרה באתר בסיום בכדי להמשיך ולעזור לנו לטייב את תהליך ההתאמה";

	public final static String bodyMailBothSideApproved = body1MailBothApproved + System.lineSeparator() + body2MailBothApproved;
	
	//subject for case that one of the side decline
	public final static String subjectMailDecline= "מחוברים לחיים: ההתאמה נדחתה!";
	
	//body for case that the offer user decline the match
	private static String body1MailOfferDecline = "מתנצלים, מציע העזרה דחה את ההתאמה";
	private static String body2MailOfferDecline = "אנו מציעים לך לנסות ולמצוא התאמה לבקשת העזרה דרך האתר";
	public final static String bodyMailOfferDecline = body1MailOfferDecline + System.lineSeparator() + body2MailOfferDecline;
	
	//body for case that the application user decline the match
	private static String body1MailApplicationDecline = "מתנצלים, מבקש העזרה דחה את ההתאמה";
	private static String body2MailApplicationDecline = "אנו מציעים לך לנסות ולמצוא התאמה להצעת העזרה דרך האתר";
	public final static String bodyMailApplicationDecline = body1MailApplicationDecline + System.lineSeparator() + body2MailApplicationDecline;
	
	//subject and body for survey form
	public final static String subjectMailSurvey= "מחוברים לחיים: סקר קצר על חווית העזרה!";
	
	private static String body1MailSurvey = "נודה לך אם תשיב/י על שאלון קצר על חווית העזרה שבוצעה";
	private static String body2MailSurvey = "יש לציין שלא יעשה כל שימוש בנתונים, מלבד לצרכי הסקר ושיפור השירות";
	private static String body3MailSurvey = "תשובותיך יסייעו לנו רבות בהמשך שיפור ושימור שאר צרכי המתמשים";
	public final static String bodyMailSurvey = body1MailSurvey + System.lineSeparator() + body2MailSurvey + body3MailSurvey;
	
	//subject and body for send reminder to the application user that did'nt approve/decline the match yet
	public final static String subjectReminderApplication = "מחוברים לחיים: טרם השלמת את תהליך אישור ההתאמה לבקשת העזרה שלך";

	private static String body1MailReminderApplication = "טרם השלמת את תהליך אישור ההתאמה לבקשת העזרה שהעלאת לאתר";
	private static String body2MailReminderApplication = "אנא היכנס/י לאתר בכדי להשלים את תהליך ההתאמה";

	public final static String bodyMailReminderApplication = body1MailReminderApplication + System.lineSeparator() + body2MailReminderApplication;

	//subject and body for send reminder to the application user that did'nt approve/decline the match yet
	public final static String subjectReminderOffer = "מחוברים לחיים: טרם השלמת את תהליך אישור ההתאמה להצעת העזרה שלך";

	private static String body1MailReminderOffer = "טרם השלמת את תהליך אישור ההתאמה להצעת העזרה שהעלאת לאתר";
	private static String body2MailReminderOffer = "אנא היכנס/י לאתר בכדי להשלים את תהליך ההתאמה";

	public final static String bodyMailReminderOffer = body1MailReminderOffer + System.lineSeparator() + body2MailReminderOffer;
	
	
	
	
}

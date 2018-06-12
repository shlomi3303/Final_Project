package com.shenkar.finalProject.Globals;

public class ConstantVariables 
{
	
    public final static long TEST = 1000*60; 
    public final static long sessionMaxIdle =10*1000*60; 
	public final static long ONCE_PER_DAY = 1000*60*60*24;
	
	public final static String userName = "root";
	public final static String password = "root";
	public final static String URL = "jdbc:mysql://localhost:3306/finalproject?useSSL=false";
	
	public final static String newMsgInPrivateZone = "������ ������ ������� �� ����� �����";
	
	public final static String waitingForYourApproval = "����� �������";

	public final static String waitingForAppApproval = "����� ������ ���� �����";	
	public final static String waitingForOfferApproval = "����� ������ ���� �����";
	
	public final static String waitingForMatch = "����� ������ �����";
	public final static String waitingForbothSideApproval = "����� ������ ��� ������";
	
	public final static String bothSideApproved = "��� ������ ����� �� ������";
		
	public final static String offerDeclined = "���� ����� ��� �� ������";
	public final static String applicationDeclined = "���� ����� ��� �� ������";

	//////variable for Email///////
	
	public final static String subjectMailInterestedInApplication = "������� �����: �����/� ����/� ����� ��!";
	public final static String subjectMailInterestedInOffer = "������� �����: �����/� �������/� ����� ���!";
	
	public final static String subjectMailAutoMatchApplication = "������� �����: ����� ����� ���� ������ ����� ���!";
	public final static String subjectMailAutoMatchOffer = "������� �����: ����� ����� ���� ������ ����� ���!";

	private static String body1MailApplication = "����� ����� ���� ������ ����� ���!";
	private static String body2MailApplication = "�� ��� ���������� ���� ������ �� ����� �����";
	public final static String bodyMailApplication = body1MailApplication + System.lineSeparator() + body2MailApplication;
	
	
	private static String body1InterestedInOffer = "�����/� �������/� ����� ���� ���� ����� ����� ������ ������� ������� �����";
	private static String body2InterestedInOffer = "�� ���/� ���������� ���� ������ �� ����� �����";
	public final static String bodyMailInterestedInOffer = body1InterestedInOffer + System.lineSeparator() + body2InterestedInOffer;
	
	
	//subject and body for case that both side approved
	public final static String subjectMailBothApproved = "������� �����: ��� ������ ����� �� ������!";
	
	private static String body1MailBothApproved = "���� ���! ��� ������ ����� �� ������!";
	private static String body2MailBothApproved = "���� ���� ��� ��� �� ���� ����� ����� ���� ����� ���� ������ ������ ��� ����� �� ����� ������";

	public final static String bodyMailBothSideApproved = body1MailBothApproved + System.lineSeparator() + body2MailBothApproved;
	
	//subject for case that one of the side decline
	public final static String subjectMailDecline= "������� �����: ������ �����!";
	
	//body for case that the offer user decline the match
	private static String body1MailOfferDecline = "�������, ���� ����� ��� �� ������";
	private static String body2MailOfferDecline = "��� ������ �� ����� ������ ����� ����� ����� ��� ����";
	public final static String bodyMailOfferDecline = body1MailOfferDecline + System.lineSeparator() + body2MailOfferDecline;
	
	//body for case that the application user decline the match
	private static String body1MailApplicationDecline = "�������, ���� ����� ��� �� ������";
	private static String body2MailApplicationDecline = "��� ������ �� ����� ������ ����� ����� ����� ��� ����";
	public final static String bodyMailApplicationDecline = body1MailApplicationDecline + System.lineSeparator() + body2MailApplicationDecline;
	
	//subject and body for survey form
	public final static String subjectMailSurvey= "������� �����: ��� ��� �� ����� �����!";
	
	private static String body1MailSurvey = "���� �� �� ����/� �� ����� ��� �� ����� ����� ������";
	private static String body2MailSurvey = "�� ����� ��� ���� �� ����� �������, ���� ����� ���� ������ ������";
	private static String body3MailSurvey = "�������� ������ ��� ���� ����� ����� ������ ��� ���� �������";
	public final static String bodyMailSurvey = body1MailSurvey + System.lineSeparator() + body2MailSurvey + body3MailSurvey;
	
	//subject and body for send reminder to the application user that did'nt approve/decline the match yet
	public final static String subjectReminderApplication = "������� �����: ��� ����� �� ����� ����� ������ ����� ����� ���";

	private static String body1MailReminderApplication = "��� ����� �� ����� ����� ������ ����� ����� ������ ����";
	private static String body2MailReminderApplication = "��� �����/� ���� ���� ������ �� ����� ������";

	public final static String bodyMailReminderApplication = body1MailReminderApplication + System.lineSeparator() + body2MailReminderApplication;

	//subject and body for send reminder to the application user that did'nt approve/decline the match yet
	public final static String subjectReminderOffer = "������� �����: ��� ����� �� ����� ����� ������ ����� ����� ���";

	private static String body1MailReminderOffer = "��� ����� �� ����� ����� ������ ����� ����� ������ ����";
	private static String body2MailReminderOffer = "��� �����/� ���� ���� ������ �� ����� ������";

	public final static String bodyMailReminderOffer = body1MailReminderOffer + System.lineSeparator() + body2MailReminderOffer;
	
	
	
	
}

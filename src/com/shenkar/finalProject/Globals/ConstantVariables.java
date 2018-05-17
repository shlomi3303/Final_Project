package com.shenkar.finalProject.Globals;

public class ConstantVariables 
{
	
    public final static long TEST = 1000*60; 
	public final static long ONCE_PER_DAY = 1000*60*60*24;

	
	public final static String waitingForAppApproval = "����� ������ ���� �����";	
	public final static String waitingForOfferApproval = "����� ������ ���� �����";
	public final static String waitingForMatch = "����� ������ �����";
	
	public final static String bothSideApproved = "��� ������ �����";
		
	
	public final static String offerDeclined = "���� ����� ��� �� ������";
	public final static String applicationDeclined = "���� ����� ��� �� ������";

	
	//variable for Email
	public final static String subjectMailApplication = "������� �����: �����/� ����/� ����� ��!";
	
	private static String body1MailApplication = "����� ����� ���� ������ ����� ���!";
	private static String body2MailApplication = "�� ��� ���������� ���� ������ �� ����� �����";
	public final static String bodyMailApplication = body1MailApplication + System.lineSeparator() + body2MailApplication;
	
	//subject and body for case that both side approved
	public final static String subjectMailBothApproved = "������� �����: ��� ������ ����� �� ������!";
	
	private static String body1MailBothApproved = "���� ���! ��� ������ ����� �� ������!";
	private static String body2MailBothApproved = "���� ���� ��� ��� �� ���� ����� ����� ���� ����� ���� ������ ������ ��� ����� �� ����� ������";

	public final static String bodyMailBothSideApproved = body1MailBothApproved + System.lineSeparator() + body2MailBothApproved;
	
	//subject and body for case that one of the side decline
	public final static String subjectMailDecline= "������� �����: ������ �����!";
	
	private static String body1MailOfferDecline = "�������, ���� ����� ��� �� ������";
	private static String body2MailOfferDecline = "��� ������ �� ����� ������ ����� ����� ����� ��� ����";

	public final static String bodyMailOfferDecline = body1MailOfferDecline + System.lineSeparator() + body2MailOfferDecline;
	
	//subject and body for survey form
	public final static String subjectMailSurvey= "������� �����: ��� ��� �� ����� �����!";
	
	private static String body1MailSurvey = "���� �� �� ����/� �� ����� ��� �� ����� ����� ������";
	private static String body2MailSurvey = "�� ����� ��� ���� �� ����� �������, ���� ����� ���� ������ ������";
	private static String body3MailSurvey = "�������� ������ ��� ���� ����� ����� ������ ��� ���� �������";

	public final static String bodyMailSurvey = body1MailSurvey + System.lineSeparator() + body2MailSurvey + body3MailSurvey;
	
	//subject and body for send reminder to the application user that did'nt approve the match yet

	

	
}

package com.shenkar.finalProject.model.interfaces;

import com.shenkar.finalProject.model.Application;
import com.shenkar.finalProject.model.Offer;

import java.util.List;

public interface IAutoMatch 
{
	public void ranking(Application application, List <Offer> offer);
	
	public void matching(Application application, Offer offer);
	
	public void status (String status, Offer offer);
	
	public int ttlCalc (int ttl);
	
	//public void notify 
	
	
	
}

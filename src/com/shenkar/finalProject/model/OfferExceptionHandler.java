package com.shenkar.finalProject.model;

public class OfferExceptionHandler extends Exception
{
	private static final long serialVersionUID = 1L;

	/**
	 * constructor
	 * @param msg
	 */
	public OfferExceptionHandler(String e) {
		super(e);
	}
	
	/**
	 * constructor
	 * @param msg
	 * @param throwable
	 */
	public OfferExceptionHandler(String e, Throwable throwMsg) {
		super(e,throwMsg);
	}
	
}

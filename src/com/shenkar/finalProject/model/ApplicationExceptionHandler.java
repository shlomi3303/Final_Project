package com.shenkar.finalProject.model;

public class ApplicationExceptionHandler extends Exception
{
	private static final long serialVersionUID = 1L;

	/**
	 * constructor
	 * @param msg
	 */
	public ApplicationExceptionHandler(String e) {
		super(e);
	}
	
	/**
	 * constructor
	 * @param msg
	 * @param throwable
	 */
	public ApplicationExceptionHandler(String e, Throwable throwMsg) {
		super(e,throwMsg);
	}
		
}

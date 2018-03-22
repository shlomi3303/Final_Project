package com.shenkar.finalProject.model;

/**
 * UserExceptionHandler Handles Users errors
 */
public class UserExceptionHandler extends Exception{

	private static final long serialVersionUID = 1L;

	/**
	 * constructor
	 * @param msg
	 */
	public UserExceptionHandler(String e) {
		super(e);
	}
	
	/**
	 * constructor
	 * @param msg
	 * @param throwable
	 */
	public UserExceptionHandler(String e, Throwable throwMsg) {
		super(e,throwMsg);
	}
}

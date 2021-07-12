package com.cg.phonebook.exception;

public class PhonebookUsersException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public PhonebookUsersException() {
		 super();
	 }
	 public PhonebookUsersException(String errMessage) {
		 super(errMessage);
	 }
}

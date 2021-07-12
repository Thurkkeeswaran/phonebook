package com.cg.phonebook.exception;

public class ContactException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	public ContactException() {
		super();
	}
	public ContactException(String errMsg) {
		super(errMsg);
	}

}

package com.cg.phonebook.exception;

public class UserException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	public UserException() {
		super();
	}
	public UserException(String errMsg) {
		super(errMsg);
	}

}

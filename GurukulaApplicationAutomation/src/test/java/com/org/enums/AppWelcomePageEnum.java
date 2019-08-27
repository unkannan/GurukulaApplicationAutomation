package com.org.enums;


public enum AppWelcomePageEnum {

	LOGIN_LINK("login"), REGISTER_LINK("Register a new account"), AUTHENTICATE("Authenticate"), REGISTER("Register");

	String val;

	private AppWelcomePageEnum(String val) {
		this.val = val;
	}

	public String getVal() {
		return val;
	}
}

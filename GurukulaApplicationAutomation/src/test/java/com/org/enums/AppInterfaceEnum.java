package com.org.enums;

public enum AppInterfaceEnum {

	Settings("Settings"), Password("Password"), Sessions("Sessions"), Logout("Log out");
	String val;

	private AppInterfaceEnum(String val) {
		this.val = val;
	}

	public String getVal() {
		return val;
	}

}

package com.org.enums;
/*
 * These are the warning messages/ error messages which user might see for for invalid inputs.
 * These messages are used to do the validations/verifications
 * */
 

public class AppValidationConstantMessages {

	public static final long IMPLICIT_WAIT_TIME = 5;
	public static final long EXPLICIT_WAIT_TIME = 10;

	public static final String BRANCH_NAME_REQUIRED = "This field is required.";
	public static final String BRANCH_NAME_MINIMUM = "This field is required to be at least 5 characters.";
	public static final String BRANCH_NAME_MAXIMUM = "This field cannot be longer than 20 characters.";
	public static final String BRANCH_NAME_PATTERN = "This field should follow pattern ^[a-zA-Z\\s]*$.";

	public static final String BRANCH_CODE_REQUIRED = "This field is required.";
	public static final String BRANCH_CODE_MINIMUM = "This field is required to be at least 2 characters.";
	public static final String BRANCH_CODE_MAXIMUM = "This field cannot be longer than 10 characters.";
	public static final String BRANCH_CODE_PATTERN = "This field should follow pattern ^[A-Z0-9]*$.";

	public static final String STAFF_NAME_REQUIRED = "This field is required.";
	public static final String STAFF_NAME_MINIMUM = "This field is required to be at least 0 characters.";
	public static final String STAFF_NAME_MAXIMUM = "This field cannot be longer than 50 characters.";
	public static final String STAFF_NAME_PATTERN = "This field should follow pattern ^[a-zA-Z\\s]*$.";

	public static final String LOGIN_REQUIRED = "Your login is required.";
	public static final String LOGIN_MINIMUM = "Your login is required to be at least 1 character.";
	public static final String LOGIN_MAXIMUM = "Your login cannot be longer than 50 characters.";
	public static final String LOGIN_LOWER_CASE = "Your login can only contain lower-case letters and digits.";

	public static final String EMAIL_REQUIRED = "Your e-mail is required.";
	public static final String EMAIL_INVALID = "Your e-mail is invalid.";
	public static final String EMAIL_MINIMUM = "Your e-mail is required to be at least 5 characters.";
	public static final String EMAIL_MAXIMUM = "Your e-mail cannot be longer than 50 characters.";

	public static final String PASSWORD_REQUIRED = "Your password is required.";
	public static final String PASSWORD_MINIMUM = "Your password is required to be at least 5 characters.";
	public static final String PASSWORD_MAXIMUM = "Your password cannot be longer than 50 characters.";
	public static final String PASSWORD_RULE = "Password should begin with a alphabet and should contain a number and a special character.";

	public static final String CONFIRM_PASSWORD_REQUIRED = "Your confirmation password is required.";
	public static final String CONFIRM_PASSWORD_MINIMUM = "Your confirmation password is required to be at least 5 characters.";
	public static final String CONFIRM_PASSWORD_MAXIMUM = "Your confirmation password cannot be longer than 50 characters.";

	public static final String PASSWORD_NOT_MATCHED = "The password and its confirmation do not match!";

	public static final String FIRST_NAME_REQUIRED = "Your first name is required.";
	public static final String FIRST_NAME_MINIMUM = "Your first name is required to be at least 1 character.";
	public static final String FIRST_NAME_MAXIMUM = "Your first name cannot be longer than 50 characters.";

	public static final String LAST_NAME_REQUIRED = "Your last name is required.";
	public static final String LAST_NAME_MINIMUM = "Your last name is required to be at least 1 character.";
	public static final String LAST_NAME_MAXIMUM = "Your last name cannot be longer than 50 characters.";

}

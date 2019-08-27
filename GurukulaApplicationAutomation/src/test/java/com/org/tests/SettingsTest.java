package com.org.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.org.enums.AppValidationConstantMessages;
import com.org.enums.AppInterfaceEnum;
import com.org.enums.AppMenuEnum;
import com.org.enums.AppWelcomePageEnum;
import com.org.pages.LoginPage;
import com.org.pages.SettingsPage;
import com.org.pages.WelcomePage;
import com.org.utility.StartApplication;

@Test(groups = { "settings" })
public class SettingsTest extends StartApplication {

	WelcomePage welcomePage;
	LoginPage loginPage;
	SettingsPage settingsPage;

	@Test
	public void tc_01_testSettingsPageHeading() {
		preSteps();
		settingsPage.assertSettingsPageHeading(USERNAME);
	}

	@Test(dataProvider = "updateDetails")
	public void tc_02_testUpdateSettings(String firstName, String lastName, String email, String lang) {
		preSteps();
		settingsPage.enterDetailsAndSubmit(firstName, lastName, email, lang);
		settingsPage.assertMessage(SettingsPage.SUCCESS);
	}

	@Test(dataProvider = "validations")
	public void tc_03_testSettingsFieldValidation(String fieldName, String vMsg, String txt) {
		preSteps();
		settingsPage.enterTextIn(fieldName, txt);
		settingsPage.assertFieldValidationMessage(fieldName, vMsg);
		settingsPage.assertSaveBtnDisable();
	}

	private void preSteps() {
		welcomePage = new WelcomePage(driver);
		loginPage = new LoginPage(driver);
		settingsPage = new SettingsPage(driver);

		welcomePage.clickOnLink(AppWelcomePageEnum.LOGIN_LINK.getVal());
		loginPage.LoginSucessFunctionalityCheck(USERNAME, PASSWORD);

		welcomePage.clickOnOption(AppMenuEnum.Account.name(), AppInterfaceEnum.Settings.getVal());
	}

	@DataProvider(name = "updateDetails")
	public Object[][] updateDetails() {
		return new Object[][] { { "kannan", "Kumar", "unniqannan@gamil.com", "English" } };
	}

	@DataProvider(name = "validations")
	public Object[][] validations() {
		return new Object[][] {
			// @formatter:off
            {SettingsPage.FIRST_NAME, AppValidationConstantMessages.FIRST_NAME_REQUIRED, " "},
            {SettingsPage.FIRST_NAME, AppValidationConstantMessages.FIRST_NAME_MINIMUM, ""},
            {SettingsPage.FIRST_NAME, AppValidationConstantMessages.FIRST_NAME_MAXIMUM,
                "this text is to test the maximum limit of the First Name text field"},

            {SettingsPage.LAST_NAME, AppValidationConstantMessages.LAST_NAME_REQUIRED, " "},
            {SettingsPage.LAST_NAME, AppValidationConstantMessages.LAST_NAME_MINIMUM, ""},
            {SettingsPage.LAST_NAME, AppValidationConstantMessages.LAST_NAME_MAXIMUM,
                "this text is to test the maximum limit of the Last Name text field"},

            {SettingsPage.EMAIL, AppValidationConstantMessages.EMAIL_REQUIRED, " "},
            {SettingsPage.EMAIL, AppValidationConstantMessages.EMAIL_MINIMUM, "a@b"},
            {SettingsPage.EMAIL, AppValidationConstantMessages.EMAIL_INVALID, "abcdef"},
            {SettingsPage.EMAIL, AppValidationConstantMessages.EMAIL_MAXIMUM,
                "ThisEmailIsToValidateTheMaximumLimitOfEmail@TextField.com"}
            //@formatter:on
		};
	}
}

package com.org.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.org.enums.AppValidationConstantMessages;
import com.org.enums.AppInterfaceEnum;
import com.org.enums.AppMenuEnum;
import com.org.enums.AppWelcomePageEnum;
import com.org.pages.LoginPage;
import com.org.pages.UpdatePasswordPage;
import com.org.pages.WelcomePage;
import com.org.utility.StartApplication;

@Test(groups = { "password" })
public class UpdatePasswordTest extends StartApplication {

	WelcomePage welcomePage;
	LoginPage loginPage;
	UpdatePasswordPage updatePasswordPage;

	@Test
	public void tc_01_testUpdatePasswordPageHeading() {
		preSteps();
		updatePasswordPage.assertUpdatePasswordPageHeading(USERNAME);
	}

	@Test
	public void tc_02_testUpdatePassword() {
		preSteps();
		updatePasswordPage.enterNewPasswordAndSubmit("Test#123", "Test#123");

		updatePasswordPage.assertMessage(UpdatePasswordPage.SUCCESS);
	}

	@Test(dataProvider = "validations")
	public void tc_03_testUpdatePasswordFieldValidation(String fieldName, String vMsg, String txt) {
		preSteps();
		updatePasswordPage.enterTextIn(fieldName, txt);

		updatePasswordPage.assertFieldValidationMessage(fieldName, vMsg);
		updatePasswordPage.assertSaveBtnDisable();

	}

	@Test(dataProvider = "requiredField")
	public void tc_04_testPasswordRequiredValidation(String fieldName, String vMsg, String txt) {
		preSteps();
		updatePasswordPage.enterTextIn(fieldName, txt);
		updatePasswordPage.clearText(fieldName);

		updatePasswordPage.assertFieldValidationMessage(fieldName, vMsg);
		updatePasswordPage.assertSaveBtnDisable();
	}

	@Test
	public void tc_05_testPasswordAndConfirmPasswordMatch() {
		preSteps();
		updatePasswordPage.enterNewPasswordAndSubmit("Test#123", "Abc#123");
		updatePasswordPage.assertMessage(AppValidationConstantMessages.PASSWORD_NOT_MATCHED);
	}

	private void preSteps() {
		welcomePage = new WelcomePage(driver);
		loginPage = new LoginPage(driver);
		updatePasswordPage = new UpdatePasswordPage(driver);

		welcomePage.clickOnLink(AppWelcomePageEnum.LOGIN_LINK.getVal());
		loginPage.LoginSucessFunctionalityCheck("admin", "admin");
		welcomePage.clickOnOption(AppMenuEnum.Account.name(), AppInterfaceEnum.Password.getVal());
	}

	@DataProvider(name = "requiredField")
	public Object[][] requiredField() {
		return new Object[][] {
			// @formatter:off
            {UpdatePasswordPage.PASSWORD, AppValidationConstantMessages.PASSWORD_REQUIRED, "req"},
            {UpdatePasswordPage.CONFIRM_PASSWORD, AppValidationConstantMessages.CONFIRM_PASSWORD_REQUIRED, "req"},
            //@formatter:on
		};
	}

	@DataProvider(name = "validations")
	public Object[][] validations() {
		return new Object[][] {
			// @formatter:off
            {UpdatePasswordPage.PASSWORD, AppValidationConstantMessages.PASSWORD_MINIMUM, "A"},
            {UpdatePasswordPage.PASSWORD, AppValidationConstantMessages.PASSWORD_MAXIMUM,
                "this text is to test the max limit of the password1"},

            {UpdatePasswordPage.CONFIRM_PASSWORD, AppValidationConstantMessages.CONFIRM_PASSWORD_MINIMUM, "A"},
            {UpdatePasswordPage.CONFIRM_PASSWORD, AppValidationConstantMessages.CONFIRM_PASSWORD_MAXIMUM,
                "this text is to test the max limit of the password1"}
            //@formatter:on
		};
	}
}

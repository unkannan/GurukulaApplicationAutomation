package com.org.tests;

import com.org.enums.AppValidationConstantMessages;
import com.org.enums.AppWelcomePageEnum;
import com.org.pages.ResetPasswordPage;
import com.org.pages.WelcomePage;
import com.org.utility.StartApplication;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test(groups = { "resetPassword" })
public class ResetPasswordTest extends StartApplication {

	WelcomePage welcomePage;
	ResetPasswordPage resetPasswordPage;

	@Test
	public void tc_01_testResetPasswordPageHeading() {
		preSteps();
		resetPasswordPage.assertResetPasswordPageHeading();
	}

	@Test
	public void tc_02_testResetPasswordWithRegisteredEmail() {
		preSteps();
		resetPasswordPage.enterEmailAndSubmit("admin@localhost");
		resetPasswordPage.assertSuccessMsg();
	}

	@Test
	public void tc_03_testResetPasswordWithUnRegisteredEmail() {
		preSteps();
		resetPasswordPage.enterEmailAndSubmit("unniqannan@gmail.com");
		resetPasswordPage.assertErrorMsg();

	}

	@Test(dataProvider = "validations")
	public void tc_04_testEmailFieldValidations(String value, String expectedMsg) {
		preSteps();
		resetPasswordPage.enterEmail(value);
		resetPasswordPage.assertValidationMsg(expectedMsg);
		resetPasswordPage.assertResetPasswordBtnDisable();
	}

	@Test
	public void tc_05_testRequiredEmailValidation() {
		preSteps();
		resetPasswordPage.enterEmail("kannan");
		resetPasswordPage.clearText();
		resetPasswordPage.assertValidationMsg(AppValidationConstantMessages.EMAIL_REQUIRED);
		resetPasswordPage.assertResetPasswordBtnDisable();
	}

	private void preSteps() {
		welcomePage = new WelcomePage(driver);
		resetPasswordPage = new ResetPasswordPage(driver);

		welcomePage.clickOnLink(AppWelcomePageEnum.LOGIN_LINK.getVal());
		resetPasswordPage.clickOnLink();
	}

	@DataProvider(name = "validations")
	public Object[][] validations() {
		return new Object[][] {
			// @formatter:off
			{ "abcde", AppValidationConstantMessages.EMAIL_INVALID }, 
			{ "a@b", AppValidationConstantMessages.EMAIL_MINIMUM },
			{ "ThisEmailIsToValidateTheMaximumLimitOfEmail@TextField.com", AppValidationConstantMessages.EMAIL_MAXIMUM } 
			// @formatter:on
		};
	}
}


package com.org.tests;

import org.apache.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.org.enums.AppMenuEnum;
import com.org.enums.AppPageAttributesEnum;
import com.org.enums.AppWelcomePageEnum;
import com.org.pages.LoginPage;
import com.org.pages.RegistrationPage;
import com.org.pages.ResetPasswordPage;
import com.org.pages.WelcomePage;
import com.org.utility.StartApplication;
import com.org.enums.AppInterfaceEnum;


@Test(groups = "login")
public class LoginTest extends StartApplication {

	public static Logger logger = Logger.getLogger(LoginTest.class.getName());

	WelcomePage welcomePage;
	LoginPage loginPage;

	@Test
	public void tc_01_testLoginWithValidCredentials() {
		preSteps();
		loginPage.LoginSucessFunctionalityCheck(USERNAME, PASSWORD);
	}

	@Test(dataProvider = "invalidCredentials")
	public void tc_02_testLoginWithInValidCredentials(String username, String password) {
		preSteps();
		loginPage.LoginSuccessValidatingMessageCheck(username, password);
	}

	@Test
	public void tc_03_testForgotPasswordLink() {
		preSteps();
		loginPage.clickOnLink(LoginPage.FORGET_LINK);

		ResetPasswordPage resetPasswordPage = new ResetPasswordPage(driver);
		resetPasswordPage.assertResetPasswordPageHeading();
	}

	@Test
	public void tc_04_testRegistrationLink() {
		preSteps();
		loginPage.clickOnLink(LoginPage.REGISTER_LINK);

		RegistrationPage registrationPage = new RegistrationPage(driver);
		registrationPage.assertRegistrationPageHeading();
	}

	@Test
	public void tc_05_testLogout() {
		preSteps();
		loginPage.LoginSucessFunctionalityCheck(USERNAME, PASSWORD);

		welcomePage.clickOnOption(AppMenuEnum.Account.name(), AppInterfaceEnum.Logout.getVal());
		welcomePage.assertWelcomePageHeading();

	}

	@Test
	public void tc_06_testHomeTab() {
		preSteps();
		loginPage.LoginSucessFunctionalityCheck(USERNAME, PASSWORD);

		welcomePage.clickOnOption(AppMenuEnum.Entities.name(), AppPageAttributesEnum.Staff.name());
		welcomePage.clickOnMenu(AppMenuEnum.Home.name());
		welcomePage.assertWelcomePageHeading();
	}

	@Test
	public void tc_07_testLogoNavigation() {
		preSteps();
		loginPage.LoginSucessFunctionalityCheck(USERNAME, PASSWORD);

		welcomePage.clickOnOption(AppMenuEnum.Entities.name(), AppPageAttributesEnum.Staff.name());
		welcomePage.clickOnLogo();
		welcomePage.assertWelcomePageHeading();
	}

	private void preSteps() {
		welcomePage = new WelcomePage(driver);
		loginPage = new LoginPage(driver);

		welcomePage.clickOnLink(AppWelcomePageEnum.LOGIN_LINK.getVal());
	}

	@DataProvider(name = "invalidCredentials")
	public Object[][] invalidCredentials() {
		return new Object[][] {
			// @formatter:off
			{ "!nv@lid", "INVALID" }, 
			{ "admin", "INVALID" }, 
			{ "INVALID", "admin" }, 
			{ "", "" },			
			{ "admin", "" }, 
			{ "", "admin" }
			// @formatter:on
		};
	}

}

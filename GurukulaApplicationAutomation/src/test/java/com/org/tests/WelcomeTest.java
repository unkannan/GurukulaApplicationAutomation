
package com.org.tests;

import org.testng.annotations.Test;

import com.org.enums.AppMenuEnum;
import com.org.enums.AppWelcomePageEnum;
import com.org.pages.LoginPage;
import com.org.pages.RegistrationPage;
import com.org.pages.WelcomePage;
import com.org.utility.StartApplication;

@Test(groups = "welcome")
public class WelcomeTest extends StartApplication {

	@Test
	public void tc_01_testWelcomePage() {
		WelcomePage welcomePage = new WelcomePage(driver);
		welcomePage.assertWelcomePageHeading();
	}

	@Test
	public void tc_02_testLoginLink() {
		WelcomePage welcomePage = new WelcomePage(driver);
		welcomePage.clickOnLink(AppWelcomePageEnum.LOGIN_LINK.getVal());

		LoginPage loginPage = new LoginPage(driver);
		loginPage.LoginPageHeaderCheck();
	}

	@Test
	public void tc_03_testRegistrationLink() {
		WelcomePage welcomePage = new WelcomePage(driver);
		welcomePage.clickOnLink(AppWelcomePageEnum.REGISTER_LINK.getVal());

		RegistrationPage registrationPage = new RegistrationPage(driver);
		registrationPage.assertRegistrationPageHeading();
	}

	@Test
	public void tc_04_testAuthenticateMenuOption() {
		WelcomePage welcomePage = new WelcomePage(driver);
		welcomePage.clickOnOption(AppMenuEnum.Account.name(), AppWelcomePageEnum.AUTHENTICATE.getVal());

		LoginPage loginPage = new LoginPage(driver);
		loginPage.LoginPageHeaderCheck();
	}

	@Test
	public void tc_05_testRegisterMenuOption() {
		WelcomePage welcomePage = new WelcomePage(driver);
		welcomePage.assertWelcomePageHeading();
		welcomePage.clickOnOption(AppMenuEnum.Account.name(), AppWelcomePageEnum.REGISTER.getVal());

		RegistrationPage registrationPage = new RegistrationPage(driver);
		registrationPage.assertRegistrationPageHeading();
	}
}

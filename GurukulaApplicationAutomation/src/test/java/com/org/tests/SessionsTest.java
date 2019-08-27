package com.org.tests;

import org.testng.annotations.Test;

import com.org.enums.AppInterfaceEnum;
import com.org.enums.AppMenuEnum;
import com.org.enums.AppWelcomePageEnum;
import com.org.pages.LoginPage;
import com.org.pages.SessionsPage;
import com.org.pages.WelcomePage;
import com.org.utility.StartApplication;;

@Test(groups = { "sessions" })
public class SessionsTest extends StartApplication {

	WelcomePage welcomePage;
	LoginPage loginPage;
	SessionsPage sessionsPage;

	@Test
	public void tc_01_testSessionsPageHeading() {
		preSteps();
		sessionsPage.assertUpdatePasswordPageHeading("admin");
	}

	@Test
	public void tc_02_testInvalidateSession() {
		preSteps();
		sessionsPage.invalidateSession();
		sessionsPage.assertSuccessMessage();
	}

	private void preSteps() {
		welcomePage = new WelcomePage(driver);
		loginPage = new LoginPage(driver);
		sessionsPage = new SessionsPage(driver);

		welcomePage.clickOnLink(AppWelcomePageEnum.LOGIN_LINK.getVal());
		loginPage.LoginSucessFunctionalityCheck(USERNAME, PASSWORD);
		welcomePage.clickOnOption(AppMenuEnum.Account.name(), AppInterfaceEnum.Sessions.getVal());
	}

}

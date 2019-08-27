  
package com.org.tests;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.org.enums.AppPageAttributesEnum;
import com.org.enums.AppMenuEnum;
import com.org.enums.AppWelcomePageEnum;
import com.org.pages.BranchMainPage;
import com.org.pages.LoginPage;
import com.org.pages.StaffMainPage;
import com.org.pages.WelcomePage;
import com.org.utility.StartApplication;

 

@Test(groups = { "searchStaff" })
public class SearchStaffTest extends StartApplication {

	WelcomePage welcomePage;
	LoginPage loginPage;
	StaffMainPage staffPage;
	BranchMainPage branchPage;

	@Test
	public void tc_01_testCreateBranchAndMultipleSatffs() {
		preSteps();
		welcomePage.clickOnOption(AppMenuEnum.Entities.name(), AppPageAttributesEnum.Branch.name());
		int id = branchPage.createNewBranch("Associated Branch", "AS");
		branchPage.assertBranchRowBy(id, "Associated Branch", "AS");
		welcomePage.clickOnOption(AppMenuEnum.Entities.name(), AppPageAttributesEnum.Staff.name());
		id = staffPage.createNewStaff("Test Staff One", "Associated Branch");
		staffPage.assertStaffRowBy(id, "Test Staff One", "Associated Branch");

		id = staffPage.createNewStaff("Test Staff Two", "Associated Branch");
		staffPage.assertStaffRowBy(id, "Test Staff Two", "Associated Branch");

		id = staffPage.createNewStaff("StaffThree", "Associated Branch");
		staffPage.assertStaffRowBy(id, "StaffThree", "Associated Branch");
	}

	@Test(dataProvider = "searchKeywords", dependsOnMethods = "tc_01_testCreateBranchAndMultipleSatffs")
	public void tc_02_testSearchStaff(String searchKeyword) {
		preSteps();
		staffPage.searchStaff(searchKeyword);
		staffPage.assertSearchResult(searchKeyword);
	}

	@Test(dependsOnMethods = "tc_01_testCreateBranchAndMultipleSatffs")
	public void tc_03_testSearchStaffByMultipleKeywords() {
		preSteps();
		staffPage.searchStaff("One Two");
		staffPage.assertAtleatOneResultFound("One");
		staffPage.assertAtleatOneResultFound("Two");
	}

	@Test(dependsOnMethods = "tc_01_testCreateBranchAndMultipleSatffs")
	public void tc_04_testSearchStaffById() {
		preSteps();
		String id = String.valueOf(staffPage.getMaxId());
		staffPage.searchStaff(id);
		staffPage.assertSearchResult(id);
	}

	@Test(dependsOnMethods = "tc_01_testCreateBranchAndMultipleSatffs")
	public void tc_05_testSearchStaffShouldNotContain() {
		preSteps();
		staffPage.searchStaff("!One");
		staffPage.assertSearchResultShouldNotContain("One");
	}

	@Test(dependsOnMethods = "tc_01_testCreateBranchAndMultipleSatffs")
	public void tc_06_testSearchStaffNoResult() {
		preSteps();

		staffPage.searchStaff("doesNotExist");
		List<List<String>> lst = branchPage.getAllRows();
		assertEquals(lst.size(), 0, "Search result should be blank.");
	}

	private void preSteps() {
		welcomePage = new WelcomePage(driver);
		loginPage = new LoginPage(driver);
		branchPage = new BranchMainPage(driver);
		staffPage = new StaffMainPage(driver);

		welcomePage.clickOnLink(AppWelcomePageEnum.LOGIN_LINK.getVal());
		loginPage.LoginSucessFunctionalityCheck(USERNAME, PASSWORD);
		welcomePage.clickOnOption(AppMenuEnum.Entities.name(), AppPageAttributesEnum.Staff.name());
	}

	@DataProvider(name = "searchKeywords")
	public Object[][] searchKeywords() {

		return new Object[][] { { "StaffThree" }, { "Staff" }, { "STAFFTHREE" }, { "Associated Branch" } };
	}

}

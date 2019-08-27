
package com.org.tests;

import static org.testng.Assert.assertEquals;

import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.org.enums.AppUserActionsEnum;
import com.org.enums.AppValidationConstantMessages;
import com.org.enums.AppWelcomePageEnum;
import com.org.enums.AppPageAttributesEnum;
import com.org.enums.AppMenuEnum;
import com.org.pages.WelcomePage;
import com.org.pages.BranchMainPage;
import com.org.pages.LoginPage;
import com.org.pages.StaffDetailPage;
import com.org.pages.StaffMainPage;
import com.org.utility.StartApplication;
 

@Test(groups = { "staff" })
public class StaffTest extends StartApplication {

	WelcomePage welcomePage;
	LoginPage loginPage;
	StaffMainPage staffPage;
	StaffDetailPage staffDetailPage;
	BranchMainPage branchPage;

	@Test(dataProvider = "newBranchDetails")
	public void tc_01_testCreateNewBranch(String name, String code, ITestContext context) {
		preSteps();
		welcomePage.clickOnOption(AppMenuEnum.Entities.name(), AppPageAttributesEnum.Branch.name());
		int id = branchPage.createNewBranch(name, code);

		branchPage.assertBranchRowBy(id, name, code);
		context.setAttribute("id", id);
	}

	@Test
	public void tc_02_testStaffPage() {
		preSteps();
		staffPage.assertStaffPageHeading();
	}

	@Test(dataProvider = "newStaffDetails", dependsOnMethods = "tc_01_testCreateNewBranch")
	public void tc_03_testCreateNewStaff(String name, String branch, ITestContext context) {
		preSteps();
		int id = staffPage.createNewStaff(name, branch);

		staffPage.assertStaffRowBy(id, name, branch);
		context.setAttribute("id", id);
	}

	@Test(dataProvider = "newStaffDetails", dependsOnMethods = "tc_01_testCreateNewBranch")
	public void tc_04_testCreateNewStaffAndCancel(String name, String branch) {
		preSteps();
		int maxIdBefore = staffPage.getMaxId();
		int maxIdAfter = staffPage.createAndCancel(name, branch);

		assertEquals(maxIdAfter, maxIdBefore, "New Staff should not be created.");
	}

	@Test(dataProvider = "newStaffDetails", dependsOnMethods = { "tc_03_testCreateNewStaff" })
	public void tc_05_testStaffDetailsByIdLink(String name, String branch, ITestContext context) {
		preSteps();
		int id = (Integer) context.getAttribute("id");
		staffPage.navigateToStaffDetailPageById(id);

		staffDetailPage.assertInfoOnStaffDetailPage(id, name, branch);
	}

	@Test(dataProvider = "newStaffDetails", dependsOnMethods = { "tc_03_testCreateNewStaff" })
	public void tc_06_testStaffDetailsByView(String name, String branch, ITestContext context) {
		preSteps();
		int id = (Integer) context.getAttribute("id");
		staffPage.clickOnActionCorrespondingToId(AppUserActionsEnum.View.name(), id);

		staffDetailPage.assertInfoOnStaffDetailPage(id, name, branch);
	}

	@Test(dataProvider = "newStaffDetails", dependsOnMethods = { "tc_03_testCreateNewStaff" })
	public void tc_07_testDetailsOnEditStaff(String name, String branch, ITestContext context) {
		preSteps();
		int id = (Integer) context.getAttribute("id");
		staffPage.clickOnActionCorrespondingToId(AppUserActionsEnum.Edit.name(), id);

		staffPage.assertDetailsOnEditForm(id, name, branch);
	}

	@Test(dataProvider = "editStaffDetails", dependsOnMethods = { "tc_03_testCreateNewStaff" })
	public void tc_08_testEditStaffDetailsAndCancel(String name, String branch, String updatedName,
			String updatedbranch, ITestContext context) {
		preSteps();
		int id = (Integer) context.getAttribute("id");
		staffPage.clickOnActionCorrespondingToId(AppUserActionsEnum.Edit.name(), id);
		staffPage.assertDetailsOnEditForm(id, name, branch);
		staffPage.updateStaffDetails(AppUserActionsEnum.Cancel.name(), updatedName, updatedbranch);

		staffPage.assertStaffRowBy(id, name, branch);
	}

	@Test(dataProvider = "editStaffDetails", dependsOnMethods = { "tc_03_testCreateNewStaff" })
	public void tc_09_testEditStaffDetails(String name, String branch, String updatedName, String updatedbranch,
			ITestContext context) {
		preSteps();
		int id = (Integer) context.getAttribute("id");
		staffPage.clickOnActionCorrespondingToId(AppUserActionsEnum.Edit.name(), id);

		staffPage.assertDetailsOnEditForm(id, name, branch);
		staffPage.updateStaffDetails(AppUserActionsEnum.Save.name(), updatedName, updatedbranch);

		staffPage.assertStaffRowBy(id, updatedName, updatedbranch);
	}

	@Test(dependsOnMethods = { "tc_03_testCreateNewStaff" })
	public void tc_10_testDeleteStaffAndCancel(ITestContext context) {
		preSteps();
		int id = (Integer) context.getAttribute("id");
		Map<String, String> staffRow = staffPage.getStaffRowById(id);
		staffPage.clickOnActionCorrespondingToId(AppUserActionsEnum.Delete.name(), id);

		staffPage.assertDeleteConfirmationModalForStaff(id);
		staffPage.deleteStaff(AppUserActionsEnum.Cancel.name());

		staffPage.assertStaffRowBy(id, staffRow.get("Name"), staffRow.get("Branch"));
	}

	@Test(dependsOnMethods = { "tc_03_testCreateNewStaff" })
	public void tc_11_testDeleteStaff(ITestContext context) {
		preSteps();
		int id = (Integer) context.getAttribute("id");
		staffPage.clickOnActionCorrespondingToId(AppUserActionsEnum.Delete.name(), id);

		staffPage.assertDeleteConfirmationModalForStaff(id);
		staffPage.deleteStaff(AppUserActionsEnum.Delete.name());
		int index = staffPage.getRowIndexById(id);

		assertEquals(index, -1, "Staff should get deleted.");
	}

	@Test(dataProvider = "validations")
	public void tc_12_testNewStaffFormValidations(String msg, String txt) {
		preSteps();

		staffPage.assertValidationMsg(txt, msg);
		staffPage.assertSaveBtnDisable();
	}

	private void preSteps() {
		welcomePage = new WelcomePage(driver);
		loginPage = new LoginPage(driver);
		branchPage = new BranchMainPage(driver);
		staffPage = new StaffMainPage(driver);
		staffDetailPage = new StaffDetailPage(driver);

		welcomePage.clickOnLink(AppWelcomePageEnum.LOGIN_LINK.getVal());
		loginPage.LoginSucessFunctionalityCheck(USERNAME, PASSWORD);
		welcomePage.clickOnOption(AppMenuEnum.Entities.name(), AppPageAttributesEnum.Staff.name());
	}

	@DataProvider(name = "newBranchDetails")
	public Object[][] newBranchDetails() {
		return new Object[][] { { "Computer Science", "CS" } };
	}

	@DataProvider(name = "newStaffDetails")
	public Object[][] newStaffDetails() {
		return new Object[][] { { "Unni Kannan", "Computer Science" } };
	}

	@DataProvider(name = "editStaffDetails")
	public Object[][] editBranchDetails() {
		return new Object[][] { { "Unni Kannan", "Computer Science", "Raj", "Computer Science" } };
	}

	@DataProvider(name = "validations")
	public Object[][] validations() {
		return new Object[][] {
			// @formatter:off
			{ AppValidationConstantMessages.STAFF_NAME_REQUIRED, "" },
			{ AppValidationConstantMessages.STAFF_NAME_MINIMUM, "A" },
			{ AppValidationConstantMessages.STAFF_NAME_MAXIMUM, "test the maximum limit of text field on the staff edit form" },
			{ AppValidationConstantMessages.STAFF_NAME_PATTERN, "test1" },
			{ AppValidationConstantMessages.STAFF_NAME_PATTERN, "test@" }
			
			// @formatter:on
		};
	}

}

 
package com.org.tests;

import static org.testng.Assert.assertEquals;

import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.org.enums.AppUserActionsEnum;
import com.org.enums.AppValidationConstantMessages;
import com.org.enums.AppPageAttributesEnum;
import com.org.enums.AppMenuEnum;
import com.org.enums.AppWelcomePageEnum;
import com.org.pages.BranchMainPage;
import com.org.pages.BranchesMainDetailPage;
import com.org.pages.LoginPage;
import com.org.pages.WelcomePage;
import com.org.utility.StartApplication;
 

@Test(groups = { "branch" })
public class BranchTest extends StartApplication {

	WelcomePage welcomePage;
	LoginPage loginPage;
	BranchMainPage branchPage;
	BranchesMainDetailPage branchDetailPage;

	@Test
	public void tc_01_testBranchPage() {
		preSteps();
		branchPage.assertBranchPageHeading();
	}

	@Test(dataProvider = "newBranchDetails")
	public void tc_02_testCreateNewBranch(String name, String code, ITestContext context) {
		preSteps();
		int id = branchPage.createNewBranch(name, code);
		branchPage.assertBranchRowBy(id, name, code);
		context.setAttribute("id", id);
	}

	@Test(dataProvider = "newBranchDetails")
	public void tc_03_testCreateNewBranchAndCancel(String name, String code) {
		preSteps();
		int maxIdBefore = branchPage.getMaxId();
		int maxIdAfter = branchPage.createAndCancel(name, code);

		assertEquals(maxIdAfter, maxIdBefore, "New Branch should not be created.");
	}

	@Test(dataProvider = "newBranchDetails", dependsOnMethods = "tc_02_testCreateNewBranch")
	public void tc_04_testBranchDetailsByIdLink(String name, String code, ITestContext context) {
		preSteps();
		int id = (Integer) context.getAttribute("id");
		branchPage.navigateToBranchDetailPageById(id);

		branchDetailPage.BranchesResultsMainPageValidationCheck(id, name, code);
	}

	@Test(dataProvider = "newBranchDetails", dependsOnMethods = "tc_02_testCreateNewBranch")
	public void tc_05_testBranchDetailsByView(String name, String code, ITestContext context) {
		preSteps();
		int id = (Integer) context.getAttribute("id");
		branchPage.clickOnActionCorrespondingToId(AppUserActionsEnum.View.name(), id);

		branchDetailPage.BranchesResultsMainPageValidationCheck(id, name, code);
	}

	@Test(dataProvider = "editBranchDetails", dependsOnMethods = "tc_02_testCreateNewBranch")
	public void tc_06_testDetailsOnEditBranch(String name, String code, String updatedName, String updatedCode,
			ITestContext context) {
		preSteps();
		int id = (Integer) context.getAttribute("id");
		branchPage.clickOnActionCorrespondingToId(AppUserActionsEnum.Edit.name(), id);

		branchPage.assertDetailsOnEditForm(id, name, code);
	}

	@Test(dataProvider = "editBranchDetails", dependsOnMethods = "tc_02_testCreateNewBranch")
	public void tc_07_testEditBranchDetailsAndCancel(String name, String code, String updatedName, String updatedCode,
			ITestContext context) {
		preSteps();
		int id = (Integer) context.getAttribute("id");
		branchPage.clickOnActionCorrespondingToId(AppUserActionsEnum.Edit.name(), id);
		branchPage.assertDetailsOnEditForm(id, name, code);
		branchPage.updateBranchDetails(AppUserActionsEnum.Cancel.name(), updatedName, updatedCode);

		branchPage.assertBranchRowBy(id, name, code);
	}

	@Test(dataProvider = "editBranchDetails", dependsOnMethods = "tc_02_testCreateNewBranch")
	public void tc_08_testEditBranchDetails(String name, String code, String updatedName, String updatedCode,
			ITestContext context) {
		preSteps();
		int id = (Integer) context.getAttribute("id");
		branchPage.clickOnActionCorrespondingToId(AppUserActionsEnum.Edit.name(), id);
		branchPage.assertDetailsOnEditForm(id, name, code);
		branchPage.updateBranchDetails(AppUserActionsEnum.Save.name(), updatedName, updatedCode);

		branchPage.assertBranchRowBy(id, updatedName, updatedCode);
	}

	@Test(dependsOnMethods = "tc_02_testCreateNewBranch")
	public void tc_09_testDeleteBranchAndCancel(ITestContext context) {
		preSteps();

		int id = (Integer) context.getAttribute("id");
		Map<String, String> branchRow = branchPage.getBranchRowById(id);
		branchPage.clickOnActionCorrespondingToId(AppUserActionsEnum.Delete.name(), id);
		branchPage.assertDeleteConfirmationModalForBranch(id);
		branchPage.deleteBranch(AppUserActionsEnum.Cancel.name());

		branchPage.assertBranchRowBy(id, branchRow.get("Name"), branchRow.get("Code"));
	}

	@Test(dependsOnMethods = "tc_02_testCreateNewBranch")
	public void tc_10_testDeleteBranch(ITestContext context) {
		preSteps();
		int id = (Integer) context.getAttribute("id");
		branchPage.clickOnActionCorrespondingToId(AppUserActionsEnum.Delete.name(), id);
		branchPage.assertDeleteConfirmationModalForBranch(id);
		branchPage.deleteBranch(AppUserActionsEnum.Delete.name());
		int index = branchPage.getRowIndexById(id);

		assertEquals(index, -1, "Staff should get deleted.");
	}

	@Test(dataProvider = "validations")
	public void tc_11_testNewBranchFormValidations(String fieldName, String msg, String value) {
		preSteps();
		branchPage.assertValidationMsg(fieldName, value, msg);
		branchPage.assertSaveBtnDisable();
	}

	private void preSteps() {
		welcomePage = new WelcomePage(driver);
		loginPage = new LoginPage(driver);
		branchPage = new BranchMainPage(driver);
		branchDetailPage = new BranchesMainDetailPage(driver);

		welcomePage.clickOnLink(AppWelcomePageEnum.LOGIN_LINK.getVal());
		loginPage.LoginSucessFunctionalityCheck(USERNAME, PASSWORD);
		welcomePage.clickOnOption(AppMenuEnum.Entities.name(), AppPageAttributesEnum.Branch.name());
	}

	@DataProvider(name = "newBranchDetails")
	public Object[][] newBranchDetails() {
		return new Object[][] { { "Computer Science", "CS" } };
	}

	@DataProvider(name = "editBranchDetails")
	public Object[][] editBranchDetails() {
		return new Object[][] { { "Computer Science", "CS", "Branch CS Updated", "CS1" } };
	}

	@DataProvider(name = "validations")
	public Object[][] validations() {
		return new Object[][] {
			// @formatter:off
			{ "name", AppValidationConstantMessages.BRANCH_NAME_REQUIRED, "" },
			{ "name", AppValidationConstantMessages.BRANCH_NAME_MINIMUM, "a" },
			{ "name", AppValidationConstantMessages.BRANCH_NAME_PATTERN, "test1" },
			{ "name", AppValidationConstantMessages.BRANCH_NAME_PATTERN, "test@"},
			{ "name", AppValidationConstantMessages.BRANCH_NAME_MAXIMUM , "test the maximum limit of text field"},
			
			{ "code", AppValidationConstantMessages.BRANCH_CODE_REQUIRED, ""  },
			{ "code", AppValidationConstantMessages.BRANCH_CODE_MINIMUM, "A" },
			{ "code", AppValidationConstantMessages.BRANCH_CODE_PATTERN, "A@" },
			{ "code", AppValidationConstantMessages.BRANCH_CODE_PATTERN, "Ac" },
			{ "code", AppValidationConstantMessages.BRANCH_CODE_MAXIMUM, "CS1234567890" }
			// @formatter:on
		};
	}

}

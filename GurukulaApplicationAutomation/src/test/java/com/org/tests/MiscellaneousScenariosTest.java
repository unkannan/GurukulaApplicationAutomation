 
package com.org.tests;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.org.enums.AppUserActionsEnum;
import com.org.enums.AppPageAttributesEnum;
import com.org.enums.AppWelcomePageEnum;
import com.org.enums.AppMenuEnum;
import com.org.pages.BranchMainPage;
import com.org.pages.LoginPage;
import com.org.pages.StaffMainPage;
import com.org.pages.WelcomePage;
import com.org.utility.StartApplication;

 
@Test(groups = { "misc" })
public class MiscellaneousScenariosTest extends StartApplication {

	WelcomePage welcomePage;
	LoginPage loginPage;
	StaffMainPage staffPage;
	BranchMainPage branchPage;

	@Test(dataProvider = "branchAndStaffDetails")
	public void tc_01_testUpdatedBranchNameOnStaff(String branch, String code, String staff, ITestContext context) {
		preSteps();
		int branchId = branchPage.createNewBranch(branch, code);
		branchPage.assertBranchRowBy(branchId, branch, code);
		context.setAttribute("branchId", branchId);

		welcomePage.clickOnOption(AppMenuEnum.Entities.name(), AppPageAttributesEnum.Staff.name());

		int staffId = staffPage.createNewStaff(staff, branchId);
		staffPage.assertStaffRowBy(staffId, staff, branch);

		welcomePage.clickOnOption(AppMenuEnum.Entities.name(), AppPageAttributesEnum.Branch.name());
		branchPage.clickOnActionCorrespondingToId(AppUserActionsEnum.Edit.name(), branchId);
		branchPage.assertDetailsOnEditForm(branchId, branch, code);
		branchPage.updateBranchDetails(AppUserActionsEnum.Save.name(), "Civil", code);

		branchPage.assertBranchRowBy(branchId, "Civil", code);

		welcomePage.clickOnOption(AppMenuEnum.Entities.name(), AppPageAttributesEnum.Staff.name());
		staffPage.assertStaffRowBy(staffId, staff, "Civil");

	}

	@Test
	public void tc_02_testDeleteBranchAssociatedWithStaff(ITestContext context) {
		preSteps();
		int branchId = (Integer) context.getAttribute("branchId");
		branchPage.clickOnActionCorrespondingToId(AppUserActionsEnum.Delete.name(), branchId);
		branchPage.assertDeleteConfirmationModalForBranch(branchId);
		branchPage.deleteBranch(AppUserActionsEnum.Delete.name());

		/*
		 * TODO: Code need to be added after defect ID [defect_25] get fixed.
		 */
	}

	private void preSteps() {
		welcomePage = new WelcomePage(driver);
		loginPage = new LoginPage(driver);
		branchPage = new BranchMainPage(driver);
		staffPage = new StaffMainPage(driver);

		welcomePage.clickOnLink(AppWelcomePageEnum.LOGIN_LINK.getVal());
		loginPage.LoginSucessFunctionalityCheck(USERNAME, PASSWORD);
		welcomePage.clickOnOption(AppMenuEnum.Entities.name(), AppPageAttributesEnum.Branch.name());
	}

	@DataProvider(name = "branchAndStaffDetails")
	public Object[][] newBranchDetails() {
		return new Object[][] { { "Commerce", "ME", "Kannan" } };
	}

}

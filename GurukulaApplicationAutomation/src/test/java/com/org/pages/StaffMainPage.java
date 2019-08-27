package com.org.pages;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import com.org.enums.AppUserActionsEnum;
import com.org.utility.UIOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

 
public class StaffMainPage extends UIOperation {

    static Logger logger = Logger.getLogger(StaffMainPage.class.getName());

    public static final String HEADING = "Staffs";
    private static final String FORM_HEADING = "Create or edit a Staff";
    private static final String CONFIRMATION_MSG = "Are you sure you want to delete Staff *id*?";
    private static final String SEARCH_A_STAFF = "Search a Staff";

    private String xpathFormHeading = "//h4[@id='myStaffLabel']";
    private String xpathHeading = "//h2[@translate='gurukulaApp.staff.home.title']";
    private String xpathTableRows = "//table/tbody//tr";
    private String xpathBtn = "//button//span[contains(text(),'*btn*')]";
    private String xpathIdTF = "//input[@name='id']";
    private String xpathNameTF = "//input[@name='name']";
    private String xpathSelectBranch = "//select[@name='related_branch']";
    private String xpathTableRow = "//table/tbody//tr[*row*]";
    private String xpathTableCell = xpathTableRow + "//td[*col*]";
    private String xpathRowAction = "//tbody//tr[*row*]//td[4]/button//span[text()='*btn*']";
    private String xpathDeleteForm = "//form[@name='deleteForm']";
    private String xpathDeleteConfMsg = xpathDeleteForm + "//p";
    private String xpathDeleteFormBtn = xpathDeleteForm + "//span[text()='*btn*']";
    private String xpathValidationMsg = "//div[contains(@ng-show, 'name')]//p[not(contains(@class, 'ng-hide'))]";
    private String xpathDisabledSaveBtn = "//button[@disabled='disabled']//span[contains(text(),'Save')]";
    private String xpathSearchTF = "//input[@id='searchQuery']";

    Map<String, String> mapStaff;
    WebDriver driver;

    public StaffMainPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }
    /**
     * This function is to assert Staff page heading.
     */
    public void assertStaffPageHeading() {
        waitForElement(xpathHeading);
        String actualHeading = assertAndGetText(xpathHeading);
        logger.info("# Staff page header: " + actualHeading);
        assertEquals(actualHeading, HEADING,
            "Actual heading '" + actualHeading + "' should be same as expected heading '" + HEADING
                + "'.");
    }

    /**
     * This function is to create new staff with staff name & branch name.
     *
     * @param name: Name of staff
     * @param branch: Name of Branch associate to staff
     * @return id: newly created staff id
     */
    public int createNewStaff(String name, String branch) {
        clickOnBtn(AppUserActionsEnum.Create.name());
        fillCreateOrEditForm(name, branch);
        clickOnBtn(AppUserActionsEnum.Save.name());
        waitForElementInvisibility(xpathBtn.replace("*btn*", AppUserActionsEnum.Save.name()));
        int id = getMaxId();
        logger.info("# Newly Created Staff's Id: " + id);
        return id;
    }

    /**
     * This is to create new staff with staff name & branch id.
     *
     * @param name: Name of staff
     * @param branchId: id of Branch associate to staff
     * @return id: newly created staff id
     */
    public int createNewStaff(String name, int branchId) {
        clickOnBtn(AppUserActionsEnum.Create.name());
        fillCreateOrEditForm(name, branchId);
        clickOnBtn(AppUserActionsEnum.Save.name());
        waitForElementInvisibility(xpathBtn.replace("*btn*", AppUserActionsEnum.Save.name()));
        int id = getMaxId();
        logger.info("# Newly Created Staff's Id: " + id);
        return id;
    }

    /**
     * This function is to update Staff's details.
     *
     * @param action: Is to click on either Save or Cancel.
     * @param name: Staff's name
     * @param branch: Staff's associated branch
     */
    public void updateStaffDetails(String action, String name, String branch) {
        fillCreateOrEditForm(name, branch);
        clickOnBtn(action);
        waitForElementInvisibility(xpathBtn.replace("*btn*", action));
        logger.info("# Updated staff details");
    }

    /**
     * This is function is to create or cancel staff.
     *
     * @param name: Name of staff
     * @param branch: Name of Branch associate to staff
     */
    public int createAndCancel(String name, String branch) {
        clickOnBtn(AppUserActionsEnum.Create.name());
        fillCreateOrEditForm(name, branch);
        clickOnBtn(AppUserActionsEnum.Cancel.name());
        waitForElementInvisibility(xpathBtn.replace("*btn*", AppUserActionsEnum.Cancel.name()));
        return getMaxId();
    }

    /**
     * This function is to assert staff by id, name & branch.
     *
     * @param id: Staff's ID
     * @param name: Staff's Name
     * @param branch: Staff's Branch
     */
    public void assertStaffRowBy(int id, String name, String branch) {
        mapStaff = getStaffRowById(id);
        assertEquals(mapStaff.get("ID"), String.valueOf(id),
            "Actual ID " + mapStaff.get("ID") + "should be same as expected " + id);
        assertEquals(mapStaff.get("Name"), name,
            "Actual Name " + mapStaff.get("Name") + "should be same as expected " + name);
        assertEquals(mapStaff.get("Branch"), branch,
            "Actual Branch " + mapStaff.get("Branch") + "should be same as expected " + branch);
    }

    /**
     * This function is to navigate to Staff's Details Page corresponding to given staff's id.
     *
     * @param id: Staff's id
     */
    public void navigateToStaffDetailPageById(int id) {
        String rowIndex = String.valueOf(getRowIndexById(id));
        String xpathIdLink = xpathTableCell.replace("*row*", rowIndex).replace("*col*", "1");
        xpathIdLink = xpathIdLink + "//a";
        assertAndClick(xpathIdLink);
        logger.info("# Clicked on id link: " + id);
    }

    /**
     * This function is to click on actions(View, Edit, Delete) button corresponding to staff's id.
     *
     * @param action: actions like Save or Edit or Delete
     * @param id: Staff's Id.
     */
    public void clickOnActionCorrespondingToId(String action, int id) {
        String rowIndex = String.valueOf(getRowIndexById(id));
        String xpathRowView = xpathRowAction.replace("*row*", rowIndex);
        xpathRowView = xpathRowView.replace("*btn*", action);
        assertAndClick(xpathRowView);
        logger.info("# Clicked on '" + action + "' button corresponding to id: " + id);
    }

    /**
     * This function is to assert details on Staff's Edit Form corresponding to staff's id.
     *
     * @param id: Staff's id
     * @param name: Staff's Name
     * @param branch: Staff's Branch
     */
    public void assertDetailsOnEditForm(int id, String name, String branch) {
    	waitForElementVisibility(xpathFormHeading);
        String actualId = assertAndGetAttributeValue(xpathIdTF, "value");
        String actualName = assertAndGetAttributeValue(xpathNameTF, "value");
        String actualBranch = getSelectTextByAttribute(xpathSelectBranch, "label");

        assertEquals(actualId, String.valueOf(id),
            "Actual ID " + actualId + " should be same as expected " + id);
        assertEquals(actualName, name,
            "Actual Name " + actualName + " should be same as expected " + name);
        assertEquals(actualBranch, branch,
            "Actual Branch " + actualBranch + " should be same as expected " + branch);
    }

    /**
     * This function is to assert Confirmation modal corresponding to staff's id.
     *
     * @param id: Staff's Id
     */
    public void assertDeleteConfirmationModalForStaff(int id) {
        waitForElementVisibility(xpathDeleteConfMsg);
        String actualMsg = assertAndGetAttributeValue(xpathDeleteConfMsg, "innerText");
        logger.info("# Delete confirmation modal: " + actualMsg);
        String expetedMsg = CONFIRMATION_MSG.replace("*id*", String.valueOf(id));
        assertEquals(actualMsg, expetedMsg,
            "Actual message '" + actualMsg + "' should be same as expected '" + expetedMsg + "'.");
    }

    /**
     * This function is to click on the Delete button on the Delete Confirmation modal.
     *
     * @param action: Delete
     */
    public void deleteStaff(String action) {
        assertAndClick(xpathDeleteFormBtn.replace("*btn*", action));
        waitForElementInvisibility(xpathDeleteFormBtn.replace("*btn*", action));
        logger.info("# Clicked on '" + action + "' button on Delete confirmation modal");
    }

    /**
     * This function is to assert validation message for Staff's name field on Create or Edit form.
     *
     * @param txt: text to be entered
     * @param msg: validation message
     */
    public void assertValidationMsg(String txt, String msg) {
        clickOnBtn(AppUserActionsEnum.Create.name());
        waitForElementVisibility(xpathFormHeading);
        String actual = assertAndGetAttributeValue(xpathFormHeading, "innerText");
        assertEquals(actual, FORM_HEADING,
            "Actual heading '" + actual + "' should be same as expected heading '" + FORM_HEADING
                + "'.");
        logger.info("# User is on '" + actual + "' form");
        assertAndType(xpathNameTF, txt);
        actual = assertAndGetText(xpathValidationMsg);
        logger.info("# Validation message for 'Name': " + actual);
        assertEquals(actual, msg,
            "Actual validation msg '" + actual + "' should be same as expected '" + msg + "'.");
    }

    /**
     * This function is to get the staff row corresponding to given staff's id.
     *
     * @param id: Staff's Id
     * @return map: Map of Staff's row
     */
    public Map<String, String> getStaffRowById(int id) {
        String rowIndex = String.valueOf(getRowIndexById(id));
        String xpathRow = xpathTableCell.replace("*row*", rowIndex);
        mapStaff = new HashMap<String, String>();

        mapStaff.put("ID", assertAndGetText(xpathRow.replace("*col*", "1")));
        mapStaff.put("Name", assertAndGetText(xpathRow.replace("*col*", "2")));
        mapStaff.put("Branch", assertAndGetText(xpathRow.replace("*col*", "3")));

        logger.info("# Staff info corresponding to staff id (" + id + "): " + mapStaff);
        return mapStaff;
    }

    /**
     * This function is to get the row index corresponding to staff's id.
     *
     * @param id: Staff's Id
     * @return row index
     */
    public int getRowIndexById(int id) {

        String xpathRowsWithIdCell = xpathTableCell.replace("*col*", "1");
        int rowIndex = 1;
        String xpath = xpathRowsWithIdCell.replace("*row*", String.valueOf(rowIndex));
        while (isElementPresent(xpath)) {
            if (assertAndGetText(xpath).equals(String.valueOf(id))) {
                return rowIndex;
            }
            rowIndex++;
            xpath = xpathRowsWithIdCell.replace("*row*", String.valueOf(rowIndex));
        }
        return -1;
    }

    /**
     * This function is to fill the details on the Create or Edit Staff Form.
     *
     * @param name: Staff's Name
     * @param branch: Branch name
     */
    private void fillCreateOrEditForm(String name, String branch) {
        waitForElementVisibility(xpathFormHeading);
        String actual = assertAndGetAttributeValue(xpathFormHeading, "innerText");
        assertEquals(actual, FORM_HEADING,
            "Actual heading '" + actual + "' should be same as expected heading '" + FORM_HEADING
                + "'.");
        logger.info("# User is on '" + actual + "' form");
        assertAndType(xpathNameTF, name);
        logger.info("# Entered staff name: " + name);
        selectByVisibleText(xpathSelectBranch, branch);
        logger.info("# Select branch name: " + branch);
    }

    /**
     * This function is to fill the details on the Create or Edit Staff Form.
     *
     * @param name: Staff's Name
     * @param branchId: Branch Id
     */
    private void fillCreateOrEditForm(String name, int branchId) {
        waitForElementVisibility(xpathFormHeading);
        String actual = assertAndGetAttributeValue(xpathFormHeading, "innerText");
        assertEquals(actual, FORM_HEADING,
            "Actual heading '" + actual + "' should be same as expected heading '" + FORM_HEADING
                + "'.");
        logger.info("# User is on '" + actual + "' form");
        assertAndType(xpathNameTF, name);
        logger.info("# Entered staff name: " + name);
        selectByValue(xpathSelectBranch, "number:" + branchId);
        logger.info("# Select branch id: " + branchId);
    }

    /**
     * This function is to get the max Id on the Staff page.
     *
     * @return max id
     */
    public int getMaxId() {
        int count = driver.findElements(By.xpath(xpathTableRows)).size();
        int maxID = -1;
        if (count > 0) {
            maxID = Integer.parseInt(assertAndGetText(xpathTableRows + "[" + count + "]//td[1]"));
        }
        return maxID;
    }

    /**
     * This function is to click on the button based on button text.
     *
     * @param btnTxt: button name.
     */
    private void clickOnBtn(String btnTxt) {
        waitForElementClickable(xpathBtn.replace("*btn*", btnTxt));
        assertAndClick(xpathBtn.replace("*btn*", btnTxt));
        logger.info("# Clicked on '" + btnTxt + "' button");
    }

    /**
     * This function is to assert that save button should be disabled.
     */
    public void assertSaveBtnDisable() {
        assertTrue(isElementPresent(xpathDisabledSaveBtn), "Save button should be disabled.");
        logger.info("# 'Save' button is disabled.");
    }

    /**
     * This function is to search staffs on the Staff page.
     *
     * @param searchTxt: search keywords
     */
    public void searchStaff(String searchTxt) {
        assertAndType(xpathSearchTF, searchTxt);
        clickOnBtn(SEARCH_A_STAFF);
        logger.info("# Performed staff search for '" + searchTxt + "'");
    }

    /**
     * This function is to return list of all rows present on Staff page.
     *
     * @return list of staff rows.
     */
    private List<List<String>> getAllRows() {
        List<List<String>> rowLst = new ArrayList<List<String>>();
        int rowIndex = 1;
        String xpathRow = xpathTableRow.replace("*row*", String.valueOf(rowIndex));
        while (isElementPresent(xpathRow) && isElementVisible(xpathRow)) {
            List<String> row = new ArrayList<String>();
            for (int i = 1; i <= 3; i++) {
                String xpathCell = xpathTableCell.replace("*row*", String.valueOf(rowIndex))
                    .replace("*col*",
                        String.valueOf(i));
                waitForElementVisibility(xpathCell);
                row.add(assertAndGetText(xpathCell));
            }
            logger.info("# Row " + rowIndex + ": " + row);
            rowLst.add(row);
            xpathRow = xpathTableRow.replace("*row*", String.valueOf(++rowIndex));
        }
        logger.info("# List of rows: " + rowLst);
        return rowLst;
    }

    /**
     * This function is to assert that search text or searched keyword is present in searched
     * result.
     *
     * @param searchKeyword: search keywords
     */
    public void assertSearchResult(String searchKeyword) {
        List<List<String>> rowLst = getAllRows();
        assertTrue(rowLst.size() > 0, "Search is not performed.");
        boolean isPresent;
        for (List<String> row : rowLst) {
            isPresent = false;
            for (String col : row) {
                if (col.toLowerCase().contains(searchKeyword.toLowerCase())) {
                    isPresent = true;
                    break;
                }
            }
            assertTrue(isPresent,
                "Search keyword '" + searchKeyword + "' is not present in row: " + row);
        }
    }

    /**
     * This function is to assert that searched text is not present in the search result. Negation
     * keyword search
     *
     * @param searchKeyword: search keyword.
     */
    public void assertSearchResultShouldNotContain(String searchKeyword) {
        List<List<String>> rowLst = getAllRows();
        for (List<String> row : rowLst) {
            boolean isPresent = false;
            for (String col : row) {
                if (col.toLowerCase().contains(searchKeyword.toLowerCase())) {
                    isPresent = true;
                    break;
                }
            }
            assertFalse(isPresent,
                "Search keyword '" + searchKeyword + "' is present in row: " + row);
        }
    }

    /**
     * This function is to assert that searched keyword should be present at-least in one of the
     * searched staffs
     *
     * @param searchKeyword: search keywords -- Multiple search keywords.
     */
    public void assertAtleatOneResultFound(String searchKeyword) {
        List<List<String>> rowLst = getAllRows();
        boolean isPresent = false;
        for (List<String> row : rowLst) {
            for (String col : row) {
                if (col.toLowerCase().contains(searchKeyword.toLowerCase())) {
                    isPresent = true;
                    break;
                }
            }
            if (isPresent) {
                break;
            }
        }
        assertTrue(isPresent,
            "Search keyword '" + searchKeyword + "' is not present in searched result: " + rowLst);
    }
}

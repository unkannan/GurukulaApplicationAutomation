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

 
public class BranchMainPage extends UIOperation {

     
	static Logger logger = Logger.getLogger(BranchMainPage.class.getName());

    public static final String HEADING = "Branches";
    private static final String FORM_HEADING = "Create or edit a Branch";
    private static final String CONFIRMATION_MSG = "Are you sure you want to delete Branch *id*?";
    private static final String SEARCH_A_BRANCH = "Search a Branch";

    private String xpathHeading = "//h2[@translate='gurukulaApp.branch.home.title']";
    private String xpathBtn = "//button//span[contains(text(),'*btn*')]";
    private String xpathFormHeading = "//h4[@id='myBranchLabel']";
    private String xpathIdTF = "//input[@name='id']";
    private String xpathNameTF = "//input[@name='name']";
    private String xpathCodeTF = "//input[@name='code']";
    private String xpathTextField = "//input[@name='*label*']";
    private String xpathTableRows = "//table/tbody//tr";
    private String xpathTableRow = "//table/tbody//tr[*row*]";
    private String xpathTableCell = xpathTableRow + "//td[*col*]";
    private String xpathRowAction = "//tbody//tr[*row*]//td[4]/button//span[text()='*btn*']";
    private String xpathDeleteForm = "//form[@name='deleteForm']";
    private String xpathDeleteConfMsg = xpathDeleteForm + "//p";
    private String xpathDeleteFormBtn = xpathDeleteForm + "//span[text()='*btn*']";
    private String xpathValidationMsg = "//div[contains(@ng-show, '*label*')]//p[not(contains(@class, 'ng-hide'))]";
    private String xpathDisabledSaveBtn = "//button[@disabled='disabled']//span[contains(text(),'Save')]";
    private String xpathSearchTF = "//input[@id='searchQuery']";

    private WebDriver driver;

    public BranchMainPage(WebDriver driver) {
   	 super(driver);
        this.driver = driver;
	}


    /**
     * This function is to assert Branch page heading.
     */
    public void assertBranchPageHeading() {
        waitForElement(xpathHeading);
        String actualHeading = assertAndGetText(xpathHeading);
        logger.info("# Branch page header: " + actualHeading);
        assertEquals(actualHeading, HEADING,
            "Actual heading '" + actualHeading + "' should be same as expected heading '" + HEADING
                + "'.");
    }

    /**
     * This function is to create new branch with branch name & branch code.
     *
     * @param name: Branch's name
     * @param code: Branch's code
     * @return id: newly created branch id
     */
    public int createNewBranch(String name, String code) {
        clickOnBtn(AppUserActionsEnum.Create.name());
        fillCreateOrEditForm(name, code);
        clickOnBtn(AppUserActionsEnum.Save.name());
        waitForElementInvisibility(xpathBtn.replace("*btn*", AppUserActionsEnum.Save.name()));
        int id = getMaxId();
        logger.info("# Newly Created Branch's Id: " + id);
        return id;
    }

    /**
     * This is function is to create or cancel branch.
     *
     * @param name: Branch's Name
     * @param code: Branch's code
     */
    public int createAndCancel(String name, String code) {
        clickOnBtn(AppUserActionsEnum.Create.name());
        fillCreateOrEditForm(name, code);
        clickOnBtn(AppUserActionsEnum.Cancel.name());
        waitForElementInvisibility(xpathBtn.replace("*btn*", AppUserActionsEnum.Cancel.name()));
        return getMaxId();
    }

    /**
     * This function is to get the max Id on the Branch page.
     *
     * @return max id
     */
    public int getMaxId() {
        int count = driver.findElements(By.xpath(xpathTableRows)).size();
        if (count > 0) {
            return Integer.parseInt(assertAndGetText(xpathTableRows + "[" + count + "]//td[1]"));
        }
        return -1;
    }

    /**
     * This function is to fill the details on the Create or Edit Branch Form.
     *
     * @param name: Branch's Name
     * @param code: Branch's Code
     */
    private void fillCreateOrEditForm(String name, String code) {
        waitForElementVisibility(xpathFormHeading);
        String actual = assertAndGetAttributeValue(xpathFormHeading, "innerText");
        assertEquals(actual, FORM_HEADING,
            "Actual heading '" + actual + "' should be same as expected heading '" + FORM_HEADING
                + "'.");
        logger.info("# User is on '" + actual + "' form");
        assertAndType(xpathNameTF, name);
        logger.info("# Entered branch name: " + name);
        assertAndType(xpathCodeTF, code);
        logger.info("# Entered branch code: " + code);
    }

    /**
     * This function is to click on the button based on button text.
     *
     * @param btn: button name.
     */
    private void clickOnBtn(String btn) {
        waitForElementClickable(xpathBtn.replace("*btn*", btn));
        assertAndClick(xpathBtn.replace("*btn*", btn));
        logger.info("# Clicked on '" + btn + "' button");
    }

    /**
     * This function is to assert branch by id, branch name & branch code.
     *
     * @param id: Branch's ID
     * @param name: Branch's Name
     * @param code: Branch's Code
     */
    public void assertBranchRowBy(int id, String name, String code) {
        Map<String, String> mapBranch = getBranchRowById(id);
        assertEquals(mapBranch.get("ID"), String.valueOf(id),
            "Actual ID '" + mapBranch.get("ID") + "' should be same as expected " + id);
        assertEquals(mapBranch.get("Name"), name,
            "Actual Name '" + mapBranch.get("Name") + "' should be same as expected " + name);
        assertEquals(mapBranch.get("Code"), code,
            "Actual Branch '" + mapBranch.get("Code") + "' should be same as expected " + code);
    }

    /**
     * This function is to get the branch row corresponding to given branch's id.
     *
     * @param id: branch's Id
     * @return map: Map of branch's row
     */
    public Map<String, String> getBranchRowById(int id) {
        String rowIndex = String.valueOf(getRowIndexById(id));
        String xpathRow = xpathTableCell.replace("*row*", rowIndex);
        Map<String, String> mapBranch = new HashMap<String, String>();

        mapBranch.put("ID", assertAndGetText(xpathRow.replace("*col*", "1")));
        mapBranch.put("Name", assertAndGetText(xpathRow.replace("*col*", "2")));
        mapBranch.put("Code", assertAndGetText(xpathRow.replace("*col*", "3")));

        logger.info("# Branch info corresponding to branch id (" + id + "): " + mapBranch);
        return mapBranch;
    }

    /**
     * This function is to get the row index corresponding to branch's id.
     *
     * @param id: Branch's Id
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
     * This function is to navigate to Branch's Details Page corresponding to given branch's id.
     *
     * @param id: Branch's id
     */
    public void navigateToBranchDetailPageById(int id) {
        String rowIndex = String.valueOf(getRowIndexById(id));
        String xpathIdLink = xpathTableCell.replace("*row*", rowIndex).replace("*col*", "1");
        xpathIdLink = xpathIdLink + "//a";
        assertAndClick(xpathIdLink);
        logger.info("# Clicked on id link: " + id);
    }

    /**
     * This function is to click on actions(View, Edit, Delete) button corresponding to branch's
     * id.
     *
     * @param action: actions like Save or Edit or Delete
     * @param id: Branch's Id.
     */
    public void clickOnActionCorrespondingToId(String action, int id) {
        String rowIndex = String.valueOf(getRowIndexById(id));
        String xpathRowView = xpathRowAction.replace("*row*", rowIndex);
        xpathRowView = xpathRowView.replace("*btn*", action);
        assertAndClick(xpathRowView);
        logger.info("# Clicked on '" + action + "' button corresponding to id: " + id);
    }

    /**
     * This function is to assert details on Branch's Edit Form corresponding to branch's id.
     *
     * @param id: Branch's id
     * @param name: Branch's Name
     * @param code: Branch's Code
     */
    public void assertDetailsOnEditForm(int id, String name, String code) {
    	waitForElementVisibility(xpathFormHeading);
        String actualId = assertAndGetAttributeValue(xpathIdTF, "value");
        String actualName = assertAndGetAttributeValue(xpathNameTF, "value");
        String actualCode = assertAndGetAttributeValue(xpathCodeTF, "value");

        assertEquals(actualId, String.valueOf(id),
            "Actual ID " + actualId + "should be same as expected " + id);
        assertEquals(actualName, name,
            "Actual Name " + actualName + "should be same as expected " + name);
        assertEquals(actualCode, code,
            "Actual Code " + actualCode + "should be same as expected " + code);
    }

    /**
     * This function is to update Branch's details.
     *
     * @param action: Is to click on either Save or Cancel.
     * @param name: Branch's name
     * @param code: Branch's code
     */
    public void updateBranchDetails(String action, String name, String code) {
        fillCreateOrEditForm(name, code);
        clickOnBtn(action);
        waitForElementInvisibility(xpathBtn.replace("*btn*", action));
        logger.info("# Updated branch details");
    }

    /**
     * This function is to assert Confirmation modal corresponding to branch's id.
     *
     * @param id: Branch's Id
     */
    public void assertDeleteConfirmationModalForBranch(int id) {
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
    public void deleteBranch(String action) {
        assertAndClick(xpathDeleteFormBtn.replace("*btn*", action));
        waitForElementInvisibility(xpathDeleteFormBtn.replace("*btn*", action));
        logger.info("# Clicked on '" + action + "' button on Delete confirmation modal");
    }

    /**
     * This function is to assert validation message on Branch Create or Edit form.
     *
     * @param fieldName: Field name Name or Code
     * @param value: value to be entered
     * @param msg: validation message
     */
    public void assertValidationMsg(String fieldName, String value, String msg) {
        clickOnBtn(AppUserActionsEnum.Create.name());
        waitForElementVisibility(xpathFormHeading);
        String actual = assertAndGetAttributeValue(xpathFormHeading, "innerText");
        assertEquals(actual, FORM_HEADING,
            "Actual heading '" + actual + "' should be same as expected heading '" + FORM_HEADING
                + "'.");
        logger.info("# User is on '" + actual + "' form");

        fieldName = fieldName.toLowerCase();
        assertAndType(xpathTextField.replace("*label*", fieldName), value);
        actual = assertAndGetText(xpathValidationMsg.replace("*label*", fieldName));
        logger.info("# Validation message for '" + fieldName + "': " + actual);
        assertEquals(actual, msg,
            "Actual validation msg '" + actual + "' should be same as expected '" + msg + "'.");
    }

    /**
     * This function is to assert that save button should be disabled.
     */
    public void assertSaveBtnDisable() {
        assertTrue(isElementPresent(xpathDisabledSaveBtn), "Save button should be disabled.");
        logger.info("# 'Save' button is disabled");
    }

    /**
     * This function is to search Barnches on the Branch page.
     *
     * @param searchTxt: search keywords
     */
    public void searchBranch(String searchTxt) {
        assertAndType(xpathSearchTF, searchTxt);
        clickOnBtn(SEARCH_A_BRANCH);
        logger.info("# Performed branch search for '" + searchTxt + "'");
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
     * This function is to return list of all rows present on Branch page.
     *
     * @return list of branch rows.
     */
    public List<List<String>> getAllRows() {
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
     * This function is to assert that searched keyword should be present atleast in one of the
     * searched branches
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

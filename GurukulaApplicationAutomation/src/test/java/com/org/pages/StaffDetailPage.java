package com.org.pages;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import com.org.utility.UIOperation;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class StaffDetailPage extends UIOperation {

    static Logger logger = Logger.getLogger(StaffDetailPage.class.getName());
    public static final String HEADING = "Staff ";

    private String xpathHeading = "//h2";
    private String xpathInfoIF = "//input[@value='*val*']";

    WebDriver driver;

    public StaffDetailPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    /**
     * This function is to assert staff details on the Staff Detail Page.
     *
     * @param id: staff id
     * @param name: staff name
     * @param branch: branch name
     */
    public void assertInfoOnStaffDetailPage(int id, String name, String branch) {
        waitForElement(xpathHeading);

        String expectedHeading = HEADING + id;
        String actualHeading = assertAndGetText(xpathHeading);
        logger.info("# Staff Details page header: " + actualHeading);
        assertEquals(actualHeading, expectedHeading,
            "Actual heading '" + actualHeading + "' should be same as expected heading '"
                + expectedHeading + "'.");

        assertTrue(isElementPresent(xpathInfoIF.replace("*val*", name)),
            "Name '" + name + "' should be present.");
        assertTrue(isElementPresent(xpathInfoIF.replace("*val*", branch)),
            "Branch '" + branch + "' should be present.");

    }
}
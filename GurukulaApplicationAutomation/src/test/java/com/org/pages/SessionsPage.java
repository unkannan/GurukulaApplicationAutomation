 
package com.org.pages;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import com.org.utility.UIOperation;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
 
public class SessionsPage extends UIOperation {

    static Logger logger = Logger.getLogger(SessionsPage.class.getName());

    public static final String HEADING = "Active sessions for [*uname*]";
    public static final String SUCCESS = "Session invalidated!";
    public static final String ERROR = "An error has occurred! The session could not be invalidated.";

    private static String xpathHeading = "//h2";
    private static String xpathInvalidateBtn = "//button[text()='Invalidate']";
    private static String xpathAlert = "//div[contains(@class, 'alert') and not(contains(@class, 'hide'))]";

    WebDriver driver;

    public SessionsPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    /**
     * This function is to assert Session page and username on that page.
     *
     * @param username: enter username.
     */
    public void assertUpdatePasswordPageHeading(String username) {
        waitForElement(xpathHeading);
        String actualHeading = assertAndGetText(xpathHeading);
        String expectedHeading = HEADING.replace("*uname*", username);
        logger.info("# Sessions page header: " + actualHeading);
        assertEquals(actualHeading, expectedHeading,
            "Actual heading '" + actualHeading + "' should be same as '" + expectedHeading + "'.");
    }

    /**
     * This function is to invalidate active sessions.
     */
    public void invalidateSession() {
        waitForElement(xpathInvalidateBtn);
        assertAndClick(xpathInvalidateBtn);
        logger.info("# Clicked on 'Invalidate' session button");
    }

    /**
     * This function is to assert success message.
     */
    public void assertSuccessMessage() {
        assertTrue(isElementPresent(xpathAlert), "Session should be invalidated.");
        logger.info("# Success message: " + SUCCESS);
    }

}

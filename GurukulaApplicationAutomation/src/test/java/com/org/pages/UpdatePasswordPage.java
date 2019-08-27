
package com.org.pages;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import com.org.utility.UIOperation;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

 
public class UpdatePasswordPage extends UIOperation {

    static Logger logger = Logger.getLogger(UpdatePasswordPage.class.getName());

    public static final String HEADING = "Password for [*uname*]";
    public static final String SUCCESS = "Password changed!";
    public static final String ERROR = "An error has occurred! The password could not be changed.";
    public static final String VALIDATION = "The password and its confirmation do not match!";
    public static final String PASSWORD = "password";
    public static final String CONFIRM_PASSWORD = "confirmPassword";

    public static String xpathHeading = "//h2";
    private static String xpathTextField = "//input[@name='*fieldName*']";
    private static String xpathSave = "//button[@type='submit']";
    private static String xpathAlertMsg = "//div[contains(@class, 'alert') and not(contains(@class, 'ng-hide'))]";
    private static String xpathFieldValidation = xpathTextField
        + "/following-sibling::div/p[not(contains(@class, 'hide'))]";
    private String xpathDisabledSaveBtn = "//button[@disabled='disabled' and contains(text(),'Save')]";

    WebDriver driver;

    public UpdatePasswordPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    /**
     * This function is to assert Password page corresponding to user.
     *
     * @param username: username
     */
    public void assertUpdatePasswordPageHeading(String username) {
        waitForElement(xpathHeading);
        String actualHeading = assertAndGetText(xpathHeading);
        logger.info("# Pasword page header: " + actualHeading);
        String expectedHeading = HEADING.replace("*uname*", username);
        assertEquals(actualHeading, expectedHeading,
            "Actual heading '" + actualHeading + "' should be same as '" + expectedHeading + "'.");
    }

    /**
     * This function is to update password.
     *
     * @param newPassword: enter new password
     * @param confirmPassword: enter confirmation password
     */
    public void enterNewPasswordAndSubmit(String newPassword, String confirmPassword) {
        enterTextIn(PASSWORD, newPassword);
        enterTextIn(CONFIRM_PASSWORD, confirmPassword);
        clickOnSave();
    }

    /**
     * This function is enter values in the given field
     *
     * @param fieldName: field name
     * @param txt: text to enter
     */
    public void enterTextIn(String fieldName, String txt) {
        String xpath = xpathTextField.replace("*fieldName*", fieldName);
        waitForElement(xpath);
        assertAndType(xpath, txt);
        logger.info("# Entered text in '" + fieldName + "': " + txt);
    }

    /**
     * This function is to click on Save button.
     */
    private void clickOnSave() {
        assertAndClick(xpathSave);
        logger.info("# Clicked on 'Save' button");
    }

    /**
     * This function is to clear text from the field.
     *
     * @param fieldName: field name.
     */
    public void clearText(String fieldName) {
        driver.findElement(By.xpath(xpathTextField.replace("*fieldName*", fieldName))).clear();
        logger.info("# Cleared '" + fieldName + "' to verify required field validation");
    }
    /**
     * This function is to assert message
     *
     * @param msg: message
     */
    public void assertMessage(String msg) {
        waitForElement(xpathAlertMsg);
        String actualMsg = assertAndGetText(xpathAlertMsg);
        logger.info("# Alert message: " + actualMsg);
        assertEquals(actualMsg, msg,
            "Actual message '" + actualMsg + "' should be same as expected message '" + msg + "'.");
    }
    /**
     * This function is to assert validation messages corresponding to given field name.
     *
     * @param fieldName: field name
     * @param vMsg: message
     */
    public void assertFieldValidationMessage(String fieldName, String vMsg) {
        String xpath = xpathFieldValidation.replace("*fieldName*", fieldName);
        String actual = assertAndGetText(xpath);
        logger.info("# Validation message for '" + fieldName + "': " + actual);
        assertEquals(actual, vMsg, "Actual validation message '" + actual
            + "' should be same as expected validation message '" + vMsg + "'.");
    }

    /**
     * This function is to assert that Save button is disabled.
     */
    public void assertSaveBtnDisable() {
        assertTrue(isElementPresent(xpathDisabledSaveBtn), "Save button should be disabled.");
        logger.info("# 'Save' button is disabled");
    }

}

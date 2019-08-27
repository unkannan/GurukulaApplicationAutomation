 
package com.org.pages;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import com.org.utility.UIOperation;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
 
public class SettingsPage extends UIOperation {

    static Logger logger = Logger.getLogger(SettingsPage.class.getName());

    public static final String HEADING = "User settings for [*uname*]";
    public static final String SUCCESS = "Settings saved!";
    public static final String ERROR = "An error has occurred! Settings could not be saved";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String EMAIL = "email";

    private static String xpathHeading = "//h2";
    private static String xpathTextField = "//input[@name='*fieldName*']";
    private static String xpathSelectLang = "//select[@name='langKey']";
    private static String xpathSave = "//button[@type='submit']";
    private static String xpathAlertMsg = "//div[contains(@class, 'alert') and not(contains(@class, 'ng-hide'))]";
    private static String xpathFieldValidation = xpathTextField
        + "/following-sibling::div/p[not(contains(@class, 'hide'))]";
    private String xpathDisabledSaveBtn = "//button[@disabled='disabled' and contains(text(),'Save')]";

    WebDriver driver;

    public SettingsPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    /**
     * This function is to assert Settings page corresponding to user.
     *
     * @param username: enter username
     */
    public void assertSettingsPageHeading(String username) {
        waitForElement(xpathHeading);
        String actualHeading = assertAndGetText(xpathHeading);
        logger.info("# Settings page header: " + actualHeading);
        String expectedHeading = HEADING.replace("*uname*", username);
        assertEquals(actualHeading, expectedHeading,
            "Actual heading '" + actualHeading + "' should be same as '" + expectedHeading + "'.");
    }

    /**
     * This function is to enter details on th setting page.
     *
     * @param firstName: enter first name.
     * @param lastName: enter last name.
     * @param email: enter email.
     * @param lang: enter language.
     */
    public void enterDetailsAndSubmit(String firstName, String lastName, String email,
        String lang) {
        enterTextIn(FIRST_NAME, firstName);
        enterTextIn(LAST_NAME, lastName);
        enterTextIn(EMAIL, email);
        selectLanguage(lang);
        clickOnSave();
    }

    /**
     * This function is to enter text into given field.
     *
     * @param fieldName: field name
     * @param txt: text to be entered
     */
    public void enterTextIn(String fieldName, String txt) {
        String xpath = xpathTextField.replace("*fieldName*", fieldName);
        waitForElement(xpath);
        assertAndType(xpath, txt);
        logger.info("# Entered text in '" + fieldName + "': " + txt);
    }

    /**
     * This function is to select language.
     *
     * @param lang: enter language.
     */
    private void selectLanguage(String lang) {
        waitForElement(xpathSelectLang);
        selectByVisibleText(xpathSelectLang, lang);
        logger.info("# Selected language: " + lang);
    }

    /**
     * This function is to click on the Save button.
     */
    private void clickOnSave() {
        assertAndClick(xpathSave);
        logger.info("# Clicked on 'Save' button");
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

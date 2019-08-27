 
package com.org.pages;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import com.org.utility.UIOperation;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

 
public class RegistrationPage extends UIOperation {

    static Logger logger = Logger.getLogger(RegistrationPage.class.getName());

    public static final String HEADING = "Registration";
    public static final String LOGIN = "login";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String CONFIRM_PASSWORD = "confirmPassword";

    public static final String SUCCESS = "Registration saved! Please check your email for confirmation.";
    public static final String REGISTRAION_FAILED = "Registration failed! Please try again later.";
    public static final String ALREADY_REGISTERED = "Login name already registered! Please choose another one.";
    public static final String EMAIL_IN_USE = "E-mail is already in use! Please choose another one.";

    private String xpathHeading = "//h1[@translate='register.title']";
    private String xpathTextField = "//input[@name='*fieldName*']";
    private String xpathRegisterBtn = "//button[text()='Register']";
    private String xpathAlertMsg = "//div[contains(@class, 'alert') and not(contains(@class, 'hide') or contains(@class, 'warning'))]";
    private String xpathFieldValidation =
        xpathTextField + "/following-sibling::div/p[not(contains(@class, 'hide'))]";
    private String xpathDisabledSaveBtn = "//button[@disabled='disabled' and contains(text(),'Register')]";

    WebDriver driver;

    public RegistrationPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    /**
     * This function is to checking the Registration page heading.
     */
    public void assertRegistrationPageHeading() {
        waitForElement(xpathHeading);
        String actualHeading = assertAndGetText(xpathHeading);
        logger.info("# Registration page header: " + actualHeading);
        assertEquals(actualHeading, HEADING,
            "Actual heading '" + actualHeading + "' should be same as expected heading '" + HEADING
                + "'.");
    }

    /**
     * This function is to provide input details on Registration page.
     *
     * @param name: enter name
     * @param email: enter email
     * @param password: enter password
     * @param confirmPassword: enter confirmation password
     */
    public void registerNewUser(String name, String email, String password,
        String confirmPassword) {
        enterTextIn(LOGIN, name);
        enterTextIn(EMAIL, email);
        enterTextIn(PASSWORD, password);
        enterTextIn(CONFIRM_PASSWORD, confirmPassword);
        clickOnSaveBtn();
    }

    /**
     * This function is to click on the Save button.
     */
    private void clickOnSaveBtn() {
        waitForElement(xpathRegisterBtn);
        assertAndClick(xpathRegisterBtn);
        logger.info("# Clicked on 'Save' button");
    }

    /**
     * This function is to enter values in the field
     *
     * @param fieldName: field name
     * @param txt: test to be entered
     */
    public void enterTextIn(String fieldName, String txt) {
        String xpath = xpathTextField.replace("*fieldName*", fieldName);
        waitForElement(xpath);
        assertAndType(xpath, txt);
        logger.info("# Entered text in '" + fieldName + "': " + txt);
    }

    /**
     * This function is to assert various validation messages
     *
     * @param msg: message to be validated.
     */
    public void assertMessage(String msg) {
        waitForElement(xpathAlertMsg);
        String actualMsg = assertAndGetText(xpathAlertMsg);
        logger.info("# Alert message: " + actualMsg);
        assertEquals(actualMsg, msg,
            "Actual message '" + actualMsg + "' should be same as expected message '" + msg + "'.");
    }

    /**
     * This function is to assert validation message corresponding to given field.
     *
     * @param fieldName: field name
     * @param vMsg: validation message.
     */
    public void assertFieldValidationMessage(String fieldName, String vMsg) {
        String xpath = xpathFieldValidation.replace("*fieldName*", fieldName);
        String actual = assertAndGetText(xpath);
        logger.info("# Validation message for '" + fieldName + "': " + actual);
        assertEquals(actual, vMsg, "Actual validation message '" + actual
            + "' should be same as expected validation message '" + vMsg + "'.");
    }

    /**
     * This function is to assert that Register button is disabled.
     */
    public void assertRegisterBtnDisable() {
        assertTrue(isElementPresent(xpathDisabledSaveBtn), "Save button should be disabled.");
        logger.info("# 'Register' button is disabled");
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

}

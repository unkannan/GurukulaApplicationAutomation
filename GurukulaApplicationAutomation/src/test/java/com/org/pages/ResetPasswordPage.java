  
package com.org.pages;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import com.org.utility.UIOperation;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

 
public class ResetPasswordPage extends UIOperation {

    static Logger logger = Logger.getLogger(ResetPasswordPage.class.getName());

    public static final String HEADING = "Reset your password";
    private static final String SUCCESS = "Check your e-mails for details on how to reset your password.";
    private static final String ERROR = "E-Mail address isn't registered! Please check and try again";

    private String xpathHeading = "//h1[@translate='reset.request.title']";
    private String xpathForgotPasswordLink = "//a[text()='Did you forget your password?']";
    private String xpathEmailTF = "//input[@name='email']";
    private String xpathResetPasswordBtn = "//button[@type='submit']";
    private String xpathValidationMsg = "//div[contains(@ng-show, 'email')]//p[not(contains(@class, 'ng-hide'))]";
    private String xpathAlertMsg = "//div[contains(@class, 'alert') and not(contains(@class, 'hide') or contains(@class, 'warning'))]";
    private String xpathDisabledResetPasswordBtn = "//button[@disabled='disabled' and contains(text(),'Reset')]";

    WebDriver driver;

    public ResetPasswordPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    /**
     * This fUnction is to assert Reset Password page heading.
     */
    public void assertResetPasswordPageHeading() {
        waitForElement(xpathHeading);
        String actualHeading = assertAndGetText(xpathHeading);
        logger.info("# Reset Password page header: " + actualHeading);
        assertEquals(actualHeading, HEADING,
            "Actual heading '" + actualHeading + "' should be same as expected heading '" + HEADING
                + "'.");
    }

    /**
     * This function is to click on the forgot password link
     */
    public void clickOnLink() {
        assertAndClick(xpathForgotPasswordLink);
        logger.info("# Clicked on 'Did you forget your password?'");
    }

    /**
     * This function is to enter email address in email field.
     *
     * @param value: email address.
     */
    public void enterEmail(String value) {
        assertAndType(xpathEmailTF, value);
        logger.info("# Entered text in 'E-mail': " + value);
    }

    /**
     * This function is to clear text from the field.
     */
    public void clearText() {
        driver.findElement(By.xpath(xpathEmailTF)).clear();
        logger.info("# Cleared 'E-mail' to verify required field validation");
    }

    /**
     * This function is to enter email and click on the Reset password button.
     *
     * @param email: enter email address
     */
    public void enterEmailAndSubmit(String email) {
        assertAndType(xpathEmailTF, email);
        assertAndClick(xpathResetPasswordBtn);
        logger.info("# Clicked on 'Reset password' button");
    }

    /**
     * This function is to assert success message.
     */
    public void assertSuccessMsg() {
        String actualMsg = assertAndGetText(xpathAlertMsg);
        logger.info("# Alert message: " + actualMsg);
        assertEquals(actualMsg, SUCCESS);
    }

    /**
     * This function is to assert error message.
     */
    public void assertErrorMsg() {
        String actualMsg = assertAndGetText(xpathAlertMsg);
        logger.info("# Alert message: " + actualMsg);
        assertEquals(actualMsg, ERROR);
    }

    /**
     * This function is to assert validation messages.
     *
     * @param expectedMsg: text to be entered.
     */
    public void assertValidationMsg(String expectedMsg) {
        String actualMsg = assertAndGetText(xpathValidationMsg);
        logger.info("# Validation message for 'E-mail': " + actualMsg);
        assertEquals(actualMsg, expectedMsg, "Actual validation message '" + actualMsg
            + "' should be same as expected validation message '" + expectedMsg + "'.");
    }

    /**
     * This function is to assert Reset password button is disabled.
     */
    public void assertResetPasswordBtnDisable() {
        assertTrue(isElementPresent(xpathDisabledResetPasswordBtn),
            "Reset Password button should be disabled.");
        logger.info("# 'Reset password' button is disabled");
    }

}

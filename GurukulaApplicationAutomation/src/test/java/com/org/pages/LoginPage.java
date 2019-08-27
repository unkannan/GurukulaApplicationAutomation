 
package com.org.pages;

import static org.testng.Assert.assertEquals;

import com.org.utility.UIOperation;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
 
public class LoginPage extends UIOperation {

    static Logger logger = Logger.getLogger(LoginPage.class.getName());

    public static final String HEADING = "Authentication";
    public static final String SUCCSESS_MSG = "You are logged in as user \"**username**\".";
    public static final String VALIDATION_MSG = "Authentication failed! Please check your credentials and try again.";
    public static final String FORGET_LINK = "Did you forget your password?";
    public static final String REGISTER_LINK = "Register a new account";

    private String xpathHeading = "//h1[@translate='login.title']";
    private String idUsernameTextField = "username";
    private String idPasswordTextField = "password";
    private String xpathAuthenticateBtn = "//button[@type='submit' and text()='Authenticate']";
    private String xpathAlertValidationMsg = "//div[contains(@class, 'alert-danger')]";
    private String xpathLinks = "//a[text()='*lnk*']";

    WebDriver driver;

    public LoginPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    /**
     * This function is to assert Login page heading.
     */
    public void LoginPageHeaderCheck() {
        waitForElement(xpathHeading);
        String actualHeading = assertAndGetText(xpathHeading);
        logger.info("# Login page header: " + actualHeading);
        assertEquals(actualHeading, HEADING,
            "Actual heading '" + actualHeading + "' should be same as expected heading '" + HEADING
                + "'.");
    }

    /**
     * This function is to login into application and assert Successful message
     *parameters
     * username: enter username
     * password: enter password
     */
    public void LoginSucessFunctionalityCheck(String username, String password) {
        loginAction(username, password);
        waitForElement(WelcomePage.xpathAlertSuccessMsg);
        String actualMsg = assertAndGetText(WelcomePage.xpathAlertSuccessMsg);
        String expectedMsg = SUCCSESS_MSG.replace("**username**", username);
        assertEquals(actualMsg, expectedMsg,
            "Actual success message '" + actualMsg + "' should be same as expected message '"
                + expectedMsg + "'.");
    }

    /**
     * This function is to assert login validation messages
     *parameters
     * username: enter username
     * password: enter password
     */
    public void LoginSuccessValidatingMessageCheck(String username, String password) {
        loginAction(username, password);
        waitForElement(xpathAlertValidationMsg);
        String actualMsg = assertAndGetAttributeValue(xpathAlertValidationMsg, "innerText");
        assertEquals(actualMsg, VALIDATION_MSG, "Actual validation message '" + actualMsg
            + "' should be same as expected message '" + VALIDATION_MSG + "'.");
    }

    /**
     * This function is to click on the links present on Login page.
     */
    public void clickOnLink(String lnkTxt) {
        String xpath = xpathLinks.replace("*lnk*", lnkTxt);
        waitForElement(xpath);
        assertAndClick(xpath);
        logger.info("# Clicked on link: " + lnkTxt);
    }

    /**
     * This function is to perform login action
     *parameters
     * username: enter username
     * password: enter password
     */
    private void loginAction(String username, String password) {
    	assertEnterText(idUsernameTextField, username);
    	assertEnterText(idPasswordTextField, password);
        assertAndClick(xpathAuthenticateBtn);
        logger.info("# Login into application(" + username + ", " + password + ")");
    }

}

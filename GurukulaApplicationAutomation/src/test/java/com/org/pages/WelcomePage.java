 
package com.org.pages;

import static org.testng.Assert.assertEquals;

import com.org.utility.UIOperation;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

 
public class WelcomePage extends UIOperation {

    static Logger logger = Logger.getLogger(WelcomePage.class.getName());

    public static final String HEADING = "Welcome to Gurukula!";

    WebDriver driver;

    public static String xpathHeading = "//h1[@translate='main.title']";
    private static String xpathLink = "//a[text()='**lnkTxt**']";
    private static String xpathDropDownToggle = "//li//a//span[text()='*menu*']";
    private static String xpathMenuOption = "//ul[@class='dropdown-menu']/li//span[text()='**optTxt**']";
    public static String xpathAlertSuccessMsg = "//div[contains(@class, 'alert-success')]";
    private static String xpathLogo = "//a[@class='navbar-brand']/img";

    public WelcomePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    /**
     * This function is to assert respective page heading.
     */
    public void assertWelcomePageHeading() {
        waitForElement(xpathHeading);
        String actualHeading = assertAndGetText(xpathHeading);
        logger.info("# Welcome page header: " + actualHeading);
        assertEquals(actualHeading, HEADING,
            "Actual heading '" + actualHeading + "' should be same as expected heading '" + HEADING
                + "'.");
    }

    /**
     * This function is to click on the links present on the Welcome page
     *
     * @param lnkTxt: link text.
     */
    public void clickOnLink(String lnkTxt) {
        String locator = xpathLink.replace("**lnkTxt**", lnkTxt);
        assertAndClick(locator);
        logger.info("# Clicked on '" + lnkTxt + "' link");
    }

    /**
     * This function is to click on the menu.
     *
     * @param menu: menu for e.g. Account
     */
    public void clickOnMenu(String menu) {
        String xpath = xpathDropDownToggle.replace("*menu*", menu);
        assertAndClick(xpath);
        logger.info("# Clicked on '" + menu + "' menu");
    }

    /**
     * This function is to click on the Menu > option.
     *
     * @param menu: menu for e.g. Account
     * @param optTxt: option for e.g. Authenticate
     */
    public void clickOnOption(String menu, String optTxt) {
        clickOnMenu(menu);
        String locator = xpathMenuOption.replace("**optTxt**", optTxt);
        assertAndClick(locator);
        logger.info("# Clicked on '" + optTxt + "' Menu Option");
    }

    /**
     * This function is to click on the Gurukula Logo.
     */
    public void clickOnLogo() {
        assertAndClick(xpathLogo);
        logger.info("# Clicked on 'Gurukula Logo'");
    }

}

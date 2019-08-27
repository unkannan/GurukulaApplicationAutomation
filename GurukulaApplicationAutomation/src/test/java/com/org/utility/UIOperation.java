
package com.org.utility;

import static org.testng.Assert.assertTrue;

import com.org.enums.AppValidationConstantMessages;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

 
public abstract class UIOperation {

    protected WebDriver driver;

    public UIOperation(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * This function is to assert and click on the element.
     */
    protected void assertAndClick(String xpath) {
        assertElementPresent(xpath);
        driver.findElement(By.xpath(xpath)).click();
    }

    /**
     * This function is assert and get the text present in element.
     */
    protected String assertAndGetText(String xpath) {
        assertElementPresent(xpath);
        return driver.findElement(By.xpath(xpath)).getText();
    }

    /**
     * This function is to assert and type.
     */
    protected void assertAndType(String xpath, String txt) {
        assertElementPresent(xpath);
        driver.findElement(By.xpath(xpath)).clear();
        driver.findElement(By.xpath(xpath)).sendKeys(txt);
    }

    /**
     * This functions is to assert and type in element by id
     */
    protected void assertEnterText(String id, String txt) {
        assertElementPresentById(id);
        driver.findElement(By.id(id)).sendKeys(txt);
    }

    /**
     * This function is to assert element is present by id.
     */
    private void assertElementPresentById(String id) {
        assertTrue(isElementPresentById(id), "Element " + id + " not found.");
    }

    /**
     * This function is to verify weather element is present or not.
     */
    private boolean isElementPresentById(String id) {
        try {
            driver.findElement(By.id(id));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * This function is to get element visibility.
     */
    protected boolean isElementVisible(String xpath) {
        try {
            driver.findElement(By.xpath(xpath)).isDisplayed();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * This function is to assert element by xpath.
     *
     * @param xpath: locator as xpath
     */
    private void assertElementPresent(String xpath) {
        assertTrue(isElementPresent(xpath), "Element " + xpath + " not found.");
    }

    /**
     * This function is to get the presence os element.
     *
     * @param xpath: locator as xpath
     * @return true/false
     */
    protected boolean isElementPresent(String xpath) {
        try {
            driver.findElement(By.xpath(xpath));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * This function is to assert and get value by attribute.
     */
    protected String assertAndGetAttributeValue(String xpath, String attribute) {
        assertElementPresent(xpath);
        return driver.findElement(By.xpath(xpath)).getAttribute(attribute);
    }

    /**
     * This function is to wait for element.
     *
     * @param xpath: locator as xpath
     */
    protected void waitForElement(String xpath) {
        new WebDriverWait(driver, AppValidationConstantMessages.EXPLICIT_WAIT_TIME)
            .until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
    }

    /**
     * This function is to wait for element to be clickable.
     *
     * @param xpath: locator as xpath
     */
    protected void waitForElementClickable(String xpath) {
        new WebDriverWait(driver, AppValidationConstantMessages.EXPLICIT_WAIT_TIME)
            .until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
    }

    /**
     * This function is to wait for element visibility.
     */
    protected void waitForElementVisibility(String xpath) {
        new WebDriverWait(driver, AppValidationConstantMessages.EXPLICIT_WAIT_TIME)
            .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    /**
     * This function is to wait for element invisibility.
     */
    protected void waitForElementInvisibility(String xpath) {
        new WebDriverWait(driver, AppValidationConstantMessages.EXPLICIT_WAIT_TIME)
            .until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
    }

    /**
     * This function is to select option by visible text.
     */
    protected void selectByVisibleText(String xpath, String txt) {
        waitForElement(xpath);
        WebElement ele = driver.findElement(By.xpath(xpath));
        Select select = new Select(ele);
        select.selectByVisibleText(txt);
    }

    /**
     * This function is to select option by value.
     */
    protected void selectByValue(String xpath, String value) {
        waitForElement(xpath);
        WebElement ele = driver.findElement(By.xpath(xpath));
        Select select = new Select(ele);
        select.selectByValue(value);
    }

    /**
     * This function is get the first selected option fron Select element.
     */
    protected String getSelectTextByAttribute(String xpath, String attribute) {
        waitForElement(xpath);
        WebElement ele = driver.findElement(By.xpath(xpath));
        Select select = new Select(ele);
        return select.getFirstSelectedOption().getAttribute(attribute);
    }

}

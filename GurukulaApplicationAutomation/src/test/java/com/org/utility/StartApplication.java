 
package com.org.utility;

import com.org.enums.AppValidationConstantMessages;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

 public class StartApplication {

    static Logger logger = Logger.getLogger(StartApplication.class.getName());

    public WebDriver driver;
    private static Properties prop = ConfigFileReader.ReadProperties();
    public final static String URL = prop.getProperty("url");
    public final static String USERNAME = prop.getProperty("username");
    public final static String PASSWORD = prop.getProperty("password");

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        driver = getDriver();
    	//driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(AppValidationConstantMessages.IMPLICIT_WAIT_TIME, TimeUnit.SECONDS);
        driver.get(URL);
    }

    @AfterMethod(alwaysRun = true)
    public void teardown(ITestResult result) {
        if (driver != null) {
            if (ITestResult.FAILURE == result.getStatus()) {
                ScreenshotUtil.captureScreenshot(driver, result.getMethod().getMethodName());
            }
            driver.quit();
        }
    }
 

    private WebDriver getDriver() {
        String browser = System.getProperty("browser");
        if (null == browser) {
            browser = "firefox";
        }
        if (browser.equalsIgnoreCase("chrome")) {
            return new ChromeDriver();
        }
        if (browser.equalsIgnoreCase("iexplorer")) {
        	//System.setProperty("webdriver.ie.driver", );
            return new InternetExplorerDriver();
        }
        return new FirefoxDriver();
    }

}

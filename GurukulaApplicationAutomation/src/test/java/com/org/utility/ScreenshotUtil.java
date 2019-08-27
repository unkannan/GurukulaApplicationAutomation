package com.org.utility;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotUtil {

    private static final String SCREENSHOT_LOCATION =
        System.getProperty("user.dir") + File.separator + "target"
            + File.separator + "screenshots" + File.separator;

    /**
     * This function is to capture screenshot.
     */
    public static void captureScreenshot(WebDriver driver, String testname) {
        try {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File src = screenshot.getScreenshotAs(OutputType.FILE);
            SimpleDateFormat formatter = new SimpleDateFormat("_HH_mm_ss_dd_MM_yyyy");
            Date date = new Date(System.currentTimeMillis());
            testname = testname + formatter.format(date);
            FileUtils.copyFile(src, new File(SCREENSHOT_LOCATION + testname + ".png"));
        } catch (Exception e) {
            System.out.println("Exception while taking screenshot " + e.getMessage());
        }
    }
}

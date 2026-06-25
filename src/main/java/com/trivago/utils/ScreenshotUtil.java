package com.trivago.utils;

import org.openqa.selenium.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

    public static String captureScreenshot(WebDriver driver, String testName) {

        try {
            TakesScreenshot ts = (TakesScreenshot) driver;

            File src = ts.getScreenshotAs(OutputType.FILE);

            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

            String path = "./screenshots/" + testName + "_" + timestamp + ".png";

            File dest = new File(path);
            dest.getParentFile().mkdirs();

            src.renameTo(dest);

            System.out.println(" Screenshot saved: " + path);

            return path;

        } catch (Exception e) {
            System.out.println("Screenshot failed: " + e.getMessage());
            return null;
        }
    }
}

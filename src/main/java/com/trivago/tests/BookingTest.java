package com.trivago.tests;

import com.aventstack.extentreports.ExtentTest;
import com.trivago.base.BaseTest;
import com.trivago.pages.BookingHomePage;
import com.trivago.pages.SearchResultsPage;
import com.trivago.utils.ScreenshotUtil;
import org.testng.annotations.Test;

public class BookingTest extends BaseTest {

    @Test
    public void testHotelAvailability() {

        ExtentTest test = extent.createTest("Booking Hotel Test");

        try {
            test.info("Test Started");

            // Page Objects
            BookingHomePage homePage = new BookingHomePage(driver);
            SearchResultsPage resultsPage = new SearchResultsPage(driver);

            // Step 1: Enter City
            homePage.enterCity("Mumbai");
            test.pass("City Entered: Mumbai");

            // Step 2: Handle Popup
            homePage.handlePopup();
            test.pass("Popup handled");

            // Step 3: Select Dates
            homePage.selectDates("20", "22");
            test.pass("Dates Selected");

            // Step 4: Click Done
            homePage.clickDone();
            test.pass("Done Clicked");

            // Step 5: Click Search
            homePage.clickSearch();
            test.pass("Search Clicked");

            // Step 6: Wait for Results Page
            resultsPage.waitForResults();
            test.pass("Search Results Loaded");

            // Step 7: SCROLL TO LOAD ALL HOTELS (IMPORTANT FIX)
            resultsPage.scrollToLoadHotels();
            test.pass("All hotels loaded using smart scrolling");

            // Step 8: Print Hotel Name + Price
            resultsPage.printHotelNamesWithPrices();
            test.pass("Hotel Names & Prices Extracted");

            // Screenshot once
            String screenshotPath = ScreenshotUtil.captureScreenshot(driver, "HotelResults");
            test.addScreenCaptureFromPath(screenshotPath);
            test.pass("Screenshot captured and attached");

            // Step 9: Export Data to Excel
            resultsPage.exportHotelDataToExcel();
            test.pass("Excel File Generated");

            // Step 10: Validate Ratings
            resultsPage.validateRatingsDescending();
            test.pass("Ratings Verified");

            // Step 11: Validate City
            resultsPage.validateFirstFiveHotels("Mumbai");
            test.pass("City Validation Completed");

            System.out.println(" TEST COMPLETED SUCCESSFULLY");
            test.pass("Test Completed Successfully");

        } catch (Exception e) {

            System.out.println(" TEST FAILED: " + e.getMessage());

            // Screenshot on failure
            ScreenshotUtil.captureScreenshot(driver, "BookingTest");

            // Report failure
            test.fail("Test Failed: " + e.getMessage());

            throw e;
        }
    }
}
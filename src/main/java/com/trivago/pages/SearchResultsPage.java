package com.trivago.pages;

import com.trivago.utils.ExcelUtil;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.*;

public class SearchResultsPage {

    WebDriver driver;
    WebDriverWait wait;

    // Constructor
    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        PageFactory.initElements(driver, this);
    }


    // Locators (PageFactory)

    @FindBy(css = "[data-testid='title']")
    List<WebElement> hotelTitles;

    @FindBy(css = "[data-testid='review-score']")
    List<WebElement> ratings;

    // Methods

    public void waitForResults() {

        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("[data-testid='title']")
        ));

        System.out.println("Search Results Loaded");
    }

    // Print Hotel Names + Prices
    public void printHotelNamesWithPrices() {

        List<WebElement> hotelList = driver.findElements(
                By.cssSelector("[data-testid='property-card']")
        );

        System.out.println("\nHOTEL NAME + PRICE:\n");

        for (WebElement hotel : hotelList) {

            try {
                String name = hotel.findElement(
                        By.cssSelector("[data-testid='title']")
                ).getText();

                String price = hotel.findElement(
                        By.cssSelector("[data-testid='price-and-discounted-price']")
                ).getText();

                System.out.println(name + "  ->  " + price);

            } catch (Exception e) {
                // skip
            }
        }
    }

    // Validate Ratings Descending
    public void validateRatingsDescending() {

        List<Double> ratingValues = new ArrayList<>();

        for (WebElement r : ratings) {
            try {
                ratingValues.add(Double.parseDouble(r.getText()));
            } catch (Exception ignored) {}
        }

        boolean sorted = true;

        for (int i = 0; i < ratingValues.size() - 1; i++) {
            if (ratingValues.get(i) < ratingValues.get(i + 1)) {
                sorted = false;
                break;
            }
        }

        if (sorted) {
            System.out.println(" Ratings are in descending order");
        } else {
            System.out.println(" Ratings NOT sorted correctly");
        }
    }

    // Scroll to load all hotels
    public void scrollToLoadHotels() {

        JavascriptExecutor js = (JavascriptExecutor) driver;

        int previousCount = 0;

        while (true) {

            List<WebElement> hotels = driver.findElements(
                    By.cssSelector("[data-testid='property-card']")
            );

            int currentCount = hotels.size();
            System.out.println("Hotels loaded: " + currentCount);

            js.executeScript("window.scrollBy(0, 1500);");

            try {
                wait.until(driver -> {
                    int newCount = driver.findElements(
                            By.cssSelector("[data-testid='property-card']")
                    ).size();
                    return newCount > currentCount;
                });
            } catch (TimeoutException e) {
                System.out.println(" Reached end of results");
                break;
            }

            previousCount = currentCount;
        }
    }

    // Validate first 5 hotels belong to city
    public void validateFirstFiveHotels(String city) {

        System.out.println("\n HOTEL CITY VALIDATION:");

        for (int i = 0; i < 5 && i < hotelTitles.size(); i++) {

            String text = hotelTitles.get(i).getText();

            if (text.toLowerCase().contains(city.toLowerCase())) {
                System.out.println(" " + text);
            } else {
                System.out.println(" Not " + city + ": " + text);
            }
        }
    }

    // Export to Excel
    public void exportHotelDataToExcel() {

        List<WebElement> hotelList = driver.findElements(
                By.cssSelector("[data-testid='property-card']")
        );

        List<String> names = new ArrayList<>();
        List<String> pricesList = new ArrayList<>();

        for (WebElement hotel : hotelList) {

            try {
                String name = hotel.findElement(
                        By.cssSelector("[data-testid='title']")
                ).getText();

                String price = hotel.findElement(
                        By.cssSelector("[data-testid='price-and-discounted-price']")
                ).getText();

                names.add(name);
                pricesList.add(price);

            } catch (Exception e) {
                // skip
            }
        }
        ExcelUtil.writeHotelData(names, pricesList);
    }
}

package com.trivago.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;

public class BookingHomePage {

    WebDriver driver;
    WebDriverWait wait;

    // Constructor

    public BookingHomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        PageFactory.initElements(driver, this);
    }


    // Locators

    @FindBy(name = "ss")
    WebElement destination;

    @FindBy(css = "[data-testid='searchbox-dates-container']")
    WebElement dateBox;

    @FindBy(css = "[data-testid='searchbox-datepicker']")
    WebElement calendarContainer;

    @FindBy(css = "button[type='submit']")
    WebElement searchButton;

    @FindBy(css = "[aria-label='Dismiss sign-in info.']")
    WebElement popupClose;


    // Methods

    // Enter City
    public void enterCity(String city) {

        handlePopup();

        WebElement searchBox = wait.until(
                ExpectedConditions.visibilityOf(destination)
        );

        searchBox.click();

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].value='';", searchBox);

        searchBox.sendKeys(city);

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[data-testid='autocomplete-results']")
        ));

        WebElement firstOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("[data-testid='autocomplete-result']")
        ));

        firstOption.click();

        System.out.println(" City Entered: " + city);
    }

    // Handle Popup
    public void handlePopup() {

        try {
            WebElement closeBtn = wait.until(
                    ExpectedConditions.elementToBeClickable(popupClose)
            );
            closeBtn.click();

            System.out.println(" Popup closed");
        } catch (Exception e) {
            System.out.println(" No popup found");
        }
    }

    // Select Dates
    public void selectDates(String checkInDay, String checkOutDay) {

        try {

            List<WebElement> calendar = driver.findElements(
                    By.cssSelector("[data-testid='searchbox-datepicker']")
            );

            if (calendar.size() == 0) {

                WebElement dateBoxElement = wait.until(
                        ExpectedConditions.elementToBeClickable(dateBox)
                );

                ((JavascriptExecutor) driver)
                        .executeScript("arguments[0].click();", dateBoxElement);
            }

            wait.until(ExpectedConditions.visibilityOf(calendarContainer));

            selectDate(checkInDay);
            selectDate(checkOutDay);

            System.out.println(" Dates Selected");

        } catch (Exception e) {
            throw new RuntimeException("Date selection failed: " + e.getMessage());
        }
    }

    // Select Individual Date
    private void selectDate(String day) {

        List<WebElement> dates = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(
                        By.xpath("//span[text()='" + day + "']")
                )
        );

        for (WebElement d : dates) {
            if (d.isDisplayed() && d.isEnabled()) {

                ((JavascriptExecutor) driver)
                        .executeScript("arguments[0].click();", d);
                return;
            }
        }

        throw new RuntimeException(" Date not clickable: " + day);
    }

    // Click Done
    public void clickDone() {

        try {
            WebElement doneBtn = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//button//span[text()='Done']")
                    )
            );
            doneBtn.click();
            System.out.println(" Done clicked");
        } catch (Exception e) {
            System.out.println(" Done not required");
        }
    }

    // Click Search
    public void clickSearch() {

        driver.findElement(By.tagName("body")).click();

        WebElement searchBtn = wait.until(
                ExpectedConditions.visibilityOf(searchButton)
        );

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", searchBtn);

        try { Thread.sleep(1000); } catch (Exception e) {}

        wait.until(ExpectedConditions.elementToBeClickable(searchBtn));

        try {
            searchBtn.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", searchBtn);
        }

        System.out.println(" Search Clicked");
    }
}
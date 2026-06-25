# booking.com Automation Framework
## Overview

This project is an automation framework built using Selenium WebDriver, TestNG, and Java to test hotel search functionality on a booking website.

## Tech Stack
- Java
- Selenium
- WebDriver
- TestNG
- Page Object Model (POM)
- PageFactory- Extent Reports

## Features
- Search hotels by city
- Select check-in and check-out dates
- Handle popups- Scroll to load all hotel results
- Extract hotel name and price
- Validate ratings and city
- Export data to Excel

## Project StructureShow more lines
src/main/java/com/trivago
│
├── base        -> Test setup
├── pages       -> Page classes
├── utils       -> Utilities
│
src/test/java/com/trivago/tests
└── BookingTest.java


## How to Run
- Run the `BookingTest` class  
or  
- Execute using `testng.xml`



## Output
- Reports: `reports/ExtentReport.html`
<img width="1912" height="1000" alt="image" src="https://github.com/user-attachments/assets/bd91801d-5451-424a-8704-846cd8d0db2b" />
<img width="1919" height="997" alt="image" src="https://github.com/user-attachments/assets/326283d1-ba64-4a86-8bcc-de354f7b897d" />

- Screenshots: `screenshots/`
<img width="1919" height="859" alt="image" src="https://github.com/user-attachments/assets/23376804-0949-410e-ab95-7617ef5aea38" />

- Excel File: `testdata/HotelData.xlsx`
<img width="793" height="1090" alt="image" src="https://github.com/user-attachments/assets/4e4087ca-61b8-4a6a-a86b-543d29101a3c" />



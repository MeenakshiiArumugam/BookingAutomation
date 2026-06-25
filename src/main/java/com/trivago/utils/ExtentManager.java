package com.trivago.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getReport() {

        if (extent == null) {

            //  Create HTML report file
            ExtentSparkReporter reporter =
                    new ExtentSparkReporter("./reports/ExtentReport.html");

            reporter.config().setReportName("Booking Automation Report");
            reporter.config().setDocumentTitle("Test Execution Report");

            extent = new ExtentReports();
            extent.attachReporter(reporter);
        }

        return extent;
    }
}
package com.trivago.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.util.List;

public class ExcelUtil {

    public static void writeHotelData(List<String> names, List<String> prices) {

        try {

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Hotel Data");

            // Header
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Hotel Name");
            header.createCell(1).setCellValue("Price");

            // Data rows
            for (int i = 0; i < names.size(); i++) {

                Row row = sheet.createRow(i + 1);

                row.createCell(0).setCellValue(names.get(i));
                row.createCell(1).setCellValue(prices.get(i));
            }

            // Write to file
            FileOutputStream fos = new FileOutputStream("./testdata/HotelData.xlsx");

            workbook.write(fos);

            fos.close();
            workbook.close();

            System.out.println(" Excel file created: ./testdata/HotelData.xlsx");

        } catch (Exception e) {
            System.out.println(" Excel write failed: " + e.getMessage());
        }
    }
}
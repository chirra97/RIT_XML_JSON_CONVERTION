package com.ibm.exeTests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class SetUp {

	static HashMap<String, String> configData = new HashMap<String, String>();
	
	public static HashMap<String, String> excelDataSetUp() throws IOException{
	
		FileInputStream fis;
		Workbook  fis_workbook = null;
		Sheet fis_worksheet;
		String fis_file_location = "Config.xlsx";
		
		fis = new FileInputStream(new File(fis_file_location));
		if (fis_file_location.toLowerCase().endsWith("xlsx")) {
			 fis_workbook = new XSSFWorkbook(fis);
		}else if(fis_file_location.toLowerCase().endsWith("xls")){
			 fis_workbook = new HSSFWorkbook(fis);
		}
		fis_worksheet = fis_workbook.getSheetAt(0);
		int fis_RowCount = fis_worksheet.getLastRowNum();
		//String cellText = fis_worksheet.getRow(0).getCell(0).toString();
		
		for (int i = 0; i <= fis_RowCount; i++) {
			String key = fis_worksheet.getRow(i).getCell(0).toString();
			String value = fis_worksheet.getRow(i).getCell(1).toString();
			configData.put(key, value);
			System.out.println("=== key : "+key+" === value : "+value);
		}
		fis.close();
		
		return configData;
	}
}

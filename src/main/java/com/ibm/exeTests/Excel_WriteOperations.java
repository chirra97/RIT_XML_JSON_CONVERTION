package com.ibm.exeTests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class Excel_WriteOperations {

	
	static String fout_file_location;
	static Workbook  fout_workbook;	
	static Sheet fout_worksheet;
	Row fout_row;
	Cell fout_cell;
	int fout_SheetsCount;
	int  fout_RowCount;
	int fout_ColumnCount;
	CellStyle fout_style;
	org.apache.poi.ss.usermodel.Font fis_font;
	
	
	
	static FileOutputStream out;
	public static void excelCreate() throws IOException{
		fout_file_location = "C:\\Users\\IBM_ADMIN\\Downloads\\xml\\textResult.xlsx";
		out = new FileOutputStream(new File(fout_file_location));
		fout_workbook = new XSSFWorkbook();
		fout_worksheet = fout_workbook.createSheet("Results");
		fout_workbook.write(out);
		out.flush();
		out.close();
	}	
	
	public static void excelCreate(String filePath) throws IOException{
		fout_file_location = filePath;//Constants.outPutExcel; //"C:\\Users\\IBM_ADMIN\\Downloads\\xml\\textResult.xlsx";
		out = new FileOutputStream(new File(fout_file_location));
		fout_workbook = new XSSFWorkbook();
		fout_worksheet = fout_workbook.createSheet("Results");
		fout_workbook.write(out);
		out.flush();
		out.close();
	}
	public static void setCellValue(int rowNum, int colNum, String value) throws IOException{
		FileInputStream file = new FileInputStream(new File(fout_file_location));
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		Sheet sheet = workbook.getSheetAt(0);
				
		Row r = null;
		try {
			r = sheet.getRow(rowNum);
		} catch (Exception e) {
		}
		fout_worksheet.getRow(rowNum);
		
		if (r == null)  r = sheet.createRow(rowNum);
		
		Cell c = r.getCell(colNum, Row.CREATE_NULL_AS_BLANK);
		
		CellStyle style = workbook.createCellStyle();
	    Font font = workbook.createFont();
        //font.setColor(IndexedColors.RED.getIndex());
        if(value.equalsIgnoreCase("pass")) font.setColor(IndexedColors.GREEN.getIndex());
		else if(value.equalsIgnoreCase("fail")) font.setColor(IndexedColors.RED.getIndex());
		else if(rowNum == 0) font.setColor(IndexedColors.ROYAL_BLUE.getIndex());
		else font.setColor(IndexedColors.BLACK.getIndex());
	    style.setFont(font);
		
		c.setCellValue(value);
		c.setCellStyle(style);
		//fout_worksheet.getRow(rowNum).getCell(colNum).setCellStyle(fout_style);
		
		sheet.autoSizeColumn(colNum); 		
		FileOutputStream outFile =new FileOutputStream(new File(fout_file_location));
		workbook.write(outFile);
		outFile.close();
		
	}
	
	public static void excelWriteClose(){
		try {
			//out = new FileOutputStream(new File(fout_file_location));
			fout_workbook.write(out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static void excelTocsvConvert(File inputFile, File outputFile) {
        // For storing data into CSV files
        StringBuffer data = new StringBuffer();
        try {
                FileOutputStream fos = new FileOutputStream(outputFile);

                // Get the workbook object for XLSX file
       XSSFWorkbook wBook = new XSSFWorkbook(new FileInputStream(inputFile));

                // Get first sheet from the workbook
                XSSFSheet sheet = wBook.getSheetAt(0);
                Row row;
                Cell cell;

                // Iterate through each rows from first sheet
                Iterator<Row> rowIterator = sheet.iterator();
                while (rowIterator.hasNext()) {
                        row = rowIterator.next();

                        // For each row, iterate through each columns
                        Iterator<Cell> cellIterator = row.cellIterator();
                        while (cellIterator.hasNext()) {

                                cell = cellIterator.next();

                                switch (cell.getCellType()) {
                                case Cell.CELL_TYPE_BOOLEAN:
                                        data.append(cell.getBooleanCellValue() + ",");

                                        break;
                                case Cell.CELL_TYPE_NUMERIC:
                                        data.append(cell.getNumericCellValue() + ",");

                                        break;
                                case Cell.CELL_TYPE_STRING:
                                        data.append(cell.getStringCellValue() + ",");
                                        break;

                                case Cell.CELL_TYPE_BLANK:
                                        data.append("" + ",");
                                        break;
                                default:
                                        data.append(cell + ",");

                                }
                        }
                        data.append('\n'); 
                }

                fos.write(data.toString().getBytes());
                fos.close();

        } catch (Exception ioe) {
                ioe.printStackTrace();
        }
	}
	public static void generateCSVOutputFile(){
		 String outputFile_csv = fout_file_location.replace("xlsx", "csv");
		 System.out.println("====== csv file name & path : "+outputFile_csv);
		 
		 File inputFile = new File(fout_file_location);
		 File outputFile = new File(outputFile_csv);
         excelTocsvConvert(inputFile, outputFile);
	}
}

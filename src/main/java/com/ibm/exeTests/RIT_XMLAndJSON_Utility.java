package com.ibm.exeTests;

import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class RIT_XMLAndJSON_Utility {

	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {

		HashMap<String, String> configData = new HashMap<String, String>();
		configData = SetUp.excelDataSetUp();

		XMLToXML.XMLToXML_Convert(configData.get("input_XMLToXML"), configData.get("ouput_XMLToXML_XML"),
				configData.get("ouput_XMLToXML_Excel"));

		XMLToExcel.XMLToExcel_Convertion(configData.get("input_XMLToEXCEL"), configData.get("ouput_XMLToEXCEL_Excel"));

		/*
		 * JSONToExcel_SingleLine.JSONToExcel_SingleLine_Convert(configData.get(
		 * "input_JSONToExcel_SingleLine"),
		 * configData.get("ouput_JSONToExcel_SingleLine_json"),
		 * configData.get("ouput_JSONToExcel_SingleLine_excel"));
		 * 
		 * JSONToExcelAndJSON.JSONToExcelAndJSON_Convert(configData.get(
		 * "input_JSONToExcelAndJSON"), configData.get("ouput_JSONToExcelAndJSON_json"),
		 * configData.get("ouput_JSONToExcelAndJSON_excel"));
		 */
	}

}

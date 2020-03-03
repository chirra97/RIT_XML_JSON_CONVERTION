package com.ibm.exeTests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class XMLToExcel {

	public static void XMLToExcel_Convertion(String inputPath, String output_excel)
			throws ParserConfigurationException, SAXException, IOException {

		System.out.println("====== output_excel : " + output_excel);
		ArrayList<String> tagnames = new ArrayList<>();
		HashMap<String, String> xmlTagVal = new HashMap<String, String>();

		File filePath = new File(inputPath);
		Reader fileReader = new FileReader(filePath);
		BufferedReader bufReader = new BufferedReader(fileReader);
		StringBuilder sb = new StringBuilder();
		String line = bufReader.readLine();
		while (line != null) {
			try {
				sb.append(line).append("\n");
				line = bufReader.readLine();
				// System.out.println("=========== line : "+line);
				if (line == null || line.trim().length() == 0)
					continue;
				int first_startNumber = 0, first_endNumber = 0;
				try {
					first_startNumber = line.indexOf("<");
				} catch (Exception e1) {
					// e1.printStackTrace();
					System.out.println("Please check the Issue in 'first_startNumber'!");
				}
				first_endNumber = line.indexOf(">");
				String first_tagname = null;
				try {
					first_tagname = line.substring(first_startNumber + 1, first_endNumber);
					String[] splitText = first_tagname.split(" ");
					first_tagname = splitText[0].trim();
				} catch (Exception e) {
					System.out.println("Please check the Issue in 'first_tagname'!");
				}

				int last_startNumber = 0, last_endNumber = 0;
				last_startNumber = line.indexOf("<", first_endNumber);
				last_endNumber = line.indexOf(">", first_endNumber + 1);
				String last_tagname = null;

				try {
					last_tagname = line.substring(last_startNumber + 2, last_endNumber);
				} catch (Exception e) {
					System.out.println("Please check the Issue in 'last_tagname'!");
				}

				// System.out.println("======= last Tagname : "+last_tagname);
				if (first_tagname.equalsIgnoreCase(last_tagname)) {
					System.out.println("============================================= Matched Tag  :" + last_tagname);
					String temp_last_tagname = last_tagname;
					for (int i = 1; i < 1000; i++) {
						if (tagnames.contains(last_tagname)) {
							System.out.println("tag Name already exsted : " + last_tagname);
							last_tagname = temp_last_tagname + i;
						} else {
							tagnames.add(last_tagname);
							xmlTagVal.put(last_tagname, line.substring(first_endNumber + 1, last_startNumber));
							break;
						}
					}
					// System.out.println("---last_tagname :
					// --"+last_tagname+"------ value :
					// "+line.substring(first_endNumber+1, last_startNumber));
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}

		}

		Excel_WriteOperations.excelCreate(output_excel);
		int colNum = 0;
		for (String text : tagnames) {
			Excel_WriteOperations.setCellValue(0, colNum, text);
			System.out.println("=== TagName : " + text + " === value : " + xmlTagVal.get(text));
			Excel_WriteOperations.setCellValue(1, colNum, xmlTagVal.get(text));
			colNum = colNum + 1;
		}
		System.out.println("\n\n=========> Execution Completed! <==========");
	}

}

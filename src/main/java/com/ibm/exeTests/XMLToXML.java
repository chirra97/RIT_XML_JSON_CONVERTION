package com.ibm.exeTests;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class XMLToXML {

	public static void XMLToXML_Convert(String inputPath, String output_xml, String output_excel)
			throws ParserConfigurationException, SAXException, IOException {

		ArrayList<String> tagnames = new ArrayList<>();
		HashMap<String, String> xmlTagVal = new HashMap<String, String>();
		File filePath = new File(inputPath);
		Reader fileReader = new FileReader(filePath);
		BufferedReader bufReader = new BufferedReader(fileReader);
		StringBuilder sb = new StringBuilder();
		String line = bufReader.readLine();
		String xml_outWrite = new String();
		boolean firstRow = false;
		int first_startNumber = 0, last_endNumber = 0;
		while (line != null) {
			try {
				if (!firstRow) {
					xml_outWrite = xml_outWrite + line;
					firstRow = true;
				}
				// sb.append(line).append("\n");
				line = bufReader.readLine();

				if (line == null || line.trim().length() == 0)
					continue;
				int first_endNumber = 0;
				try {
					first_startNumber = line.indexOf("<");
				} catch (Exception e1) {
				}
				first_endNumber = line.indexOf(">");
				String first_tagname = null;
				try {
					first_tagname = line.substring(first_startNumber + 1, first_endNumber);
					String[] splitText = first_tagname.split(" ");
					first_tagname = splitText[0].trim();
				} catch (Exception e) {
				}

				int last_startNumber = 0;
				last_startNumber = line.indexOf("<", first_endNumber);
				last_endNumber = line.indexOf(">", first_endNumber + 1);
				String last_tagname = null;
				try {
					last_tagname = line.substring(last_startNumber + 2, last_endNumber);
				} catch (Exception e) {
				}

				if (first_tagname.equalsIgnoreCase(last_tagname)) {
					// System.out.println("========================= Matched Tag :"+last_tagname);
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
					System.out.println("---last_tagname : --" + last_tagname + "------ value  : "
							+ line.substring(first_endNumber + 1, last_startNumber));

					line = "<" + last_tagname + ">" + "%%" + last_tagname + "%%" + "</" + last_tagname + ">";
					System.out.println("------If---- line : " + line);
					// xml_outWrite = xml_outWrite + line+"\n";
					xml_outWrite = xml_outWrite + line;
				} else {
					System.out.println("----Else------ line : " + line);
					// xml_outWrite = xml_outWrite + line+"\n";
					xml_outWrite = xml_outWrite + line;
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
			Excel_WriteOperations.setCellValue(1, colNum, "'" + xmlTagVal.get(text));
			System.out.println("===> TagName : " + text + " = value : " + xmlTagVal.get(text));
			colNum = colNum + 1;
		}

		try {
			String path = output_xml;
			BufferedWriter bw = null;
			FileWriter fw = null;
			fw = new FileWriter(path);
			bw = new BufferedWriter(fw);
			bw.write(xml_outWrite);
			bw.close();
			fw.close();
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("Please ccheck the issue in XML file create and wirite.");
		}

		System.out.println("========> Execution Completed! <=========");
	}

}

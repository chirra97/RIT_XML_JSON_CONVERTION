package com.ibm.exeTests;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class JSONToExcel_SingleLine {

	public static void JSONToExcel_SingleLine_Convert(String inputPath, String output_json, String output_excel)
			throws ParserConfigurationException, SAXException, IOException {

		ArrayList<String> tagnames = new ArrayList<>();
		HashMap<String, String> xmlTagVal = new HashMap<String, String>();

		BufferedWriter bw = null;
		FileWriter fw = null;
		
		//String path = Constants.inputFolderPath + Constants.input_JSONToExcel_SingleLine;
		System.out.println("==== Input Path : " + inputPath);

		File xmlFile = new File(inputPath);
		BufferedReader b = new BufferedReader(new FileReader(xmlFile));
		String line = "";

		fw = new FileWriter(output_json);
		bw = new BufferedWriter(fw);
		String appendtext = "";
		while ((line = b.readLine()) != null) {
			int counter = 0;
			for (int i = 0; i < line.length(); i++) {
				if (line.charAt(i) == '"') {
					counter++;
				}
			}
			//System.out.println("======= : " + counter);

			if (counter == 2) {
				appendtext = "";
				appendtext = line.replace("\"", "").replace(":", "").replaceAll(" ", "").trim();
				appendtext = appendtext.substring(0, appendtext.length() - 1) + "_";
				System.out.println("===== appendtext : " + appendtext);
			}

			if (counter == 4) {
				
				/*System.out.println("===== Value : "+line);
				if(line.contains("//")){
					line = line.replace("//", "~");
					line = line.replace("/", "#");
					System.out.println("===== Value : "+line);
					//System.exit(0);
				}*/
				
				String[] data = line.split(":");
				String last_tagname = data[0].replaceAll("\"", "").trim();

				// System.out.println("============ last_tagname :
				// "+last_tagname);
				String value = data[1].replaceAll("\"", "").replaceAll(",", "").trim();
				if(data.length >= 3)value = value + data[2];
				
				//System.exit(0);
				String temp_last_tagname = last_tagname;

				for (int i = 1; i < 1000; i++) {
					if (tagnames.contains(last_tagname)) {
						// System.out.println("tag Name already exsted :
						// "+last_tagname);
						last_tagname = temp_last_tagname + i;
					} else {
						tagnames.add(last_tagname);
						xmlTagVal.put(last_tagname, value);
						break;
					}
				}

				System.out.println("==== tagName : " + last_tagname + "======= value : " + value);
				/*String ifLine = "\"" + last_tagname + "\": \"%%" + last_tagname
						+ "%%\",".replaceAll(" ", "");*/
				String ifLine = "\"" + temp_last_tagname + "\": \"%%" + last_tagname
						+ "%%\",".replaceAll(" ", "");
				
				
				bw.write(ifLine.replace(" ", ""));
				System.out.println("==== tagName : " + last_tagname + "======= value : " + value);
			} else {
				// bw.write(line+"\n");
				bw.write(line.replace(" ", ""));
			}
		}
		if (bw != null) bw.close();
		if (fw != null) fw.close();
		
		//String outPutPath = Constants.outputFolderPath + Constants.ouput_JSONToExcel_SingleLine;
		String outPutPath = output_excel;
		System.out.println("=== outPutPath : " + outPutPath);
		Excel_WriteOperations.excelCreate(outPutPath);
		Excel_WriteOperations.setCellValue(0, 0, "SNO");
		Excel_WriteOperations.setCellValue(0, 1, "TC_ID");
		Excel_WriteOperations.setCellValue(0, 2, "Request");
		int colNum = 3;
		for (String text : tagnames) {
			Excel_WriteOperations.setCellValue(0, colNum, text);
			String[] valuesList = xmlTagVal.get(text).split("~");
			for (int i = 0; i < valuesList.length; i++)
				Excel_WriteOperations.setCellValue((i + 1), colNum, "'" + valuesList[i]);
			colNum = colNum + 1;
			System.out.println("Name : " + text + " = Value : " + xmlTagVal.get(text));
		}
		
		BufferedReader br = null;
		FileReader fr = null;
		String linedata = "";
		try {
			//fr = new FileReader(Constants.outputFolderPath + Constants.ouput_JSONToExcel_SingleLine_json);
			fr = new FileReader(output_json);
			br = new BufferedReader(fr);
			String sCurrentLine;
			//br = new BufferedReader(new FileReader(Constants.outputFolderPath + Constants.ouput_JSONToExcel_SingleLine_json));
			br = new BufferedReader(new FileReader(output_json));
			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println(sCurrentLine);
				linedata = sCurrentLine;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
		if (bw != null) bw.close(); if (fw != null) fw.close();
		
		File file = new File(output_json);
		if(file.delete()){
			System.out.println(file.getName() + " is deleted!");
		}else{
		}
		 try{
			FileWriter fstream = new FileWriter(output_json);
			BufferedWriter out = new BufferedWriter(fstream);
			linedata = linedata.replaceAll(",}", "}");
			out.write(linedata);
			out.close();
	    }catch (Exception e){//Catch exception if any
	    	System.err.println("Error: " + e.getMessage());
	    }
		System.out.println("\n\n======= EXE Done - Thanks!!!!!=====");
	}
}

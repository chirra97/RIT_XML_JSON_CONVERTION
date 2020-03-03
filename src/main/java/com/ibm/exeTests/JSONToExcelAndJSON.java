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

public class JSONToExcelAndJSON {

	public static void JSONToExcelAndJSON_Convert(String inputPath, String output_json, String output_excel)
			throws ParserConfigurationException, SAXException, IOException {

		ArrayList<String> tagnames = new ArrayList<>();
		HashMap<String, String> xmlTagVal = new HashMap<String, String>();

		BufferedWriter bw = null;
		FileWriter fw = null;

		//File f = new File(Constants.inputFolderPath + Constants.input_JSONToExcelAndJSON);
		File f = new File(inputPath);
		BufferedReader b = null;
		try {
			b = new BufferedReader(new FileReader(f));
		} catch (Exception e1) {
			System.out.println("Unable find or read file in defined Path : "+inputPath);
		}
		String line = "";

		//fw = new FileWriter(Constants.outputFolderPath + Constants.ouput_JSONToExcelAndJSON_json);
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
				// System.out.println("===== appendtext : "+appendtext);
			}

			if (counter == 4) {
				String[] data = line.split(":");
				String last_tagname = data[0].replaceAll("\"", "").trim();
				if (!last_tagname.contains(appendtext))
					last_tagname = appendtext + last_tagname;
				String value = data[1].replaceAll("\"", "").replaceAll(",", "").trim();

				// String temp_last_tagname = last_tagname;

				if (tagnames.contains(last_tagname)) {
					String tagValue = xmlTagVal.get(last_tagname);
					// System.out.println("---------- tagValue : "+tagValue);
					// System.out.println("---------- appendtext :
					// "+appendtext);
					if (tagValue.contains(appendtext))
						tagValue = tagValue.replaceAll(appendtext, "");
					value = tagValue + "~" + value;
					// value = appendtext+value;
					// value = value;
					xmlTagVal.remove(last_tagname);
					System.out.println("====== value : " + value);
					xmlTagVal.put(last_tagname, value);
				} else {
					tagnames.add(last_tagname);
					xmlTagVal.put(last_tagname, value);
				}

				// bw.write(" "+"\""+last_tagname.replace(appendtext, "")+"\":
				// \"%%"+last_tagname+"%%\",\n");
				String ifLine = "\"" + last_tagname + "\": \"%%" + last_tagname
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
		//Excel_WriteOperations.excelCreate(Constants.inputFolderPath + Constants.ouput_JSONToExcelAndJSON_excel);output_excel
		Excel_WriteOperations.excelCreate(output_excel);
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
			//fr = new FileReader(Constants.outputFolderPath + Constants.ouput_JSONToExcelAndJSON_json);
			fr = new FileReader(output_json);
			br = new BufferedReader(fr);
			String sCurrentLine;
			br = new BufferedReader(new FileReader(output_json));
			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println(sCurrentLine);
				linedata = sCurrentLine;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
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
		
		System.out.println("\n\n===========> EXE Done <==========");

	}

}

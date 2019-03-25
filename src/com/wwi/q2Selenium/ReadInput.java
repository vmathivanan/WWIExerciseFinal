package com.wwi.q2Selenium;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadInput {

	public String ReadInputVariables(String inputField, String FileName) {
		Properties prop = new Properties();
		InputStream input = null;
		String inputFieldVal=null;
		try {
			input = new FileInputStream(FileName);
			try {
				prop.load(input);
				inputFieldVal = prop.getProperty(inputField);
				//System.out.println("Input File Name is : " + FilePath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("Properties File not Found !!");
		}
		return inputFieldVal;
	}

}

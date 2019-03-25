package com.wwi.q1dictionary;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {

	public String LoadProperties() {
		Properties prop = new Properties();
		InputStream input = null;
		String FilePath=null;
		try {
			input = new FileInputStream("config.properties");
			try {
				prop.load(input);
				FilePath= prop.getProperty("FilePath");
				System.out.println("Input File Name is : " + FilePath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("Properties File not Found !!");
		}
		return FilePath;
	}

	public static void main(String[] args) {
		PropertiesReader read = new PropertiesReader();
		read.LoadProperties();	}
}

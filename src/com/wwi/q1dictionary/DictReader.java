package com.wwi.q1dictionary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class DictReader {

	public boolean doesFileExist(File FilePath) {

		// this method checks if file exists : returns true if file exists, else false
		boolean fileExists = false;
		fileExists = FilePath.exists();
		return fileExists;

	}

	public void readDictFile(String FilePath) throws FileNotFoundException {

		//this method reads the input file to print words and their meanings

		FileInputStream in = new FileInputStream(new File(FilePath));
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String line = null;
		try {
			line = reader.readLine();
			while (line != null) {
				try {
					//Split words and their meanings
					String[] ps = line.split("\\-");
					String word = ps[0];
					System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
					System.out.println("Word : " + word);

					//if more than one meaning is present, split and print both their values
					String ds[] = ps[1].split(",");
					for (int i = 0; i < ds.length; i++) {
						int j = i + 1;
						System.out.println("Meaning " + j + " : " + ds[i]);
					}

				}				
				catch (Exception e)
				{
					System.out.println("Check input file format to follow this template : Word-Meaning1,Meaning2");
				}
				line = reader.readLine();
			}
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error reading line from input file");
			e.printStackTrace();
		}

		try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		DictReader reader = new DictReader();
		PropertiesReader propRead = new PropertiesReader();
		String filepath = propRead.LoadProperties();
		File inputFile = new File(filepath);

		//if file exists, read file | Else print appropriate error message
		try {
			if(reader.doesFileExist(inputFile) == true) 
			{
				reader.readDictFile(filepath);
			}
			else
			{
				System.out.println("Input File name is not correct.Please check!");
			}
		} catch (FileNotFoundException e) {
			System.out.println("File doesn't exist");
			e.printStackTrace();
		}

	}

}
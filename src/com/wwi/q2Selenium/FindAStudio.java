package com.wwi.q2Selenium;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FindAStudio {

	public static String ReadInputVariables(String inputField, String FileName) {
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


	public static void main(String[] args) {
		//Variable Initialization

		String  inputFileName = "SeleniumInput.properties";
		ReadInput iVariables = new ReadInput();
		final String browserVal = iVariables.ReadInputVariables("Browser", inputFileName);
		final String URL = iVariables.ReadInputVariables("URL", inputFileName);
		final String expHomePageTitle = iVariables.ReadInputVariables("ExpectedHomePageTitle", inputFileName);
		final String expFindStudioTitle = iVariables.ReadInputVariables("ExpectedStudioPageTitle", inputFileName);
		final String PostCode = iVariables.ReadInputVariables("PostCode", inputFileName);

		//		String browserVal = ReadInputVariables("Browser", inputFileName);
		//		System.out.println(ReadInputVariables("URL", inputFileName));
		//		String expHomePageTitle = ReadInputVariables("HomePageTitle", inputFileName);
		//		String expFindStudioTitle = ReadInputVariables("ExpectedStudioPageTitle", inputFileName);
		//		String PostCode = ReadInputVariables("PostCode", inputFileName);

		WebDriver driver;
		switch(browserVal.toLowerCase())
		{
		case "firefox":
			System.setProperty("webdriver.gecko.driver", iVariables.ReadInputVariables("DriverPath", inputFileName));
			driver = new FirefoxDriver();
			break;

//		case "ie":
//			System.setProperty("webdriver.ie.driver", iVariables.ReadInputVariables("DriverPath", inputFileName));
//			driver = new InternetExplorerDriver();
//			break;

		case "chrome":
			System.setProperty("webdriver.chrome.driver", iVariables.ReadInputVariables("DriverPath", inputFileName));
			driver = new ChromeDriver();
			break;		
		default:
			//default browser
			System.setProperty("webdriver.gecko.driver", iVariables.ReadInputVariables("DriverPath", inputFileName));
			driver = new FirefoxDriver();
			break;
		}

//				System.setProperty("webdriver.gecko.driver", iVariables.ReadInputVariables("DriverPath", inputFileName) );
//				driver = new FirefoxDriver();

		//Step 1 : Navigate to https://www.weightwatchers.com/us/

		driver.get(URL);

		//Step 2 : Verify loaded page title matches “Weight Loss Program, Recipes & Help | Weight Watchers”
		String actualFirstTitle = driver.getTitle();
		//Not used assertions here
		System.out.println("~~~~~~~~~~~~~~~~~~~~VALIDATION 1~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("Actual title   : " + actualFirstTitle );
		System.out.println("Actual title   : " + expHomePageTitle );

		// compare the actual title with the expected title
		if (actualFirstTitle.contentEquals(expHomePageTitle))
		{
			System.out.println( "Pass : Home Page Title matches with expected value") ;
		}
		else {
			System.out.println( "Fail : Home Page Title doesn't match with expected value" );
		}

		// Step 3 : On the right corner of the page, click on “Find a Meeting”
		driver.findElement(By.className("find-a-meeting")).click();

		// Step 4 : Verify loaded page title contains “Get Schedules & Times Near You”
		String actualSecondTitle = driver.getTitle();

		System.out.println("~~~~~~~~~~~~~~~~~~~~VALIDATION 2~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("Actual title   : " + actualSecondTitle );
		System.out.println("Expected title : " + expFindStudioTitle );
		// compare the actual title with the expected title
		if (actualSecondTitle.contentEquals(expFindStudioTitle))
		{
			System.out.println( "Pass : Find Studio Page Title matches with expected value") ;
		}
		else {
			System.out.println( "Fail : Find Studio Page Title doesn't match with expected value");
		}

		// Step 5 : In the search field, search for meetings for zip code: 10011
		// Explicitly wait for element to be visible and then enter zip code
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("meetingSearch")));	
		if(driver.findElement(By.id("meetingSearch")).isDisplayed() == false)
		{
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.println("Meeting Search edit box is not displayed within 30 seconds");
			driver.close();
		};

		driver.findElement(By.id("meetingSearch")).sendKeys(PostCode +(Keys.RETURN));

		// Step 6: Print the title of the first result and the distance (located on the right of location title/name)
		WebDriverWait wait1 = new WebDriverWait(driver, 45);
		wait1.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("location__name")));	


		List<WebElement> LocationNames = driver.findElements(By.className("location__name"));

		WebElement FirstLocation = LocationNames.get(0).findElement(By.cssSelector("div[class='location__name'] span[ng-if='!linkName']"));
		String DispLocName = FirstLocation.getText();
		String DispLocDist = LocationNames.get(0).findElement(By.xpath("..//*[@class='location__distance']")).getText();
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~FIRST RESULT ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("The first returned result : " + DispLocName + " is " + DispLocDist + " away");

		// Step 7 : Click on the first search result 
		FirstLocation.click();

		//		DayOfWeek dayOfWeek = DayOfWeek.from(LocalDate.now());
		//		String currentDay = dayOfWeek.getDisplayName(TextStyle.SHORT,Locale.ENGLISH);
		//		System.out.println(currentDay);

		// Step 8 : From this location page, print TODAY’s hours of operation (located towards the bottom of the page)
		WebElement todayOpHours = driver.findElement(By.cssSelector("div[class*='hours-list--currentday']"));

		List<WebElement> opHoursElement = todayOpHours.findElements(By.xpath("..//div[contains(@ng-repeat,'day.meetings')]"));
		//System.out.println(opHoursElement.size());
		System.out.println("~~~~~~~~~~~~~~~~~~~~HOURS OF OPERATION~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		for(WebElement opHours : opHoursElement)
		{
			System.out.println(opHours.getText());
		}
		driver.close();
	}

}


package ExeURL;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ExeURLClass {

	static WebDriver driver;

	@BeforeSuite
	public void startup() {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		// options.addArguments("headless");
		options.addArguments("headless");
		options.addArguments("--incognito");
		options.addArguments("--test-type");
		options.addArguments("--no-proxy-server");
		options.addArguments("--proxy-bypass-list=*");
		options.addArguments("--disable-extensions");
		options.addArguments("--no-sandbox");
		options.addArguments("--headless");
		options.addArguments("window-size=1366x788");
		capabilities.setPlatform(Platform.ANY);
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		driver = new ChromeDriver(options);
		// Default size
		Dimension currentDimension = driver.manage().window().getSize();
		int height = currentDimension.getHeight();
		int width = currentDimension.getWidth();
		System.out.println("Current height: " + height);
		System.out.println("Current width: " + width);
		System.out.println("window size==" + driver.manage().window().getSize());

		// Set new size
		Dimension newDimension = new Dimension(1366, 788);
		driver.manage().window().setSize(newDimension);

		// Getting
		Dimension newSetDimension = driver.manage().window().getSize();
		int newHeight = newSetDimension.getHeight();
		int newWidth = newSetDimension.getWidth();
		System.out.println("Current height: " + newHeight);
		System.out.println("Current width: " + newWidth);
	}

	@Test
	public static void exeURLClass() throws IOException {
		// Create File In D: Driver.

		read();
	}

	public static void read() throws IOException {

		// Response file
		String responsefile = ".//Production Log//CR27 Request with Production Data-01-Response.txt";

		File FC = new File(responsefile);// Created object of java File class.
		FC.createNewFile();// Create file.
		String requestfile = ".//Production Log//CR27 Request with Production Data-01.txt";

		// Reading from file.
		// Create Object of java FileReader and BufferedReader class.
		FileReader FR = new FileReader(requestfile);
		BufferedReader BR = new BufferedReader(FR);
		FileWriter FW = new FileWriter(responsefile);
		BufferedWriter BW = new BufferedWriter(FW);
		String Content = "";

		// Loop to read all lines one by one from file and print It.
		while ((Content = BR.readLine()) != null) {
			System.out.println(Content);
			driver.get(Content);
			String response = driver.findElement(By.xpath("/html/body/pre")).getText();
			String[] response1 = response.split("Label");
			String response2;
			response2 = response1[0];
			BW.write(response2); // Writing In To File.
			BW.newLine();// To write next string on new line.
		}
		BR.close();
		BW.close();

	}

	@AfterSuite
	public void end() {
		driver.close();
		driver.quit();
	}
}
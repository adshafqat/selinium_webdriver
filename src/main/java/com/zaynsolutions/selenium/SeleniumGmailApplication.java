package com.zaynsolutions.selenium;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.testng.Assert;

import java.util.Collections;
import java.util.Date;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
//@SpringBootApplication
public class SeleniumGmailApplication {

	public static void main(String[] args) throws InterruptedException {

		System.setProperty("webdriver.chrome.driver", "c:\\code\\chromedriver.exe");
 
		ChromeOptions options = new ChromeOptions();
	
		// Fixing 255 Error crashes
		options.addArguments("--no-sandbox");
		options.addArguments("--disable-dev-shm-usage");
	
		// Options to trick bot detection
			// Removing webdriver property
		options.addArguments("--disable-blink-features=AutomationControlled");
		options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
		options.setExperimentalOption("useAutomationExtension", null);
	
			// Changing the user agent / browser fingerprint
		options.addArguments("window-size=1920,1080");
		options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36");
	
		// Other
		options.addArguments("disable-infobars");		

		WebDriver driver = new ChromeDriver(options);
	
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();


		driver.get("https://medium.com/");

	
		try{
			TimeUnit.SECONDS.sleep(1);
		}catch(Exception e){
			e.printStackTrace();
		}

		//aria-label="close"
		driver.findElement(By.xpath("//*[@aria-label='close']")).click();

		try{
			TimeUnit.SECONDS.sleep(1);
		}catch(Exception e){
			e.printStackTrace();
		}

		driver.findElement(By.linkText("Sign In")).click();

		try{
			TimeUnit.SECONDS.sleep(1);
		}catch(Exception e){
			e.printStackTrace();
		}
		driver.findElement(By.linkText("Sign in with Google")).click();

		try{
			TimeUnit.SECONDS.sleep(1);
		}catch(Exception e){
			e.printStackTrace();
		}
		//driver.findElement(By.xpath("//*[@title='Sign In']")).click();
		//driver.findElement(By.xpath("//*[@title='Sign In']")).click();
 
		driver.findElement(By.xpath("//input[@type='email']")).sendKeys("bla@gmail.com"); 

		try{
			TimeUnit.SECONDS.sleep(5);
		}catch(Exception e){
			e.printStackTrace();
		}
		driver.findElement(By.xpath("//*[@id ='identifierNext']")).click(); 
		
	
		//driver.findElement(By.xpath("//input[@id='pass']")).sendKeys("password$"); 
	//	driver.findElement(By.xpath("//button[text()='Log In']")).click(); 

	//	Set<Cookie> cookies = driver.manage().getCookies();
	//	System.out.println("Cookies: "+cookies);
	try{
		TimeUnit.SECONDS.sleep(20);
	}catch(Exception e){
		e.printStackTrace();
	}
	driver.close();


	//	authenticatedPage(cookies);

}


public static void authenticatedPage(Set<Cookie> cookies) {
	WebDriver driver1 = new ChromeDriver();
	driver1.manage().window().maximize(); 
	driver1.get("https://www.facebook.com");
	driver1.findElement(By.xpath("//*[@title='Only allow essential cookies']")).click();
	Iterator<Cookie> cookiesIterator = cookies.iterator();
	while(cookiesIterator.hasNext()) {
		Cookie c=cookiesIterator.next();
		driver1.manage().addCookie(c);
	 }
	 try{
		TimeUnit.SECONDS.sleep(3);
	}catch(Exception e){
		e.printStackTrace();
	}
	driver1.get("https://www.facebook.com");
	
	try{
		TimeUnit.MINUTES.sleep(1);
	}catch(Exception e){
		e.printStackTrace();
	}
	driver1.close();
}
}

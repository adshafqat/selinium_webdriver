package com.zaynsolutions.selenium;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.testng.Assert;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

//@SpringBootApplication
public class SeleniumLinkedInApplication {


	public static void main(String[] args) {


		SeleniumLinkedInApplication localObj= new SeleniumLinkedInApplication();
		String passwordString=localObj.getProperty("login.password");

		SpringApplication.run(SeleniumLinkedInApplication.class, args);
		System.setProperty("webdriver.chrome.driver","c:\\code\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize(); 
		driver.get("https://www.linkedin.com/login");

		WebElement username=driver.findElement(By.id("username")); 
		WebElement password=driver.findElement(By.id("password")); 
		WebElement login=driver.findElement(By.xpath("//button[text()='Sign in']")); 
		username.sendKeys("blablabla@blabla.com"); 
		password.sendKeys(passwordString); 
		login.click(); 
		
		String actualUrl="https://www.linkedin.com/feed/"; 
		String expectedUrl= driver.getCurrentUrl(); 
		Assert.assertEquals(expectedUrl,actualUrl); 

		Set<Cookie> cookies = driver.manage().getCookies();
		//System.out.println("Cookies: "+cookies);
		driver.close();

		callHttpURL("https://www.linkedin.com/feed/",cookies);
		authenticatedPage(cookies);
	}

	public static void authenticatedPage(Set<Cookie> cookies) {
		WebDriver driver1 = new ChromeDriver();
		driver1.manage().window().maximize(); 
		driver1.get("https://www.linkedin.com/feed");
		Iterator<Cookie> cookiesIterator = cookies.iterator();
		while(cookiesIterator.hasNext()) {
			Cookie c=cookiesIterator.next();
			driver1.manage().addCookie(c);
		 }
		driver1.get("https://www.linkedin.com/feed");
		
		try{
			TimeUnit.MINUTES.sleep(1);
		}catch(Exception e){
			e.printStackTrace();
		}
		driver1.close();
	}

	public String getProperty(String config){
		ConfigProperties configProp=new ConfigProperties();
		return configProp.getPassword();
	}


	public static void callHttpURL(String url, Set<Cookie> cookies) {
			RestTemplate rest=new RestTemplate();
			
			//String result=rest.getForObject(url,String.class);

			HttpHeaders headers=new HttpHeaders();

			Iterator<Cookie> cookiesIterator = cookies.iterator();
			while(cookiesIterator.hasNext()) {
				Cookie c=cookiesIterator.next();
				//System.out.println("----"+c);
				headers.add("Cookie",c.toString());
			}



			HttpEntity<String> entity=new HttpEntity<String>(headers);
			String resultHTML=rest.exchange(url,HttpMethod.GET,entity,String.class).getBody();
	
			//Printing Results in a file	

			try {
				FileWriter fileWriter;
				fileWriter = new FileWriter(new File("text.html"), true);
				
				// Writes text to a character-output stream
				BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
				bufferWriter.write(resultHTML);
				bufferWriter.close();
				
			} catch (IOException e) {
				e.printStackTrace();				
			}

		}
}

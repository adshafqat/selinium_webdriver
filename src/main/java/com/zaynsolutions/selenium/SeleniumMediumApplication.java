package com.zaynsolutions.selenium;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
//@SpringBootApplication
public class SeleniumMediumApplication {

	public static void main(String[] args) throws InterruptedException {

		System.setProperty("webdriver.chrome.driver", "c:\\code\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();		

		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://medium.com/");


		Date dt = new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(dt); 
		c.add(Calendar.DATE, 20);
		dt = c.getTime();
		
		Cookie c1 = new Cookie("__cfruid", "blablabla-1675888232", ".medium.com","/", dt,true); 	
		Cookie c2 = new Cookie("_ga", "GA1.2.1111111.1675888233", ".medium.com","/", dt,true); 	
		Cookie c3 = new Cookie("_gid", "GA1.2.as231212.1212121", ".medium.com","/", dt,true); 	
		Cookie c4 = new Cookie("lightstep_guid/medium-web", "1211111", "medium.com","/", dt,true); 	
		Cookie c5 = new Cookie("lightstep_session_id", "11111", "medium.com","/", dt,true); 	
		Cookie c6 = new Cookie("nonce", "X6Uo6hut", ".medium.com","/", dt,true); 	
		Cookie c7 = new Cookie("pr", "1.5", "medium.com","/", dt,true); 	
		Cookie c8 = new Cookie("sid", "112122222222222", ".medium.com","/", dt,true); 	
		Cookie c9 = new Cookie("sz", "1263", "medium.com","/", dt,true); 	
		Cookie c10 = new Cookie("tz", "0", "medium.com","/", dt,true); 	
		Cookie c11 = new Cookie("uid", "blablabla", ".medium.com","/", dt,true); 	
		Cookie c12 = new Cookie("xsrf", "111111111111", "medium.com","/", dt,true); 	

		driver.manage().addCookie(c1);
		driver.manage().addCookie(c2);
		driver.manage().addCookie(c3);
		driver.manage().addCookie(c4);
		driver.manage().addCookie(c5);
		driver.manage().addCookie(c6);
		driver.manage().addCookie(c7);
		driver.manage().addCookie(c8);
		driver.manage().addCookie(c9);
		driver.manage().addCookie(c10);
		driver.manage().addCookie(c11);
		driver.manage().addCookie(c12);

		//		driver=setCookies(driver);					
		driver.get("https://medium.com/");
		Set<Cookie> cookies = driver.manage().getCookies();
		try{
			TimeUnit.SECONDS.sleep(10);
		}catch(Exception e){
			e.printStackTrace();
		}

	//	Set<Cookie> cookies = driver.manage().getCookies();

		driver.close();
		callHttpURL("https://medium.com/",cookies);
		authenticatedPage(cookies);
		
}


public static void authenticatedPage(Set<Cookie> cookies) {
	WebDriver driver1 = new ChromeDriver();
	driver1.manage().window().maximize(); 
	driver1.get("https://medium.com/");
	Iterator<Cookie> cookiesIterator = cookies.iterator();
	while(cookiesIterator.hasNext()) {
		Cookie c=cookiesIterator.next();
		System.out.println(1);
		driver1.manage().addCookie(c);
	 }
	driver1.get("https://medium.com/");
	
	try{
		TimeUnit.MINUTES.sleep(1);
	}catch(Exception e){
		e.printStackTrace();
	}
	driver1.close();
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
			fileWriter = new FileWriter(new File("medium.html"), true);
			
			// Writes text to a character-output stream
			BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
			bufferWriter.write(resultHTML);
			bufferWriter.close();
			
		} catch (IOException e) {
			e.printStackTrace();				
		}

	}
}

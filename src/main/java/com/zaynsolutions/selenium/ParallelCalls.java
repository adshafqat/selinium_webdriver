package com.zaynsolutions.selenium;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.Cookie;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ParallelCalls {
    

    public static void main(String args[]){
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
    try {
        BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(new File("cookies.txt"), false));
        /* 
        callURL("http://medium.com",driver,bufferWriter);    
        callURL("http://yahoo.com",driver,bufferWriter);    
        callURL("http://gmail.com",driver,bufferWriter);    
        */

        new MultiCalls("http://medium.com",driver,bufferWriter).start();    
        new MultiCalls("http://yahoo.com",driver,bufferWriter).start();    
        new MultiCalls("http://gmail.com",driver,bufferWriter).start();    

       // bufferWriter.close();
    } catch (IOException e) {
        e.printStackTrace();				
    }
  
  //  driver.close();

    }

    public static void callURL(String url, WebDriver driver,BufferedWriter bufferWriter){

        driver.get(url);
        Set<Cookie> cookies = driver.manage().getCookies();

        /* 
        System.out.println("Cookies: "+cookies);
        Iterator<Cookie> cookiesIterator = cookies.iterator();
        int i=1;
        while(cookiesIterator.hasNext()) {
            Cookie c=cookiesIterator.next();
            System.out.println(i+":"+c.toString());
            i++;
         }
         */

         try {
            // Writes text to a character-output stream
            bufferWriter.write("------- "+url+" Cookies------");
            bufferWriter.write(System.getProperty( "line.separator" ));
            bufferWriter.write(cookies.toString());
            bufferWriter.write(System.getProperty( "line.separator" ));
            bufferWriter.write("-------------------");
            bufferWriter.write(System.getProperty( "line.separator" ));            
        } catch (IOException e) {
            e.printStackTrace();				
        }

    }

}




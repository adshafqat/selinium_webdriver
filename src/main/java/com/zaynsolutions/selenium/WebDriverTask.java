package com.zaynsolutions.selenium;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Set;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

public class WebDriverTask  implements Runnable { 
    private String url;
    WebDriver driver;
    BufferedWriter bufferWriter;

    public WebDriverTask(String url) {
        this.url = url;
    }
 
    public void run() {
        executeTask();
    }

    public void setWebDriver(WebDriver driver){
        this.driver=driver;
    }

    public void setBufferWriter(BufferedWriter bufferWriter){
        this.bufferWriter=bufferWriter;
    }

    public void executeTask(){
        System.out.println("Thread " + Thread.currentThread().getId()+ " is running");
        driver.get(url);
        Set<Cookie> cookies = driver.manage().getCookies();
         try {
            BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(new File("cookies.txt"), true)); 
            bufferWriter.write("Thread " + Thread.currentThread().getId()+"------- "+url+" Cookies------"+new Date().toString());
            bufferWriter.write(System.getProperty( "line.separator" ));
            bufferWriter.write("Thread " + Thread.currentThread().getId()+"--"+cookies.toString());
            bufferWriter.write(System.getProperty( "line.separator" ));
            bufferWriter.write("Thread " + Thread.currentThread().getId()+"-------------------");
            bufferWriter.write(System.getProperty( "line.separator" ));      
            bufferWriter.flush();
            bufferWriter.close();
        } catch (IOException e) {
            e.printStackTrace();				
        }
        System.out.println("Thread " + Thread.currentThread().getId()+ " is finished");        
    }
}
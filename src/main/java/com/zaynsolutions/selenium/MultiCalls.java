
package com.zaynsolutions.selenium;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class MultiCalls extends Thread {
    String url;
    WebDriver driver;
    BufferedWriter bufferWriter;

    public MultiCalls(String url, WebDriver driver,BufferedWriter bufferWriter){
        this.url=url;
        this.driver=driver;
        this.bufferWriter=bufferWriter;
    }

    
    public void run() {
            openURL();
    }

    public void openURL(){
        synchronized(this.driver){
        System.out.println("Thread " + Thread.currentThread().getId()+ " is running");
        driver.get(url);
        Set<Cookie> cookies = driver.manage().getCookies();
         try {
            bufferWriter.write("Thread " + Thread.currentThread().getId()+"------- "+url+" Cookies------");
            bufferWriter.write(System.getProperty( "line.separator" ));
            bufferWriter.write("Thread " + Thread.currentThread().getId()+"--"+cookies.toString());
            bufferWriter.write(System.getProperty( "line.separator" ));
            bufferWriter.write("Thread " + Thread.currentThread().getId()+"-------------------");
            bufferWriter.write(System.getProperty( "line.separator" ));      
            bufferWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();				
        }
        System.out.println("Thread " + Thread.currentThread().getId()+ " is finished");

        /* 
        for(int i=0;i<10;i++){
            System.out.println("Thread " + Thread.currentThread().getId()+ " is running:"+i);
        }
        */
    }
    }


}

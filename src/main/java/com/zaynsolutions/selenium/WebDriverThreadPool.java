package com.zaynsolutions.selenium;
import java.util.Collections;
import java.util.concurrent.LinkedBlockingQueue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebDriverThreadPool {
    private final int NUMBER_OF_THREADS=2;
    private final WebDriverInstance[] threads;
    private final LinkedBlockingQueue queue;
     
    public WebDriverThreadPool() {

        System.out.println("------------------------Thread Pool initiated at the start of application---------------------");
        queue = new LinkedBlockingQueue();
        threads = new WebDriverInstance[NUMBER_OF_THREADS];

        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            threads[i] = new WebDriverInstance(createWebDriver());
            threads[i].start();
        }
    }
     
    public void execute(Runnable task) {
        synchronized (queue) {
            queue.add(task);
            queue.notify();
        }
    }


        public WebDriver createWebDriver(){
            System.out.println("Creating a WebDriver ====================================================================");

            System.setProperty("webdriver.chrome.driver", "c:\\code\\chromedriver.exe");
 
            ChromeOptions options = new ChromeOptions();
        
            // Fixing 255 Error crashes
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");

            //If you want to make WebDriver headless
            options.addArguments("--headless");
        
            // Options to trick bot detection
            // Removing webdriver property
            options.addArguments("--disable-blink-features=AutomationControlled");
            options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
            options.setExperimentalOption("useAutomationExtension", null);
        
            // Changing the user agent / browser fingerprint
            options.addArguments("window-size=1920,1080");
            options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36");
        
            options.addArguments("disable-infobars");		
        
            WebDriver driver = new ChromeDriver(options);
            System.out.println("A Web Driver Object is Created====================================================================");
            return driver;
        }        
    
        public String toString(){
            return "I am not Null, I am initiated by AutoConfigured Annotation";
        }
     

    private class WebDriverInstance  extends Thread{
        
        WebDriver driver;
        public WebDriverInstance(WebDriver driver){
            this.driver=driver;
        }

        public void run() {
            WebDriverTask task;
            while (true) {
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            System.out.println("An error occurred while queue is waiting: " + e.getMessage());
                        }
                    }
                    task =  (WebDriverTask) queue.poll();
                }
                try {
                    task.setWebDriver(driver);
                    task.run();
                } catch (RuntimeException e) {
                    System.out.println("Thread pool is interrupted due to an issue: " + e.getMessage());
                }
            }
        }
    }

}
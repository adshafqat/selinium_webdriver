package com.zaynsolutions.selenium;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller    
@RequestMapping(path="/webdriverapp") 
public class WebDriverController {
    @Autowired
    WebDriverThreadPool pool;

    @GetMapping(path="/invokeurl")
    public @ResponseBody String getCustomer(@RequestParam String url) {

        System.out.println("I am adding a new Task in the Task List");
        System.out.println(pool.toString());
        pool.execute(new WebDriverTask(url));
        return "I have added a new Task in the Task List";
    }
}
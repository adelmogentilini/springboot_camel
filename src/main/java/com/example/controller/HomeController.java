package com.example.controller;

import org.apache.camel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HomeController {

    @Autowired
    CamelContext camelContext;


    @GetMapping("/")
    public String index() {

        //invoke();
        return "Greetings from Spring Boot!";

    }
    /*
    TODO:
    far partire rotta camel
    vedere da : /route

    scivere test per parte camel
     */

/*
    public String invoke() {


        String out = consumer.receiveBody(
                "direct:hello", 5000,
                String.class); //5000 is the receive timeout
        return out;
    }
*/


}
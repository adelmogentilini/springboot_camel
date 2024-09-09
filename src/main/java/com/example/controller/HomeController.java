package com.example.controller;

import com.example.processor.HelloProcessor;
import jakarta.websocket.server.PathParam;
import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.FluentProducerTemplate;
import org.apache.camel.builder.ExchangeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HomeController {
    private static final Logger LOG = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    CamelContext camelContext;

    @EndpointInject("direct:hello")
    private FluentProducerTemplate producer;

    @GetMapping("/")
    public String index() {

        //invoke();
        return "Greetings from Spring Boot! /hello/name for better message";
    }

    @GetMapping("/hello/{name}")
    public ResponseEntity<String> index(@PathVariable(value = "name") String name){

        try {
            LOG.info(("PARAM name = "+name));
            final Exchange requestExchange = ExchangeBuilder.anExchange(producer.getCamelContext()).withBody(name).build();
            Exchange param = producer.withExchange(requestExchange).send();
            String  result = (String) param.getIn().getBody();
                return ResponseEntity.status(200).body(result);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred while processing the request.");
        }
    }
}
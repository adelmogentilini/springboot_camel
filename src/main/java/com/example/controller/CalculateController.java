package com.example.controller;

import com.example.dto.NumberDTO;
import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.FluentProducerTemplate;
import org.apache.camel.builder.ExchangeBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculateController {

    @Autowired
    CamelContext camelContext;

    @EndpointInject("direct:sum")
    private FluentProducerTemplate producerSum;

    @EndpointInject("direct:multiply")
    private FluentProducerTemplate producerMultiply;

    @GetMapping("/route/sum")
    public ResponseEntity<String> invokeSumRoute(@RequestParam("numbers") String numbers) {

        try {
            final Exchange requestExchange = ExchangeBuilder.anExchange(producerSum.getCamelContext()).withBody(numbers).build();
            Exchange param = producerSum.withExchange(requestExchange).send();
            NumberDTO result = (NumberDTO) param.getIn().getBody();
            if (result.getErrore()== null || result.getErrore().trim().isEmpty()){
                return ResponseEntity.status(200).body("Rotta somma eseguita: "+result.getSomma());
            }else{
                return ResponseEntity.status(400).body("Errore: "+result.getErrore());
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred while processing the request.");
        }
    }

    @GetMapping("/route/multiply")
    public ResponseEntity<String> invokeMultiplyRoute(@RequestParam("numbers") String numbers) {

        try {
            final Exchange requestExchange = ExchangeBuilder.anExchange(producerMultiply.getCamelContext()).withBody(numbers).build();
            Exchange param = producerMultiply.withExchange(requestExchange).send();
            NumberDTO result = (NumberDTO) param.getIn().getBody();
            if (result.getErrore()== null || result.getErrore().trim().isEmpty()){
                return ResponseEntity.status(200).body("Rotta moltiplicazione eseguita: "+result.getSomma());
            }else{
                return ResponseEntity.status(400).body("Errore: "+result.getErrore());
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred while processing the request.");
        }
    }
}
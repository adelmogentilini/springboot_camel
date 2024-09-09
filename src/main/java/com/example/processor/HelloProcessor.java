package com.example.processor;

import com.example.dto.NumberDTO;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloProcessor implements Processor {
    private static final Logger LOG = LoggerFactory.getLogger(HelloProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {

        LOG.info(" START PROCESSOR");

        //ottengo variabili

        String helloMsg = exchange.getIn().getBody(String.class);
        LOG.info(" START PROCESSOR ==> "+helloMsg);


        //set body messaggio:
         exchange.getIn().setBody("HELLO WORLD: "+helloMsg);
    }
}

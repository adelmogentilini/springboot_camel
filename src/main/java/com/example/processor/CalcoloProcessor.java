package com.example.processor;

import com.example.dto.NumberDTO;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CalcoloProcessor implements Processor {
    private static final Logger LOG = LoggerFactory.getLogger(CalcoloProcessor.class);

    public CalcoloProcessor(String s) {
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println(" START CALCOLO PROCESSOR ");


    //    recupero variabile creata in HelloProcessor:
        NumberDTO numberDTO = new NumberDTO();
        String number = exchange.getProperty("SUMRESULTInput").toString();
        String[] numArray = number.split(",");

        int res=1;

        for (String s : numArray) {
                res = res* Integer.parseInt(s);
        }

        numberDTO.setProdotto(String.valueOf(res));
        numberDTO.setSomma(exchange.getProperty("SUMRESULT").toString());
        numberDTO.setNumeriInput(exchange.getProperty("SUMRESULTInput").toString());


        exchange.setProperty("SUMRESULT",numberDTO.getSomma());

        exchange.getIn().setBody(numberDTO);



        System.out.println("dto  "+ numberDTO);




    }
}

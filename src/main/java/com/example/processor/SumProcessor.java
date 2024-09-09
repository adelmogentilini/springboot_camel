package com.example.processor;

import com.example.dto.NumberDTO;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SumProcessor implements Processor {
    private static final Logger LOG = LoggerFactory.getLogger(SumProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {

        LOG.info(" START PROCESSOR");

        //ottengo variabili

        String sumString = exchange.getIn().getBody(String.class);

        //conversione da string a int e somma valori:
        String[] sumArray = sumString.split(",");
        int sum=0;
        int errorController=0;
        NumberDTO numberDTO = new NumberDTO();

        numberDTO.setNumeriInput(sumString);

        for (String s : sumArray) {
              try  {
                  sum += Integer.parseInt(s);
              }catch (Exception e){
                  errorController++;
              }
        }

        if(errorController>0){
            numberDTO.setErrore("error");
           exchange.setProperty("SUM_ERROR","errore nella somma");
        }else{
            numberDTO.setSomma(String.valueOf(sum));
            exchange.setProperty("SUM_INPUT",numberDTO.getNumeriInput());
            exchange.setProperty("SUM_RESULT",numberDTO.getSomma());
        }
        //set body messaggio:
         exchange.getIn().setBody(numberDTO);
    }
}

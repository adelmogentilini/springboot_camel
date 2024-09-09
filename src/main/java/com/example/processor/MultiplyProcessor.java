package com.example.processor;

import com.example.dto.NumberDTO;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultiplyProcessor implements Processor {
    private static final Logger LOG = LoggerFactory.getLogger(MultiplyProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {

    //    recupero variabile creata in HelloProcessor:
        NumberDTO numberDTO = new NumberDTO();
        String number = exchange.getProperty("SUMRESULTInput").toString();
        String[] numArray = number.split(",");

        int res=1;

        int errorController=0;
        for (String s : numArray) {
            try  {
                res = res* Integer.parseInt(s);
            }catch (Exception e){
                errorController++;
            }
        }
        if(errorController>0){
            numberDTO.setErrore("error");
            exchange.setProperty("MULTIPLY_ERROR","errore nel prodotto");
        }else{
            numberDTO.setProdotto(String.valueOf(res));
            exchange.setProperty("MULTIPLY_INPUT",numberDTO.getNumeriInput());
            exchange.setProperty("MULTIPLAY_RESULT",numberDTO.getProdotto());
        }
        //set body messaggio:
        exchange.getIn().setBody(numberDTO);
    }
}

package com.example.processor;

import com.example.dto.NumberDTO;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloProcessor implements Processor {
    private static final Logger LOG = LoggerFactory.getLogger(HelloProcessor.class);

    public HelloProcessor(String s) {
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println(" START PROCESSOR ");
        //1.passo valore stringa
        //2.la converto in int e faccio somma
        //3.restituisco stringa


        LOG.info(" START PROCESSOR");

        //ottengo variabili
        System.out.println("EXCHANGE");
        System.out.println(exchange);
        System.out.println(exchange.getIn().getBody());

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

        //altra  route moltiplicazione valori

        //rotta->somma->se non errore seconda rotta chiamata da prima -> moltiplicazione


        if(errorController>0){
            numberDTO.setErrore("error");
           exchange.setProperty("SUMRESULTINPUT","errore");
        }else{
            numberDTO.setSomma(String.valueOf(sum));
            exchange.setProperty("SUMRESULTInput",numberDTO.getNumeriInput());
            exchange.setProperty("SUMRESULT",numberDTO.getSomma());
        }


        //set body messaggio:
         exchange.getIn().setBody(numberDTO);

        System.out.println(" SONO PASSATO DALPROCESSOR "+ numberDTO.getSomma());
        System.out.println("num  numberDTO: "+ numberDTO);
        System.out.println("InPROCESSOR SUMRESULT "+ exchange.getProperty("SUMRESULT"));






    }
}

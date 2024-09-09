package com.example.controller;

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

/* TODO:
    - aggiungere un parametro alla rotta, tornare un valore e restituirlo a video a chi invoca la rotta
        - il messaggio sara una stringa di numeri separati da , e lui torna la somma
            invoco route con 3,4,5 => risultato: 12 se non è posibile ritorna NAN  (come js)
    - cercare il modo per far partire una rotta in maniera parametrica (senza iniettarla)
*/
@RestController
public class MyRestController {

    @Autowired
    CamelContext camelContext;

    @EndpointInject("direct:hello")
    private FluentProducerTemplate producer;

    @GetMapping("/route")
    public ResponseEntity<String> invokeRoutingRoute(@RequestParam("numbers") String numbers) {

        try {
            System.out.println("CREO l'EXCHANGE");
            final Exchange requestExchange = ExchangeBuilder.anExchange(producer.getCamelContext()).withBody(numbers).build();
            System.out.println("EXCHANGE CREATO VADO DI REQUEST");
            Exchange param = producer.withExchange(requestExchange).send();
            //prova fix problema visualizzazione riultati a schermo:
            return ResponseEntity.status(200).body("Rotta ParameterSum eseguita: "+param.getIn().getBody());//param.getIn().getBody(Integer.class)

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred while processing the request.");
        }
    }


//    @GetMapping("/route/parameterSum")
//    public ResponseEntity<String> invokeRoutingRouteSum(@RequestParam("numbers") String numbers) {
//        try {
//            producer.request();
//
//
//                return ResponseEntity.status(200).body("Rotta ParameterSum eseguita: " + numbers);
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("An error occurred while processing the request.");
//        }
//    }
//
////    @GetMapping("/route/parameter/{value}")
//    public ResponseEntity<String> invokeRoutingRouteParameter(@PathVariable String value) {
//        try {
//            producer.request();
//            return ResponseEntity.status(200).body("Aggiungere un parametro alla rotta, tornare un valore e " +
//                    "restituirlo a video a chi invoca la rotta. Valore rotta: " + value);
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("An error occurred while processing the request.");
//        }
//    }

//    @GetMapping("/route/parameterSum") //query= route/parameterSum?numbers=1,2,3,4
//    public ResponseEntity<String> invokeRoutingRouteParameterSum(@RequestParam("numbers") String numbers) {
//        try {
//            //conversione,somma:
//            String[] sumArray = numbers.split(",");
//            int sum=0;
//            for (String s : sumArray) {
//                sum += Integer.parseInt(s);
//            }
//
//            producer.request(); //passa da HelloProcessor
//
//            String result = producer.withBody(sum).request(String.class);
//
//            return ResponseEntity.status(200).body("Somma valori in route:" + result);
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("An error occurred while processing the request." + e.getMessage());        }
//    }
}
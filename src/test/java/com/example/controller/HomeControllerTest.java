package com.example.controller;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.FluentProducerTemplate;
import org.apache.camel.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = HomeController.class)
public class HomeControllerTest {

    public HomeControllerTest() {
        LOG.info("**************** \t sto costruendo classe di test \t *****************");
    }

    private static final Logger LOG = LoggerFactory.getLogger(HomeControllerTest.class);
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private CamelContext camelContext;

    @MockBean
    FluentProducerTemplate producer;

    @MockBean
    Exchange exchange;
    @MockBean
    Message message;

    @BeforeEach
    public void setUp() {
        LOG.info("\tSETUP "+this.toString());
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // Manually create and inject the mock
        HomeController homeController = webApplicationContext.getBean(HomeController.class);
        homeController.producer = producer; // Iniettato con EndpointInject quindi se non lo inietto nel setup non funziona

        // Definisco il comportamento delle chiamate che uso per invocare le rotte
        when(homeController.producer.getCamelContext()).thenReturn(camelContext);
        when(homeController.producer.withExchange((Exchange) any())).thenReturn(producer);
        when(homeController.producer.send()).thenReturn(exchange);
        when(exchange.getIn()).thenReturn(message);
        LOG.info("\tSETUP DONE ");
    }

    @Test
    public void testIndex() throws Exception {
        // Perform the test
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string("Greetings from Spring Boot! /hello/name for better message"));

        // Verify that the Camel route was not called (since we're mocking it)
        verifyNoInteractions(producer);
    }

    @Test
    public void testAltraRoute() throws Exception {

        when(message.getBody()).thenReturn("CIAO");
        // Perform the test
        mockMvc.perform(get("/hello/testName"))
                .andExpect(status().isOk())
                .andExpect(content().string("CIAO"));
    }

}
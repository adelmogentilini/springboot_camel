package com.example.controller;

import org.apache.camel.*;
import org.apache.camel.clock.EventClock;
import org.apache.camel.spi.*;
import org.apache.camel.support.jsse.SSLContextParameters;
import org.apache.camel.vault.VaultConfiguration;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = HomeController.class)
public class HomeControllerTest {
    private static final Logger LOG = LoggerFactory.getLogger(HomeControllerTest.class);
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private CamelContext camelContext;

    @MockBean
    private FluentProducerTemplate producer;
    @MockBean
    Exchange exchange;
    @MockBean
    Message message;

    @BeforeEach
    public void setUp() {
        LOG.info("**************************************************  SETUP **************************************************  ");
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // Manually create and inject the mock
        this.producer = Mockito.mock(FluentProducerTemplate.class);
        this.camelContext = Mockito.mock(CamelContext.class);
        this.exchange = Mockito.mock(Exchange.class);
        HomeController homeController = webApplicationContext.getBean(HomeController.class);
        homeController.producer = this.producer;
        when( homeController.producer.getCamelContext()).thenReturn(this.camelContext);
        when(homeController.producer.withExchange((Exchange)any())).thenReturn(homeController.producer);
        when(homeController.producer.send()).thenReturn(exchange);
        LOG.info(producer.toString());
        LOG.info("**************************************************   SETUP DONE **************************************************  ");
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
        when(this.exchange.getIn()).thenReturn(message);
        when(message.getBody()).thenReturn("CIAO");
        // Perform the test
        mockMvc.perform(get("/hello/testName"))
                .andExpect(status().isOk())
                .andExpect(content().string("CIAO"));
    }

}
package com.wiemanboy.cnsdbankapplication.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DummyAPIServiceIntegrationTest {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DummyAPIService dummyAPIService;
    private MockRestServiceServer mockServer;

    @BeforeEach
    void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    @Description("Test if quote is retrieved from Dummy API")
    void testGetQuote() {
        mockServer.expect(MockRestRequestMatchers.requestTo("https://dummyjson.com/quotes/random"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withSuccess("{\"quote\":\"Test quote\"}", MediaType.APPLICATION_JSON));

        String quote = dummyAPIService.getQuote();
        assertNotNull(quote);
        assertEquals("{\"quote\":\"Test quote\"}", quote);

        mockServer.verify();
    }
}
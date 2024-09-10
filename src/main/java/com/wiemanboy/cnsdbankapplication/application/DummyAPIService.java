package com.wiemanboy.cnsdbankapplication.application;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DummyAPIService {
    private final RestTemplate restTemplate;

    public DummyAPIService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getQuote() {
        return restTemplate.getForObject("https://dummyjson.com/quotes/random", String.class);
    }
}

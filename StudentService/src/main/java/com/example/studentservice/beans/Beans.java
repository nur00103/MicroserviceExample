package com.example.studentservice.beans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class Beans {

    @Value("${address.service.url}")
    private String addressServiceUrl;

    @Bean
    public WebClient webClient(){
        return WebClient.builder().baseUrl(addressServiceUrl).build();
    }
}

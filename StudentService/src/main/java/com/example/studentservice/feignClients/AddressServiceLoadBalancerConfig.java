package com.example.studentservice.feignClients;

import feign.Feign;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;

@LoadBalancerClient(value = "address-service")
public class AddressServiceLoadBalancerConfig {

    // Addres service cagirilarken ,
    // Api Gateway istifade edirikse Load Balancing ist etmeye ehtiyac yoxdur.
    // Api Gateway Load Balancing prosesini ozu edir.
    @LoadBalanced
    @Bean
    public Feign.Builder feignBuilder(){
        return Feign.builder();
    }
}

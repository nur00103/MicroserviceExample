package com.example.studentservice.service;

import com.example.studentservice.feignClients.AddressFeignClient;
import com.example.studentservice.response.AddressResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommonService {

    Logger logger= LoggerFactory.getLogger(CommonService.class);
    long count=1;
    private final AddressFeignClient addressFeignClient;

    //Calling Address microservice with OpenFeign Client
    //ResilienceFourj internally uses Spring AOP
    //If you are calling the method with in same class,the Spring AOP will not work.Because of, we created Common service.
    @CircuitBreaker(name = "addressService",fallbackMethod = "fallbackGetAddressById") //this name must be same with instance name in the properties file
    public AddressResponse getAddressById(long addressId){
        logger.info("Count= "+count);
        count++;
        AddressResponse addressResponse=addressFeignClient.getById(addressId);
        return addressResponse;
    }

    public AddressResponse fallbackGetAddressById(long addressId,Throwable throwable){
        logger.error("Error= "+throwable);
        return new AddressResponse();   //This is dummy response.
    }


}

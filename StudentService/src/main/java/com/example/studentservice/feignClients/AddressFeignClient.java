package com.example.studentservice.feignClients;


import com.example.studentservice.response.AddressResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


//// Without Eureka
//@FeignClient(url = "${address.service.url}" ,value = "address-feign-client" /* we can use the PATH after url*/)
@FeignClient(value = "api-gateway") //With eureka client
public interface AddressFeignClient {

    @GetMapping("/api/address/getById/{id}")
    public AddressResponse getById(@PathVariable long id);
}

package com.springframework.msscbeerservice.web.controller;

import com.springframework.brewery.model.CustomerDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RequestMapping("/api/v1/customer")
@RestController
public class CustomerController {
    @GetMapping({"/{customerId}"})
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable UUID customerId){
        return new ResponseEntity<>(CustomerDto.builder().id(customerId).build(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity saveNewCustomer(@RequestBody CustomerDto customerDto, UriComponentsBuilder uriComponentsBuilder){
        UriComponents uriComponents = uriComponentsBuilder.path("/api/v1/customer/{id}").buildAndExpand(customerDto.getId());
        return ResponseEntity.created(uriComponents.toUri()).build();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{customerId}")
    public void updateCustomerById(@PathVariable UUID customerId,@RequestBody CustomerDto customerDto){

    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{customerId}")
    public void deleteBeerById(@PathVariable UUID customerId){

    }
}

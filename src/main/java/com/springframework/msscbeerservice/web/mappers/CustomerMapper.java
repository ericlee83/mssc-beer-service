package com.springframework.msscbeerservice.web.mappers;

import com.springframework.msscbeerservice.domain.Customer;
import com.springframework.brewery.model.CustomerDto;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
    Customer customerDtoToCustomer(CustomerDto customerDto);

    CustomerDto customerToCustomerDto(Customer customer);
}

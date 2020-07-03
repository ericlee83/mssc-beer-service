package com.springframework.msscbeerservice.web.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public class BaseTest {

    @Autowired
    ObjectMapper objectMapper;

    public static final String BEER_1_UPC = "0631234200036";

    BeerDto getDto(){
        return BeerDto.builder()
                .beerName("Beer")
                .beerStyle(BeerStyleEnum.PALE_ALE)
                .id(UUID.randomUUID())
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .price(new BigDecimal("12.59"))
                .upc(BEER_1_UPC)
                .mylocalDate(LocalDate.now())
                .build();
    }
}

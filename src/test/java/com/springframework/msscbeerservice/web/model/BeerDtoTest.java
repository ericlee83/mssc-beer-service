package com.springframework.msscbeerservice.web.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import static org.junit.jupiter.api.Assertions.*;

@JsonTest
class BeerDtoTest extends BaseTest{

    @Test
    void testSerializeDto() throws JsonProcessingException {
        BeerDto beerDto = getDto();
        String jsonString = objectMapper.writeValueAsString(beerDto);
        System.out.println(jsonString);
    }

    @Test
    void testDeserializeDto() throws JsonProcessingException {
        String json = "{\"version\":null,\"createdDate\":\"2020-06-30T15:49:33+1000\",\"lastModifiedDate\":\"2020-06-30T15:49:33+1000\",\"beerName\":\"Beer\",\"beerStyle\":\"PALE_ALE\",\"upc\":123123123,\"price\":\"12.59\",\"quantityOnHand\":null,\"mylocalDate\":\"20200630\",\"beerId\":\"3d393c87-87a6-452e-9704-58472c31728c\"}";
        BeerDto beerDto = objectMapper.readValue(json,BeerDto.class);
        System.out.println(beerDto);
    }

}
package com.springframework.msscbeerservice.services;

import com.springframework.msscbeerservice.web.model.BeerDto;
import com.springframework.msscbeerservice.web.model.BeerPagedList;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface BeerService {
    BeerDto findBeerById(UUID beerId,Boolean showInventoryOnHand);

    BeerDto saveNewBeer(BeerDto beerDto);

    BeerDto updateBeerById(UUID beerId, BeerDto beerDto);

    void deleteBeerById(UUID beerId);

    BeerPagedList listBeers(String beerName, String beerStyle, PageRequest pageRequest,Boolean showInventoryOnHand);

    BeerDto findBeerByUpc(String upc);
}

package com.springframework.msscbeerservice.events;

import com.springframework.msscbeerservice.web.model.BeerDto;
import lombok.NoArgsConstructor;


@NoArgsConstructor
public class BrewBeerEvent extends BeerEvent {
    public BrewBeerEvent(BeerDto beerDto) {
        super(beerDto);
    }
}

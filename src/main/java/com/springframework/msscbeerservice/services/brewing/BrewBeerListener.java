package com.springframework.msscbeerservice.services.brewing;

import com.springframework.msscbeerservice.config.JmsConfig;
import com.springframework.msscbeerservice.domain.Beer;
import com.springframework.msscbeerservice.events.BrewBeerEvent;
import com.springframework.msscbeerservice.events.NewInventoryEvent;
import com.springframework.msscbeerservice.repositories.BeerRepository;
import com.springframework.msscbeerservice.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BrewBeerListener {

    private final BeerRepository beerRepository;
    private final JmsTemplate jmsTemplate;

    @Transactional
    @JmsListener(destination = JmsConfig.BEER_REQUEST)
    public void listen(BrewBeerEvent event){
        BeerDto beerDto = event.getBeerDto();
        Beer beer = beerRepository.getOne(beerDto.getId());
        beerDto.setQuantityOnHand(beer.getQuantityToBrew());
        NewInventoryEvent newInventoryEvent = new NewInventoryEvent(beerDto);
        log.debug("Brewed beer "+ beer.getMinOnHand()+" : QOH: "+beerDto.getQuantityOnHand());
        jmsTemplate.convertAndSend(JmsConfig.NEW_INVENTORY,newInventoryEvent);
    }
}

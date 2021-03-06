package com.springframework.msscbeerservice.services.brewing;

import com.springframework.brewery.model.BeerDto;
import com.springframework.brewery.model.events.BeerEvent;
import com.springframework.brewery.model.events.NewInventoryEvent;
import com.springframework.msscbeerservice.config.JmsConfig;
import com.springframework.msscbeerservice.domain.Beer;
import com.springframework.msscbeerservice.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BrewBeerListener {

    private final BeerRepository beerRepository;
    private final JmsTemplate jmsTemplate;

    @Transactional
    @JmsListener(destination = JmsConfig.BEER_REQUEST)
    public void listen(BeerEvent event){
        BeerDto beerDto = event.getBeerDto();
        Optional<Beer> beerOptional = beerRepository.findById(beerDto.getId());
        beerOptional.ifPresentOrElse(beer -> {
            beerDto.setQuantityOnHand(beer.getQuantityToBrew());
            NewInventoryEvent newInventoryEvent = new NewInventoryEvent(beerDto);
            log.debug("Brewed beer "+ beer.getMinOnHand()+" : QOH: "+beerDto.getQuantityOnHand());
            jmsTemplate.convertAndSend(JmsConfig.NEW_INVENTORY,newInventoryEvent);
        },()->log.error("beer not found id: "+beerDto.getId()));
    }
}

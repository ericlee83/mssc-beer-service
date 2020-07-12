package com.springframework.msscbeerservice.services.brewing;

import com.springframework.msscbeerservice.config.JmsConfig;
import com.springframework.msscbeerservice.domain.Beer;
import com.springframework.brewery.model.events.BeerEvent;
import com.springframework.msscbeerservice.repositories.BeerRepository;
import com.springframework.msscbeerservice.services.inventory.BeerInventoryService;
import com.springframework.msscbeerservice.web.mappers.BeerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrewingService {
    private final BeerRepository beerRepository;
    private final BeerInventoryService beerInventoryService;
    private final JmsTemplate jmsTemplate;
    private final BeerMapper beerMapper;

    @Scheduled(fixedRate = 5000)
    public void checkForLowInventory(){
        List<Beer> beers = beerRepository.findAll();
        beers.forEach(beer -> {
            Integer invQOH = beerInventoryService.getOnhandInventory(beer.getId());
            log.debug("Min on hand is "+ beer.getMinOnHand());
            log.debug("Inventory is:" + invQOH);
            if(beer.getMinOnHand() >= invQOH){
                jmsTemplate.convertAndSend(JmsConfig.BEER_REQUEST,new BeerEvent(beerMapper.beerToBeerDto(beer)));
            }
        });
    }
}

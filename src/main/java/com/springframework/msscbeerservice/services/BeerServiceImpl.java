package com.springframework.msscbeerservice.services;

import com.springframework.msscbeerservice.domain.Beer;
import com.springframework.msscbeerservice.repositories.BeerRepository;
import com.springframework.msscbeerservice.web.controller.NotFoundException;
import com.springframework.msscbeerservice.web.mappers.BeerMapper;
import com.springframework.brewery.model.BeerDto;
import com.springframework.brewery.model.BeerPagedList;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Cacheable(cacheNames = "beerCache",key = "#beerId",condition = "#showInventoryOnHand == false")
    @Override
    public BeerDto findBeerById(UUID beerId,Boolean showInventoryOnHand) {
        if(showInventoryOnHand){
            return beerMapper.beerToBeerDtoWithInventory(
                    beerRepository.findById(beerId).orElseThrow(NotFoundException::new)
            );
        }else{
            return beerMapper.beerToBeerDto(
                    beerRepository.findById(beerId).orElseThrow(NotFoundException::new)
            );
        }

    }

    @Override
    public BeerDto saveNewBeer(BeerDto beerDto) {
        return beerMapper.beerToBeerDto(
                beerRepository.save(
                        beerMapper.beerDtoToBeer(beerDto)
                )
        );
    }

    @Override
    public BeerDto updateBeerById(UUID beerId, BeerDto beerDto) {
        Beer beer = beerRepository.findById(beerId).orElseThrow(NotFoundException::new);

        beer.setBeerName(beerDto.getBeerName());
        beer.setBeerStyle(beerDto.getBeerStyle().name());
        beer.setPrice(beerDto.getPrice());
        beer.setUpc(beerDto.getUpc());
        return beerMapper.beerToBeerDto(
                beerRepository.save(beer)
        );
    }

    @Override
    public void deleteBeerById(UUID beerId) {

    }

    @Cacheable(cacheNames = "beerListCache",condition = "#showInventoryOnHand == false")
    @Override
    public BeerPagedList listBeers(String beerName, String beerStyle, PageRequest pageRequest,Boolean showInventoryOnHand) {
        BeerPagedList beerPagedList;
        Page<Beer> beerPage;

        if(!StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)){
            beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName,beerStyle,pageRequest);
        }else if(!StringUtils.isEmpty(beerName) && StringUtils.isEmpty(beerStyle)){
            beerPage = beerRepository.findAllByBeerName(beerName,pageRequest);
        }else if(StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)){
            beerPage = beerRepository.findAllByBeerStyle(beerStyle,pageRequest);
        }else{
            beerPage = beerRepository.findAll(pageRequest);
        }

        if(showInventoryOnHand){
            beerPagedList = new BeerPagedList(
                    beerPage.getContent()
                            .stream()
                            .map(beerMapper::beerToBeerDtoWithInventory)
                            .collect(Collectors.toList()),
                    PageRequest.of(beerPage.getPageable().getPageNumber(),beerPage.getPageable().getPageSize()),
                    beerPage.getTotalElements()
            );
        }else{
            beerPagedList = new BeerPagedList(
                    beerPage.getContent()
                            .stream()
                            .map(beerMapper::beerToBeerDto)
                            .collect(Collectors.toList()),
                    PageRequest.of(beerPage.getPageable().getPageNumber(),beerPage.getPageable().getPageSize()),
                    beerPage.getTotalElements()
            );
        }
        return beerPagedList;
    }

    @Cacheable(cacheNames = "beerUpcCache")
    @Override
    public BeerDto findBeerByUpc(String upc) {
        return beerMapper.beerToBeerDto(
                beerRepository.findBeerByUpc(upc).orElseThrow(NotFoundException::new)
        );
    }
}
package com.springframework.msscbeerservice.web.controller;

import com.springframework.msscbeerservice.services.BeerService;
import com.springframework.msscbeerservice.web.model.BeerDto;
import com.springframework.msscbeerservice.web.model.BeerPagedList;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.UUID;

@RequestMapping("/api/v1/")
@RestController
@RequiredArgsConstructor
public class BeerController {

    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 25;

    private final BeerService beerService;

    @GetMapping(produces = {"application/json"},path = "beer")
    public ResponseEntity<BeerPagedList> listBeers(
        @RequestParam(value = "pageNumber", required=false) Integer pageNumber,
        @RequestParam(value = "pageSize", required=false) Integer pageSize,
        @RequestParam(value = "beerName", required=false) String beerName,
        @RequestParam(value = "beerStyle", required=false) String beerStyle,
        @RequestParam(value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand
    ){
        if(showInventoryOnHand == null){
            showInventoryOnHand = false;
        }
        if(pageNumber == null || pageNumber<0){
            pageNumber = DEFAULT_PAGE_NUMBER;
        }
        if(pageSize == null || pageSize<0){
            pageSize = DEFAULT_PAGE_SIZE;
        }
        BeerPagedList beerPagedList = beerService.listBeers(beerName,beerStyle, PageRequest.of(pageNumber,pageSize),showInventoryOnHand);
        return new ResponseEntity<>(beerPagedList,HttpStatus.OK);
    }

    @GetMapping("beer/{beerId}")
    public ResponseEntity<BeerDto> getBeerById(@PathVariable UUID beerId,
                                               @RequestParam(value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand){
        if(showInventoryOnHand == null){
            showInventoryOnHand = false;
        }
        return new ResponseEntity<>(beerService.findBeerById(beerId,showInventoryOnHand),HttpStatus.OK);
    }

    @GetMapping("beerupc/{upc}")
    public ResponseEntity<BeerDto> getBeerByUPC(@PathVariable String upc) {
        return new ResponseEntity<>(beerService.findBeerByUpc(upc), HttpStatus.OK);
    }

    @PostMapping("beer")
    public ResponseEntity saveNewBeer(@RequestBody @Valid BeerDto beerDto, UriComponentsBuilder uriComponentsBuilder){
        UriComponents uriComponents = uriComponentsBuilder.path("/api/v1/beer/{id}")
                .buildAndExpand(beerService.saveNewBeer(beerDto).getId());
        return ResponseEntity.created(uriComponents.toUri()).build();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("beer/{beerId}")
    public ResponseEntity updateBeerById(@PathVariable UUID beerId,@RequestBody @Valid BeerDto beerDto){
        return new ResponseEntity<>(beerService.updateBeerById(beerId,beerDto),HttpStatus.NO_CONTENT);
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("beer/{beerId}")
    public void deleteBeerById(@PathVariable UUID beerId){
        beerService.deleteBeerById(beerId);
    }


}

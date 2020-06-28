package com.springframework.msscbeerservice.web.controller;

import com.springframework.msscbeerservice.web.model.BeerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RequestMapping("/api/v1/beer")
@RestController
public class BeerController {


    @GetMapping({"/{beerId}"})
    public ResponseEntity<BeerDto> getBeerById(@PathVariable UUID beerId){
        return new ResponseEntity<>(BeerDto.builder().build(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity saveNewBeer(@RequestBody BeerDto beerDto,UriComponentsBuilder uriComponentsBuilder){
        UriComponents uriComponents = uriComponentsBuilder.path("/api/v1/beer/{id}").buildAndExpand(beerDto.getId());
        return ResponseEntity.created(uriComponents.toUri()).build();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{beerId}")
    public void updateBeerById(@PathVariable UUID beerId,@RequestBody BeerDto beerDto){

    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{beerId}")
    public void deleteBeerById(@PathVariable UUID beerId){

    }


}

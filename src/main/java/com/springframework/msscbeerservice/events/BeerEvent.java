package com.springframework.msscbeerservice.events;

import com.springframework.msscbeerservice.web.model.BeerDto;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BeerEvent implements Serializable {

    static final long serialVersionUID = -5173935999266003444L;

    private BeerDto beerDto;
}

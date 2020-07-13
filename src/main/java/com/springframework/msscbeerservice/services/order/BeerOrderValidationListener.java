package com.springframework.msscbeerservice.services.order;

import com.springframework.brewery.model.events.ValidateOrderRequest;
import com.springframework.brewery.model.events.ValidateOrderResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import static com.springframework.msscbeerservice.config.JmsConfig.VALIDATE_ORDER;
import static com.springframework.msscbeerservice.config.JmsConfig.VALIDATE_ORDER_RESPONSE;

@Slf4j
@Component
@RequiredArgsConstructor
public class BeerOrderValidationListener {

    private final JmsTemplate jmsTemplate;

    private final BeerOrderValidator beerOrderValidator;

    @JmsListener(destination = VALIDATE_ORDER)
    public void listen(ValidateOrderRequest request){
        Boolean isValid = beerOrderValidator.validateOrder(request.getBeerOrder());

        jmsTemplate.convertAndSend(VALIDATE_ORDER_RESPONSE,
                ValidateOrderResult.builder()
        .isValid(isValid).orderId(request.getBeerOrder().getId()).build());
    }


}

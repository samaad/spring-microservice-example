package org.bee.configserver.controller;

import lombok.extern.log4j.Log4j2;
import org.bee.configserver.model.ExchangeValue;
import org.bee.configserver.repository.ExchangeValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@Log4j2
@RestController
@RequestMapping("/currency-exchange")
public class CurrencyExchangeController {

//  http://localhost:8882/currency-exchange/from/EUR/to/INR
//  direct using zuul and naming service
// http://localhost:8332/currency-exchange-service/currency-exchange/from/AUD/to/INR
  @Autowired
  private ExchangeValueRepository valueRepository;

  @Value("${server.port}")
  private int port;

  @GetMapping("/from/{from}/to/{to}")
  public ExchangeValue retrieveExchangevalue(@PathVariable String from, @PathVariable String to){
    ExchangeValue exchangeValue = valueRepository.findByFromAndTo(from, to);
    exchangeValue.setPort(port);
    log.info("{}", exchangeValue);
    return exchangeValue;
  }
}

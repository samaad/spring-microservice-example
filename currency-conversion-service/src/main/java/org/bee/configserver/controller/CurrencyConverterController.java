package org.bee.configserver.controller;

import lombok.extern.log4j.Log4j2;
import org.bee.configserver.client.CurrencyExchangeProxy;
import org.bee.configserver.client.ZuulExchangeProxy;
import org.bee.configserver.model.CurrencyConverterValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/currency-converter")
public class CurrencyConverterController {

//  direct
//  http://localhost:9883/currency-converter/from/AUD/to/INR/quantity/40
//  direct with zuul port
//  http://localhost:9883/currency-converter/zuul/from/PKR/to/INR/quantity/40
//  direct with zuul naming service
//  http://localhost:8332/currency-conversion-service/currency-converter/zuul/from/PKR/to/INR/quantity/40


  @Autowired
  private CurrencyExchangeProxy currencyExchangeProxy;

  @Autowired
  private ZuulExchangeProxy zuulExchangeProxy;

  @Value("${server.port}")
  private int port;

  @GetMapping("from/{from}/to/{to}/quantity/{quantity}")
  public CurrencyConverterValue retrieveCurrencyConverter(
      @PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity
  ){

//  Without Feign
    Map<String,String> uriVariables = new HashMap<>();
    uriVariables.put("from", from);
    uriVariables.put("to", to);
    ResponseEntity<CurrencyConverterValue> responseEntity = new RestTemplate().getForEntity
        ("http://localhost:8882/currency-exchange/from/{from}/to/{to}",
        CurrencyConverterValue.class, uriVariables);
    CurrencyConverterValue response = responseEntity.getBody();
    log.info("DIRECT response {}", response);
    return new CurrencyConverterValue(response.getId(), from ,to, response.getConversionMultiple(), quantity,
        quantity.multiply(response.getConversionMultiple()), port, response.getPort());
  }

  @GetMapping("feign/from/{from}/to/{to}/quantity/{quantity}")
  public CurrencyConverterValue retrieveCurrencyConverterWithFeign(
      @PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity
  ){

    CurrencyConverterValue response = currencyExchangeProxy.retrieveExchangevalue(from, to);
    log.info("FEIGN response {}", response);
    return new CurrencyConverterValue(response.getId(), from ,to, response.getConversionMultiple(), quantity,
        quantity.multiply(response.getConversionMultiple()), port, response.getPort());
  }

  @GetMapping("zuul/from/{from}/to/{to}/quantity/{quantity}")
  public CurrencyConverterValue retrieveCurrencyConverterWithZuulFeign(
      @PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity
  ){

    CurrencyConverterValue response = zuulExchangeProxy.retrieveExchangevalue(from, to);
    log.info("ZUUL response {}", response);
    return new CurrencyConverterValue(response.getId(), from ,to, response.getConversionMultiple(), quantity,
        quantity.multiply(response.getConversionMultiple()), port, response.getPort());
  }
}

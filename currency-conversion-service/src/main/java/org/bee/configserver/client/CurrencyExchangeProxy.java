package org.bee.configserver.client;

import org.bee.configserver.model.CurrencyConverterValue;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//Incase of ribbion no need to add the url
//@FeignClient(name = "currency-exchange-service", url = "localhost:8882")
@FeignClient(name = "currency-exchange-service")
@RibbonClient(name = "currency-exchange-service", configuration = RibbonConfiguration.class )
public interface CurrencyExchangeProxy {

  @GetMapping("/currency-exchange/from/{from}/to/{to}")
  public CurrencyConverterValue retrieveExchangevalue(@PathVariable("from") String from, @PathVariable("to") String to);
}

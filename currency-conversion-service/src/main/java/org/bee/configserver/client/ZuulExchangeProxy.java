package org.bee.configserver.client;


import org.bee.configserver.model.CurrencyConverterValue;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "zuul-api-gateway-server")
@RibbonClient(name = "currency-exchange-service", configuration = RibbonConfiguration.class )
public interface ZuulExchangeProxy {

  @GetMapping("/currency-exchange-service/currency-exchange/from/{from}/to/{to}")
  public CurrencyConverterValue retrieveExchangevalue(@PathVariable("from") String from, @PathVariable("to") String to);
}

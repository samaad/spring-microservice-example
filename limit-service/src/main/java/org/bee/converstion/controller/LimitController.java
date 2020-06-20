package org.bee.converstion.controller;

import org.bee.converstion.Configuration;
import org.bee.converstion.model.LimitConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitController {

  @Autowired
  Configuration configuration;

  @GetMapping("/limits")
  public LimitConfiguration retrieveLimitsFromConfig(){
    return new LimitConfiguration(configuration.getMaximum(), configuration.getMinimum());
  }
}

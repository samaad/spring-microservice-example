package org.bee.configserver.logger;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class ZuulLogginFilter extends ZuulFilter{

  private static final Logger LOGGER = LoggerFactory.getLogger(ZuulLogginFilter.class);
  @Override
  public String filterType() {
    return "pre";
  }

  @Override
  public int filterOrder() {
    return 1;
  }

  @Override
  public boolean shouldFilter() {
    return true;
  }

  @Override
  public Object run() throws ZuulException {
    HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
    LOGGER.info("requst -> {}  request uri -> {}",request, request.getRequestURI());
    return null;
  }
}

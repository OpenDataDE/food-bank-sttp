package com.opendatadelaware.feede.config;

import com.opendatadelaware.feede.service.UsersService;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by aaronlong on 6/28/17.
 */
@EnableWebMvc
@Configuration
@ComponentScan("com.opendatadelaware.feede")
public class RestConfig extends WebMvcConfigurerAdapter {
  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder) {
    return builder.build();
  }
}

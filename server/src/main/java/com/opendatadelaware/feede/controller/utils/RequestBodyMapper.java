package com.opendatadelaware.feede.controller.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

/**
 * Created by aaronlong on 7/2/17.
 */
public class RequestBodyMapper <T> {
  private static final Logger LOGGER = LoggerFactory.getLogger(RequestBodyMapper.class);
  private transient Optional<T> value;

  private RequestBodyMapper(byte[] json, Class<T> tClass) {
    try {
      T result = new ObjectMapper().readValue(json, tClass);
      value = Optional.of(result);
    } catch (IOException e) {
      LOGGER.warn(e.getMessage());
      value = Optional.empty();
    }
  }

  public boolean doesExist() {
    return value.isPresent();
  }

  public T get() {
    return value.get();
  }

  public static <T> RequestBodyMapper factory(byte[] json, Class<T> tClass) {
    return new RequestBodyMapper<T>(json, tClass);
  }
}

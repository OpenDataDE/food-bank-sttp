package com.opendatadelaware.feede.controller.utils;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * Created by aaronlong on 7/2/17.
 */
public class TestRequestBodyMapper {
  private static final Logger LOGGER = LoggerFactory.getLogger(TestRequestBodyMapper.class);

  private static Optional<byte[]> readFile(String fileName) {
    try {
      URL path = TestRequestBodyMapper.class.getResource(fileName);
      return Optional.of(Files.readAllBytes(Paths.get(path.toURI())));
    } catch (URISyntaxException | IOException e) {
      LOGGER.warn(e.getMessage());
      return Optional.empty();
    }
  }

  @Test
  public void testRequestBodyMapperCreation() {
    Optional<byte[]> bytes = readFile("/json/GoodUserInput.json");
    if (bytes.isPresent()) {
      RequestBodyMapper<UserAuthValidator> result = RequestBodyMapper.<UserAuthValidator>factory(bytes.get(), UserAuthValidator.class);
      Assert.assertNotNull("Checking if UserAuthValidator was created", result.get());
    }
  }

  @Test
  public void testRequestBodyMapperReturnObjectValidity() {
    Optional<byte[]> bytes = readFile("/json/GoodUserInput.json");
    if (bytes.isPresent()) {
      RequestBodyMapper<UserAuthValidator> result = RequestBodyMapper.<UserAuthValidator>factory(bytes.get(), UserAuthValidator.class);
      Assert.assertTrue("Checking if return Validator isValid", result.get().isValid());
    }
  }
}

package com.opendatadelaware.feede.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opendatadelaware.feede.dao.UsersDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


/**
 * Created by aaronlong on 6/28/17.
 */
@RunWith(SpringRunner.class)
public class TestUserController {
  private static final Logger LOGGER = LoggerFactory.getLogger(TestUserController.class);

  private MockMvc mvc;

  @MockBean
  private UsersDao dao;

  @InjectMocks
  private UsersController controller;

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
    mvc = MockMvcBuilders.standaloneSetup(controller).build();
  }

  static Optional<String> jsonFileToBase64String(String fileName) {
    try {
      URL inputFile = TestUserController.class.getResource(fileName);
      byte[] jsonData = Files.readAllBytes(Paths.get(inputFile.toURI()));
      return Optional.<String>of(Base64.getEncoder().encodeToString(jsonData));
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
      return Optional.empty();
    }
  }

  @Test
  // Needs to handle the exception
  public void testPostBadInput() throws Exception {
    //String badAuth = "eyIiOiIiLCJoZXkiOiJ3aGF0c3VwIiwibm8iOiJwcm9mYW5pdHkiLCJkZW5uaXMiOiJtYXJpb2thcnRtYXN0ZXIifQ==";
    Optional<String> badAuth = jsonFileToBase64String("/json/BadUserInput.json");
    if (badAuth.isPresent()) {
      Map<String, String> badInput = Collections.singletonMap("auth", badAuth.get());
      String badAuthBody = new ObjectMapper().writeValueAsString(badInput);
      this.mvc.perform(post("/api/user")
                               .contentType(MediaType.APPLICATION_JSON).content(badAuthBody))
              .andExpect(status().isBadRequest());
    } else {
      Assert.fail("File not available");
    }
  }

//  @Test
//  public void testPutValidInput() throws Exception {
//      byte[] jsonAsBytes = Files.readAllBytes(Paths.get("GoodInputJson.json"));
//      String jsonAsString = new String(jsonAsBytes);
//      //String validAuth = "eyJmaXJzdE5hbWUiOiJEZW5uaXMiLCJsYXN0TmFtZSI6IkthbGF5Z2lhbiIsImVtYWlsIjoiZGVubmlza2FsYXlnaWFuQGdtYWlsLmNvbSIsInBhc3N3b3JkIjoiMTIzNDUiLCJwaG9uZSI6IjMwMjMzMzMzMzMiLCJzdHJlZXQiOiI4MTEgQmlyY2ggQXZlIiwiY2l0eSI6Ik1pbGZvcmQiLCJzdGF0ZSI6IkRFIiwiemlwIjoiMTk5NjMifQ==";
//      ObjectMapper objectMapper = new ObjectMapper();
//      TypeReference<HashMap<String, String>> typeRef = new TypeReference<HashMap<String,String>>() {};
//      Map<String, String> user = objectMapper.readValues(jsonAsString, new HashMap<String,String>());//typeRef);
//      //String jsonValue = o
//
//
//  }
}

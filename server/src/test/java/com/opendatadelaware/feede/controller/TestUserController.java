package com.opendatadelaware.feede.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opendatadelaware.feede.dao.UsersDao;
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

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by aaronlong on 6/28/17.
 */
@RunWith(SpringRunner.class)
public class TestUserController {
  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

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

  @Test
  // Needs to handle the exception
  public void testPutBadInput() throws Exception {
    //String badAuth = "eyIiOiIiLCJoZXkiOiJ3aGF0c3VwIiwibm8iOiJwcm9mYW5pdHkiLCJkZW5uaXMiOiJtYXJpb2thcnRtYXN0ZXIifQ==";
    URL inputFile = TestUserController.class.getResource("/json/BadUserInput.json");
    byte[] jsonData = Files.readAllBytes(Paths.get(inputFile.toURI()));
    String badAuth = Base64.getEncoder().encodeToString(jsonData);
    Map<String, String> badInput = Collections.singletonMap("auth", badAuth);
    String badAuthBody = new ObjectMapper().writeValueAsString(badInput);
    this.mvc.perform(post("/api/user")
            .contentType(MediaType.APPLICATION_JSON).content(badAuthBody))
              .andExpect(status().isBadRequest());
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

package com.opendatadelaware.feede.controller;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opendatadelaware.feede.dao.UsersDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
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
import java.util.HashMap;
import java.util.Map;


/**
 * Created by aaronlong on 6/28/17.
 */
@RunWith(SpringRunner.class)
public class TestUserController {
  //private static Logger LOGGER = Logger.getLogger(TestUserController.class.getName());

  private MockMvc mvc;

  @MockBean
  private UsersDao dao;

  @InjectMocks
  private UsersController controller;

  @Before
  public void init() {
    LoggingSystem.get(ClassLoader.getSystemClassLoader()).setLogLevel(Logger.ROOT_LOGGER_NAME, LogLevel.ERROR);
    MockitoAnnotations.initMocks(this);
    mvc = MockMvcBuilders.standaloneSetup(controller).build();
  }

  @Test
  public void testPutBadInput() throws Exception {
    //String badAuth = "eyIiOiIiLCJoZXkiOiJ3aGF0c3VwIiwibm8iOiJwcm9mYW5pdHkiLCJkZW5uaXMiOiJtYXJpb2thcnRtYXN0ZXIifQ==";
    URL inputFile = TestUserController.class.getResource("/GoodInputJson.json");
    //LOGGER.info(inputFile.toString());
    //Map<String, String> badInput = Collections.singletonMap("auth", badAuth);
    byte[] jsonData = Files.readAllBytes(Paths.get(inputFile.toURI()));
    Base64.Encoder decoder = Base64.getEncoder();
    String mapData = decoder.encodeToString(jsonData);
    System.out.println(String.format("\n%s\n", mapData));
//    Map<String,String> objectMap = new HashMap<String, String>();
//    ObjectMapper objectMapper = new ObjectMapper();
//    objectMap = objectMapper.readValue(mapData, HashMap.class);
//    this.mvc.perform(post("/api/user")
//            .contentType(MediaType.APPLICATION_JSON).content(badInput.get("auth")))
//              .andExpect(status().isBadRequest());
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

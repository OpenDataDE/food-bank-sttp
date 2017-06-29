package com.opendatadelaware.feede.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opendatadelaware.feede.dao.UsersDao;
import com.opendatadelaware.feede.model.Users;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
  public void testPutBadInput() throws Exception {
    String badAuth = "eyIiOiIiLCJoZXkiOiJ3aGF0c3VwIiwibm8iOiJwcm9mYW5pdHkiLCJkZW5uaXMiOiJtYXJpb2thcnRtYXN0ZXIifQ==";
    Map<String, String> badInput = Collections.singletonMap("auth", badAuth);
    byte[] jsonData = Files.readAllBytes(Paths.get(badInput.get("auth")));
    Base64.Decoder decoder = Base64.getDecoder();
    byte[] mapData = decoder.decode(jsonData);
    Map<String,String> objectMap = new HashMap<String, String>();
    ObjectMapper objectMapper = new ObjectMapper();
    objectMap = objectMapper.readValue(mapData, HashMap.class);

    /*this.mvc.perform(post("/api/user")
            .contentType(MediaType.APPLICATION_JSON).content(badInput.get("auth")))
              .andExpect(status().isBadRequest());*/
  }

  @Test
  public void testPutValidInput() throws Exception {
      byte[] jsonAsBytes = Files.readAllBytes(Paths.get("GoodInputJson.json"));
      String jsonAsString = new String(jsonAsBytes);
      //String validAuth = "eyJmaXJzdE5hbWUiOiJEZW5uaXMiLCJsYXN0TmFtZSI6IkthbGF5Z2lhbiIsImVtYWlsIjoiZGVubmlza2FsYXlnaWFuQGdtYWlsLmNvbSIsInBhc3N3b3JkIjoiMTIzNDUiLCJwaG9uZSI6IjMwMjMzMzMzMzMiLCJzdHJlZXQiOiI4MTEgQmlyY2ggQXZlIiwiY2l0eSI6Ik1pbGZvcmQiLCJzdGF0ZSI6IkRFIiwiemlwIjoiMTk5NjMifQ==";
      ObjectMapper objectMapper = new ObjectMapper();
      TypeReference<HashMap<String, String>> typeRef = new TypeReference<HashMap<String,String>>() {};
      Map<String, String> user = objectMapper.readValues(jsonAsString, new HashMap<String,String>);//typeRef);
      //String jsonValue = o


  }
}

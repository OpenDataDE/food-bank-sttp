package com.opendatadelaware.feede.controller;

import com.opendatadelaware.feede.controller.responses.BadRequest;
import com.opendatadelaware.feede.controller.responses.Response;
import com.opendatadelaware.feede.controller.utils.RequestBodyMapper;
import com.opendatadelaware.feede.controller.utils.UserAuthValidator;
import com.opendatadelaware.feede.service.UsersService;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by aaronlong on 6/27/17.
 */
@RestController
@RequestMapping("/api/user")
public class UsersController {
  private UsersService service;

  @Autowired
  public void setUserService(UsersService aService) {
    service = aService;
  }

  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity<?> getUserInfo() {
    return new ResponseEntity<String>("Hello World", HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<? extends Response> addUser(@RequestBody Map<String, String> userSubmission) {
    if (userSubmission.containsKey("auth")) {
        byte[] jsonRepresentation = Base64.decode(userSubmission.get("auth"));
        RequestBodyMapper<UserAuthValidator> auth = RequestBodyMapper.factory(jsonRepresentation, UserAuthValidator.class);
        return service.createUser(auth);
    }
    return new BadRequest().makeResponse(HttpStatus.BAD_REQUEST);
  }

}

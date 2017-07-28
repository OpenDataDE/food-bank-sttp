package com.opendatadelaware.feede.controller;

import com.opendatadelaware.feede.controller.responses.BadRequest;
import com.opendatadelaware.feede.controller.responses.Response;
import com.opendatadelaware.feede.controller.utils.RequestBodyMapper;
import com.opendatadelaware.feede.controller.utils.UserAuthValidator;
import com.opendatadelaware.feede.service.TokenService;
import com.opendatadelaware.feede.service.UserService;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
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
    private UserService service;
    private TokenService tokenService;

    private static <T> RequestBodyMapper<T> base64ToRequestBodyMapper(String encodedString, Class<T> theClass) {
        byte[] jsonRepresentation = Base64.decode(encodedString);
        return RequestBodyMapper.<T>factory(jsonRepresentation, theClass);
    }

    @Autowired
    public void setUserService(UserService theUserService) {
        service = theUserService;
    }

    @Autowired
    public void setTokenService(TokenService theTokenService) {
        tokenService = theTokenService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getUserInfo() {
        return new ResponseEntity<String>("Hello World", HttpStatus.OK);
    }

}

package com.opendatadelaware.feede.controller;

import com.opendatadelaware.feede.config.WebSecurityConfig;
import com.opendatadelaware.feede.config.jwt.token.JwtToken;
import com.opendatadelaware.feede.controller.responses.BadRequest;
import com.opendatadelaware.feede.controller.responses.Response;
import com.opendatadelaware.feede.controller.responses.Success;
import com.opendatadelaware.feede.controller.utils.RequestBodyMapper;
import com.opendatadelaware.feede.controller.utils.UserAuthValidator;
import com.opendatadelaware.feede.controller.utils.UserCredentials;
import com.opendatadelaware.feede.model.Users;
import com.opendatadelaware.feede.service.EntityWrapper;
import com.opendatadelaware.feede.service.TokenService;
import com.opendatadelaware.feede.service.UserService;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.CredentialException;
import java.util.Map;

/**
 * Created by aaronlong on 7/14/17.
 */
@RestController
@RequestMapping("/login")
public class AuthenticationController {

    private UserService service;
    private TokenService tokenService;

    @Autowired
    public void setUserService(UserService theUserService) {
        service = theUserService;
    }

    @Autowired
    public void setTokenService(TokenService theTokenService) {
        tokenService = theTokenService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<? extends Response> loginRequestHandler(@RequestBody Map<String, String> userSubmission)
            throws CredentialException {
        if (userSubmission.containsKey("auth")) {
            UserCredentials auth = new UserCredentials(userSubmission.get("auth"));
            EntityWrapper<Users> user = service.validateUserForLogin(auth);
            JwtToken token = tokenService.createToken(user);
            String bearerToken = String.format("Bearer %s", token.getTokenString());
            HttpHeaders header = new HttpHeaders();
            header.add(WebSecurityConfig.JWT_TOKEN_HEADER_PARAM, bearerToken);
            return new Success()
                    .setHeader(header)
                    .setStatusCode(HttpStatus.OK)
                    .makeResponse();
        }
        return new BadRequest().makeResponse(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public ResponseEntity<? extends Response> addUser(@RequestBody Map<String, String> userSubmission) {
        if (userSubmission.containsKey("auth")) {
            RequestBodyMapper<UserAuthValidator> auth = base64ToRequestBodyMapper(userSubmission.get("auth"), UserAuthValidator.class);
            return service.createUserFromRequest(auth).makeResponse();
        }
        return new BadRequest().makeResponse(HttpStatus.BAD_REQUEST);
    }

    private static <T> RequestBodyMapper<T> base64ToRequestBodyMapper(String encodedString, Class<T> theClass) {
        byte[] jsonRepresentation = Base64.decode(encodedString);
        return RequestBodyMapper.<T>factory(jsonRepresentation, theClass);
    }
}

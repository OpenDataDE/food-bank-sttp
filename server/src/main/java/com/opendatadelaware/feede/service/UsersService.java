package com.opendatadelaware.feede.service;

import com.opendatadelaware.feede.controller.responses.BadRequest;
import com.opendatadelaware.feede.controller.responses.Response;
import com.opendatadelaware.feede.controller.responses.Success;
import com.opendatadelaware.feede.controller.utils.RequestBodyMapper;
import com.opendatadelaware.feede.controller.utils.UserAuthValidator;
import com.opendatadelaware.feede.dao.UsersDao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


/**
 * Created by aaronlong on 6/27/17.
 */
@Service
public class UsersService extends AbstractService<UsersDao> {
  private static final String successMessage = "User was created";
  private static final String failureMessage = "User couldn't be create because of missing or invalid properties";

  public ResponseEntity<? extends Response> createUser(RequestBodyMapper<UserAuthValidator> authSubmission) {
    if (authSubmission.doesExist() && authSubmission.get().isValid()) {
      return new Success(successMessage).makeResponse(HttpStatus.CREATED);
    }
    return new BadRequest(failureMessage).makeResponse(HttpStatus.BAD_REQUEST);
  }
}

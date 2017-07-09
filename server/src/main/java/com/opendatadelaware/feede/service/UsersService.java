package com.opendatadelaware.feede.service;

import com.opendatadelaware.feede.controller.responses.BadRequest;
import com.opendatadelaware.feede.controller.responses.Response;
import com.opendatadelaware.feede.controller.responses.Success;
import com.opendatadelaware.feede.controller.utils.RequestBodyMapper;
import com.opendatadelaware.feede.controller.utils.UserAuthValidator;
import com.opendatadelaware.feede.dao.UsersDao;
import com.opendatadelaware.feede.model.Users;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * Created by aaronlong on 6/27/17.
 */
@Service
public class UsersService extends AbstractService<UsersDao> {
  private static final String successMessage = "User was created";
  private static final String failureMessage = "User couldn't be create because of missing or invalid properties";

  public Response createUserFromRequest(RequestBodyMapper<UserAuthValidator> authSubmission) {
    if (authSubmission.doesExist() && authSubmission.get().isValid()) {
      UserAuthValidator auth = authSubmission.get();
      if (!dao.getUserByEmail(auth.getEmail()).isPresent()) {
        Users user = new Users().setEmail(auth.getEmail())
                             .setPhone(auth.getPhone());
        return new Success(successMessage).setStatusCode(HttpStatus.CREATED);
      }
    }
    return new BadRequest(failureMessage).setStatusCode(HttpStatus.BAD_REQUEST);
  }

  public EntityWrapper<Users> getUserFromEmail(String email) {
    return EntityWrapper.makeWrapper(dao.getUserByEmail(email));
  }

  public EntityWrapper<Users> validateUserForLogin() {
    return EntityWrapper.makeWrapper(Optional.empty());
  }
}

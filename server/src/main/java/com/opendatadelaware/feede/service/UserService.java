package com.opendatadelaware.feede.service;

import com.opendatadelaware.feede.controller.responses.BadRequest;
import com.opendatadelaware.feede.controller.responses.Response;
import com.opendatadelaware.feede.controller.responses.Success;
import com.opendatadelaware.feede.controller.utils.RequestBodyMapper;
import com.opendatadelaware.feede.controller.utils.UserAuthValidator;
import com.opendatadelaware.feede.controller.utils.UserCredentials;
import com.opendatadelaware.feede.dao.UserDao;
import com.opendatadelaware.feede.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


/**
 * Created by aaronlong on 6/27/17.
 */
@Service
@Transactional
public class UserService extends AbstractService<UserDao> {

  private PasswordEncoder passwordEncoder;

  private static final String successMessage = "User was created";
  private static final String failureMessage = "User couldn't be created because of missing or invalid properties";

  public Response createUserFromRequest(RequestBodyMapper<UserAuthValidator> authSubmission) {
    if (authSubmission.doesExist() && authSubmission.get().isValid()) {
      UserAuthValidator auth = authSubmission.get();
      if (!dao.getUserByEmail(auth.getEmail()).isPresent()) {
        String encodedPassword = passwordEncoder.encode(auth.getPassword());
        Users user = new Users().setEmail(auth.getEmail())
                             .setPhone(auth.getPhone())
                             .setAddress(auth.getStreet())
                             .setCity(auth.getCity()).setState(auth.getState())
                             .setZip(auth.getZip()).setPassword(encodedPassword);
        dao.create(user);
        return new Success(successMessage).setStatusCode(HttpStatus.CREATED);
      }
    }
    return new BadRequest(failureMessage).setStatusCode(HttpStatus.BAD_REQUEST);
  }

  public EntityWrapper<Users> getUserFromEmail(String email) {
    return EntityWrapper.makeWrapper(dao.getUserByEmail(email));
  }

  public EntityWrapper<Users> validateUserForLogin(UserCredentials creds) {
    Optional<Users> user = dao.getUserByEmail(creds.getUsername());
    if(user.isPresent() && passwordEncoder.matches(creds.getPassword(), user.get().getPassword())) {
      return EntityWrapper.makeWrapper(user);
    }
    return EntityWrapper.makeWrapper(Optional.empty());
  }

  @Autowired
  public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }
}

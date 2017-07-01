package com.opendatadelaware.feede.dao;

import com.opendatadelaware.feede.model.Users;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Created by aaronlong on 6/27/17.
 */
@Repository
public class UsersDao extends AbstractDao<Users, UUID> {

  public UsersDao () {
    super(Users.class);
  }

}

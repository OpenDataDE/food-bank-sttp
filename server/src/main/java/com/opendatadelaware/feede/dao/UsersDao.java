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

  public void addUser(Users user) {
    getSession().save(user);
  }

  public Users getUserByUsername(String username) {
    return getSession().get(getType(), username);
  }

  public void updateUserByUsername(String username, Users user) {
    getSession().update(username, user);
  }

  public void deleteUserByUsername(String username, Users user) {
    getSession().delete(username, user);
  }

}

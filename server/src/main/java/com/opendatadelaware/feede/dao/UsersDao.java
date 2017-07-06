package com.opendatadelaware.feede.dao;

import com.opendatadelaware.feede.model.Users;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
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

  public Optional<Users> getUserByEmail(String email) {
    Users result = (Users) getSession().createQuery("FROM Users WHERE email = :email").uniqueResult();
    return result != null ? Optional.of(result) : Optional.empty();
  }

  public void updateUserByEmail(String email, Users user) {
    getSession().update(email, user);
  }

  public void deleteUserByEmail(String email, Users user) {
    getSession().delete(email, user);
  }

}

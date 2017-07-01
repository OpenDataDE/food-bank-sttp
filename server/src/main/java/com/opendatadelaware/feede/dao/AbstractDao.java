package com.opendatadelaware.feede.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * Created by aaronlong on 6/27/17.
 */
public abstract class AbstractDao<E extends Serializable, PK extends Serializable> {

  protected SessionFactory session;

  private Class<E> type;

  public AbstractDao (Class<E> daoType) {
    type = daoType;
  }

  @Autowired
  protected void setSession (SessionFactory sessionFactory) {
    session = sessionFactory;
  }

  protected Session getSession () {
    return session.getCurrentSession();
  }

  public E get (PK id) {
    return session.getCurrentSession().get(type, id);
  }
}

package com.opendatadelaware.feede.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TemporalType;
import javax.persistence.Temporal;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Created by aaronlong on 6/22/17.
 */
@Entity
@Table(name = "ORDERS")
public class Orders implements Serializable {
  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "uuid", columnDefinition = "BINARY(16)", updatable = false, nullable = false)
  private UUID uuid;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "date_time", nullable = false, updatable=false)
  private Date dateTime;

  @ManyToOne(fetch= FetchType.EAGER)
  @JoinColumn(name = "order_number", referencedColumnName = "uuid")
  private Users user;

  public UUID getUUID() { return uuid; }

  public Orders setUUID(UUID uuid) {
    this.uuid = uuid;
    return this;
  }

  public Date getDateTime() {
    return dateTime;
  }

  public Orders setDateTime(Date dateTime) {
    this.dateTime = dateTime;
    return this;
  }

  public Users getUser() {
    return user;
  }

  public Orders setUser(Users user) {
    this.user = user;
    return this;
  }
}

package com.opendatadelaware.feede.controller.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * Created by aaronlong on 7/2/17.
 */
@ResponseBody
public class Success implements Response {
  @JsonProperty("status")
  private String status;
  @JsonProperty("time")
  private final Long time;

  public Success() {
    time = new Date().getTime();
    status = "Request was processed";
  }

  public Success(String statusMessage) {
    this();
    status = statusMessage;
  }

  public ResponseEntity<? extends Response> makeResponse(HttpStatus httpCode) {
    return new ResponseEntity<>(this, httpCode);
  }
}

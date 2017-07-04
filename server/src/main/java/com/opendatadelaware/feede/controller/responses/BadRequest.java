package com.opendatadelaware.feede.controller.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by aaronlong on 7/3/17.
 */
@ResponseBody
public class BadRequest implements Response {
  @JsonProperty("status")
  private String error;

  public BadRequest() {
    error = "Your request couldn't be processed as is. Consult the documentation";
  }

  public BadRequest(String message) {
    error = message;
  }

  public ResponseEntity<? extends Response> makeResponse(HttpStatus httpCode) {
    return new ResponseEntity<>(this, httpCode);
  }

}

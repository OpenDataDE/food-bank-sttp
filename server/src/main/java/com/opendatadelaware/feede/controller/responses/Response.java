package com.opendatadelaware.feede.controller.responses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

/**
 * Created by aaronlong on 7/3/17.
 */
public interface Response extends Serializable {
  ResponseEntity<? extends Response> makeResponse(HttpStatus httpCode);
}

package com.opendatadelaware.feede.controller;

import com.opendatadelaware.feede.dao.ItemsDao;
import com.opendatadelaware.feede.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by jarrydstamatelos on 6/30/17.
 */
@RestController
@RequestMapping("/items/{uuid}")
public class ItemsController {

    private ItemsService service;


    @Autowired
    public void setItemsService(ItemsService aService) {
        service = aService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getItems() {
        return new ResponseEntity<Object>("Forbidden Action", HttpStatus.FORBIDDEN);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> addItems(@RequestBody Map<String, String> s) {
        return new ResponseEntity<Object>("Forbidden Action", HttpStatus.FORBIDDEN);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<?> updateItems(@RequestBody Map<String, String> s) {
        return new ResponseEntity<Object>("Forbidden Action", HttpStatus.FORBIDDEN);
    }

    // Only request allowed by the item controller - delete individual item by uuid
    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteItemByUUID(@PathVariable Long uuid) {

        return new ResponseEntity<Object>(HttpStatus.OK);
    }

}

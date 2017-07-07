package com.opendatadelaware.feede.controller;

import com.opendatadelaware.feede.controller.responses.Success;
import com.opendatadelaware.feede.dao.ItemsDao;
import com.opendatadelaware.feede.model.Items;
import com.opendatadelaware.feede.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;
import java.util.UUID;

/**
 * Created by jarrydstamatelos on 6/30/17.
 */
@RestController
@RequestMapping("/api/items")
public class ItemsController {

    private ItemsService service;
    private ItemsDao itemsDao;

    @Autowired
    public void setItemsService(ItemsService aService) {
        service = aService;
        service.setDao(itemsDao);
    }

    @RequestMapping(value = "/{uuid}/", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteItemByUUID(@PathVariable UUID uuid) {
        itemsDao.deleteByUUID(uuid);
        return new Success().makeResponse(HttpStatus.OK);
    }

    @RequestMapping(value = "/{uuid}/", method = RequestMethod.GET)
    public ResponseEntity<Items> getItemsByUUID(@PathVariable UUID uuid) {
        return new ResponseEntity<>(itemsDao.read(uuid), HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> postItems(@RequestBody Items items) {
        itemsDao.create(items);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newItemsURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{uuid}")
                .buildAndExpand(items.getUUID())
                .toUri();
        responseHeaders.setLocation(newItemsURI);
        return new Success().makeResponse(HttpStatus.CREATED);
    }

}

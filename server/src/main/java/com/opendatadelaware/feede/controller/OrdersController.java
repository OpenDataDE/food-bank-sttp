package com.opendatadelaware.feede.controller;

import com.opendatadelaware.feede.controller.responses.Response;
import com.opendatadelaware.feede.controller.responses.Success;
import com.opendatadelaware.feede.dao.OrdersDao;
import com.opendatadelaware.feede.model.Orders;
import com.opendatadelaware.feede.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

/**
 * Created by markbrown on 6/29/17.
 */

@RequestMapping("/api/orders")
@RestController
public class OrdersController {

    private OrdersDao dao;
    private OrdersService service;

    @Autowired
    public void setOrderService(OrdersService orderService) {
        service = orderService;
        service.setDao(dao);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<Orders> postOrder(@RequestBody Orders order) {
        dao.create(order);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newOrderURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{uuid}")
                .buildAndExpand(order.getUUID())
                .toUri();
        responseHeaders.setLocation(newOrderURI);
        return new ResponseEntity<Orders>(responseHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{uuid}/", method = RequestMethod.GET)
    public ResponseEntity<?> getOrderByID(@PathVariable UUID uuid) {
        return new ResponseEntity<>(dao.getById(uuid), HttpStatus.OK);
    }

    @RequestMapping(value = "/{uuid}/", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteOrderById(@PathVariable UUID uuid) {
        dao.deleteById(uuid);
        return new Success().makeResponse(HttpStatus.OK);
    }






}

package com.opendatadelaware.feede.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opendatadelaware.feede.controller.responses.Response;
import com.opendatadelaware.feede.dao.OrdersDao;
import com.opendatadelaware.feede.model.Users;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.opendatadelaware.feede.model.Orders;
import com.opendatadelaware.feede.controller.responses.Success;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.util.Date;


import java.util.UUID;

/**
 * Created by markbrown on 7/5/17.
 */
@RunWith(SpringRunner.class)
public class TestOrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestUserController.class);

    private MockMvc mvc;
    private Users user;
    private Orders order;

    @Mock
    OrdersDao dao;

    @InjectMocks
    OrdersController ordersController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(ordersController).build();
        user = new Users();
        user.setUsername("markb");
        order = new Orders().setUUID(UUID.randomUUID()).setDateTime(Date.from(Instant.now())).setUser(user);

    }

    @Test
    public void testGetByIDMethodShouldPass() throws Exception {
        UUID uuid = order.getUUID();
        when(dao.getById(order.getUUID())).thenReturn(order);
        mvc.perform(get("/api/orders/{uuid}/", uuid).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid").exists())
                .andExpect(jsonPath("$.uuid").value(order.getUUID().toString()))
                .andExpect(jsonPath("$.user.username").exists())
                .andExpect(jsonPath("$.user.username").value("markb"));
    }

    @Test
    public void testGetByIDMethodShouldFailDueToWrongEndpoint() throws Exception {
        UUID uuid = order.getUUID();
        when(dao.getById(order.getUUID())).thenReturn(order);
        mvc.perform(get("/api/orders/", uuid).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testGetByIDMethodShouldFailDueToWrongUUID() throws Exception {
        UUID random = UUID.randomUUID();
        when(dao.read(order.getUUID())).thenReturn(order);
        mvc.perform(get("/api/orders/{random}/", random).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.uuid").doesNotExist())
                .andExpect(jsonPath("$.user.username").doesNotExist());
    }

    @Test
    public void testSuccessfulPost() throws Exception {
        UUID uuid = order.getUUID();
        String orderAsJsonString;
        try {
            orderAsJsonString = new ObjectMapper().writeValueAsString(order);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        when(dao.create(order)).thenReturn(order.getUUID());
        mvc.perform(post("/api/orders/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderAsJsonString))
                .andExpect(status().isCreated());
    }

    @Test
    public void testPostWithIncorrectEndpoint() throws Exception {
        Users user = new Users();
        Orders order = new Orders().setUUID(UUID.randomUUID()).setDateTime(Date.from(Instant.now())).setUser(user);
        String orderAsJsonString;
        try {
            orderAsJsonString = new ObjectMapper().writeValueAsString(order);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        when(dao.create(order)).thenReturn(order.getUUID());
        mvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderAsJsonString))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testDeleteById() throws Exception {
        Orders order = new Orders();
        UUID uuid = UUID.randomUUID();
        when(dao.read(uuid)).thenReturn(order);
        mvc.perform(delete("/api/orders/{uuid}/", uuid))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteByIdFailsBecauseOfIncorrectEndpoint() throws Exception {
        Orders order = new Orders();
        UUID uuid = UUID.randomUUID();
        when(dao.read(uuid)).thenReturn(order);
        mvc.perform(delete("/api/orders/", uuid))
                .andExpect(status().is4xxClientError());
    }





}

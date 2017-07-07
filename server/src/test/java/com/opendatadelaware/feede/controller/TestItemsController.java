package com.opendatadelaware.feede.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opendatadelaware.feede.dao.ItemsDao;
import com.opendatadelaware.feede.model.Items;
import com.opendatadelaware.feede.service.ItemsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

/**
 * Created by jarrydstamatelos on 6/30/17.
 */

@RunWith(SpringRunner.class)
public class TestItemsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestItemsController.class);

    private MockMvc mvc;
    private Items items;
    private UUID uuid;

    @MockBean
    private ItemsDao itemsDao;

    @SpyBean
    private ItemsService itemsService;

    @InjectMocks
    private ItemsController controller;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
        uuid = UUID.randomUUID();
        items = new Items().setUUID(uuid).setQuantity(10.00)
                            .setDescription("Good Food").setCategory("Food");
    }

    private String objectToString(List<Items> p) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(p);
        } catch (JsonProcessingException e) {
            System.out.println(e);
            return null;
        }
    }

    @Test
    public void testGetByUUID() throws Exception {
        when(itemsDao.read(uuid)).thenReturn(items);
        mvc.perform(get("/api/items/{uuid}/", uuid))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.uuid").value(uuid.toString()))
                .andExpect(jsonPath("$.quantity").value(10.00))
                .andExpect(jsonPath("$.description").value("Good Food"))
                .andExpect(jsonPath("$.category").value("Food"));
    }

    @Test
    public void testUUIDDoesNotExist() throws Exception {
        UUID random = UUID.randomUUID();
        when(itemsDao.read(items.getUUID())).thenReturn(items);
        mvc.perform(get("/api/items/{random}/", random)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.uuid").doesNotExist())
                .andExpect(jsonPath("$.user.username").doesNotExist());
    }

    @Test
    public void testDeleteByUUID() throws Exception {
        when(itemsDao.deleteByUUID(uuid)).thenReturn(items);
        mvc.perform(delete("/api/items/{uuid}/", uuid)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    public void testSuccessfulPost() throws Exception {
        String itemsAsJsonString;
        try {
            itemsAsJsonString = new ObjectMapper().writeValueAsString(items);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        when(itemsDao.create(items)).thenReturn(uuid);
        mvc.perform(post("/api/items/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(itemsAsJsonString))
                .andExpect(status().isCreated());
    }

    @Test
    public void testFailureDueToIncorrectEndPoint() throws Exception {
        when(itemsDao.read(items.getUUID())).thenReturn(items);
        mvc.perform(get("/api/items/", uuid)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }


}

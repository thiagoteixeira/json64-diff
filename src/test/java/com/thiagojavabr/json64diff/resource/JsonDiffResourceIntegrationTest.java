package com.thiagojavabr.json64diff.resource;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

import com.thiagojavabr.json64diff.domain.JsonData;
import com.thiagojavabr.json64diff.repository.JsonDataRepository;
import com.thiagojavabr.json64diff.util.Result;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

/**
 * Integration tests for {@link JsonDiffResource} features.
 *
 * @author Thiago A. Teixeira
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class JsonDiffResourceIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext applicationContext;

    @Autowired
    private JsonDataRepository repository;

    @Before
    public void setup() {
        this.mvc = webAppContextSetup(applicationContext).build();
        this.repository.deleteAll();
    }

    @Test
    public void testCreateJsonDataWithLeft() throws Exception {
        var expectedId = 1L;
        var expectedBinaryLeft = "ewogICJuYW1lIjoiVGhpYWdvIFRlaXhlaXJhIgp9";
        var expectedJsonContent = String.format("{\"id\":%s,\"left\":\"%s\",\"right\":null}", expectedId, expectedBinaryLeft);

        MvcResult result = mvc.perform(post("/v1/diff/{id}/left".replace("{id}", String.valueOf(expectedId)))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(expectedBinaryLeft))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(expectedJsonContent))
                .andReturn();

        Optional<JsonData> jsonDataOpt = this.repository.findById(expectedId);
        assertThat(jsonDataOpt).isPresent();
        assertThat(jsonDataOpt.get().getId()).isEqualTo(expectedId);
        assertThat(jsonDataOpt.get().getLeft()).isEqualTo(expectedBinaryLeft);
        assertThat(jsonDataOpt.get().getRight()).isNull();
    }

    @Test
    public void testCreateJsonDataWithRight() throws Exception {
        var expectedId = 2L;
        var expectedBinaryRight = "ewogICJuYW1lIjoiVGhpYWdvIFRlaXhlaXJhIgp9";
        var expectedJsonContent = String.format("{\"id\":%s,\"left\":null,\"right\":\"%s\"}", expectedId, expectedBinaryRight);

        MvcResult result = mvc.perform(post("/v1/diff/{id}/right".replace("{id}", String.valueOf(expectedId)))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(expectedBinaryRight))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(expectedJsonContent))
                .andReturn();

        Optional<JsonData> jsonDataOpt = this.repository.findById(expectedId);
        assertThat(jsonDataOpt).isPresent();
        assertThat(jsonDataOpt.get().getId()).isEqualTo(expectedId);
        assertThat(jsonDataOpt.get().getLeft()).isNull();
        assertThat(jsonDataOpt.get().getRight()).isEqualTo(expectedBinaryRight);
    }

    @Test
    public void testGetDiffById() throws Exception {
        var expectedId = 3L;
        var expectedBinaryLeft = "ewogICJuYW1lIjoiVGhpYWdvIFRlaXhlaXJhIgp9";
        var expectedBinaryRight = "ewogICJuYW1lIjoiVGhpYWdvIFRlaXhlaXJhIgp9";
        var expectedJsonContent = String.format("{\"message\":\"%s\"}", Result.Type.EQUAL_CONTENT);

        mvc.perform(post("/v1/diff/{id}/left".replace("{id}", String.valueOf(expectedId)))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(expectedBinaryLeft));

        mvc.perform(post("/v1/diff/{id}/right".replace("{id}", String.valueOf(expectedId)))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expectedBinaryRight));

        MvcResult result = mvc.perform(get("/v1/diff/{id}".replace("{id}", String.valueOf(expectedId)))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(expectedJsonContent))
                .andReturn();
    }

    @Test
    public void testGetDiffByIdNotFound() throws Exception {
        var expectedId = 4L;

        MvcResult result = mvc.perform(get("/v1/diff/{id}".replace("{id}", String.valueOf(expectedId)))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""))
                .andReturn();

        Optional<JsonData> jsonDataOpt = this.repository.findById(expectedId);
        assertThat(jsonDataOpt).isNotPresent();
    }
}

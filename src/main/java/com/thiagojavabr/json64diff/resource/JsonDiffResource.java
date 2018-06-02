package com.thiagojavabr.json64diff.resource;

import com.thiagojavabr.json64diff.domain.JsonData;
import com.thiagojavabr.json64diff.enums.JsonSide;
import com.thiagojavabr.json64diff.repository.JsonDataRepository;
import com.thiagojavabr.json64diff.service.JsonDiffService;
import com.thiagojavabr.json64diff.util.Issue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Rest controller to create sides of a {@link JsonData} and also to compare their respective sides.
 *
 * @author Thiago A. Teixeira
 */
@RestController
@RequestMapping("/v1/diff/{id}")
public class JsonDiffResource {


    @Autowired
    private JsonDiffService service;

    @Autowired
    private JsonDataRepository repository;


    /**
     * Endpoint to create the left side of a {@link JsonData}
     *
     * @param id The object identifier
     * @param json The JSON binary data
     * @return A JSON representation from the {@link JsonData} object created
     */
    @PostMapping(value = "/left", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JsonData> createLeft(@PathVariable Long id, @RequestBody String json){
        var jsonData = service.save(id, json, JsonSide.LEFT);
        return ResponseEntity.status(HttpStatus.CREATED).body(jsonData);
    }

    /**
     * Endpoint to create the right side of a {@link JsonData}
     *
     * @param id The object identifier
     * @param json The JSON binary data
     * @return A JSON representation from the {@link JsonData} object created
     */
    @PostMapping(value = "/right", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JsonData> createRight(@PathVariable Long id, @RequestBody String json){
        var jsonData = service.save(id, json, JsonSide.RIGHT);
        return ResponseEntity.status(HttpStatus.CREATED).body(jsonData);
    }

    /**
     * Endpoint that performs a binary comparison of the sides of a {@link JsonData } existent object.
     * @param id The object identifier
     * @return A {@link Issue} object represantation
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Issue> getDiffById(@PathVariable Long id){
        Optional<JsonData> jsonDataOpt = repository.findById(id);
        if(jsonDataOpt.isPresent()){
            var validationMessage = service.validate(jsonDataOpt.get());
            return ResponseEntity.ok(validationMessage);
        }
        return ResponseEntity.notFound().build();
    }
}

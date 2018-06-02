package com.thiagojavabr.json64diff.service;

import com.thiagojavabr.json64diff.domain.JsonData;
import com.thiagojavabr.json64diff.enums.JsonSide;
import com.thiagojavabr.json64diff.repository.JsonDataRepository;
import com.thiagojavabr.json64diff.util.Result;
import jdk.internal.joptsimple.internal.Strings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service that provides the persistence and validation of the sides of a {@link JsonData} object instance.
 *
 * @author Thiago A. Teixeira
 */
@Service
public class JsonDiffService {

    private static final Logger log = LogManager.getLogger(JsonDiffService.class);

    @Autowired
    private JsonDataRepository repository;

    /**
     * Create or update a {@link JsonData} persistent object in the database
     *
     * @param id The object identifier
     * @param json The JSON binary data
     * @param side The side of a {@link JsonData} object represented by {@link JsonSide}
     * @return The {@link JsonData} object created or updated.
     */
    public JsonData save(Long id, String json, JsonSide side) {
        log.info("Saving id={} data={} side={}", id, json, side);
        Optional<JsonData> jsonDataOpt = repository.findById(id);
        JsonData jsonData = jsonDataOpt.isPresent() ? jsonDataOpt.get() : new JsonData(id);
        if (JsonSide.LEFT.equals(side)) {
            jsonData.setLeft(json);
        } else {
            jsonData.setRight(json);
        }
        return repository.save(jsonData);
    }

    /**
     * Encapsulates the call to {@link JsonDiffService#validateImpl(JsonData)}
     *
     * @see #validateImpl(JsonData)
     */
    public Result validate(JsonData jsonData) {
        log.info("Validating the sides of {}", jsonData);
        Result result = validateImpl(jsonData);
        log.info("Validating result: {}", result);
        return result;
    }

    /**
     * Compares the bytes of the sides of a {@link JsonData} object instance.
     * @param jsonData The {@link JsonData} object instance that will have its sides compared.
     * @return An {@link Result} object instance with a message regarding the result of the comparison. If sides are equal
     *         then {@link Result.Type#EQUAL_CONTENT}, otherwise check if the sizes are not equal and then the message is
     *         {@link Result.Type#NOT_EQUAL_SIZE}, already if they have the same size but different content then the
     *         message will be represented by {@link Result.Type#EQUAL_SIZE_DIFFERENT_CONTENT} with the offsets.
     */
    private Result validateImpl(JsonData jsonData) {
        var left = jsonData.getLeft();
        var right = jsonData.getRight();

        byte[] leftBytes = left != null ? left.getBytes() : Strings.EMPTY.getBytes();
        byte[] rightBytes = right != null ? right.getBytes() : Strings.EMPTY.getBytes();

        boolean result = Arrays.equals(leftBytes, rightBytes);

        if(result){
            return new Result(Result.Type.EQUAL_CONTENT);
        }

        if(leftBytes.length != rightBytes.length){
            return new Result(Result.Type.NOT_EQUAL_SIZE);
        } else {
            List<String> offsets = new ArrayList<>();
            var tmp = 0;
            for (int index = 0; index < leftBytes.length; index++) {
                tmp = leftBytes[index] ^ rightBytes[index];
                if(tmp != 0){
                    offsets.add(String.valueOf(index));
                }
            }
            var indexes = String.join(", ", offsets);
            return new Result(Result.Type.EQUAL_SIZE_DIFFERENT_CONTENT, indexes);
        }
    }
}

package com.thiagojavabr.json64diff.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thiagojavabr.json64diff.domain.JsonData;

import java.util.Objects;

/**
 * Utility used to represent the result of the {@link com.thiagojavabr.json64diff.service.JsonDiffService#validate(JsonData)}
 *
 * @author Thiago A. Teixeira
 */
public class Result {

    /**
     * Enumerator used to represent JSON content validation messages.
     */
    public enum Type {
        EQUAL_CONTENT("The JSON contents are equal!"),
        NOT_EQUAL_SIZE("The JSON contents have not the same size!"),
        EQUAL_SIZE_DIFFERENT_CONTENT("The JSON contents have the same size, but offsets are different: %s")
        ;

        private String description;

        Type(String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return description;
        }
    }

    @JsonIgnore
    private Type type;

    private String message;

    public Result(Type type, Object...params) {
        this.type = type;
        message = String.format(type.description, params);
    }

    public Type getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result that = (Result) o;
        return Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
    }

    @Override
    public String toString() {
        return "Result{" +
                "message='" + message + '\'' +
                '}';
    }
}

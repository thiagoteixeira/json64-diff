package com.thiagojavabr.json64diff.enums;

/**
 * Enumerator to represent left and right sides
 *
 * @author Thiago A. Teixeira
 */
public enum JsonSide {
    LEFT("left"), RIGHT("right");

    private String description;

    private JsonSide(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}

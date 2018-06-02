package com.thiagojavabr.json64diff.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.util.Objects;

/**
 * Wrapper entity representing an identifier and two binary jsons(left and right)
 *
 * @author Thiago A. Teixeira
 */
@Entity
public class JsonData {
    @Id
    private Long id;

    /**
     * Binary JSON data
     */
    @Lob
    private String left;

    /**
     * Binary JSON data
     */
    @Lob
    private String right;


    public JsonData() {
    }

    public JsonData(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JsonData jsonData = (JsonData) o;
        return Objects.equals(id, jsonData.id) &&
                Objects.equals(left, jsonData.left) &&
                Objects.equals(right, jsonData.right);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, left, right);
    }

    @Override
    public String toString() {
        return "JsonData{" +
                "id=" + id +
                ", left='" + left + '\'' +
                ", right='" + right + '\'' +
                '}';
    }
}

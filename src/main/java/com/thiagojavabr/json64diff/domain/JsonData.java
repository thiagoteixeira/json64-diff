package com.thiagojavabr.json64diff.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

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
}

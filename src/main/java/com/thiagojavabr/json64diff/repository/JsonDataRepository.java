package com.thiagojavabr.json64diff.repository;

import com.thiagojavabr.json64diff.domain.JsonData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository for {@link JsonData}
 *
 * @author Thiago A. Teixeira
 */
@Repository
public interface JsonDataRepository extends JpaRepository<JsonData, Long> {
}

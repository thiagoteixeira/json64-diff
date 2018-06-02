package com.thiagojavabr.json64diff.repository;

import com.thiagojavabr.json64diff.domain.JsonData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

/**
 * Unit test for {@link JsonDataRepository}
 *
 * @author Thiago A. Teixeira
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class JsonDataRepositoryTest {
    @Autowired
    private JsonDataRepository jsonDataRepository;

    @Test
    public void testFindById(){
        var expectedId = 1L;
        JsonData jsonToCreate = new JsonData(expectedId);
        jsonDataRepository.save(jsonToCreate);

        Optional<JsonData> jsonDataOpt = jsonDataRepository.findById(expectedId);
        assertThat(jsonDataOpt).isPresent();
        assertThat(jsonDataOpt.get().getId()).isEqualTo(expectedId);
    }
}

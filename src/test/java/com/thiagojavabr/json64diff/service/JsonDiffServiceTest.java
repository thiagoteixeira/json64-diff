package com.thiagojavabr.json64diff.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;

import com.thiagojavabr.json64diff.domain.JsonData;
import com.thiagojavabr.json64diff.enums.JsonSide;
import com.thiagojavabr.json64diff.repository.JsonDataRepository;
import com.thiagojavabr.json64diff.util.Result;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

/**
 * Unit tests for {@link JsonDiffService}
 *
 * @author Thiago A. Teixeira
 */
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class JsonDiffServiceTest {

    @InjectMocks
    private JsonDiffService service;

    @Mock
    private JsonDataRepository repository;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveLeftForNewJsonData(){
        var expectedId = 1L;
        var expectedLeft = "LEFT";

        Mockito.doReturn(Optional.empty()).when(repository).findById(Mockito.eq(expectedId));
        Mockito.doAnswer(returnsFirstArg()).when(repository).save(Mockito.any(JsonData.class));

        JsonData json = service.save(expectedId, expectedLeft, JsonSide.LEFT);

        assertThat(json).isNotNull();
        assertThat(json.getId()).isEqualTo(expectedId);
        assertThat(json.getLeft()).isEqualTo(expectedLeft);
        assertThat(json.getRight()).isNull();
    }

    @Test
    public void testSaveRightForNewJsonData(){
        var expectedId = 1L;
        var expectedRight = "RIGHT";

        Mockito.doReturn(Optional.empty()).when(repository).findById(Mockito.eq(expectedId));
        Mockito.doAnswer(returnsFirstArg()).when(repository).save(Mockito.any(JsonData.class));

        JsonData json = service.save(expectedId, expectedRight, JsonSide.RIGHT);

        assertThat(json).isNotNull();
        assertThat(json.getId()).isEqualTo(expectedId);
        assertThat(json.getLeft()).isNull();
        assertThat(json.getRight()).isEqualTo(expectedRight);
    }


    @Test
    public void testSaveLeftFoundForExistentJsonData(){
        var expectedId = 1L;
        var expectedLeft = "LEFT";

        JsonData jsonData = new JsonData();
        jsonData.setId(expectedId);
        jsonData.setRight("ORIGINAL_RIGHT");

        Mockito.doReturn(Optional.of(jsonData)).when(repository).findById(Mockito.eq(expectedId));
        Mockito.doAnswer(returnsFirstArg()).when(repository).save(Mockito.any(JsonData.class));

        JsonData json = service.save(expectedId, expectedLeft, JsonSide.LEFT);

        assertThat(json).isNotNull();
        assertThat(json.getId()).isEqualTo(expectedId);
        assertThat(json.getLeft()).isEqualTo(expectedLeft);
        assertThat(json.getRight()).isEqualTo("ORIGINAL_RIGHT");
    }

    @Test
    public void testSaveRightFoundInRepository(){
        var expectedId = 1L;
        var expectedRight = "RIGHT";

        JsonData jsonData = new JsonData();
        jsonData.setId(expectedId);
        jsonData.setLeft("ORIGINAL_LEFT");

        Mockito.doReturn(Optional.of(jsonData)).when(repository).findById(Mockito.eq(expectedId));
        Mockito.doAnswer(returnsFirstArg()).when(repository).save(Mockito.any(JsonData.class));

        JsonData json = service.save(expectedId, expectedRight, JsonSide.RIGHT);

        assertThat(json).isNotNull();
        assertThat(json.getId()).isEqualTo(expectedId);
        assertThat(json.getLeft()).isEqualTo("ORIGINAL_LEFT");
        assertThat(json.getRight()).isEqualTo(expectedRight);
    }

    @Test
    public void testValidateWithoutRight(){
        JsonData jsonData = new JsonData();
        jsonData.setId(1L);
        jsonData.setLeft("LEFT");

        var issue = service.validate(jsonData);
        assertThat(issue.getType()).isEqualTo(Result.Type.NOT_EQUAL_SIZE);
    }

    @Test
    public void testValidateWithoutLeft(){
        JsonData jsonData = new JsonData();
        jsonData.setId(1L);
        jsonData.setRight("RIGHT");

        var issue = service.validate(jsonData);
        assertThat(issue.getType()).isEqualTo(Result.Type.NOT_EQUAL_SIZE);
    }

    @Test
    public void testValidateDifferentSize(){
        JsonData jsonData = new JsonData();
        jsonData.setId(1L);
        jsonData.setLeft("XPTO");
        jsonData.setRight("RIGHT");

        var issue = service.validate(jsonData);
        assertThat(issue.getType()).isEqualTo(Result.Type.NOT_EQUAL_SIZE);
    }

    @Test
    public void testValidateEqualContent(){
        JsonData jsonData = new JsonData();
        jsonData.setId(1L);
        jsonData.setLeft("XPTO");
        jsonData.setRight("XPTO");

        var issue = service.validate(jsonData);
        assertThat(issue.getType()).isEqualTo(Result.Type.EQUAL_CONTENT);
    }

    @Test
    public void testValidateSameSizeButDifferentContent(){
        var expectedDifferentIndex = 3;

        JsonData jsonData = new JsonData();
        jsonData.setId(1L);
        jsonData.setLeft("XPTO");
        jsonData.setRight("XPTI");

        var issue = service.validate(jsonData);
        assertThat(issue).isEqualTo(new Result(Result.Type.EQUAL_SIZE_DIFFERENT_CONTENT, expectedDifferentIndex));
    }
}

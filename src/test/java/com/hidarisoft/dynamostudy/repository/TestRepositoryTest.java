package com.hidarisoft.dynamostudy.repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.hidarisoft.dynamostudy.config.DynamoDBConfigTest;
import com.hidarisoft.dynamostudy.entity.TestModel;
import com.hidarisoft.dynamostudy.exception.TestException;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestRepositoryTest extends DynamoDBConfigTest {

    private final AmazonDynamoDB amazonDynamoDB = DynamoDBEmbedded.create().amazonDynamoDB();
    private final DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

    private final TestRepository testRepository = new TestRepository(dynamoDBMapper);

    {
        createTestTableLocally();
    }

    private void createTestTableLocally() {
        CreateTableRequest createTableRequest = dynamoDBMapper.generateCreateTableRequest(TestModel.class);
        createTableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
        amazonDynamoDB.createTable(createTableRequest);
    }

    @Test
    @Order(1)
    @DisplayName("Save Test Model")
    void testShouldReturnSuccessWhenSaveTestModel() {
        testRepository.saveTest(buildTestModel());
    }

    @Test()
    @Order(2)
    @DisplayName("Return Exception when save same pk and sk")
    void testShouldReturnExceptionWhenSaveSameTestModel() {
        try {
            testRepository.saveTest(buildTestModel());
        } catch (Exception ex) {
            assertTrue(ex instanceof TestException);
        }
    }

    @Test
    @Order(3)
    @DisplayName("Return Test Model When Find Result")
    void testShouldReturnTestModelWhenFindIsCalled() {
        assertEquals(buildTestModel().getPk(), testRepository.findByPKAndSK("12345678909", "SK").map(TestModel::getPk).orElse(null));
    }

    @Test
    @Order(4)
    @DisplayName("Change Created By")
    void testShouldReturnSuccessWhenChangeCreated() {
        assertEquals("MAX", testRepository.changeCreatedBy("12345678909", "SK", "MAX").map(TestModel::getCreatedBy).orElse(null));
    }

    @Test
    @Order(5)
    @DisplayName("Return Exception When PK not exists")
    void testShouldReturnExceptionWhenChangeCreated() {
        try {
            testRepository.changeCreatedBy("1", "SK", "MAX");
        }catch (Exception ex){
            assertTrue(ex instanceof TestException);
        }
    }

    private TestModel buildTestModel() {
        return TestModel.builder()
                .pk("12345678909")
                .sk("SK")
                .createdBy("MX")
                .createdAt(LocalDateTime.of(2022, 2, 8, 12, 58)).build();
    }
}
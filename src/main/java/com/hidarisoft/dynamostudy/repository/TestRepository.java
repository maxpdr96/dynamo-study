package com.hidarisoft.dynamostudy.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ConditionalOperator;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.amazonaws.util.ImmutableMapParameter;
import com.hidarisoft.dynamostudy.entity.TestModel;
import com.hidarisoft.dynamostudy.exception.TestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TestRepository {
    private final DynamoDBMapper dynamoDBMapper;
    private static final String PRIMARY_KEY = ":primaryKey";
    private static final String SORT_KEY = ":sortKey";
    private static final String KEY_CONDITION = "pk = " + PRIMARY_KEY + " AND sk = " + SORT_KEY;

    public void saveTest(TestModel testModel) {
        DynamoDBSaveExpression dynamoDBSaveExpression = new DynamoDBSaveExpression();
        Map<String, ExpectedAttributeValue> expectedAttributeValueMap =
                ImmutableMapParameter.<String, ExpectedAttributeValue>builder()
                        .put("pk", new ExpectedAttributeValue(false))
                        .put("sk", new ExpectedAttributeValue(false))
                        .build();

        dynamoDBSaveExpression.setExpected(expectedAttributeValueMap);
        dynamoDBSaveExpression.setConditionalOperator(ConditionalOperator.AND);

        try {
            log.info("Save Test: {}", testModel);
            dynamoDBMapper.save(testModel);
        } catch (ConditionalCheckFailedException ex) {
            log.error("PK with SK already exists!");
            throw new TestException("PK with SK already exists!");
        }
    }

    public Optional<TestModel> findByPKAndSK(String pk, String sk) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(PRIMARY_KEY, new AttributeValue().withS(pk));
        eav.put(SORT_KEY, new AttributeValue().withS(sk));
        DynamoDBQueryExpression<TestModel> dynamoDBQueryExpression = new DynamoDBQueryExpression<TestModel>()
                .withKeyConditionExpression(KEY_CONDITION)
                .withExpressionAttributeValues(eav);

        return dynamoDBMapper.query(TestModel.class, dynamoDBQueryExpression).stream().findAny();
    }

    public Optional<TestModel> changeCreatedBy(String pk, String sk, String createdBy) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(PRIMARY_KEY, new AttributeValue().withS(pk));
        eav.put(SORT_KEY, new AttributeValue().withS(sk));
        DynamoDBQueryExpression<TestModel> dynamoDBQueryExpression = new DynamoDBQueryExpression<TestModel>()
                .withKeyConditionExpression(KEY_CONDITION)
                .withExpressionAttributeValues(eav);

        var updateItem = dynamoDBMapper.query(TestModel.class, dynamoDBQueryExpression).stream().findAny();
        updateItem.ifPresentOrElse((TestModel testModel) -> {
            testModel.setCreatedBy(createdBy);
            dynamoDBMapper.save(testModel);
        }, () -> {
            throw new TestException("Nao existe PK");
        });
        return updateItem;
    }
}

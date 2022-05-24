package com.hidarisoft.dynamostudy.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.hidarisoft.dynamostudy.utils.LocalDateTimeToStringTypeConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
@DynamoDBTable(tableName = "TestTable")
public class TestModel {

    @DynamoDBHashKey(attributeName = "pk")
    private String pk;
    @DynamoDBRangeKey(attributeName = "sk")
    private String sk;
    @DynamoDBTypeConverted(converter = LocalDateTimeToStringTypeConverter.class)
    @DynamoDBAttribute(attributeName = "createdAt")
    private LocalDateTime createdAt;
    @DynamoDBAttribute(attributeName = "createdBy")
    private String createdBy;

}

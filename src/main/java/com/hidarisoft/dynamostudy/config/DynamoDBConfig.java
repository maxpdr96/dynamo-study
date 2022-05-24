package com.hidarisoft.dynamostudy.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamoDBConfig {

    @Value("${amazon.access.key}")
    private String awsAccessKey;
    @Value("${amazon.secret.key}")
    private String awsSecretKey;
    @Value("${amazon.region}")
    private String awsRegion;
    @Value("${amazon.endpoint}")
    private String awsDynamoDBEndPoint;

    @Bean
    public DynamoDBMapper dynamoDBMapper(){
        return new DynamoDBMapper(amazonDynamoDBConfig());
    }

    @Bean
    public AmazonDynamoDB amazonDynamoDBConfig() {
        return AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(this.awsDynamoDBEndPoint,this.awsRegion))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(this.awsAccessKey, this.awsSecretKey)))
                .build();
    }

}

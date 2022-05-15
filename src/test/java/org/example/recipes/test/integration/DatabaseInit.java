package org.example.recipes.test.integration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import org.example.recipes.model.Recipe;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:application-test.properties")
public class DatabaseInit {

    @Value("${aws.region}")
    private String region;

    @Value("${aws.dynamodb.endpoint}")
    private String dynamoDBEndpoint;

    @Test
    public void init() {
        AmazonDynamoDB dynamoDB = AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(dynamoDBEndpoint, region))
                .withCredentials(new AWSCredentialsProvider() {
                    @Override
                    public AWSCredentials getCredentials() {
                        return new BasicAWSCredentials("key", "ke2");
                    }
                    @Override
                    public void refresh() {}
                })
                .build();

        DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(dynamoDB);
        CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(Recipe.class);
        tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
        CreateTableResult result = dynamoDB.createTable(tableRequest);
    }
}

package com.iamslash.util

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import java.net.URI

object EnhancedClientUtil {

    fun openDynamoDbClient(
        region: Region = Region.US_WEST_2,
        url: String = "http://localhost:8000",
    ): DynamoDbClient {
        return DynamoDbClient.builder()
            .region(region)
            .endpointOverride(URI.create(url))
            .credentialsProvider(DefaultCredentialsProvider.builder().build())
            .build()
    }

    fun closeDynamoDbClient(
        ddb: DynamoDbClient,
    ) {
        ddb.close()
    }

    fun createEnhancedClient(
        ddb: DynamoDbClient,
    ): DynamoDbEnhancedClient {
        return DynamoDbEnhancedClient.builder()
            .dynamoDbClient(ddb)
            .build()
    }

    fun openDynamoDbAsyncClient(
        region: Region = Region.US_WEST_2,
        url: String = "http://localhost:8000",
    ): DynamoDbAsyncClient {
        return DynamoDbAsyncClient.builder()
            .region(region)
            .endpointOverride(URI.create(url))
            .credentialsProvider(DefaultCredentialsProvider.builder().build())
            .build()
    }

    fun closeDynamoDbAsyncClient(
        ddb: DynamoDbAsyncClient,
    ) {
        ddb.close()
    }

    fun createEnahncedAsyncClient(
        ddb: DynamoDbAsyncClient,
    ): DynamoDbEnhancedAsyncClient {
        return DynamoDbEnhancedAsyncClient.builder()
            .dynamoDbClient(ddb)
            .build()
    }
}

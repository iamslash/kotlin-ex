package com.iamslash.model

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey
import java.time.Instant

@DynamoDbBean
class Customer {
    @get:DynamoDbPartitionKey var id: String? = null
    @get:DynamoDbSortKey var custName: String? = null
    var email: String? = null
    var registrationDate: Instant? = null
    var point: Int? = null
}

package com.iamslash.model

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey

@DynamoDbBean
class Issues {
    @get:DynamoDbPartitionKey var id: String? = null
    @get:DynamoDbSortKey var title: String? = null
    var date: String? = null
    var description: String? = null
    @get:DynamoDbSecondaryPartitionKey(indexNames = ["dueDateIndex"]) var dueDate: String? = null
    var status: String? = null
    var priority: String? = null
    var lastUpdateDate: String? = null
}

package com.iamslash.model

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey

@DynamoDbBean
class Work {
    @get:DynamoDbPartitionKey var id: String? = null
    var date: String? = null
    var description: String? = null
    var guide: String? = null
    var name: String? = null
        @DynamoDbSortKey get
    var status: String? = null
    var archive: String? = null
}

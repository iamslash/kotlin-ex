package com.iamslash.enhancedclient

import com.iamslash.model.Customer
import com.iamslash.util.EnhancedClientUtil
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.enhanced.dynamodb.Key
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException

object QueryApp {

    @JvmStatic
    fun main(args: Array<String>) {
        val ddb = EnhancedClientUtil.openDynamoDbClient()
        val ec = EnhancedClientUtil.createEnhancedClient(ddb)
        val result = queryTable(ec)
        EnhancedClientUtil.closeDynamoDbClient(ddb)
        println(result)
    }

    fun queryTable(enhancedClient: DynamoDbEnhancedClient): String? {
        try {
            val mappedTable = enhancedClient.table("Customer", TableSchema.fromBean(Customer::class.java))
            val queryConditional = QueryConditional
                .keyEqualTo(Key.builder()
                                .partitionValue("id120")
                                .build())

            // Get items in the table and write out the ID value
            val results: Iterator<Customer> = mappedTable.query(queryConditional).items().iterator()
            var result = ""
            while (results.hasNext()) {
                val rec = results.next()
                result = rec.id.toString()
                println("The record id is $result")
            }
            return result
        } catch (e: DynamoDbException) {
            System.err.println(e.message)
            System.exit(1)
        }
        return ""
    }
}

package com.iamslash.enhancedasyncclient


import com.iamslash.model.Customer
import com.iamslash.util.EnhancedClientUtil
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient
import software.amazon.awssdk.enhanced.dynamodb.Key
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException
import kotlin.system.exitProcess

object QueryApp {

    val CUSTOMER_ID: String = "id120"

    @JvmStatic
    fun main(args: Array<String>) {
        val ddb = EnhancedClientUtil.openDynamoDbAsyncClient()
        val ec = EnhancedClientUtil.createEnahncedAsyncClient(ddb)

        val result: Mono<Customer> = reactiveQueryBeginWith(ec, CUSTOMER_ID, "David")
//        val result: Mono<Customer> = reactiveQueryKeyEqualTo(ec, CUSTOMER_ID)
//        val result: Mono<Customer> = reactiveGetItem(ec, CUSTOMER_ID)
        val customer = result
            .log()
            .block()
        println("The result is ${customer?.custName ?: null}")

        EnhancedClientUtil.closeDynamoDbAsyncClient(ddb)
    }

    fun reactiveQueryBeginWith(
        enhancedAsyncClient: DynamoDbEnhancedAsyncClient,
        customerId: String,
        prefix: String,
    ): Mono<Customer> {
        try {
            val asyncTable = enhancedAsyncClient.table(
                "Customer",
                TableSchema.fromBean(Customer::class.java))
            val queryConditional = QueryConditional
                .sortBeginsWith(Key.builder()
                                    .partitionValue(customerId)
                                    .sortValue(prefix)
                                    .build())
            return asyncTable
                .query(queryConditional)
                .flatMapIterable {
                    it.items()
                }
                .toMono()
        } catch (e: DynamoDbException) {
            System.err.println(e.message)
            exitProcess(1)
        }

        return Mono.empty()
    }

    fun reactiveQueryKeyEqualTo(
        enhancedAsyncClient: DynamoDbEnhancedAsyncClient,
        customerId: String
    ): Mono<Customer> {
        try {
            val asyncTable = enhancedAsyncClient.table(
                "Customer",
                TableSchema.fromBean(Customer::class.java))
            val queryConditional = QueryConditional
                .keyEqualTo(Key.builder()
                                .partitionValue(customerId)
                                .build())
            // Get items in the table and write out the ID value
//            return asyncTable
//                .query(queryConditional)
//                .limit(1)
//                .flatMapIterable {
//                    it.items()
//                }
//                .toMono()
            return asyncTable
                .query(queryConditional)
                .flatMapIterable {
                    it.items()
                }
                .toMono()
        } catch (e: DynamoDbException) {
            System.err.println(e.message)
            exitProcess(1)
        }
        return Mono.empty()
    }

    fun reactiveGetItem(
        enhancedAsyncClient: DynamoDbEnhancedAsyncClient,
        customerId: String
    ): Mono<Customer> {
        try {
            val asyncTable = enhancedAsyncClient.table(
                "Customer",
                TableSchema.fromBean(Customer::class.java))
            val key = Key.builder()
                .partitionValue(customerId)
                .build()
            return Mono.fromFuture(asyncTable.getItem(key))
                .doOnError { println("Exception fetching schedule for id=$customerId - $it") }
        } catch (e: DynamoDbException) {
            System.err.println(e.message)
            System.exit(1)
        }
        return Mono.empty()
    }
}


package com.iamslash.enhancedclient

import com.iamslash.model.Customer
import com.iamslash.util.EnhancedClientUtil
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException
import java.time.LocalDate
import java.time.ZoneOffset
import kotlin.system.exitProcess

object PutItemApp {

    @JvmStatic
    fun main(args: Array<String>) {
        val ddb = EnhancedClientUtil.openDynamoDbClient()
        val enhancedClient = EnhancedClientUtil.createEnhancedClient(ddb)
        putRecord(enhancedClient, "id120", "David Sun","iamslash@gmail.com", 27)
        putRecord(enhancedClient, "id120", "David Moon","iamslash@gmail.com", 20)
        putRecord(enhancedClient, "id121", "Chad Miller", "iamslash@gmail.com", 20)
        putRecord(enhancedClient, "id122", "Chad Doe", "iamslash@gmail.com", 20)
        EnhancedClientUtil.closeDynamoDbClient(ddb)
    }

    fun putRecord(
        enhancedClient: DynamoDbEnhancedClient,
        custId: String,
        custName: String,
        custEmail: String,
        custPoint: Int,
    ) {
        try {
            val custTable = enhancedClient.table("Customer", TableSchema.fromBean(Customer::class.java))

            // Create an Instant
            val localDate = LocalDate.parse("2020-04-07")
            val localDateTime = localDate.atStartOfDay()
            val instant = localDateTime.toInstant(ZoneOffset.UTC)

            // Populate the Table
            val custRecord = Customer()
            custRecord.id = custId
            custRecord.custName = custName
            custRecord.email = custEmail
            custRecord.registrationDate = instant
            custRecord.point = custPoint

            // Put the customer data into a DynamoDB table
            custTable.putItem(custRecord)
        } catch (e: DynamoDbException) {
            System.err.println(e.message)
            exitProcess(1)
        }
        println("done")
    }
}

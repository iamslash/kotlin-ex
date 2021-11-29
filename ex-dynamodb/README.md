# Abstract

This is about how to use DynamoDB SDK.

# References

* [Introducing enhanced DynamoDB client in the AWS SDK for Java v2](https://aws.amazon.com/ko/blogs/developer/introducing-enhanced-dynamodb-client-in-the-aws-sdk-for-java-v2/)
* [Amazon DynamoDB Java code examples](https://github.com/awsdocs/aws-doc-sdk-examples/tree/master/javav2/example_code/dynamodb)
* [Amazon DynamoDB Asynchronous Java code examples](https://github.com/awsdocs/aws-doc-sdk-examples/tree/master/javav2/example_code/dynamodbasync)
* [Mid-level DynamoDB mapper/abstraction for Java using the v2 AWS SDK.](https://github.com/aws/aws-sdk-java-v2/tree/master/services-custom/dynamodb-enhanced)
* [Reactive DynamoDB @ medium](https://medium.com/techieconnect/reactive-dynamodb-94ee0cce0113) 
  * [src](https://github.com/bschandramohan/AWSConnect)

# How to run DynamoDB local

```bash
# Run DynamoDB
$ docker run --name my-dynamodb --rm -p 8000:8000 -d amazon/dynamodb-local -jar DynamoDBLocal.jar -sharedDb -dbPath .

# Create table
$ aws dynamodb create-table --table-name Customer \
    --attribute-definitions AttributeName=id,AttributeType=S AttributeName=custName,AttributeType=S\
    --key-schema AttributeName=id,KeyType=HASH AttributeName=custName,KeyType=RANGE \
    --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1 \
    --endpoint-url http://localhost:8000
    
# Delete table
$ aws dynamodb delete-table --table-name Customer --endpoint-url http://localhost:8000
```

Run [NoSQL Workbench for DynamoDB](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/workbench.html) and test it.

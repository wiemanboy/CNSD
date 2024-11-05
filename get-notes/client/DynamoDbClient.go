package client

import (
	"github.com/aws/aws-sdk-go/aws"
	"github.com/aws/aws-sdk-go/aws/session"
	"github.com/aws/aws-sdk-go/service/dynamodb"
)

func NewDynamoDBClient(region string) *dynamodb.DynamoDB {
	dynamoDBSession, _ := session.NewSession(&aws.Config{
		Region: &region,
	})

	return dynamodb.New(dynamoDBSession)
}

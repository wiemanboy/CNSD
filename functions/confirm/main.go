package main

import (
	"context"
	"github.com/aws/aws-lambda-go/lambda"
	"github.com/aws/aws-sdk-go/aws"
	"github.com/aws/aws-sdk-go/service/dynamodb"
	"os"
	"reservation/shared/client"
	"reservation/shared/enums/ReservationStatus"
)

type Event struct {
	Id     string `json:"id"`
	Status string `json:"status"`
}

type Response struct {
	Message string `json:"message"`
	Id      string `json:"id"`
	Status  string `json:"status"`
}

func Confirm(ctx context.Context, event Event) (Response, error) {
	dynamoDb := client.NewDynamoDBClient("us-east-1")
	table := os.Getenv("TABLE_NAME")

	_, err := dynamoDb.UpdateItem(&dynamodb.UpdateItemInput{
		TableName:                 &table,
		Key:                       map[string]*dynamodb.AttributeValue{"pk": {S: aws.String(event.Id)}},
		UpdateExpression:          aws.String("SET #status = :status"),
		ExpressionAttributeValues: map[string]*dynamodb.AttributeValue{":status": {S: aws.String(string(ReservationStatus.Confirmed))}},
		ExpressionAttributeNames:  map[string]*string{"#status": aws.String("status")},
	})

	if err != nil {
		return Response{
			Message: "Failed to confirm reservation",
			Id:      event.Id,
			Status:  event.Status,
		}, err
	}

	return Response{
		Message: "Reservation confirmed",
		Id:      event.Id,
		Status:  string(ReservationStatus.Confirmed),
	}, nil
}

func main() {
	lambda.Start(Confirm)
}

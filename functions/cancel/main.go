package main

import (
	"context"
	"fmt"
	"github.com/aws/aws-lambda-go/lambda"
	"github.com/aws/aws-sdk-go/aws"
	"github.com/aws/aws-sdk-go/service/dynamodb"
	"os"
	"reservation/shared/client"
	"reservation/shared/enums/ReservationStatus"
	"reservation/shared/types"
)

func Cancel(ctx context.Context, event types.Event[types.ReservationEvent]) (types.Event[types.ReservationEvent], error) {
	fmt.Printf("Processing event: %s", event)

	reservation := event.Payload

	dynamoDb := client.NewDynamoDBClient("us-east-1")
	table := os.Getenv("TABLE_NAME")

	_, err := dynamoDb.UpdateItem(&dynamodb.UpdateItemInput{
		TableName:                 &table,
		Key:                       map[string]*dynamodb.AttributeValue{"pk": {S: aws.String(reservation.Id)}},
		UpdateExpression:          aws.String("SET #status = :status"),
		ExpressionAttributeValues: map[string]*dynamodb.AttributeValue{":status": {S: aws.String(string(ReservationStatus.Canceled))}},
		ExpressionAttributeNames:  map[string]*string{"#status": aws.String("status")},
	})

	if err != nil {
		return types.Event[types.ReservationEvent]{
			Payload: types.ReservationEvent{
				Message: "Failed to fail reservation",
				Id:      reservation.Id,
				Status:  reservation.Status,
			},
		}, err
	}

	return types.Event[types.ReservationEvent]{
		Payload: types.ReservationEvent{
			Message: "Reservation failed",
			Id:      reservation.Id,
			Status:  string(ReservationStatus.Canceled),
		},
	}, nil
}

func main() {
	lambda.Start(Cancel)
}

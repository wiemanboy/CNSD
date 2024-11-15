package main

import (
	"context"
	"fmt"
	"github.com/aws/aws-lambda-go/lambda"
	"github.com/aws/aws-sdk-go/aws"
	"github.com/aws/aws-sdk-go/service/dynamodb"
	"github.com/google/uuid"
	"math/rand"
	"os"
	"reservation/shared/client"
	"reservation/shared/enums/ReservationStatus"
	"reservation/shared/types"
)

func handler(ctx context.Context, event types.Event[types.ReservationInput]) (types.Event[types.ReservationEvent], error) {
	fmt.Printf("Processing event: %s", event)

	input := event.Payload

	dynamoDb := client.NewDynamoDBClient("us-east-1")
	table := os.Getenv("TABLE_NAME")

	var status ReservationStatus.ReservationStatus
	randomNumber := rand.Intn(5)
	if randomNumber == 0 {
		status = ReservationStatus.Failed
	} else {
		status = ReservationStatus.Success
	}

	reservation := types.Reservation{
		Id:       uuid.New().String(),
		Location: input.Location,
		Date:     input.Date,
		Status:   string(status),
	}

	_, err := dynamoDb.PutItem(&dynamodb.PutItemInput{
		TableName: &table,
		Item: map[string]*dynamodb.AttributeValue{
			"pk": {
				S: aws.String(reservation.Id),
			},
			"location": {
				S: aws.String(reservation.Location),
			},
			"date": {
				S: aws.String(reservation.Date),
			},
			"status": {
				S: aws.String(reservation.Status),
			},
		},
	})

	if err != nil {
		return types.Event[types.ReservationEvent]{
			Payload: types.ReservationEvent{
				Message: "Failed to reserve",
				Id:      reservation.Id,
				Status:  reservation.Status,
			},
		}, err
	}

	return types.Event[types.ReservationEvent]{
		Payload: types.ReservationEvent{
			Message: "Reservation saved",
			Id:      reservation.Id,
			Status:  reservation.Status,
		},
	}, nil
}

func main() {
	lambda.Start(handler)
}

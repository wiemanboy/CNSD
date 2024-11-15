package main

import (
	"encoding/json"
	"github.com/aws/aws-lambda-go/events"
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

type Request struct {
	Location string `json:"location"`
	Date     string `json:"date"`
}

func handler(request events.APIGatewayProxyRequest) (events.APIGatewayProxyResponse, error) {
	dynamoDb := client.NewDynamoDBClient("us-east-1")
	table := os.Getenv("TABLE_NAME")

	var requestData Request
	err := json.Unmarshal([]byte(request.Body), &requestData)
	if err != nil {
		return events.APIGatewayProxyResponse{
			Body:       "Failed to parse request body",
			StatusCode: 400,
		}, err
	}

	var status ReservationStatus.ReservationStatus
	randomNumber := rand.Intn(5)
	if randomNumber == 0 {
		status = ReservationStatus.Failed
	} else {
		status = ReservationStatus.Success
	}

	reservation := types.Reservation{
		Id:       uuid.New().String(),
		Location: requestData.Location,
		Date:     requestData.Date,
		Status:   string(status),
	}

	_, err = dynamoDb.PutItem(&dynamodb.PutItemInput{
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
		return events.APIGatewayProxyResponse{
			Body:       "Failed to save reservation",
			StatusCode: 500,
		}, err
	}

	response, _ := json.Marshal(reservation)

	return events.APIGatewayProxyResponse{
		Body:       string(response),
		StatusCode: 201,
	}, nil
}

func main() {
	lambda.Start(handler)
}

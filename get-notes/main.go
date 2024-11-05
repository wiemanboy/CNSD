package main

import (
	"encoding/json"
	"github.com/aws/aws-lambda-go/events"
	"github.com/aws/aws-lambda-go/lambda"
	"github.com/aws/aws-sdk-go/aws"
	"github.com/aws/aws-sdk-go/service/dynamodb"
	"hello-world/client"
)

type Note struct {
	NoteId  string `json:"noteId"`
	UserId  string `json:"userId"`
	Content string `json:"content"`
}

func handler(request events.APIGatewayProxyRequest) (events.APIGatewayProxyResponse, error) {
	dynamoDb := client.NewDynamoDBClient("us-east-1")
	table := "notes"

	result, err := dynamoDb.Scan(&dynamodb.ScanInput{
		TableName: aws.String(table),
	})

	if err != nil {
		return events.APIGatewayProxyResponse{
			Body:       err.Error(),
			StatusCode: 500,
		}, err
	}

	var notes []Note
	for _, item := range result.Items {
		note := Note{
			NoteId:  *item["note-id"].S,
			UserId:  *item["user-id"].S,
			Content: *item["content"].S,
		}
		notes = append(notes, note)
	}

	response, err := json.Marshal(notes)
	if err != nil {
		return events.APIGatewayProxyResponse{
			Body:       "Failed to marshal notes",
			StatusCode: 500,
		}, err
	}

	return events.APIGatewayProxyResponse{
		Body:       string(response),
		StatusCode: 200,
	}, nil
}

func main() {
	lambda.Start(handler)
}

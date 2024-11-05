package main

import (
	"encoding/json"
	"github.com/aws/aws-lambda-go/events"
	"github.com/aws/aws-lambda-go/lambda"
	"github.com/aws/aws-sdk-go/aws"
	"github.com/aws/aws-sdk-go/service/dynamodb"
	"github.com/google/uuid"
	"hello-world/client"
)

type Request struct {
	UserId  string `json:"userId"`
	Content string `json:"content"`
}

type Note struct {
	NoteId  string `json:"noteId"`
	UserId  string `json:"userId"`
	Content string `json:"content"`
}

func handler(request events.APIGatewayProxyRequest) (events.APIGatewayProxyResponse, error) {
	dynamoDb := client.NewDynamoDBClient("us-east-1")
	table := "notes"

	var requestData Request
	err := json.Unmarshal([]byte(request.Body), &requestData)
	if err != nil {
		return events.APIGatewayProxyResponse{
			Body:       "Failed to parse request body",
			StatusCode: 400,
		}, err
	}

	note := Note{
		NoteId:  uuid.New().String(),
		UserId:  requestData.UserId,
		Content: requestData.Content,
	}

	_, err = dynamoDb.PutItem(&dynamodb.PutItemInput{
		TableName: &table,
		Item: map[string]*dynamodb.AttributeValue{
			"note-id": {
				S: aws.String(note.NoteId),
			},
			"user-id": {
				S: aws.String(note.UserId),
			},
			"content": {
				S: aws.String(note.Content),
			},
		},
	})

	if err != nil {
		return events.APIGatewayProxyResponse{
			Body:       err.Error(),
			StatusCode: 500,
		}, err
	}

	response, _ := json.Marshal(note)

	return events.APIGatewayProxyResponse{
		Body:       string(response),
		StatusCode: 201,
	}, nil
}

func main() {
	lambda.Start(handler)
}

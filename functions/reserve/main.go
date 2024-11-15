package reserve

import (
	"encoding/json"
	"github.com/aws/aws-lambda-go/events"
	"github.com/aws/aws-lambda-go/lambda"
	"github.com/aws/aws-sdk-go/aws"
	"github.com/aws/aws-sdk-go/service/dynamodb"
	"github.com/google/uuid"
	"os"
	"reservation/shared/client"
)

type Request struct {
	Location string `json:"location"`
	Date     string `json:"date"`
}

type Reservation struct {
	Id       string `json:"id"`
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

	reservation := Reservation{
		Id:       uuid.New().String(),
		Location: requestData.Location,
		Date:     requestData.Date,
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

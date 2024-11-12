package process_note

import (
	"encoding/json"
	"fmt"
	"github.com/aws/aws-lambda-go/events"
	"github.com/aws/aws-lambda-go/lambda"
	"github.com/aws/aws-sdk-go/aws"
	"github.com/aws/aws-sdk-go/service/eventbridge"
	"process-note/client"
)

func handler(event events.DynamoDBEvent) (events.DynamoDBEvent, error) {
	eventBridgeClient := client.NewEventBridgeClient("us-east-1")
	eventBusName := "note-event-bus"

	for _, dynamoEventRecord := range event.Records {
		eventDetail, err := json.Marshal(dynamoEventRecord.Change)

		if err != nil {
			return event, fmt.Errorf("failed to marshal DynamoDB event record: %w", err)
		}

		_, err = eventBridgeClient.PutEvents(&eventbridge.PutEventsInput{
			Entries: []*eventbridge.PutEventsRequestEntry{
				{
					EventBusName: aws.String(eventBusName),
					DetailType:   aws.String("DynamoDBEvent"),
					Detail:       aws.String(string(eventDetail)),
				},
			},
		})

		if err != nil {
			return event, fmt.Errorf("failed to send event to EventBridge: %w", err)
		}
	}

	return event, nil
}

func main() {
	lambda.Start(handler)
}

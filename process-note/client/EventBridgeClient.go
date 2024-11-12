package client

import (
	"github.com/aws/aws-sdk-go/aws"
	"github.com/aws/aws-sdk-go/aws/session"
	"github.com/aws/aws-sdk-go/service/eventbridge"
)

func NewEventBridgeClient(region string) *eventbridge.EventBridge {
	eventBridgeSession, _ := session.NewSession(&aws.Config{
		Region: &region,
	})

	return eventbridge.New(eventBridgeSession)
}

package main

import (
	"bytes"
	"context"
	"encoding/json"
	"fmt"
	"github.com/aws/aws-lambda-go/events"
	"github.com/aws/aws-lambda-go/lambda"
	"github.com/aws/aws-sdk-go/aws"
	"github.com/aws/aws-sdk-go/service/s3"
	"io"
	"os"
	"process-image/client"
)

type SqsMessage struct {
	FileKey string `json:"fileKey"`
}

type S3Client interface {
	GetObject(input *s3.GetObjectInput) (*s3.GetObjectOutput, error)
	PutObject(input *s3.PutObjectInput) (*s3.PutObjectOutput, error)
}

type LambdaHandler struct {
	s3Client S3Client
	bucket   string
}

func NewLambdaHandler(s3Client S3Client, bucket string) *LambdaHandler {
	return &LambdaHandler{
		s3Client: s3Client,
		bucket:   bucket,
	}
}

func (handler *LambdaHandler) ProcessSqsEvent(ctx context.Context, sqsEvent events.SQSEvent) error {
	for _, message := range sqsEvent.Records {
		// Read the message body
		var sqsMessage SqsMessage
		err := json.Unmarshal([]byte(message.Body), &sqsMessage)

		if err != nil {
			return fmt.Errorf("failed to unmarshal message: %v", err)
		}

		fileKey := sqsMessage.FileKey

		// Get the object from S3
		object, err := handler.s3Client.GetObject(&s3.GetObjectInput{
			Bucket: aws.String(handler.bucket),
			Key:    aws.String(fmt.Sprintf("uploads/%s", fileKey)),
		})

		if err != nil {
			return fmt.Errorf("failed to get object: %v", err)
		}

		// Process the object
		objectContent, err := io.ReadAll(object.Body)

		if err != nil {
			return fmt.Errorf("failed to read object content: %v", err)
		}
		println(fmt.Sprintf("processing: %s", objectContent))

		// Put the object in the processed bucket
		_, err = handler.s3Client.PutObject(&s3.PutObjectInput{
			Bucket: aws.String(handler.bucket),
			Key:    aws.String(fmt.Sprintf("processed/%s", fileKey)),
			Body:   aws.ReadSeekCloser(bytes.NewReader(objectContent)),
		})

		if err != nil {
			return fmt.Errorf("failed to put object: %v", err)
		}
	}
	return nil
}

func handler(ctx context.Context, sqsEvent events.SQSEvent) error {
	region := os.Getenv("REGION")
	bucket := os.Getenv("BUCKET")

	s3Client := client.NewS3Client(&region)
	lambdaHandler := NewLambdaHandler(s3Client, bucket)

	return lambdaHandler.ProcessSqsEvent(ctx, sqsEvent)
}

func main() {
	lambda.Start(handler)
}

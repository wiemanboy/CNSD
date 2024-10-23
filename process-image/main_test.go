package main

import (
	"bytes"
	"context"
	"encoding/json"
	"errors"
	"github.com/aws/aws-sdk-go/aws/request"
	"testing"

	"github.com/aws/aws-lambda-go/events"
	"github.com/aws/aws-sdk-go/aws"
	"github.com/aws/aws-sdk-go/service/s3"
	"github.com/stretchr/testify/assert"
	"github.com/stretchr/testify/mock"
)

type MockS3Client struct {
	mock.Mock
}

func (mock *MockS3Client) GetObjectWithContext(ctx aws.Context, input *s3.GetObjectInput, opt ...request.Option) (*s3.GetObjectOutput, error) {
	args := mock.Called(input)
	if obj := args.Get(0); obj != nil {
		return obj.(*s3.GetObjectOutput), args.Error(1)
	}
	return nil, args.Error(1)
}

func (mock *MockS3Client) PutObjectWithContext(ctx aws.Context, input *s3.PutObjectInput, opt ...request.Option) (*s3.PutObjectOutput, error) {
	args := mock.Called(input)
	if obj := args.Get(0); obj != nil {
		return obj.(*s3.PutObjectOutput), args.Error(1)
	}
	return nil, args.Error(1)
}

func TestProcessSqsEvent(t *testing.T) {
	bucket := "my-bucket"

	mockS3Client := new(MockS3Client)
	lambdaHandler := NewLambdaHandler(mockS3Client, bucket)

	sqsMessage := SqsMessage{FileKey: "example-file.txt"}
	messageBody, _ := json.Marshal(sqsMessage)
	sqsEvent := events.SQSEvent{
		Records: []events.SQSMessage{
			{
				Body: string(messageBody),
			},
		},
	}

	objectContent := []byte("file content")
	mockS3Client.On("GetObjectWithContext", mock.Anything).Return(&s3.GetObjectOutput{
		Body: aws.ReadSeekCloser(bytes.NewReader(objectContent)),
	}, nil)

	mockS3Client.On("PutObjectWithContext", mock.Anything).Return(&s3.PutObjectOutput{}, nil)

	err := lambdaHandler.ProcessSqsEvent(context.Background(), sqsEvent)

	assert.NoError(t, err)
	mockS3Client.AssertExpectations(t)
}

func TestProcessSqsEvent_WhenGetObjectFails(t *testing.T) {
	bucket := "my-bucket"

	mockS3Client := new(MockS3Client)
	lambdaHandler := NewLambdaHandler(mockS3Client, bucket)

	sqsMessage := SqsMessage{FileKey: "example-file.txt"}
	messageBody, _ := json.Marshal(sqsMessage)
	sqsEvent := events.SQSEvent{
		Records: []events.SQSMessage{
			{
				Body: string(messageBody),
			},
		},
	}

	mockS3Client.On("GetObjectWithContext", mock.Anything).Return((*s3.GetObjectOutput)(nil), errors.New("failed to get object"))

	err := lambdaHandler.ProcessSqsEvent(context.Background(), sqsEvent)

	assert.Error(t, err)
	assert.Contains(t, err.Error(), "failed to get object")
}

func TestProcessSqsEvent_WhenPutObjectFails(t *testing.T) {
	bucket := "my-bucket"

	mockS3Client := new(MockS3Client)
	lambdaHandler := NewLambdaHandler(mockS3Client, bucket)

	sqsMessage := SqsMessage{FileKey: "example-file.txt"}
	messageBody, _ := json.Marshal(sqsMessage)
	sqsEvent := events.SQSEvent{
		Records: []events.SQSMessage{
			{
				Body: string(messageBody),
			},
		},
	}

	objectContent := []byte("file content")
	mockS3Client.On("GetObjectWithContext", mock.Anything).Return(&s3.GetObjectOutput{
		Body: aws.ReadSeekCloser(bytes.NewReader(objectContent)),
	}, nil)

	mockS3Client.On("PutObjectWithContext", mock.Anything).Return((*s3.PutObjectOutput)(nil), errors.New("failed to put object"))

	err := lambdaHandler.ProcessSqsEvent(context.Background(), sqsEvent)

	assert.Error(t, err)
	assert.Contains(t, err.Error(), "failed to put object")
}

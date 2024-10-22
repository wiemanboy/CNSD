package client

import (
	"github.com/aws/aws-sdk-go/aws"
	"github.com/aws/aws-sdk-go/aws/session"
	"github.com/aws/aws-sdk-go/service/s3"
	"log"
)

type S3Client interface {
	GetObject(input *s3.GetObjectInput) (*s3.GetObjectOutput, error)
	PutObject(input *s3.PutObjectInput) (*s3.PutObjectOutput, error)
}

func NewS3Client(region *string) *s3.S3 {
	s3Session, err := session.NewSession(&aws.Config{
		Region:           region,
		S3ForcePathStyle: aws.Bool(true),
	})

	if err != nil {
		log.Fatalf("failed to create s3 session: %v", err)
	}

	return s3.New(s3Session)
}

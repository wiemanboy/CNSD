#!/bin/bash

set -e  # Exit immediately if any command fails

# S3 Buckets
LAMBDA_BUCKET="function-bucket-jarno"

# Deploy Frontend (S3 Bucket)
echo "Deploying Frontend..."
aws cloudformation deploy \
  --stack-name frontend-stack \
  --template-file frontend/template.yml

echo "Frontend deployed successfully."

# Retrieve the S3 Bucket Name for the frontend
FRONTEND_BUCKET=$(aws cloudformation describe-stacks \
  --stack-name frontend-stack \
  --query "Stacks[0].Outputs[?ExportName=='WebsiteBucketName'].OutputValue" \
  --output text)

echo "Frontend bucket name: ${FRONTEND_BUCKET}"

# Upload index.html to S3
echo "Uploading index.html to S3..."
aws s3 cp frontend/index.html s3://"${FRONTEND_BUCKET}"/index.html --force

echo "index.html uploaded successfully."

# Upload Go Lambda Function to S3
echo "Uploading Lambda function to S3..."
aws s3 cp backend/Hello/function.zip s3://${LAMBDA_BUCKET}/backend/Hello/function.zip --force

echo "Lambda function uploaded successfully."

# Deploy Backend (Go Lambda)
echo "Deploying Backend (Lambda function)..."
aws cloudformation deploy \
  --stack-name backend-stack \
  --template-file backend/Hello/template.yml

echo "Backend deployed successfully."

# Deploy API Gateway
echo "Deploying API Gateway..."
aws cloudformation deploy \
  --stack-name api-gateway-stack \
  --template-file template.yml

echo "API Gateway deployed successfully."

# Output the URLs
echo "Retrieving deployed resources..."

FRONTEND_URL=$(aws cloudformation describe-stacks \
  --stack-name frontend-stack \
  --query "Stacks[0].Outputs[?OutputKey=='WebsiteURL'].OutputValue" \
  --output text)

LAMBDA_ARN=$(aws cloudformation describe-stacks \
  --stack-name backend-stack \
  --query "Stacks[0].Outputs[?OutputKey=='LambdaFunctionArn'].OutputValue" \
  --output text)

API_URL=$(aws cloudformation describe-stacks \
  --stack-name api-gateway-stack \
  --query "Stacks[0].Outputs[?OutputKey=='ApiUrl'].OutputValue" \
  --output text)

echo "----------------------------------------"
echo "Frontend URL: ${FRONTEND_URL}"
echo "Lambda ARN: ${LAMBDA_ARN}"
echo "API Gateway URL: ${API_URL}"
echo "----------------------------------------"

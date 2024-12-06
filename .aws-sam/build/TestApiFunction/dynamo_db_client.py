import boto3
from botocore.client import BaseClient


def get_client(region_name: str = 'us-east-1') -> BaseClient:
    return boto3.client(
        'dynamodb',
        region_name=region_name,
    )


def save_user(dynamodb_client: BaseClient, table_name: str, username: str, color: str) -> None:
    dynamodb_client.put_item(
        TableName=table_name,
        Item={
            'pk': {'S': "USER"},
            'sk': {'S': "USER#" + username},
            'favorite_color': {'S': color}
        }
    )

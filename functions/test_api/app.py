import json
import os

from typing import TypedDict, Dict, Any

from dynamo_db_client import get_client, save_user


class LambdaApiEvent(TypedDict):
    resource: str
    path: str
    httpMethod: str
    headers: Dict[str, str]
    queryStringParameters: Dict[str, str]
    pathParameters: Dict[str, str]
    body: str
    isBase64Encoded: bool


def lambda_handler(event: LambdaApiEvent, context: Any):
    dynamodb_client = get_client()
    table_name = os.getenv("TABLE_NAME")

    body = json.loads(event["body"])
    username = body["username"]
    color = body["color"]

    save_user(dynamodb_client, table_name, username, color)

    return {
        "statusCode": 200,
        "body": json.dumps({
            "username": f"{username}",
            "favorite_color": f"{color}",
        }),
    }

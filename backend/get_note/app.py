import json
import boto3
from boto3.dynamodb.conditions import Key
from aws_xray_sdk.core import patch_all

patch_all()

def lambda_handler(event, context):

    print (f"{event} {type(event)}")

    domain_name = event['requestContext']['domainName']
    stage = event['requestContext']['stage']
    gateway_management_api = boto3.client('apigatewaymanagementapi', endpoint_url=f"https://{domain_name}/{stage}")

    connection_id = event['requestContext']['connectionId']
    user_id = json.loads(event['body'])['user_id']
    note_id = json.loads(event['body'])['note_id']

    dynamodb = boto3.resource('dynamodb')
    table = dynamodb.Table('notes_sam_2_4_3')

    response = table.get_item(Key={
        'PK': f'NOTE#{note_id}',
        'SK': f'USER#{user_id}'
    })

    gateway_management_api.post_to_connection(
        Data=json.dumps({
            'event_type': 'note_received',
            'data': response
        }),
        ConnectionId=connection_id
    )

    if 'Item' in response:
        return {
            'statusCode': 200,
            'body': json.dumps(response['Item']),
            'headers': {
                'content-type': 'application/json'
            },
            "isBase64Encoded": False
        }

    return {
        'statusCode': 404,
        'body': 'Not Found',
        'headers': {
            'content-type': 'application/json'
        },
        "isBase64Encoded": False
    }


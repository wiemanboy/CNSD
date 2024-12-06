import json
import boto3
from boto3.dynamodb.conditions import Key, Attr
from aws_xray_sdk.core import patch_all

patch_all()

def lambda_handler(event, context):
    print (f"{event} {type(event)}")

    domain_name = event['requestContext']['domainName']
    stage = event['requestContext']['stage']
    gateway_management_api = boto3.client('apigatewaymanagementapi', endpoint_url=f"https://{domain_name}/{stage}")

    connection_id = event['requestContext']['connectionId']
    user_id = json.loads(event['body'])['user_id']

    dynamodb = boto3.resource('dynamodb', region_name="us-east-1")
    table = dynamodb.Table('notes_sam_2_4_3')
    notes = []
    done = False
    start_key = None

    while not done:
        response = table.scan(FilterExpression=Key('SK').begins_with(f"USER#{user_id}") & Attr('Type').eq('NOTE'))
        print(f"{response}")
        notes += response['Items']
        start_key = response.get('LastEvaluatedKey', None)
        done = start_key is None

    print(f"{response}")

    gateway_management_api.post_to_connection(
        Data=json.dumps({
            'event_type': 'notes_received',
            'data': response
        }),
        ConnectionId=connection_id
    )

    return {
        "statusCode": 200,
        "body": json.dumps(notes)
    }
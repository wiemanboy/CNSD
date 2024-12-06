import json
import boto3
from boto3.dynamodb.conditions import Key, Attr
from aws_xray_sdk.core import patch_all

patch_all()

def lambda_handler(event, context):
    # Set up the API Gateway Management API and table
    dynamodb = boto3.resource('dynamodb', region_name="us-east-1")
    table = dynamodb.Table('simplechat_connections')
    gateway_management_api = boto3.client('apigatewaymanagementapi', endpoint_url=f"https://3srvl5sam4.execute-api.us-east-1.amazonaws.com/Prod")

    # Get all connectionId's by userId
    user_id = event['detail']['SK']['S']
    user_id = user_id.split('#')[1]
    print(f"User ID: {user_id}")
    connectionIds = []
    done = False

    while not done:
        response = table.scan(FilterExpression=Key('userId').eq(user_id))
        print(f"{response}")
        connectionIds += response['Items']
        start_key = response.get('LastEvaluatedKey', None)
        done = start_key is None

    connectionIds = [item['connectionId'] for item in connectionIds]

    # Format the data to send to the client
    try:
        note = {
            'UserID': event['detail']['UserID']['S'],
            'NoteID': event['detail']['NoteID']['S'],
            'Tekst': event['detail']['Tekst']['S'],
            'Type': event['detail']['Type']['S'],
            'Tag': event['detail']['Tag']['S']
        }
    except:
        note = response

    # Send message to all connectionId's
    for connectionId in connectionIds:
        try:
            gateway_management_api.post_to_connection(
                Data=json.dumps({
                    'event_type': event['detail-type'],
                    'data': note
                }),
                ConnectionId=connectionId
            )
        except Exception as e:
            print(f"Exception: {e}")

    return {
        "statusCode": 200
    }
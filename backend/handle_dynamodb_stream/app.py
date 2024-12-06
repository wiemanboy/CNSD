import json
import boto3
from boto3.dynamodb.conditions import Key, Attr
from aws_xray_sdk.core import patch_all

patch_all()

def lambda_handler(event, context):
    print (f"{event} {type(event)}")

    eventbridge = boto3.client('events')

    for record in event['Records']:
        print(f"{record['eventName']} {record['dynamodb']['Keys']}")
        if record['eventName'] == 'MODIFY':
            response = eventbridge.put_events(
                Entries=[
                    {
                        'Source': 'custom.notes',
                        'DetailType': 'note_updated',
                        'Detail': json.dumps(record['dynamodb']['NewImage']),
                        'EventBusName': 'notes-event-bus-2-4-3'
                    }
                ]
            )
            print(f"EventBridge response: {response}")
        if record['eventName'] == 'INSERT':
            response = eventbridge.put_events(
                Entries=[
                    {
                        'Source': 'custom.notes',
                        'DetailType': 'note_created',
                        'Detail': json.dumps(record['dynamodb']['NewImage']),
                        'EventBusName': 'notes-event-bus-2-4-3'
                    }
                ]
            )
            print(f"EventBridge response: {response}")
        if record['eventName'] == 'REMOVE':
            response = eventbridge.put_events(
                Entries=[
                    {
                        'Source': 'custom.notes',
                        'DetailType': 'note_deleted',
                        'Detail': json.dumps(record['dynamodb']['Keys']),
                        'EventBusName': 'notes-event-bus-2-4-3'
                    }
                ]
            )
            print(f"EventBridge response: {response}")


    return {
        "statusCode": 200,
        "body": json.dumps(event)
    }


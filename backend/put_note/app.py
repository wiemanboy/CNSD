import json
import boto3
from boto3.dynamodb.conditions import Key
from aws_xray_sdk.core import patch_all

patch_all()

def lambda_handler(event, context):
    print (f"{event} {type(event)}")

    text = json.loads(event['body'])['text']
    user_id = json.loads(event['body'])['user_id']
    note_id = json.loads(event['body'])['note_id']

    dynamodb = boto3.resource('dynamodb')
    table = dynamodb.Table('notes_sam_2_4_3')

    response = table.update_item(
        Key={
            'PK': f'NOTE#{note_id}',
            'SK': f'USER#{user_id}'
        },
        UpdateExpression="set Tekst = :t",
        ExpressionAttributeValues={
            ':t': text
        },
        ReturnValues="UPDATED_NEW"
    )

    return {
        'statusCode': 200,
        'body': 'Note updated',
        'headers': {
            'content-type': 'application/json'
        },
        "isBase64Encoded": False
    }
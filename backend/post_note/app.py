import json
import boto3
import uuid
from aws_xray_sdk.core import patch_all

patch_all()

def lambda_handler(event, context):
    print (f"{event} {type(event)}")

    domain_name = event['requestContext']['domainName']
    stage = event['requestContext']['stage']

    text = json.loads(event['body'])['text']
    tag = json.loads(event['body'])['tag']
    user_id = json.loads(event['body'])['user_id']

    dynamodb = boto3.resource('dynamodb')
    table = dynamodb.Table('notes_sam_2_4_3')

    print("Adding note to DynamoDB table")
    note_id = str(uuid.uuid4())
    item={
        'PK': f'NOTE#{note_id}',
        'SK': f'USER#{user_id}',
        'NoteID': f'{note_id}',
        'UserID': f'{user_id}',
        'Tekst': text,
        'Tag': tag,
        'Type': 'NOTE',
        'GSI1PK': f'TAG#{tag}',
        'GSI1SK': f'USER#{user_id}'
    }

    response = table.put_item(Item=item)
    print (f"{response} {type(response)}")

    return {
        "statusCode": 200
    }



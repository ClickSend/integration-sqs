# ClickSend AWS SQS Integration

This repository includes example producers and a lambda consumer for sending sms through clicksend sms api over AWS SQS

There are 3 main components:

1. A sample producer which generates sms events for the AWS SQS. There are 2 example codes for java and javascript.
2. AWS SQS which is fed by the producer
3. AWS Lambda which consumes events from SQS and uses this events to send sms to clicksend api.




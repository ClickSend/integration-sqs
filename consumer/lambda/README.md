AWS Lambda for consuming sms events from SQS and sending to clicksend api

1- Replace credentials with yours:
var smsApi = new api.SMSApi("username", "password");

2- Install dependencies as mentioned at https://github.com/ClickSend/clicksend-nodejs

3- Make sure you have a lambda created at your AWS console

4- Zip the contents of your code after installing npm modules

5- upload and update your lambda with AWS console :

aws lambda update-function-code --function-name yourfunctionnamehere --zip-file fileb://yourcreatedzipfilenamehere.zip 
// Load the AWS SDK for Node.js
var AWS = require('aws-sdk');
// Set the region 
AWS.config.update({region: 'REGION'});

// Create an SQS service object
var sqs = new AWS.SQS({apiVersion: '2012-11-05'});

// change these with your own
var params = {
  MessageBody: '{"to":"+505555555","source":"sdk","body":"hello from aws"}',
  QueueUrl: "https://sqs.eu-west-1.amazonaws.com/023280750523/emaileventsqueue"
};

sqs.sendMessage(params, function(err, data) {
  if (err) {
    console.log("Error", err);
  } else {
    console.log("Success", data.MessageId);
  }
});
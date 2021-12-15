// Load the AWS SDK for Node.js
var AWS = require('aws-sdk');
// Set the region 
AWS.config.update({region: 'Region'});

// Create an SQS service object
var sqs = new AWS.SQS({apiVersion: '2012-11-05'});

var params = {
  // Your message body:- 
        // to: reciever number, 
        // body: your message, 
        // from: by default it is shared number, you can also choose your business name, or your own number,
        // for more information regarding "from" follow: https://help.clicksend.com/article/4kgj7krx00-what-is-a-sender-id-or-sender-number
  MessageBody: '{"to":"+6111111111","source":"sqs","body":"hello from node", "from":"Testing"}',

  // Insert your AWS queue url
  QueueUrl: "https://sqs.region.amazonaws.com/XXXXXXXXXXXX/queuename"
};

sqs.sendMessage(params, function(err, data) {
  if (err) {
    console.log("Error", err);
  } else {
    console.log("Success", data.MessageId);
  }
});
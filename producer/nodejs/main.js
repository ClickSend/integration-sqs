// Load the AWS SDK for Node.js
var AWS = require('aws-sdk');
// Set the region 
AWS.config.update({region: 'REGION'});

// Create an SQS service object
var sqs = new AWS.SQS({apiVersion: '2012-11-05'});


function sendMessage(to, message){
	var params = {
		MessageBody: '{"to":"'+to+'","source":"sdk","body":"'+message+'"}',
		QueueUrl: "https://sqs.eu-west-1.amazonaws.com/023280750523/smseventsqueue"
	};

	sqs.sendMessage(params, function(err, data) {
		if (err) {
		console.log("Error", err);
	} else {
		console.log("Success", data.MessageId);
	}
});
}

var numbers = ["+606666666","+505555555"];
var messageToSend = "hello from aws";

numbers.forEach(sendSmsToAll);

function sendSmsToAll(number) {
  sendMessage(number,messageToSend);
}
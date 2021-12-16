// Loads the AWS SDK for Node.js
var AWS = require('aws-sdk');
// Sets the region 
AWS.config.update({region: 'Region'});

// Create an SQS service object
var sqs = new AWS.SQS({apiVersion: '2012-11-05'});


function sendMessage(to, message){
	var params = {
	// Your message body:- 
        // to: reciever number, use "numbers" list to add the recipients
        // body: your message, use "messageToSend" variable to change the content of message
        // from: by default it is shared number, you can also choose your business name, or your own number,
        // for more information regarding "from" follow: https://help.clicksend.com/article/4kgj7krx00-what-is-a-sender-id-or-sender-number
		MessageBody: '{"to":"'+to+'","source":"SQS","body":"'+message+'","from":"Testing"}',

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
}

// Add the recipient's numbers
var numbers = ["+606666666","+505555555","+111111111"];
// Your message
var messageToSend = "Hello from AWS SQS NodeJS";

numbers.forEach(sendSmsToAll);

function sendSmsToAll(number) {
  sendMessage(number,messageToSend);
}
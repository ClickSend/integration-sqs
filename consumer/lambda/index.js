
var api = require('./node_modules/clicksend/api.js');

var smsApi = new api.SMSApi("username", "password");


exports.handler = function(event, context, callback) {
	var smsCollection = new api.SmsMessageCollection();
	smsCollection.messages = [];
	event.Records.forEach(record => {
	  
		const { body } = record;
		var message = JSON.parse(body);
		
		console.log(message.to);
		console.log(message.source);
		console.log(message.body);

		var smsMessage = new api.SmsMessage();

		smsMessage.source = message.source;
		smsMessage.to = message.to;
		smsMessage.body = message.body;
		smsCollection.messages.push(smsMessage);
	});
  
  
  	console.log("sending messages ");
	smsApi.smsSendPost(smsCollection).then(function(response) {
		console.log("sending sms success");
		console.log(response.body);
	  callback(null, response.body);
	}).catch(function(err){
	  console.log(err.body);
	  console.log("sendind sms failed:"+err.body);
	  callback(Error(err));
	});
	console.log("sending smses finished");

}

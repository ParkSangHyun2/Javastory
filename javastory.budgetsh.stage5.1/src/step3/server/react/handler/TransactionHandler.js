const ResponseMessage = require('../../../share/util/ResponseMessage.js');
const TransactionService = require('../../logic/TransactionService.js');;

exports.handle = function(requestMessage){
	//
	let operation = requestMessage.operation;
	let value1 = requestMessage.value[0];
	let value2 = requestMessage.value[1];
	let value3 = requestMessage.value[2];
	let value4 = requestMessage.value[3];
	let value5 = requestMessage.value[4];
	let value6 = requestMessage.value[5];
	let returnValue = null;
	let responseMessage = null;
	console.log('Operation: ' + operation);
	
	switch(operation){
	case 'regist':
		returnValue = TransactionService.regist(value1);
		break;
	case 'find':
		returnValue = TransactionService.find(value1, value2);
		break;
	case 'modify':
		returnValue = TransactionService.modify(value1, value2, value3,
				value4, value5, value6);
		break;
	case 'remove':
		returnValue = TransactionService.remove(value1);
		break;
	}
	
	responseMessage = 
		new ResponseMessage(requestMessage.serviceName, returnValue, true);
	
	return responseMessage;
}
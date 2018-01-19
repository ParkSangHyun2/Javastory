const ResponseMessage = require('../../../share/util/ResponseMessage.js');
const CashbookService = require('../../logic/CashbookService.js');;

exports.handle = function(requestMessage){
	//
	let operation = requestMessage.operation;
	let value = requestMessage.value[0];
	let returnValue = null;
	let responseMessage = null;
	console.log('Operation: ' + operation);
	switch(operation){
	case 'exist':
		returnValue = CashbookService.exist(value);
		break;
	case 'regist':
		returnValue = CashbookService.regist(value);
		break;
	case 'retrieve':
		returnValue = CashbookService.retrieve(value);
		break;
	case 'update':
		returnValue = CashbookService.update(value);
		break;
	case 'remove':
		returnValue = CashbookService.remove(value);
		break;
	}
	console.log('returnvalue: ' + JSON.stringify(returnValue));
	responseMessage = 
		new ResponseMessage(requestMessage.serviceName, returnValue, true);
	
	return responseMessage;
}
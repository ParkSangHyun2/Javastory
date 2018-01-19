const ResponseMessage = require('../../../share/util/ResponseMessage.js');
const AccountService = require('../../logic/AccountService.js');;

exports.handle = function(requestMessage){
	//
	let operation = requestMessage.operation;
	let value1 = requestMessage.value[0];
	let value2 = requestMessage.value[1];
	let returnValue = null;
	let responseMessage = null;
	
	switch(operation){
	case 'exist':
		returnValue = AccountService.exist(value1);
		break;
	case 'existMonth':
		returnValue = AccountService.existMonth(value1, value2);
		break;
	case 'regist':
		returnValue = AccountService.regist(value1);
		break;
	case 'retrieve':
		returnValue = AccountService.retrieve(value1);
		break;
	case 'update':
		returnValue = AccountService.update(value1);
		break;
	case 'remove':
		returnValue = AccountService.remove(value1);
		break;
	case 'removeMonthlyDue':
		returnValue = AccountService.removeMonthlyDue(value1, value2);
		break;
	case 'findAllMonthlyDue':
		returnValue = AccountService.findAllMonthlyDue(value1, value2);
		break;
	}
	
	responseMessage = 
		new ResponseMessage(requestMessage.serviceName, returnValue, true);
	
	return responseMessage;
}
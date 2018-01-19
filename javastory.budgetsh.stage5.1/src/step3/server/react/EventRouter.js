const AccountHandler = require('./handler/AccountHandler.js');
const CashbookHandler = require('./handler/CashbookHandler.js');
const TransactionHandler = require('./handler/TransactionHandler.js');

exports.responseReturn = function(requestData){
	//
	let requestMessage = JSON.parse(requestData);
	let serviceName = requestMessage.serviceName;
	let responseMessage = null;
	
	console.log('serviceName : ' + serviceName);
	switch(serviceName){
	case 'cashbookService':
		responseMessage = CashbookHandler.handle(requestMessage);
		break;
	case 'transactionService':
		responseMessage = TransactionHandler.handle(requestMessage);
		break;
	case 'accountService':
		responseMessage = AccountHandler.handle(requestMessage);
		break;
	}
	console.log('responseMessageInRouter: ' + JSON.stringify(responseMessage));
	return JSON.stringify(responseMessage);
}
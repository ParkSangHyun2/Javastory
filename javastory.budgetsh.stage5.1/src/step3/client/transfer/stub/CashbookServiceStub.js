const SocketDispatcher = require('../SocketDispatcher.js');
const RequestMessage = require('../../../share/util/RequestMessage.js');
const ResponseMessage = require('../../../share/util/ResponseMessage.js');
var async = require('async');
var co = require('co');
const Events = require('events');

exports.exist = async function(bankAccount){
	//
	let client = null;
	let requestMessage = null;
	requestMessage = new RequestMessage('exist','cashbookService');
	requestMessage.value.push(bankAccount);
	let json = JSON.stringify(requestMessage);

	console.log('받아라제발: ' + SocketDispatcher.dispatchReturn(json));
	//return await JSON.parse(clientMessage);
}


exports.regist = function(cashbook){
	let requestMessage =
		new RequestMessage('regist','cashbookService');
	requestMessage.value.push(cashbook);
	
	let responseMessage = SocketDispatcher.dispatchReturn(JSON.stringify(requestMessage),function(){
		let returnValue = JSON.parse(responseMessage);
		return returnValue.value;
	});
}

exports.retrieve = function(bankAccount){
	let requestMessage =
		new RequestMessage('retrieve','cashbookService');
	requestMessage.value.push(bankAccount);
	
	let responseMessage = SocketDispatcher.dispatchReturn(JSON.stringify(requestMessage));
	console.log("asdasdasssdsaadsd");
	console.log('retrieve!!!!: ' + responseMessage);
	let returnValue = JSON.parse(responseMessage);
	return returnValue;
}

exports.update = function(cashbook){
	let requestMessage =
		new RequestMessage('update','cashbookService');
	requestMessage.value.push(cashbook);
	
	let responseMessage = SocketDispatcher.dispatchReturn(JSON.stringify(requestMessage), function(){
		let returnValue = JSON.parse(responseMessage);
		return returnValue.value;
	});
}

exports.remove = function(cashbook){
	let requestMessage =
		new RequestMessage('remove','cashbookService');
	requestMessage.value.push(cashbook);
	
	let responseMessage = SocketDispatcher.dispatchReturn(JSON.stringify(requestMessage), function(){
		let returnValue = JSON.parse(responseMessage);
		return returnValue.value;
	});
}
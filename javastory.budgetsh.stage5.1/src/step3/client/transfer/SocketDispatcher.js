
var async = require('async');
var co = require('co');

exports.dispatchReturn = async function(requestMessage){
	//
	var message = 'nononononononono.';
	let socket = null;
	
	clientSocket = io('http://127.0.0.1:9999');
	await clientSocket.once('connect', async (socket) => {
		await clientSocket.emit('request', requestMessage);
		await clientSocket.on('response', async function(clientMessage){
			message = clientMessage;
			});
		await setTimeout(()=>{
			console.log('on: ' + message);
			
		}, 300);
	});	
	await console.log('aaaaa');
	return message;
}


//exports.dispatchReturn = function(requestMessage){
//	let response = '';
//	let clientSocket = null;
//	clientSocket = request(requestMessage);
//
//	return clientSocket;
//}
//function request(requestMessage){
//	let clientSocket = null;
//	clientSocket = io('http://127.0.0.1:9999');
//	clientSocket.connect();
//	clientSocket.emit('request', requestMessage);
//	return clientSocket;
//}

//
//	co(function* (){
//		let clientSocket = io('http://127.0.0.1:9999');
//		let response = '';
//
//		clientSocket.once('connect', function(socket){
//			console.log('connect');
//			clientSocket.emit('request', requestMessage);
//			console.log('emit');
//			});
			
//		return clientSocket.once('response', function(responseMessage){
//			console.log('Exist-->' + responseMessage);
//			//return responseMessage;
//			response = responseMessage;
//			//얘를 가장 바깥함수로 리턴하고싶음..
//			});
////		return response;
		
//	});
	
	//return response();
	
	//console.log('what: ' + response);
	
//	let clientSocket = null;
//	clientSocket = io('http://127.0.0.1:9999');
//	clientSocket.connect();
//	clientSocket.emit('request', requestMessage);
//	clientSocket.
//	clientSocket.once('response',(response)=>{return response});

	//console.log('requested value: ' + response);

	
	//return response;
	//return JSON.stringify('asd');


//exports.close = function(){
//	//
//}
//
//exports.dispatchVoid = function(){
//	//clientSocket.sockets.connect('127.0.0.1',3333);
//	clientSocket.on('connection', function(socket){
//		socket.emit('request', requestMessage);
//		socket.close();
//	});
//}


//clientSocket.on('connect', function(socket){
//	
//	clientSocket.emit('request', requestMessage);
//	clientSocket.on('response', function(responseMessage){
//		console.log('Exist-->' + responseMessage);
//		response = responseMessage;
//		return response;//얘를 가장 바깥함수로 리턴하고싶음..
//	});
//});
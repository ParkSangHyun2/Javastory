const io = require('socket.io-client');

exports.dispatchReturn = function(){
	//
	let clientSocket = io('http://127.0.0.1:9999');
	let response;
	
	let requestMessage = 'requestMessage';
	clientSocket.on('connect', function(socket){
		
		clientSocket.emit('request', requestMessage);
		clientSocket.on('response', function(responseMessage){
			response = responseMessage;
			clientSocket.close();
			return response;
			
		});
	});
	
	
}

exports.dispatchReturn();
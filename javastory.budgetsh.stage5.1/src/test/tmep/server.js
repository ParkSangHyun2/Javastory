const serverSocket = require('socket.io')(9999);

console.log('Server started..');
serverSocket.on('connection', function(socket){
	
	
	let responseMessage = 'response';
	console.log('connected Port : 4444');
	
	socket.on('request', function(requestData){
		console.log('Requested..'+ requestData);	
		serverSocket.emit('response', responseMessage);
		
	});
});
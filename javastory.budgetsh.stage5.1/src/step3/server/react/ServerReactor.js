const serverSocket = require('socket.io')(9999);
const EventRouter = require('./EventRouter.js');

console.log('Server started..');
serverSocket.on('connection', function(socket){
	
	socket.on('request', function(requestData){
		console.log(requestData);
		serverSocket.emit('response', EventRouter.responseReturn(requestData));
	});
});

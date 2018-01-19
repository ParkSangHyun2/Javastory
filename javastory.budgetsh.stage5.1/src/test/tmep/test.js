
const Emitter = require('events');

const myEmitter = new Emitter();

myEmitter.once('listener', function(){
	console.log('--> oksy1');
	myEmitter.once('listener2', function(){
		console.log('--> oksy');
	});
});

myEmitter.emit('listener1');
let line = myEmitter.emit('listener2');

//console.log('line' + line);
var ReadLine = require('readline');
const rl = ReadLine.createInterface({
	  input: process.stdin,
	  output: process.stdout
	});

rl.on('line', (line) => {
	  console.log(`Received: ${line}`);
	});

function testWrite(){
	rl.write('Delete this!');
	// Simulate Ctrl+u to delete the line written previously
	rl.write(null, { ctrl: true, name: 'u' });
	
}

testWrite();
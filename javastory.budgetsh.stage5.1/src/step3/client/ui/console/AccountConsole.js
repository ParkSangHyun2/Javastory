const co = require('co');
const prompt = require('co-prompt');
const AccountYearlyDue = require('../../../share/domain/account/AccountYearlyDue.js');
const IdName = require('../../../share/domain/share/IdName.js');
const MonthlyDue = require('../../../share/domain/account/MonthlyDue.js');
const MainMenu = require('../menu/MainMenu.js');
const RequestMessage = require('../../../share/util/RequestMessage.js');
const ResponseMessage = require('../../../share/util/ResponseMessage.js');
const io = require('socket.io-client');

let yearlyDue = null;
let account = '';
let name = '';
let year = '';

exports.register = function() {
	//
	co(function*() {
		clientSocket = io('http://127.0.0.1:9999');
		clientSocket.connect();
	
		account = yield prompt('\n Account: ');
		requestMessage = new RequestMessage('exist','accountService');
		requestMessage.value.push(account);
		
		clientSocket.emit('request', JSON.stringify(requestMessage));
		clientSocket.once('response', (responseMessage) =>{
			if (JSON.parse(responseMessage).value) {
				console.log("\n  >Already exist Account Number--> " + this.account);
				MainMenu.selectAccountMenu();
				return;
			}else{
				exports.register2();
			}
		});
	});
}

exports.register2 = function(){
	//
	let foundYearlyDue = null;
	co(function*(){
		clientSocket = io('http://127.0.0.1:9999');
		clientSocket.connect();
		
		name = yield prompt('\n MemberName: ');
		year = parseInt((yield prompt('\n Year: ')),10);
		yearlyDue = new AccountYearlyDue(new IdName(account, name), year);
		//ok
		requestMessage = new RequestMessage('regist','accountService');
		requestMessage.value.push(yearlyDue);
		
		clientSocket.emit('request', JSON.stringify(requestMessage));
		clientSocket.once('response', (responseMessage) =>{
			foundYearlyDue = JSON.parse(responseMessage).value;
			
			if(foundYearlyDue){
				console.log('> Registed--> ');
				yearlyDue.accountYearlyDue();
				MainMenu.selectAccountMenu();
				return yearlyDue;
			}else{
				console.log('> Fail to Regist..');
				MainMenu.selectAccountMenu();
				return null;
			}
		});
	});
}

exports.find = function() {
	let foundYearlyDue = new AccountYearlyDue();
	let account = '';
	
	co(function*() {
		clientSocket = io('http://127.0.0.1:9999');
		clientSocket.connect();
		
		account = yield prompt('\n Account: ');

		requestMessage = new RequestMessage('retrieve','accountService');
		requestMessage.value.push(account);
		
		clientSocket.emit('request', JSON.stringify(requestMessage));
		clientSocket.once('response', (responseMessage) =>{
			foundYearlyDue = JSON.parse(responseMessage).value;
			if(foundYearlyDue == null){
				console.log('\n > No yearlyDue in storage');
				MainMenu.selectAccountMenu();
				return;
			}else{
				console.log('Found YearlyDue --> [Year: ' + foundYearlyDue.year +
						', Account: ' + foundYearlyDue.member.id + ', AccountName: ' +
						foundYearlyDue.member.name + ']');
				MainMenu.selectAccountMenu();
				return foundYearlyDue;
			}
		});
	});
}

exports.modify = function() {
	let yearlyDue = null;
	let account = '';
	let name = '';
	let year = '';
	
	co(function*() {
		clientSocket = io('http://127.0.0.1:9999');
		clientSocket.connect();
		
		account = yield prompt('\n Account: ');
		name = yield prompt('\n Name: ');
		year = parseInt((yield prompt('\n Year: ')), 10);

		yearlyDue = new AccountYearlyDue(new IdName(account, name), year);

		requestMessage = new RequestMessage('update','accountService');
		requestMessage.value.push(yearlyDue);
		
		clientSocket.emit('request', JSON.stringify(requestMessage));
		clientSocket.once('response', (responseMessage) =>{
			if(JSON.parse(responseMessage).value){
				console.log('> modified --> ');
				yearlyDue.accountYearlyDue();
			}else{
				console.log('> fail to update..');
			}
			MainMenu.selectAccountMenu();
		});
	});
}

exports.remove = function() {
	let account = '';
	co(function*() {
		clientSocket = io('http://127.0.0.1:9999');
		clientSocket.connect();
		
		account = yield prompt('\n> Account: ');
		
		requestMessage = new RequestMessage('remove','accountService');
		requestMessage.value.push(account);
		
		clientSocket.emit('request', JSON.stringify(requestMessage));
		clientSocket.once('response', (responseMessage) =>{
			if (JSON.parse(responseMessage).value) {
				console.log('Removed..');
			} else {
				console.log('Failed to delete..');
			}
			MainMenu.selectAccountMenu();
		});
	});
}

exports.addMonthlyDue = function() {
	let account = '';
	let currentYearlyDue = new AccountYearlyDue();
	let travel = null;
	let monthlyDue = null;
	let month = '';
	let amount = '';
	let type = '';
	
	
	co(function*() {
		clientSocket = io('http://127.0.0.1:9999');
		clientSocket.connect();
		
		account = yield prompt('> Account: ');
		requestMessage = new RequestMessage('retrieve','accountService');
		requestMessage.value.push(account);
		
		clientSocket.emit('request', JSON.stringify(requestMessage));
		clientSocket.once('response', (responseMessage)=>{
			if (JSON.parse(responseMessage).value == null) {
				console.log('select YearlyDue');
				MainMenu.selectAccountMenu();
				return;
			}else{
				exports.addMonthlyDue2(JSON.parse(responseMessage).value);
			}
		});



		

	});
}

exports.addMonthlyDue2 = function(currentYearlyDue){
	co(function*(){
		//
		clientSocket = io('http://127.0.0.1:9999');
		clientSocket.connect();
		
		month = yield prompt('> Month: ');
		if(month<1 || month>12){
			console.log('invalid Month..');
			MainMenu.selectAccountMenu();
			return;
		}
		requestMessage = new RequestMessage('existMonth','accountService');
		requestMessage.value.push(currentYearlyDue);
		requestMessage.value.push(month);
		
		clientSocket.emit('request', JSON.stringify(requestMessage));
		clientSocket.once('response', (responseMessage)=>{
			if(JSON.parse(responseMessage).value){
				console.log('Already exist Month..');
				MainMenu.selectAccountMenu();
				return;
			}else{
				exports.addMonthlyDue3(currentYearlyDue);
			}
		});
	});

}

exports.addMonthlyDue3 = function(currentYearlyDue){
	//
	co(function*(){
		clientSocket = io('http://127.0.0.1:9999');
		clientSocket.connect();
		
		amount = yield prompt('> Amount: ');
		type = yield prompt('>Type(Revenue,Expense,Forward): ')
//			
		travel = new IdName(account, currentYearlyDue.member.name);
		monthlyDue = new MonthlyDue(month, amount, travel);
		monthlyDue.type = type;
		currentYearlyDue.monthlyDues.push(monthlyDue);
		
		requestMessage = new RequestMessage('update','accountService');
		requestMessage.value.push(currentYearlyDue);
		
		clientSocket.emit('request', JSON.stringify(requestMessage));
		clientSocket.once('response', (responseMessage) =>{
			if(JSON.parse(responseMessage).value){
				console.log('Registed --> [Month: ' + month +
						', Amount: ' + amount + 
						', Travel Account: ' + travel.name +']');
				MainMenu.selectAccountMenu();
			}else{
				console.log('fail to Update..');
				MainMenu.selectAccountMenu();
			}
		});
	});
}

exports.removeMonthlyDue = function(){
	//
	let account = '';
	let currentYearlyDue = null;
	
	co(function*(){
		clientSocket = io('http://127.0.0.1:9999');
		clientSocket.connect();
		
		account = yield prompt('> Account: ');
		requestMessage = new RequestMessage('retrieve','accountService');
		requestMessage.value.push(account);
		
		clientSocket.emit('request', JSON.stringify(requestMessage));
		clientSocket.once('response', (responseMessage) => {
			currentYearlyDue = JSON.parse(responseMessage).value;
			
			if(currentYearlyDue == null){
				console.log('invalid Account..');
				MainMenu.selectAccountMenu();
				return;
			}else{
				exports.removeMonthlyDue2(currentYearlyDue);
			}
		});
	});
}

exports.removeMonthlyDue2 = function(currentYearlyDue){
	//
	co(function*(){
		clientSocket = io('http://127.0.0.1:9999');
		clientSocket.connect();
		let targetMonth = '';
		targetMonth = yield prompt('> Month to delete: ');
		
		requestMessage = new RequestMessage('removeMonthlyDue','accountService');
		requestMessage.value.push(currentYearlyDue);
		requestMessage.value.push(targetMonth);
		
		clientSocket.emit('request', JSON.stringify(requestMessage));
		clientSocket.once('response', (responseMessage) =>{
			if(JSON.parse(responseMessage).value){
				console.log('Removed Month--->' + targetMonth);
			}else{
				console.log('fail to Remove Month..');
				
			}
			MainMenu.selectAccountMenu();
		});
	});
}

exports.findAllMonthlyDue = function() {
	let account = '';
	let currentYearlyDue = null;
	let monthlyDues = [];
	
	co(function*(){
		clientSocket = io('http://127.0.0.1:9999');
		clientSocket.connect();
		account = yield prompt('> Account: ');
		
		requestMessage = new RequestMessage('findAllMonthlyDue','accountService');
		requestMessage.value.push(account);
		
		clientSocket.emit('request', JSON.stringify(requestMessage));
		clientSocket.once('response', (responseMessage) =>{
			monthlyDues = JSON.parse(responseMessage).value;
			
			if(monthlyDues == null){
				console.log('Invalid Account..');
				MainMenu.selectAccountMenu();
				return;
			}else{
				for(let i =0; i < monthlyDues.length; i++){
					console.log('Registed --> [Month: ' + monthlyDues[i].month +
							', Amount: ' + monthlyDues[i].amount + 
							', Travel Account: ' + monthlyDues[i].travel.name +']');
				}
				MainMenu.selectAccountMenu();
			}
		});
	});

}
var co = require('co');
var prompt = require('co-prompt');
const Transaction = require('../../../share/domain/budget/Transaction.js');
const IdName = require('../../../share/domain/share/IdName.js');
const Item = require('../../../share/domain/share/Item.js');
const MainMenu = require('../menu/MainMenu.js');
const RequestMessage = require('../../../share/util/RequestMessage.js');
const ResponseMessage = require('../../../share/util/ResponseMessage.js');
const io = require('socket.io-client');

var foundCashbook = null;

exports.register = function(currentAccount){
	//
	let bankAccount = '';

	co(function*(){
		clientSocket = io('http://127.0.0.1:9999');
		clientSocket.connect();
		requestMessage = new RequestMessage('retrieve','cashbookService');
		requestMessage.value.push(currentAccount);
		clientSocket.emit('request',JSON.stringify(requestMessage));
		clientSocket.once('response', (responseMessage) => {
			foundCashbook = JSON.parse(responseMessage).value;
			if(foundCashbook == null){
				console.log('invalid CashbookAccount.');
				MainMenu.selectTransactionMenu(currentAccount);
				return;
			}else{
				exports.register2(currentAccount);
			}
		});
	});
}

exports.register2 = function(currentAccount){
	//
	co(function* (){
		clientSocket = io('http://127.0.0.1:9999');
		clientSocket.connect();
		
		let account = new IdName(foundCashbook.account,
				foundCashbook.leader.familyName+
				foundCashbook.leader.firstName);
				//'');
		
		let title = yield prompt('> TransactionTitle: ');
		let tranType = yield prompt('>TranType(Expense, Forward, Revenue): ');
		let amount = yield prompt('> Amount: ');
		let tranId = yield prompt('> TranId: ');
		let memo = yield prompt('> Memo: ');
		let transaction = new Transaction(currentAccount, title, account, new Item(tranType, amount), tranId);
		transaction.memo = memo;
		
		requestMessage = new RequestMessage('regist','transactionService');
		requestMessage.value.push(transaction);
		
		clientSocket.emit('request', JSON.stringify(requestMessage));
		clientSocket.once('response', (responseMessage) => {
			console.log('> Registed -->');
			transaction.transaction();	
			MainMenu.selectTransactionMenu(currentAccount);
		});
	});
}

exports.find = function(currentAccount){
	//
	var transaction = null;
	
	co(function*(){
		clientSocket = io('http://127.0.0.1:9999');
		clientSocket.connect();
		
		let id = yield prompt('> Transaction id: ');
		
		requestMessage = new RequestMessage('find','transactionService');
		requestMessage.value.push(id);
		requestMessage.value.push(currentAccount);
		
		clientSocket.emit('request', JSON.stringify(requestMessage));
		clientSocket.once('response', (responseMessage) => {
			transaction = JSON.parse(responseMessage).value;
			
			if(transaction == null){
				console.log('> No id in storage');
				MainMenu.selectTransactionMenu(currentAccount);
				return;
			}else{
				console.log('Found --> [Account: ' + transaction.cashbookId +
						', Title: ' + transaction.title +
						', Type: ' + transaction.item.type +
						', Amount: ' + transaction.item.amount +
						', Id: ' + transaction.id +
						', Memo: ' + transaction.memo);
				MainMenu.selectTransactionMenu(currentAccount);
			}
		});
	});
}

exports.modify = function(currentAccount){
	co(function*(){
		clientSocket = io('http://127.0.0.1:9999');
		clientSocket.connect();
		
		let id = yield prompt('> Id: ');
		let title = yield prompt('> Title: ');
		let type = yield prompt('> Type: ');
		let amount = yield prompt('> Amount: ');
		let memo = yield prompt('> Memo: ');
		
		requestMessage = new RequestMessage('modify','transactionService');
		requestMessage.value.push(id);
		requestMessage.value.push(title);
		requestMessage.value.push(type);
		requestMessage.value.push(amount);
		requestMessage.value.push(memo);
		requestMessage.value.push(currentAccount);
		
		clientSocket.emit('request', JSON.stringify(requestMessage));
		clientSocket.once('response', (responseMessage) => {
			console.log('Modified..');
			MainMenu.selectTransactionMenu(currentAccount);	
		});
	});
}

exports.remove = function(currentAccount){
	co(function*(){
		clientSocket = io('http://127.0.0.1:9999');
		clientSocket.connect();
		
		let id = yield prompt('> Id: ');
		
		requestMessage = new RequestMessage('remove','transactionService');
		requestMessage.value.push(id);
		
		clientSocket.emit('request', JSON.stringify(requestMessage));
		clientSocket.once('response', (responseMessage) =>{
			console.log('Removed..');
			MainMenu.selectTransactionMenu(currentAccount);
		});
	});
}
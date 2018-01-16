var co = require('co');
var prompt = require('co-prompt');
const Transaction = require('../../domain/budget/Transaction.js');
const IdName = require('../../domain/share/IdName.js');
const Item = require('../../domain/share/Item.js');
const CashbookService = require('../../logic/CashbookService.js');
const TransactionService = require('../../logic/TransactionService.js');
const MainMenu = require('../menu/MainMenu.js');

exports.register = function(currentAccount){
	co(function*(){
		let bankAccount = currentAccount;
		let foundCashbook = CashbookService.retrieve(bankAccount);
		
		if(!CashbookService.exist(bankAccount)){
			MainMenu.selectTransactionMenu(currentAccount);
			console.log('invalid CashbookAccount.');
			return;
		}else{
			let account = new IdName(foundCashbook.account,
					foundCashbook.leader.familyName+
					foundCashbook.leader.firstName);
			
			let title = yield prompt('> TransactionTitle: ');
			let tranType = yield prompt('>TranType(Expense, Forward, Revenue): ');
			let amount = yield prompt('> Amount: ');
			let tranId = yield prompt('> TranId: ');
			let memo = yield prompt('> Memo: ');
			let transaction = new Transaction(currentAccount, title, account, new Item(tranType, amount), tranId);
			transaction.memo = memo;
			
			TransactionService.regist(transaction);
			console.log('> Registed -->');
			transaction.transaction();
			MainMenu.selectTransactionMenu(currentAccount);
		}

	});
}

exports.find = function(currentAccount){
	co(function*(){
		let id = yield prompt('> Transaction id: ');
		let transaction = TransactionService.find(id, currentAccount);
		if(transaction == null){
			console.log('> No id in storage');
			MainMenu.selectTransactionMenu(currentAccount);
			return;
		}
		console.log('Found --> [Account: ' + transaction.cashbookId +
				', Title: ' + transaction.title +
				', Type: ' + transaction.item.type +
				', Amount: ' + transaction.item.amount +
				', Id: ' + transaction.id +
				', Memo: ' + transaction.memo);
		MainMenu.selectTransactionMenu(currentAccount);
	});
}

exports.modify = function(currentAccount){
	co(function*(){
		let id = yield prompt('> Id: ');
		let title = yield prompt('> Title: ');
		let type = yield prompt('> Type: ');
		let amount = yield prompt('> Amount: ');
		let memo = yield prompt('> Memo: ');
		
		TransactionService.modify(id, title, type, amount,memo,currentAccount);
		MainMenu.selectTransactionMenu(currentAccount);
	});
}

exports.remove = function(currentAccount){
	co(function*(){
		let id = yield prompt('> Id: ');
		TransactionService.remove(id);
		MainMenu.selectTransactionMenu(currentAccount);
		console.log('Removed..');
	});
}
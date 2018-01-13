var co = require('co');
var prompt = require('co-prompt');
const Transaction = require('../../domain/budget/Transaction.js');
const IdName = require('../../domain/share/IdName.js');
const Item = require('../../domain/share/Item.js');
const CashbookStore = require('../../store/CashBookStore.js');
const cashbookStore = new CashbookStore();
const TransactionService = require('../../logic/TransactionService.js');
const transactionService = new TransactionService();
const MainMenu = require('../menu/MainMenu.js');

class TransactionConsole{
	constructor(){
		
	}
	
	register(previousMenu){
		co(function*(){
			let bankAccount = yield prompt('> Cashbook Account: ');
			let foundCashbook = cashbookStore.retrieve(bankAccount);
			
			if(!cashbookStore.exist(bankAccount)){
				//previousMenu.show(previousMenu);
				MainMenu.selectTransactionMenu();
				console.log('invalid CashbookAccount.');
				return;
			}else{
				let account = new IdName(foundCashbook.account,
						foundCashbook.leader.familyName,
						foundCashbook.leader.firstName);
				
				let title = yield prompt('> TransactionTitle: ');
				let tranType = yield prompt('>TranType(Expense, Forward, Revenue): ');
				let amount = yield prompt('> Amount: ');
				let tranId = yield prompt('> TranId: ');
				let memo = yield prompt('> Memo: ');
				let transaction = new Transaction(foundCashbook.id, title, account, new Item(tranType, amount), tranId);
				transaction.memo = memo;
				
				transactionService.register(transaction);
				console.log('> Registed -->');
				transaction.transaction();
				//previousMenu.show(previousMenu);
				MainMenu.selectTransactionMenu();
			}

		});
	}
	
	find(previousMenu){
		co(function*(){
			let id = yield prompt('> Transaction id: ');
			let transaction = transactionService.find(id);
			if(transaction == null){
				console.log('> No id in storage');
			}
			transaction.transaction();
			//previousMenu.show(previousMenu);
			MainMenu.selectTransactionMenu();
		});
	}
	
	modify(previousMenu){
		co(function*(){
			let id = yield prompt('> Id: ');
			let title = yield prompt('> Title: ');
			let type = yield prompt('> Type: ');
			let amount = yield prompt('> Amount: ');
			let memo = yield prompt('> Memo: ');
			
			transactionService.modify(id, title, type, amount,memo);
			//previousMenu.show(previousMenu);
			MainMenu.selectTransactionMenu();
		});
	}
	
	removed(previousMenu){
		co(function*(){
			let id = yield prompt('> Id: ');
			transactionService.remove(id);
			//previousMenu.show(previousMenu);
			MainMenu.selectTransactionMenu();
		});
	}

}

module.exports = TransactionConsole;
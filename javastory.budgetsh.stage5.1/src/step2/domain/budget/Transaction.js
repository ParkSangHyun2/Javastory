const Entity = require('../share/Entity.js');

class Transaction{
	constructor(cashbookId, title, account, item, id){
		this.cashbookId = cashbookId;
		this.title = title;
		this.account = account;
		this.item = item;
		this.id = id;
		this.memo = '';
		this.time = 0;
	}
	
	transaction(){
		console.log('Tran Title: ' + this.title + ', Tran Id: ' + this.id+
				', Tran Memo: ' + this.memo);
		this.item.item();
	}
}

module.exports = Transaction;
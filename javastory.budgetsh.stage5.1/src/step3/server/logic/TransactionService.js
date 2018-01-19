const TransactionStore = require('../store/fileStore/TransactionStore.js');
const Item = require('../../share/domain/share/Item.js');


exports.regist = function(transaction){
	if(transaction == null){
		console.log('invalid Transaction.');
		return;
	}
	TransactionStore.regist(transaction);
}

exports.find = function(id, cashbookId){
	let transaction = null;
	if(TransactionStore.exist(id)){
		console.log('in');
		transaction = TransactionStore.retrieve(id, cashbookId);
		return transaction;
	}else{
		return null;
	}
}

exports.modify = function(id, title, type, amount,memo, currentAccount){
	var foundTransaction;
	if(TransactionStore.exist(id)){
		foundTransaction = TransactionStore.retrieve(id, currentAccount);
	}
	foundTransaction.title = title;
	foundTransaction.item = new Item(type, amount);
	foundTransaction.memo = memo;
	
	TransactionStore.update(foundTransaction);
}

exports.remove = function(id){
	if(!TransactionStore.exist(id)){
		console.log('> No Transaction in Storage.');
		return;
	}
	TransactionStore.remove(id);
}
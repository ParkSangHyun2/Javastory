const TransactionStore = require('../store/TransactionStore.js');
const transactionStore = new TransactionStore();
const Item = require('../domain/share/Item.js');


class TransactionService{
	constructor(){
		
	}
	
	register(transaction){
		if(transaction == null){
			console.log('invalid Transaction.');
			return;
		}
		transactionStore.register(transaction);
	}
	
	find(id){
		if(transactionStore.exist(id)){
			return transactionStore.find(id);
		}else{
			return null;
		}
	}
	
	modify(id, title, type, amount,memo){
		var foundTransaction;
		if(transactionStore.exist(id)){
			foundTransaction = transactionStore.find(id);
		}
		foundTransaction.title = title;
		foundTransaction.type = new Item(type, amount);
		foundTransaction.memo = memo;
		
		transactionStore.delete(id);
		transactionStore.register(foundTransaction);
	}
	
	remove(id){
		if(!transactionStore.exist(id)){
			console.log('> No Transaction in Storage.');
			return;
		}
		transactionStore.remove(id);
	}
}

module.exports = TransactionService;
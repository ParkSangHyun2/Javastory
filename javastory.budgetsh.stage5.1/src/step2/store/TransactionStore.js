const MapStorage = require('./MapStorage.js');
const mapStorage = new MapStorage();

class TransactionStore{
	constructor(){
		
	}
	exist(id){
		return mapStorage.transactionMap.has(id);
	}
	
	register(transaction){
		mapStorage.transactionMap.set(transaction.id, transaction);
	}
	
	find(id){
		return mapStorage.transactionMap.get(id);
	}
	
	remove(id){
		mapStorage.transactionMap.delete(id);
	}
}

module.exports = TransactionStore;
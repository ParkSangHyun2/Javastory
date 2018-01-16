const TransactionFile = require('./io/TransactionFile.js');

exports.regist = function(transaction){
	TransactionFile.regist(transaction);
	return true;
}

exports.exist = function(id){
	if(TransactionFile.exist(id)){
		return true;
	}
	return false;
}

exports.retrieve = function(id, cashbookId){
	return TransactionFile.retrieve(id, cashbookId);
}

exports.retrieveAll = function(cashbookId){
	return TransactionFile.retrieveAll(cashbookId);
}

exports.update = function(transaction){
	TransactionFile.update(transaction);
}

exports.remove = function(id){
	TransactionFile.remove(id);
}
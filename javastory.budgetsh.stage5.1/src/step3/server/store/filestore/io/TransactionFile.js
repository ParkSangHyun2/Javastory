const FileDbWrapper = require('./FileDbWrapper.js');
const defaultDelimiter = '||';

const transactionFile = new FileDbWrapper('Resource','Transaction',defaultDelimiter);
const transactionTempFile = new FileDbWrapper('Resource', 'TransactionTemp', defaultDelimiter);
const keyIndexMap = new Map();
keyIndexMap.set('cashbookId', 0);
keyIndexMap.set('id', 1);

transactionFile.keyIndexMap = keyIndexMap;
transactionTempFile.keyIndexMap = keyIndexMap;

exports.regist = function(transaction){
	let data = JSON.stringify(transaction);
	transactionFile.writeFile(transaction.cashbookId);
	transactionFile.writeFile(defaultDelimiter);
	transactionFile.writeFile(transaction.id);
	transactionFile.writeFile(defaultDelimiter);
	transactionFile.writeFile(data);
	transactionFile.writeFile('\r\n');
}

exports.exist = function(id){
	let found = false;

	let lines = [];
	lines = transactionFile.readFile();
	
	let lineSize = lines.length;
	for(var i = 0; i<lineSize; i++){
		if(transactionFile.hasValueOf('id', id, lines[i])){
			found = true;
			break;
		}
	}
	return found;
}

exports.retrieve = function(id, cashbookId){
	let transaction = null;
	let lines = [];
	let json = '';
	lines = transactionFile.readFile();
	let lineSize = lines.length;
	console.log('id: ' + id+'cash: ' + cashbookId);
	for(var i = 0; i<lineSize; i++){
		if(transactionFile.hasValueOf('cashbookId', cashbookId, lines[i])
				&& transactionFile.hasValueOf('id', id, lines[i])){
			
			json = lines[i].split(defaultDelimiter)[2];
			transaction = JSON.parse(json);
			break;
		}
	}
	
	return transaction;
}

exports.retrieveAll = function(cashbookId){
	let transaction = null;
	let transactions = [];
	let lines = [];
	let json = '';
	lines = transactionFile.readFile();
	
	let lineSize = lines.length;
	for(var i = 0; i<lineSize; i++){
		if(transactionFile.hasValueOf('cashbookId', cashbookId, lines[i])){
			json = lines[i].split(defaultDelimiter)[2];
			transaction = JSON.parse(json);
			transactions.push(transaction);
			break;
		}
	}
	
	return transactions;
}

exports.update = function(transaction){
	let cashbookId = transaction.cashbookId;
	let id = transaction.id;
	let lines = [];
	let json = '';
	lines = transactionFile.readFile();
	let lineSize = lines.length;
	for(var i = 0; i<lineSize; i++){
		if(transactionFile.hasValueOf('cashbookId', cashbookId, lines[i])
				&& transactionFile.hasValueOf('id', id, lines[i])){
			json = JSON.stringify(transaction);	
			transactionTempFile.writeFile(transaction.cashbookId);
			transactionTempFile.writeFile(defaultDelimiter);
			transactionTempFile.writeFile(transaction.id);
			transactionTempFile.writeFile(defaultDelimiter);
			transactionTempFile.writeFile(json);
			transactionTempFile.writeFile('\r\n');
		}else{
			transactionTempFile.writeFile(lines[i]);
			transactionTempFile.writeFile('\r\n');
		}
	}
	transactionTempFile.writeFile('\r\n');
	transactionTempFile.deleteFile(transactionFile.getFilePath());
	transactionTempFile.rename(transactionFile.getFilePath());
}

exports.remove = function(id){
	let yearlyDue = null;
	let lines = [];
	let json = '';
	lines = transactionFile.readFile();
	let lineSize = lines.length;
	for(var i = 0; i<lineSize; i++){
		if(!transactionFile.hasValueOf('id', id, lines[i])){
			transactionTempFile.writeFile(lines[i]);
			transactionTempFile.writeFile('\r\n');
		}
	}
	transactionTempFile.writeFile('\r\n');
	transactionTempFile.deleteFile(transactionFile.getFilePath());
	transactionTempFile.rename(transactionFile.getFilePath());
}
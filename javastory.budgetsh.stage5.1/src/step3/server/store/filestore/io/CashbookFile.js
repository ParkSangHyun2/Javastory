const FileDbWrapper = require('./FileDbWrapper.js');
const defaultDelimiter = '||';

const cashbookFile = new FileDbWrapper('Resource','cashbook',defaultDelimiter);
const cashbookTempFile = new FileDbWrapper('Resource', 'cashbookTemp', defaultDelimiter);
const keyIndexMap = new Map();
keyIndexMap.set('bankAccount', 0);

cashbookFile.keyIndexMap = keyIndexMap;
cashbookTempFile.keyIndexMap = keyIndexMap;

exports.exist = function(bankAccount){
	let found = false;

	let lines = [];
	lines = cashbookFile.readFile();
	
	let lineSize = lines.length;
	for(var i = 0; i<lineSize; i++){
		if(cashbookFile.hasValueOf('bankAccount', bankAccount, lines[i])){
			found = true;
			break;
		}
	}
	return found;
}

exports.regist = function(cashbook){
	//
	let data = JSON.stringify(cashbook);
	cashbookFile.writeFile(cashbook.bankAccount);
	cashbookFile.writeFile(defaultDelimiter);
	cashbookFile.writeFile(data);
	cashbookFile.writeFile('\r\n');
}

exports.retrieve = function(bankAccount){
	let cashbook = null;
	let lines = [];
	let json = '';
	lines = cashbookFile.readFile();
	
	let lineSize = lines.length;
	for(var i = 0; i<lineSize; i++){
		if(cashbookFile.hasValueOf('bankAccount', bankAccount, lines[i])){
			json = lines[i].split(defaultDelimiter)[1];
			cashbook = JSON.parse(json);
			break;
		}
	}
	
	return cashbook;
}

exports.update = function(cashbook){
	let account = cashbook.bankAccount;
	let lines = [];
	let json = '';
	lines = cashbookFile.readFile();
	let lineSize = lines.length;
	for(var i = 0; i<lineSize; i++){
		if(cashbookFile.hasValueOf('bankAccount', account, lines[i])){
			json = JSON.stringify(cashbook);	
			cashbookTempFile.writeFile(cashbook.bankAccount);
			cashbookTempFile.writeFile(defaultDelimiter);
			cashbookTempFile.writeFile(json);
			cashbookTempFile.writeFile('\r\n');
		}else{
			cashbookTempFile.writeFile(lines[i]);
			cashbookTempFile.writeFile('\r\n');
		}
	}
	cashbookTempFile.writeFile('\r\n');
	cashbookTempFile.deleteFile(cashbookFile.getFilePath());
	cashbookTempFile.rename(cashbookFile.getFilePath());
}

exports.remove = function(cashbook){
	let foundCashbook = null;
	let cashbookAccount = cashbook.bankAccount;
	let lines = [];
	let json = '';
	lines = cashbookFile.readFile();
	let lineSize = lines.length;
	for(var i = 0; i<lineSize; i++){
		if(!cashbookFile.hasValueOf('bankAccount', cashbookAccount, lines[i])){
			cashbookTempFile.writeFile(lines[i]);
			cashbookTempFile.writeFile('\r\n');
		}
	}
	cashbookTempFile.writeFile('\r\n');
	cashbookTempFile.deleteFile(cashbookFile.getFilePath());
	cashbookTempFile.rename(cashbookFile.getFilePath());
}
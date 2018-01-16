const CashbookFile = require('./io/CashbookFile.js');

exports.exist = function(bankAccount) {
	if (CashbookFile.exist(bankAccount)) {
		return true;
	} else {
		return false;
	}
}

exports.retrieve = function(bankAccount) {
	return CashbookFile.retrieve(bankAccount);
}

exports.update = function(cashbook) {
	CashbookFile.update(cashbook);
}

exports.remove = function(cashbook) {
	CashbookFile.remove(cashbook);
}

exports.regist = function(cashbook) {
	CashbookFile.regist(cashbook);
}
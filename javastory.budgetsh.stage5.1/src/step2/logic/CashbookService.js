const CashbookStore = require('../store/filestore/CashBookStore.js');

exports.exist = function(bankAccount) {
	if (CashbookStore.exist(bankAccount)) {
		return true;
	}
	return false;
}

exports.regist = function(cashbook) {
	let bankAccount = cashbook.bankAccount;
	if (CashbookStore.exist(bankAccount)) {
		return false;
	}
	CashbookStore.regist(cashbook);

	if (!CashbookStore.exist(bankAccount)) {
		return false;
	}
	return true;
}

exports.retrieve = function(bankAccount) {
	if (!CashbookStore.exist(bankAccount)) {
		return null;
	}
	return CashbookStore.retrieve(bankAccount);
}

exports.update = function(cashbook) {
	let bankAccount = cashbook.bankAccount;
	if (!CashbookStore.exist(bankAccount)) {
		return false;
	}
	CashbookStore.update(cashbook);
	return true;
}

exports.remove = function(cashbook) {
	let bankAccount = cashbook.bankAccount;
	if(!CashbookStore.exist(bankAccount)){
		return false;
	}
	CashbookStore.remove(cashbook);
	if(CashbookStore.exist(bankAccount)){
		return false;
	}
	return true;
}
const CashBookStore = require('../store/CashBookStore.js');
const cashbookStore = new CashBookStore();

exports.exist = function(bankAccount) {
	if (cashbookStore.exist(bankAccount)) {
		return true;
	}
	return false;
}

exports.regist = function(cashbook) {
	let bankAccount = cashbook.bankAccount;
	if (cashbookStore.exist(bankAccount)) {
		return false;
	}
	cashbookStore.regist(cashbook);

	if (!cashbookStore.exist(bankAccount)) {
		return false;
	}
	return true;
}

exports.retrieve = function(bankAccount) {
	if (!cashbookStore.exist(bankAccount)) {
		return null;
	}
	return cashbookStore.retrieve(bankAccount);
}

exports.update = function(cashbook) {
	let bankAccount = cashbook.bankAccount;
	if (!cashbookStore.exist(bankAccount)) {
		return false;
	}
	cashbookStore.update(cashbook);
	return true;
}

exports.remove = function(cashbook) {
	let bankAccount = cashbook.bankAccount;
	if(!cashbookStore.exist(bankAccount)){
		return false;
	}
	cashbookStore.remove(cashbook);
	if(cashbookStore.exist(bankAccount)){
		return false;
	}
	return true;
}
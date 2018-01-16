const AccountFile = require('./io/AccountFile.js');
const AccountYearlyDue = require('../../domain/account/AccountYearlyDue.js');

exports.exist = function(account){
	//
	if(AccountFile.exist(account)){
		return true;
	}
	return false;
}

exports.remove = function(account){
	//
	if(!exports.exist(account)){
		new Error('No account in Storage');
	}
	AccountFile.remove(account);
}

exports.update = function(yearlyDue){
	//
	AccountFile.update(yearlyDue);
}

exports.retrieveAll = function(account){
	if(!exports.exist(account)){
		return null;
	}
	//return AccountFile.retrieve(account);
}

exports.retrieve = function(account){
	//
	let foundAccountYearlyDue = new AccountYearlyDue();
	foundAccountYearlyDue = AccountFile.retrieve(account);
	if(!exports.exist(account)){
		return null;
	}
	return foundAccountYearlyDue;
}

exports.regist = function(yearlyDue){
	if(exports.exist(yearlyDue.member.id)){
		return;
	}
	AccountFile.regist(yearlyDue);
}
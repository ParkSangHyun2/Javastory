const AccountStore = require('../store/filestore/AccountStore.js');
const AccountYearlyDue = require('../../share/domain/account/AccountYearlyDue.js');

//const AccountStore = new AccountStore();

exports.exist = function(account) {
	if(AccountStore.exist(account)){
		return true;
	}else{
		return false;
	}
}

exports.existMonth = function(yearlyDue, month){
	let isMonthExist = false;
	let foundMonthlyDues = yearlyDue.monthlyDues;
	
	for(var i=0; i < foundMonthlyDues.length; i++){
		if(foundMonthlyDues[i].month == month){
			isMonthExist = true;
		}
	}
	
	return isMonthExist;
}

exports.regist = function(yearlyDue) {
	if(AccountStore.exist(yearlyDue.member.id)){
		throw new Error('Already registed in Storage');
		return false;
	}
	
	AccountStore.regist(yearlyDue);
	if(AccountStore.exist(yearlyDue.member.id)){
		return true;
	}else{
		return false;
	}
}

exports.update = function(yearlyDue) {
	let account = yearlyDue.member.id;
	if(!AccountStore.exist(account)){
		return false;
	}
	AccountStore.update(yearlyDue);
	return true;
}

exports.remove = function(account) {
	if(AccountStore.exist(account)){
		AccountStore.remove(account);
		if(AccountStore.exist(account)){
			return false;
		}else{
			return true;
		}
		
	}else{
		return false;
	}
}

exports.retrieve = function(account) {
	let foundAccountYearlyDue = new AccountYearlyDue();
	foundAccountYearlyDue = AccountStore.retrieve(account);
	if(foundAccountYearlyDue == null){
		return null;
	}
	return foundAccountYearlyDue;
}

exports.addMonthlyDue = function() {
	
}

exports.removeMonthlyDue = function(yearlyDue, targetMonth) {
	let monthlyDues = [];
	
	foundMonthlyDues = yearlyDue.monthlyDues;
	
	let removeIndex = -1;
	for(var i = 0; i < foundMonthlyDues.length; i++){
		if(foundMonthlyDues[i].month == targetMonth){
			removeIndex = i;
		}
	}
	
	for(var j = 0; j < foundMonthlyDues.length; j++){
		if(!(j == removeIndex)){
			monthlyDues.push(foundMonthlyDues[j]);
		}
	}
	
	if(removeIndex == -1){
		return false;
	}
	
	yearlyDue.monthlyDues = monthlyDues;
	AccountStore.update(yearlyDue);
	return true;
}

exports.updateMonthlyDue = function() {
	//TODO
}

exports.findAllMonthlyDue = function(account) {
	let foundMonthlyDue = [];
	let foundAccountYearlyDue = AccountStore.retrieve(account);
	if(foundAccountYearlyDue == null){
		return null;
	}
	
	let monthlyDues = foundAccountYearlyDue.monthlyDues;
	for(var i =0; i < monthlyDues.length; i++){
		foundMonthlyDue.push(monthlyDues[i]);
	}
	
	if(foundMonthlyDue.length == 0){
		return null;
	}
		
	return foundMonthlyDue;
}

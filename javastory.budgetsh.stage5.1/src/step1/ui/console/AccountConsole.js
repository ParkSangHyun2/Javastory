const co = require('co');
const prompt = require('co-prompt');
const AccountYearlyDue = require('../../domain/account/AccountYearlyDue.js');
const IdName = require('../../domain/share/IdName.js');
const MonthlyDue = require('../../domain/account/MonthlyDue.js');
const MainMenu = require('../menu/MainMenu.js');
const AccountService = require('../../logic/AccountService.js');

exports.register = function() {
	let yearlyDue = null;
	let account = '';
	let name = '';
	let year = '';

	co(function*() {
		account = yield prompt('\n Account: ');
		
		if (AccountService.exist(account)) {
			console.log("\n  >Already exist Account Number--> " + this.account);
			MainMenu.selectAccountMenu();
			return;
		}
		name = yield prompt('\n MemberName: ');
		year = parseInt((yield prompt('\n Year: ')),10);
		yearlyDue = new AccountYearlyDue(new IdName(account, name), year);

		if(AccountService.regist(yearlyDue)){
			console.log('> Registed--> ');
			yearlyDue.accountYearlyDue();
			MainMenu.selectAccountMenu();
			return yearlyDue;
		}else{
			console.log('> Fail to Regist..');
			MainMenu.seleteAccountMenu();
			return null;
		}
	});
}

exports.find = function() {
	let foundYearlyDue = null;
	let account = '';
	
	co(function*() {
		account = yield prompt('\n Account: ');

		if (!AccountService.exist(account)) {
			console.log('\n > No yearlyDue in storage');
			MainMenu.selectAccountMenu();
			return;
		}

		foundYearlyDue = AccountService.retrieve(account);
		console.log('Found YearlyDue --> ');
		foundYearlyDue.accountYearlyDue();
		MainMenu.selectAccountMenu();
		return foundYearlyDue;
	});
}

exports.modify = function() {
	let yearlyDue = null;
	let account = '';
	let name = '';
	let year = '';
	
	co(function*() {
		account = yield prompt('\n Account: ');

		if (!AccountService.exist(account)) {
			console.log('\n > No yearlyDue in storage');
			MainMenu.selectAccountMenu();
			return;
		}

		name = yield prompt('\n Name: ');
		year = parseInt((yield prompt('\n Year: ')), 10);

		yearlyDue = new AccountYearlyDue(new IdName(account, name), year);

		if(AccountService.update(yearlyDue)){
			console.log('> modified --> ');
			yearlyDue.accountYearlyDue();
		}else{
			console.log('> fail to update..');
		}
		MainMenu.selectAccountMenu();
	});
}

exports.remove = function() {
	let account = '';
	co(function*() {
		account = yield prompt('\n> Account: ');
		AccountService.remove(account);
		if (!AccountService.exist(account)) {
			console.log('Removed..');
		} else {
			console.log('Failed to delete..');
		}
		MainMenu.selectAccountMenu();
	});
}

exports.addMonthlyDue = function() {
	let account = '';
	let currentYearlyDue = null;
	let travel = null;
	let monthlyDue = null;
	let month = '';
	let amount = '';
	let type = '';
	
	
	co(function*() {
		account = yield prompt('> Account: ');
		currentYearlyDue = AccountService.retrieve(account);
		if (currentYearlyDue == null) {
			console.log('select YearlyDue');
			MainMenu.selectAccountMenu();
			return;
		}

		month = yield prompt('> Month: ');
		if(month<1 || month>12){
			console.log('invalid Month..');
			MainMenu.selectAccountMenu();
			return;
		}
		
		if(AccountService.existMonth(currentYearlyDue, month)){
			console.log('Already exist Month..');
			MainMenu.selectAccountMenu();
			return;
		}
		
		amount = yield prompt('> Amount: ');
		type = yield prompt('>Type(Revenue,Expense,Forward): ')

		let foundYearlyDue = AccountService.retrieve(currentYearlyDue.member.id);
		let foundAccount = foundYearlyDue.member.id;
		let foundAccountName = foundYearlyDue.member.name;
		
		travel = new IdName(foundAccount, foundAccountName);
		monthlyDue = new MonthlyDue(month, amount, travel);
		monthlyDue.type = type;
		foundYearlyDue.addMonthlyDue(monthlyDue);
		AccountService.update(foundYearlyDue);
		console.log('Registed -->');
		foundYearlyDue.accountYearlyDue();
		MainMenu.selectAccountMenu();
	});
}

exports.removeMonthlyDue = function(){
	//
	let account = '';
	let currentYearlyDue = null;
	
	co(function*(){
		account = yield prompt('> Account: ');
		currentYearlyDue = AccountService.retrieve(account);
		if(currentYearlyDue == null){
			console.log('invalid Account..');
			MainMenu.selectAccountMenu();
			return;
		}
		
		let targetMonth = '';
		targetMonth = yield prompt('> Month to delete: ');
		
		if(AccountService.removeMonthlyDue(currentYearlyDue, targetMonth)){
			console.log('Removed Month--->' + targetMonth);
		}else{
			console.log('fail to Remove Month..');
			
		}
		MainMenu.selectAccountMenu();
	});
}

exports.findAllMonthlyDue = function() {
	let account = '';
	let currentYearlyDue = null;
	let monthlyDues = null;
	
	co(function*(){
		account = yield prompt('> Account: ');
		
		monthlyDues = AccountService.findAllMonthlyDue(account);
		if(monthlyDues == null){
			console.log('Invalid Account..');
			MainMenu.selectAccountMenu();
			return;
		}
		
		for(let i =0; i < monthlyDues.length; i++){
			monthlyDues[i].monthlyDue();
		}
		MainMenu.selectAccountMenu();
	});

}
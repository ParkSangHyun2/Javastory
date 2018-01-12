const co = require('co');
const prompt = require('co-prompt');
const AccountYearlyDue = require('../../domain/account/AccountYearlyDue.js');
const IdName = require('../../domain/share/IdName.js');
const AccountStore = require('../../store/accountStore.js');
const MonthlyDue = require('../../domain/account/MonthlyDue.js');
const accountStore = new AccountStore();

class AccountConsole {
	constructor() {
		this.account = '';
		this.year = 0;
		this.name = '';
		this.yearlyDue = null;
		this.monthlyDue = null;

		this.month = 0;
		this.amount = 0;
		this.travel = null;
		this.currentYearlyDue = null;
		this.account = '';
		this.type = '';
	}

	register(previousMenu) {
		var yearlyDue = null;

		co(function*() {
			this.account = yield prompt('\n Account: ');

			if (accountStore.exist(this.account)) {
				console.log("\n  >Already exist Account Number--> " + this.account);
			}
			this.name = yield prompt('\n MemberName: ');
			this.year = Number(yield prompt('\n Year: '));
			yearlyDue = new AccountYearlyDue(new IdName(this.account, this.name), this.year);

			accountStore.regist(yearlyDue);
			console.log('> Registed--> ');
			yearlyDue.accountYearlyDue();
			previousMenu.show();
			return this.yearlyDue;
		});
	}

	find(previousMenu) {
		var foundYearlyDue = null;
		co(function*() {
			this.account = yield prompt('\n Account: ');

			if (!accountStore.exist(this.account)) {
				console.log('\n > No yearlyDue in storage');
				previousMenu.show();
				return;
			}

			foundYearlyDue = accountStore.retrieve(this.account);
			console.log('Found YearlyDue: ');
			foundYearlyDue.accountYearlyDue();
			previousMenu.show();
			return foundYearlyDue;
		});
	}

	modify(previousMenu) {
		var yearlyDue = null;
		co(function*() {
			this.account = yield prompt('\n Account: ');

			if (!accountStore.exist(this.account)) {
				console.log('\n > No yearlyDue in storage');
				this.modify(previousMenu);
			}

			this.name = yield prompt('\n Name: ');
			this.year = Number(yield prompt('\n Year: '));

			yearlyDue = new AccountYearlyDue(new IdName(this.account, this.name), this.year);
			console.log('> modified --> ');
			yearlyDue.accountYearlyDue();
			accountStore.update(yearlyDue);
			previousMenu.show(previousMenu);
		});
	}
	remove(previousMenu) {
		var account = '';
		co(function*() {
			account = yield prompt('\n> Account: ');
			accountStore.remove(account);
			if (!accountStore.exist(account)) {
				console.log('Removed..');
			} else {
				console.log('Failed to delete..');
			}
			previousMenu.show();
		});
	}

	addMonthlyDue(previousMenu) {
		co(function*() {
			this.account = yield prompt('> Account: ');
			this.currentYearlyDue = accountStore.retrieve(this.account);
			if (this.currentYearlyDue == null) {
				console.log('select YearlyDue');
				previousMenu.show(previousMenu);
				return;
			}

			this.month = yield prompt('> Month: ');
			this.amount = yield prompt('> Amount: ');
			this.type = yield prompt('>Type(Revenue,Expense,Forward): ')

			var foundYearlyDue = accountStore.retrieve(this.currentYearlyDue.member.id);
			var foundAccount = foundYearlyDue.member.id;
			var foundAccountName = foundYearlyDue.member.name;
			this.travel = new IdName(foundAccount, foundAccountName);
			this.monthlyDue = new MonthlyDue(this.month, this.amount, this.travel);
			this.monthlyDue.type = this.type;
			foundYearlyDue.addMonthlyDue(monthlyDue);
			accountStore.update(foundYearlyDue);
			console.log('Registed..');
			previousMenu.show(previousMenu);
		});
	}
	
	removeMonthlyDue(previousMenu){
		//
		co(function*(){
			this.account = yield prompt('> Account: ');
			this.currentYearlyDue = accountStore.retrieve(this.account);
			if(this.currentYearlyDue == null){
				console.log('invalid Account..');
				previousMenu.show(previousMenu);
				return;
			}
			var targetMonth = '';
			targetMonth = yield prompt('> Month to delete: ');
			var removeIndex = -1;
			for (var i = 0; i < this.currentYearlyDue.monthlyDues.length; i++) {
				if(targetMonth == this.currentYearlyDue.monthlyDues[i].month){
					removeIndex = i;
				}
			}
			if(removeIndex == -1){
				console.log('invalid Month..');
				previousMenu.show(previousMenu);
				return;
			}
			if(removeIndex == this.currentYearlyDue.monthlyDues.length-1){
				this.currentYearlyDue.monthlyDues[removeIndex] = null;
				accountStore.update(this.currentYearlyDue);
				previousMenu.show(previousMenu);
				return;
			}
			for(var j = removeIndex; j<this.currentYearlyDue.monthlyDues.length-1; j++){
				this.currentYearlyDue.monthlyDues[j] = this.currentYearlyDue.monthlyDues[j+1];
			}
			this.currentYearlyDue.monthlyDues[this.cuttentYearlyDue.monthlyDues.length-1] = null;
			accountStore.update(this.currentYearlyDue);
			previousMenu.show(previousMenu);
		});
	}

	findAllMonthlyDue(previousMenu) {
		co(function*(){
			this.account = yield prompt('> Account: ');
			this.currentYearlyDue = accountStore.retrieve(this.account);
			if(this.currentYearlyDue == null){
				console.log('invalid Account..');
				previousMenu.show(previousMenu);
				return;
			}
			for (var i = 0; i < this.currentYearlyDue.monthlyDues.length; i++) {
				if(this.currentYearlyDue.monthlyDues[i] == null){
					break;
				}
				this.currentYearlyDue.monthlyDues[i].monthlyDue();
			}
			previousMenu.show(previousMenu);
		});

	}
}

module.exports = AccountConsole;
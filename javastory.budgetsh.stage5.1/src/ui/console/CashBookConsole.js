const CashbookService = require('../../logic/CashBookService.js');
const Travel = require('../../domain/travel/Travel.js');
const IdName = require('../../domain/share/IdName.js');
const Socialian = require('../../domain/share/Socialian.js');
const CashBook = require('../../domain/budget/CashBook.js');
const cashbookService = new CashbookService();
const DatePair = require('../../domain/share/DatePair.js');
const MainMenu = require('../menu/MainMenu.js');
var co = require('co');
var prompt = require('co-prompt');
var program = require('commander');


class CashBookConsole {
	constructor() {
		this.cashbook = null;
		this.travel = null;
		this.socialian = null;
		this.bankAccount = '';
		this.travelName = '';
		this.clubName = '';
		this.leaderSocialId = '';
		this.leaderFirstName = '';
		this.leaderFamilyName = '';
		this.leaderEmail = '';
		this.leaderPhone = '';
		this.monthlyDue = 0.0;
		this.budgetGoal = 0.0;
		this.startDate = '';
		this.endDate = '';

	}

	register(previousMenu) {

		co(function*() {
			this.bankAccount = yield prompt('\n Cashbook Bank Account: ');
			if (cashbookService.exist(bankAccount)) {
				console.log("already exist this Account. -->" + bankAccount);
				register();
			}
			this.travelName = yield prompt('\n Travel Name: ');
			this.clubName = yield prompt('\n club Name: ');
			this.travel = new Travel(this.travelName);
			this.travel.club = new IdName(this.travel.id, this.clubName);

			this.leaderSocialId = yield prompt('\n SocialId: ');
			this.leaderFirstName = yield prompt('\n First Name: ');
			this.leaderFamilyName = yield prompt('\n FamilyName: ');
			this.leaderEmail = yield prompt('\n Leader Email: ');
			this.leaderPhone = yield prompt('\n Leader Phone: ');
			this.socialian = new Socialian(this.leaderSocialId, this.leaderFirstName, this.leaderFamilyName, this.leaderEmail);
			this.socialian.phone = this.leaderPhone;

			this.monthlyDue = Number(yield prompt('\n monthlyDue: '));
			this.budgetGoal = Number(yield prompt('\n Budget Goal: '));
			this.startDate = yield prompt('\n startDate(ex>2017-03-23)');
			this.endDate = yield prompt('\n End Date(ex>2018-01-10)');

			this.cashbook = new CashBook(this.travel, this.monthlyDue, this.budgetGoal, new DatePair(this.startDate, this.endDate));
			this.cashbook.bankAccount = bankAccount;
			this.cashbook.club = this.travel.club;
			this.cashbook.leader = this.socialian;

			cashbookService.regist(this.cashbook);
			
			console.log('Regist -->');
			this.cashbook.cashbook();
			//previousMenu.show();
			MainMenu.selectCashbookMenu();
			return this.cashbook;
		});
	}

	find(previousMenu) {
		co(function*() {
			this.bankAccount = yield prompt('\n Cashbook Account: ');
			if (cashbookService.exist(this.bankAccount)) {
				this.cashbook = cashbookService.retrieve(this.bankAccount);
				this.cashbook.cashbook();
				//previousMenu.show();
				MainMenu.selectCashbookMenu();
				return this.cashbook;
			} else {
				console.log('> No Cashbook in storage -->' + this.bankAccount);
			}
			//previousMenu.show();
			MainMenu.selectCashbookMenu();
			return this.cashbook;
		});
	}

	modify(previousMenu) {
		var foundCashbook = null;

		co(function*() {
				this.bankAccount = yield prompt('\n Cashbook Account: ');
				foundCashbook = cashbookService.retrieve(this.bankAccount);
				if(foundCashbook == null){
					console.log('> No Cashbook in storage.');
					//previousMenu.show();
					MainMenu.selectCashbookMenu();
					return;
				}
				this.monthlyDue = yield prompt('\n >monthly Due: ');
				if (this.monthlyDue == '')
					this.budgetGoal = yield prompt('\n >BudgetGoal: ');
				this.endDate = yield prompt('\n >End Date: ');

				this.term = new DatePair(foundCashbook.term.startDate, this.endDate);
				foundCashbook.term = this.term;
				foundCashbook.budgetGoal = this.budgetGoal;
				foundCashbook.monthlyDue = this.monthlyDue;

				cashbookService.update(foundCashbook);
				console.log('Updated --> ');
				foundCashbook.cashbook();
				//previousMenu.show();
				MainMenu.selectCashbookMenu();
		});
	}

	remove(previousMenu) {
		var foundCashbook = null;

		co(function*() {
			this.bankAccount = yield prompt('\n Cashbook BankAccount: ');
			if (cashbookService.exist(bankAccount)) {
				foundCashbook = cashbookService.retrieve(this.bankAccount);
				cashbookService.remove(foundCashbook);
				console.log('removed --> ');
				foundCashbook.cashbook();
				//previousMenu.show();
				MainMenu.selectCashbookMenu();
			}
		});
	}
}
cashBookConsole = new CashBookConsole();



module.exports = CashBookConsole;
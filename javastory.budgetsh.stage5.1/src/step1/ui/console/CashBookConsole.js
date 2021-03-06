const CashbookService = require('../../logic/CashBookService.js');
const Travel = require('../../domain/travel/Travel.js');
const IdName = require('../../domain/share/IdName.js');
const Socialian = require('../../domain/share/Socialian.js');
const CashBook = require('../../domain/budget/CashBook.js');
const DatePair = require('../../domain/share/DatePair.js');
const MainMenu = require('../menu/MainMenu.js');
var co = require('co');
var prompt = require('co-prompt');

exports.register = function(previousMenu) {
	//
	let bankAccount = '';
	let travelName = '';
	let clubName = '';
	let travel = null;
	let leaderSocialId = '';
	let leaderFirstName = '';
	let leaderFamilyName = '';
	let leaderEmail = '';
	let leaderPhone = '';
	let socialian = null;
	let monthlyDue = 0;
	let budgetGoal = 0;
	let startDate = '';
	let endDate = '';
	let cashbook = null;
	
	co(function*() {
		bankAccount = yield prompt('\n Cashbook Bank Account: ');
		if (CashbookService.exist(bankAccount)) {
			console.log("already exist this Account. -->" + bankAccount);
			MainMenu.selectCashbookMenu();
		}
		travelName = yield prompt('\n Travel Name: ');
		clubName = yield prompt('\n club Name: ');
		travel = new Travel(travelName);
		travel.club = new IdName(travel.id, clubName);

		leaderSocialId = yield prompt('\n SocialId: ');
		leaderFirstName = yield prompt('\n First Name: ');
		leaderFamilyName = yield prompt('\n FamilyName: ');
		leaderEmail = yield prompt('\n Leader Email: ');
		leaderPhone = yield prompt('\n Leader Phone: ');
		socialian = new Socialian(leaderSocialId, leaderFirstName, leaderFamilyName, leaderEmail);
		socialian.phone = leaderPhone;

		monthlyDue = Number(yield prompt('\n monthlyDue: '));
		budgetGoal = Number(yield prompt('\n Budget Goal: '));
		startDate = yield prompt('\n startDate(ex>2017-03-23)');
		endDate = yield prompt('\n End Date(ex>2018-01-10)');

		cashbook = new CashBook(travel, monthlyDue, budgetGoal, new DatePair(startDate, endDate));
		cashbook.bankAccount = bankAccount;
		cashbook.club = travel.club;
		cashbook.leader = socialian;

		CashbookService.regist(cashbook);
		
		console.log('Regist -->');
		cashbook.cashbook();
		MainMenu.selectCashbookMenu();
		return cashbook;
	});
}

exports.find = function() {

	//
	let bankAccount = '';
	let cashbook = null;
	
	co(function*() {
		bankAccount = yield prompt('\n Cashbook Account: ');
		cashbook = CashbookService.retrieve(bankAccount);
		if (cashbook == null) {
			console.log('> No Cashbook in storage -->' + bankAccount);
			MainMenu.selectCashbookMenu();
			return null;
		}
		console.log('Found Cashbook --> ');
		cashbook.cashbook();
		MainMenu.selectCashbookMenu();
		return cashbook;
	});
}

exports.modify = function() {
	let foundCashbook = null;
	let bankAccount = '';
	let monthlyDue = 0;
	let endDate = '';
	let budgetGoal = 0;
	let term = null;

	co(function*() {
			bankAccount = yield prompt('\n Cashbook Account: ');
			foundCashbook = CashbookService.retrieve(bankAccount);
			if(foundCashbook == null){
				console.log('> No Cashbook in storage.');
				MainMenu.selectCashbookMenu();
				return;
			}
			monthlyDue = parseInt(yield prompt('\n >monthly Due: '));
			budgetGoal = parseInt(yield prompt('\n >BudgetGoal: '));
			endDate = yield prompt('\n >End Date: ');

			term = new DatePair(foundCashbook.term.startDate, endDate);
			foundCashbook.term = term;
			foundCashbook.budgetGoal = budgetGoal;
			foundCashbook.monthlyDue = monthlyDue;

			if(CashbookService.update(foundCashbook)){
				console.log('Updated --> ');
				foundCashbook.cashbook();
			}else{
				console.log('fail to update.');
			}
			
			MainMenu.selectCashbookMenu();
	});
}

exports.remove = function(previousMenu) {
	var foundCashbook = null;
	let bankAccount = '';

	co(function*() {
		bankAccount = yield prompt('\n Cashbook BankAccount: ');
		if (CashbookService.exist(bankAccount)) {
			foundCashbook = CashbookService.retrieve(bankAccount);
			if(CashbookService.remove(foundCashbook)){
				console.log('removed --> ');
				foundCashbook.cashbook();
			}else{
				console.log('fail to remove');
			}

			MainMenu.selectCashbookMenu();
		}
	});
}

const CashbookService = require('../../transfer/stub/CashbookServiceStub.js');
const Travel = require('../../../share/domain/travel/Travel.js');
const IdName = require('../../../share/domain/share/IdName.js');
const Socialian = require('../../../share/domain/share/Socialian.js');
const CashBook = require('../../../share/domain/budget/CashBook.js');
const DatePair = require('../../../share/domain/share/DatePair.js');
const MainMenu = require('../menu/MainMenu.js');
var co = require('co');
var prompt = require('co-prompt');

exports.register = function() {
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
			return;
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
	
	co(function*() {
		bankAccount = yield prompt('\n Cashbook Account: ');
		let cashbook = CashbookService.retrieve(bankAccount);
		if (cashbook == null) {
			console.log('> No Cashbook in storage -->' + bankAccount);
			MainMenu.selectCashbookMenu();
			return null;
		}
		console.log('Found Cashbook --> [Travel Name: ' + cashbook.travel.name+
				', ClubName: ' + cashbook.club.name + ', leaderName: ' + cashbook.leader.firstName+
				', BankAccount: ' + cashbook.bankAccount + ', MonthlyDue: ' + cashbook.monthlyDue+
				', BudgetGoal: ' + cashbook.budgetGoal + ', term: ' + cashbook.term.startDate +' ~ '+ cashbook.term.endDate+
				', Memo: ' + cashbook.memo +']');
		MainMenu.selectCashbookMenu(cashbook.bankAccount);
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
				console.log('Modified --> [Travel Name: ' + foundCashbook.travel.name+
						', ClubName: ' + foundCashbook.club.name + ', leaderName: ' + foundCashbook.leader.firstName+
						', BankAccount: ' + foundCashbook.bankAccount + ', MonthlyDue: ' + foundCashbook.monthlyDue+
						', BudgetGoal: ' + foundCashbook.budgetGoal + ', term: ' + foundCashbook.term.startDate +' ~ '+ foundCashbook.term.endDate+
						', Memo: ' + foundCashbook.memo +']');
			}else{
				console.log('fail to update.');
			}
			
			MainMenu.selectCashbookMenu();
	});
}

exports.remove = function() {
	var foundCashbook = null;
	let bankAccount = '';

	co(function*() {
		bankAccount = yield prompt('\n Cashbook BankAccount: ');
		if (CashbookService.exist(bankAccount)) {
			foundCashbook = CashbookService.retrieve(bankAccount);
			if(CashbookService.remove(foundCashbook)){
				console.log('Removed --> [Travel Name: ' + foundCashbook.travel.name+
						', ClubName: ' + foundCashbook.club.name + ', leaderName: ' + foundCashbook.leader.firstName+
						', BankAccount: ' + foundCashbook.bankAccount + ', MonthlyDue: ' + foundCashbook.monthlyDue+
						', BudgetGoal: ' + foundCashbook.budgetGoal + ', term: ' + foundCashbook.term.startDate +' ~ '+ foundCashbook.term.endDate+
						', Memo: ' + foundCashbook.memo +']');
			}else{
				console.log('fail to remove');
			}

			MainMenu.selectCashbookMenu();
		}
	});
}

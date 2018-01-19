//const CashbookService = require('../../transfer/stub/CashbookServiceStub.js');
const Travel = require('../../../share/domain/travel/Travel.js');
const IdName = require('../../../share/domain/share/IdName.js');
const Socialian = require('../../../share/domain/share/Socialian.js');
const CashBook = require('../../../share/domain/budget/CashBook.js');
const DatePair = require('../../../share/domain/share/DatePair.js');
const RequestMessage = require('../../../share/util/RequestMessage.js');
const ResponseMessage = require('../../../share/util/ResponseMessage.js');

const MainMenu = require('../menu/MainMenu.js');
var co = require('co');
const prompt = require('co-prompt');
const io = require('socket.io-client');

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
let requestMessage = null;
let responseMessage = null;
var foundCashbook = null;


exports.register = function() {
	//
	clientSocket = io('http://127.0.0.1:9999');
	clientSocket.once('connect', (socket) =>{
		co(function*() {
			var prompt = require('co-prompt');
			
			bankAccount = yield prompt('\n Cashbook Bank Account: ');
			requestMessage = new RequestMessage('exist','cashbookService');
			requestMessage.value.push(bankAccount);
			
			clientSocket.emit('request', JSON.stringify(requestMessage));
			clientSocket.once('response', (responseMessage)=>{
				responseMessage = JSON.parse(responseMessage);
				
				if (responseMessage.value) {
					console.log("already exist this Account. -->" + bankAccount);
					MainMenu.selectCashbookMenu();
					return;
				}else{
					exports.register2();
				}
			});		
	});
	});
}

exports.register2 = function(){

	co(function*(){
		var prompt = require('co-prompt');
		clientSocket = io('http://127.0.0.1:9999');
		clientSocket.connect();
			
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

		requestMessage = new RequestMessage('regist','cashbookService');
		requestMessage.value.push(cashbook);
			
		clientSocket.emit('request', JSON.stringify(requestMessage));
		clientSocket.once('response', (responseMessage) =>{
			console.log(responseMessage.value);
			console.log('Regist -->');
			cashbook.cashbook();
			MainMenu.selectCashbookMenu();
		});
	});
	}


exports.find = function() {
	//
	let bankAccount = '';
	
	co(function*() {
		clientSocket = io('http://127.0.0.1:9999');
		clientSocket.connect();
		bankAccount = yield prompt('\n Cashbook Account: ');
		requestMessage = new RequestMessage('retrieve','cashbookService');
		requestMessage.value.push(bankAccount);
		
		clientSocket.emit('request',JSON.stringify(requestMessage));
		clientSocket.once('response', (responseMessage) =>{
			cashbook = JSON.parse(responseMessage).value;
			if (cashbook == null) {
				console.log('> No Cashbook in storage -->' + bankAccount);
				MainMenu.selectCashbookMenu();
				return null;
			}
			else{
				console.log('Found Cashbook --> [Travel Name: ' + cashbook.travel.name+
						', ClubName: ' + cashbook.club.name + ', leaderName: ' + cashbook.leader.firstName+
						', BankAccount: ' + cashbook.bankAccount + ', MonthlyDue: ' + cashbook.monthlyDue+
						', BudgetGoal: ' + cashbook.budgetGoal + ', term: ' + cashbook.term.startDate +' ~ '+ cashbook.term.endDate+
						', Memo: ' + cashbook.memo +']');
				MainMenu.selectCashbookMenu(cashbook.bankAccount);
				return cashbook;
			}
		});
	});
}

exports.modify = function() {
	let bankAccount = '';
	let monthlyDue = 0;
	let endDate = '';
	let budgetGoal = 0;
	let term = null;

	co(function*() {
			clientSocket = io('http://127.0.0.1:9999');
			clientSocket.connect();
			bankAccount = yield prompt('\n Cashbook Account: ');
			requestMessage = new RequestMessage('retrieve','cashbookService');
			requestMessage.value.push(bankAccount);
			
			clientSocket.emit('request', JSON.stringify(requestMessage));
			clientSocket.once('response',(responseMessage)=>{
				foundCashbook = JSON.parse(responseMessage).value;
				if(foundCashbook == null){
					console.log('> No Cashbook in storage.');
					MainMenu.selectCashbookMenu();
					return;
				}else{
					exports.modify2();
				}
			});
	});
}

exports.modify2 = function(){

	let bankAccount = '';
	let monthlyDue = 0;
	let endDate = '';
	let budgetGoal = 0;
	let term = null;
	
	co(function*(){
		clientSocket = io('http://127.0.0.1:9999');
		clientSocket.connect();
		
		monthlyDue = parseInt(yield prompt('\n >monthly Due: '));
		budgetGoal = parseInt(yield prompt('\n >BudgetGoal: '));
		endDate = yield prompt('\n >End Date: ');

		term = new DatePair(foundCashbook.term.startDate, endDate);
		foundCashbook.term = term;
		foundCashbook.budgetGoal = budgetGoal;
		foundCashbook.monthlyDue = monthlyDue;

		requestMessage = new RequestMessage('update','cashbookService');
		requestMessage.value.push(foundCashbook);
		clientSocket.emit('request', JSON.stringify(requestMessage));
		
		clientSocket.once('response', (responseMessage) =>{
			if(JSON.parse(responseMessage)){
				console.log('Modified --> [Travel Name: ' + foundCashbook.travel.name+
						', ClubName: ' + foundCashbook.club.name + ', leaderName: ' + foundCashbook.leader.firstName+
						', BankAccount: ' + foundCashbook.bankAccount + ', MonthlyDue: ' + foundCashbook.monthlyDue+
						', BudgetGoal: ' + foundCashbook.budgetGoal + ', term: ' + foundCashbook.term.startDate +' ~ '+ foundCashbook.term.endDate+
						', Memo: ' + foundCashbook.memo +']');
			}
			else{
				console.log('fail to update.');
			}
			MainMenu.selectCashbookMenu();
		});
	});
}

exports.remove = function() {
	let bankAccount = '';

	co(function*() {
		clientSocket = io('http://127.0.0.1:9999');
		clientSocket.connect();
		
		bankAccount = yield prompt('\n Cashbook BankAccount: ');
		requestMessage = new RequestMessage('retrieve', 'cashbookService');
		requestMessage.value.push(bankAccount);
		
		clientSocket.emit('request', JSON.stringify(requestMessage));
		clientSocket.once('response', (responseMessage) =>{
			foundCashbook = (JSON.parse(responseMessage)).value;
			if (foundCashbook != null) {
				exports.remove2();
			}else{
				console.log('Not in Storage..');
				MainMenu.selectCashbookMenu();
			}
		});

	});
}

exports.remove2 = function(){
	//
	co(function*(){
		clientSocket = io('http://127.0.0.1:9999');
		clientSocket.connect();
		
		requestMessage = new RequestMessage('remove', 'cashbookService');
		requestMessage.value.push(foundCashbook);
		clientSocket.emit('request', JSON.stringify(requestMessage));
		clientSocket.once('response', (responseMessage) =>{
			message = JSON.parse(responseMessage);
		if(message.value){
			console.log('Removed --> ['+
					', ClubName: ' + foundCashbook.club.name + ', leaderName: ' + foundCashbook.leader.firstName+
					', BankAccount: ' + foundCashbook.bankAccount + ', MonthlyDue: ' + foundCashbook.monthlyDue+
					', BudgetGoal: ' + foundCashbook.budgetGoal + ', term: ' + foundCashbook.term.startDate +' ~ '+ foundCashbook.term.endDate+
					', Memo: ' + foundCashbook.memo +']');
		}else{
			console.log('fail to remove');
		}
		MainMenu.selectCashbookMenu();
		});
	});
}
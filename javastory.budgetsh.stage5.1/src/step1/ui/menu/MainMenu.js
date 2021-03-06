const co = require('co');
const prompt = require('co-prompt');
const CashbookConsole = require('../console/CashBookConsole.js');
const TransactionConsole = require('../console/TransactionConsole.js');
const AccountConsole = require('../console/AccountConsole.js');

const transactionConsole = new TransactionConsole();

start();

function showMainMenu() {
	console.log("\n------------------------------");
	console.log(" Main Menu");
	console.log("------------------------------");
	console.log(" 1. CashBook menu");
	console.log(" 2. Account Menu");
	console.log("------------------------------");
	console.log(" 0. exit");
}

function showCashbookMenu() {
	console.log("\n------------------------------");
	console.log("CashBook Menu");
	console.log("------------------------------");
	console.log(" 1. register");
	console.log(" 2. find");
	console.log(" 3. modify");
	console.log(" 4. remove");
	console.log("------------------------------");
	console.log(" 5. Transaction Menu");
	console.log("------------------------------");
	console.log(" 0. Previous Menu");
}

function showTransactionMenu() {
	console.log("\n------------------------------");
	console.log('Transaction Menu');
	console.log("------------------------------");
	console.log(" 1. register");
	console.log(" 2. find");
	console.log(" 3. modify");
	console.log(" 4. remove");
	console.log("------------------------------");
	console.log(" 0. Previous Menu");
}

function showAccountMenu() {
	console.log("\n------------------------------");
	console.log("Account Menu");
	console.log("------------------------------");
	console.log(" 1. register");
	console.log(" 2. find");
	console.log(" 3. modify");
	console.log(" 4. remove");
	console.log("------------------------------");
	console.log(" 5. add monthly Due");
	console.log(" 6. remove monthly Due");
	console.log(" 7. findAll monthly Due");
	console.log("------------------------------");
	console.log(" 0. Previous Menu");
}

function selectMainMenu() {
	showMainMenu();
	co(function*() {
		let inputNumber = yield prompt('> ');
		inputNumber = parseInt(inputNumber,10);
		switch (inputNumber) {
		case 1:
			exports.selectCashbookMenu();
			break;
		case 2:
			exports.selectAccountMenu();
			break;
		case 0:
			console.log('Exit...');
			process.exit();
			break;
		default:
			break;
		}
	});
}

exports.selectCashbookMenu = function() {
	co(function*() {
		showCashbookMenu();
		let inputNumber = yield prompt('> ');
		inputNumber = parseInt(inputNumber,10);
		switch (inputNumber) {
		case 1:
			CashbookConsole.register();
			break;
		case 2:
			CashbookConsole.find();
			break;
		case 3:
			CashbookConsole.modify();
			break;
		case 4:
			CashbookConsole.remove();
			break;
		case 5:
			exports.selectTransactionMenu();
			break;
		case 0:
			selectMainMenu();
			break;

		default:
			selectMainMenu();
			break;
		}
	});
}

exports.selectTransactionMenu = function () {
	showTransactionMenu();
	co(function*() {
		let inputNumber = yield prompt('> ');
		inputNumber = parseInt(inputNumber,10);
		switch (inputNumber) {
		case 1:
			transactionConsole.register();
			break;
		case 2:
			transactionConsole.find();
			break;
		case 3:
			transactionConsole.modify();
			break;
		case 4:
			transactionConsole.remove();
			break;
		case 0:
			exports.selectCashbookMenu();
			break;

		default:
			exports.selectCashbookMenu();
			break;
		}
	});
}

exports.selectAccountMenu = function () {
	co(function*() {
		showAccountMenu();
		let inputNumber = yield prompt('> ');
		inputNumber = parseInt(inputNumber,10);
		switch (inputNumber) {
		case 1:
			AccountConsole.register();
			break;
		case 2:
			AccountConsole.find();
			break;
		case 3:
			AccountConsole.modify();
			break;
		case 4:
			AccountConsole.remove();
			break;
		case 5:
			AccountConsole.addMonthlyDue();
			break;
		case 6:
			AccountConsole.removeMonthlyDue();
			break;
		case 7:
			AccountConsole.findAllMonthlyDue();
			break;
		case 0:
			selectMainMenu();
			break;

		default:
			selectMainMenu();
			break;
		}
	});
}

function start(){
	selectMainMenu();
}

var co = require('co');
var prompt = require('co-prompt');
const TransactionMenu = require('./TransactionMenu.js');
const CashBookConsole = require('../console/CashBookConsole.js');
//const MainMenu = require('./MainMenu.js');
//
const cashbookConsole = new CashBookConsole();
const transactionMenu = new TransactionMenu();

class CashBookMenu {
	constructor() {
	}

	show(previousMenu) {
		this.selectMenu(previousMenu);
		//this.previousMenu = previousMenu;
	}

	displayMenu() {
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

	selectMenu(previousMenu) {
		var selectedNumber = '';

		this.displayMenu();
		co(function*() {
			var selectedNumber = yield prompt('>  ');
			var numberLine = Number(selectedNumber);
			if (numberLine > 0 && numberLine <= 5) {
				sendToService(numberLine);
			} else if (numberLine == 0) {
				previousMenu.show();
			} else {
				this.selectNumber(previousMenu);
			}
		});
	}

}
cashbookMenu = new CashBookMenu();

function sendToService(inputNumber) {
	var currentBook = null;
	switch (inputNumber) {
	//
	case 1:
		currentBook = cashbookConsole.register(cashbookMenu);
		break;
	case 2:
		currentBook = cashbookConsole.find(cashbookMenu);
		break;
	case 3:
		currentBook = cashbookConsole.modify(cashbookMenu);
		break;
	case 4:
		cashbookConsole.remove(cashbookMenu);
		break;
	case 5:
		transactionMenu.show(cashbookMenu);
		break;
	case 0:
		break;
	default:
		break;
	}
}

module.exports = CashBookMenu;
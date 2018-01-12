var co = require('co');
var prompt = require('co-prompt');
const TransactionConsole = require('../console/TransactionConsole.js');
const transactionConsole = new TransactionConsole();

class TransactionMenu {
	constructor() {
		this.currentBook = null;
	}
	show(previousMenu) {
		this.selectMenu(previousMenu);
	}

	displayMenu() {
		console.log("------------------------------");
		console.log('Transaction Menu')
		console.log("------------------------------");
		console.log(" 1. register");
		console.log(" 2. find");
		console.log(" 3. modify");
		console.log(" 4. remove");
		console.log("------------------------------");
		console.log(" 0. Previous Menu");
	}

	selectMenu(previousMenu) {
		var selectedNumber = '';

		this.displayMenu();
		co(function*() {
			var selectedNumber = yield prompt('>  ');
			var numberLine = Number(selectedNumber);
			if (numberLine > 0 && numberLine <= 4) {
				sendToService(numberLine);
			} else if (numberLine == 0) {
				previousMenu.show();
			} else {
				this.selectNumber(previousMenu);
			}
		});
	}

}

var transactionMenu = new TransactionMenu();

function sendToService(inputNumber) {
	//var currentBook = null;
	switch (inputNumber) {
	//
	case 1:
		transactionConsole.register(transactionMenu);
		break;
	case 2:
		transactionConsole.find(transactionMenu);
		break;
	case 3:
		transactionConsole.modify(transactionMenu);
		break;
	case 4:
		transactionConsole.remove(transactionMenu);
		break;
	case 0:
		return;
	}
}

module.exports = TransactionMenu;
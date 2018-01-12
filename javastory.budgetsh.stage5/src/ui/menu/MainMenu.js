var co = require('co');
var prompt = require('co-prompt');
var commander = require('commander');
const CashbookMenu = require('./CashBookMenu.js');
const AccountMenu = require('./AccountMenu.js');

class MainMenu {
	constructor() {
		
	}
	
	show() {
		this.selectNumber();
	}

	displayMenu() {
		console.log("------------------------------");
		console.log(" Main Menu");
		console.log("------------------------------");
		console.log(" 1. CashBook menu");
		console.log(" 2. Account Menu");
		console.log("------------------------------");
		console.log(" 0. exit");
	}

	selectNumber() {
		var selectedNumber = '';
		this.displayMenu();
		co(function*() {
			var selectedNumber = yield prompt('>  ');
			var numberLine = Number(selectedNumber);
			if (numberLine >= 0 && numberLine <= 2) {
				sendToMenu(numberLine);
			}else{
				this.selectNumber();
			}
		});

	}

	exit() {
		console.log('Exit..');
		process.exit();
	}
}
var mainMenu = new MainMenu();

function sendToMenu(selectNumber) {
	switch (selectNumber) {
	case 0:
		mainMenu.exit();
		break;

	case 1:
		var cashbookMenu = new CashbookMenu();
		cashbookMenu.show(mainMenu);
		break;

	case 2:
		var accountMenu = new AccountMenu();
		accountMenu.show(mainMenu);
		break;
	}
}



mainMenu.show();
module.exports = MainMenu;
const co = require('co');
const prompt = require('co-prompt');
const AccountConsole = require('../console/AccountConsole.js');
const accountConsole = new AccountConsole();

class AccountMenu {
	constructor() {
		this.currentYearlyDue = null;
	}

	show(previousMenu) {
		this.selectMenu(previousMenu);
	}

	displayMenu() {
		console.log("Account Menu ( )");
		console.log("------------------------------");
		console.log(" 1. register");
		console.log(" 2. find");
		console.log(" 3. modify");
		console.log(" 4. remove");
		console.log("------------------------------");
		console.log(" 5. add monthly Due");
		console.log(" 6. remove monthly Due");
		console.log(" 7. modify monthly Due");
		console.log(" 8. findAll monthly Due");
		console.log("------------------------------");
		console.log(" 0. Previous Menu");
	}

	selectMenu(previousMenu) {
		var selectedNumber = '';
		var loop = false;

		this.displayMenu();
		co(function*() {
			var selectedNumber = yield prompt('> ');
			var numberLine = Number(selectedNumber);
			if (numberLine > 0 && numberLine <= 8) {
				sendToService(numberLine);
			} else if (numberLine == 0) {
				previousMenu.show();
			} else {
				this.selectMenu(previousMenu);
			}
		});
	}

}
accountMenu = new AccountMenu();
function sendToService(inputNumber) {
	switch (inputNumber) {
	//
	case 1:
		accountMenu.currentYearlyDue = accountConsole.register(accountMenu);
		break;
	case 2:
		accountMenu.currentYearlyDue = accountConsole.find(accountMenu);
		break;
	case 3:
		accountConsole.modify(accountMenu);
		break;
	case 4:
		accountConsole.remove(accountMenu);
		break;
	case 5:
		accountConsole.addMonthlyDue(accountMenu);
		break;
	case 6:
		accountConsole.removeMonthlyDue(accountMenu);
		break;
	case 7:
		//accountMonthlyConsole.modify(currentYearlyDue);
		break;
	case 8:
		accountConsole.findAllMonthlyDue(accountMenu);
		break;
	case 0:
		return;

	default:
		console.log("choose again!");
	}
}

module.exports = AccountMenu;
const Travel = require('../travel/Travel.js'); 

class MonthlyDue{
	constructor(month, amount, travel){
		this.month = month;
		var currencyCode = '';
		var type = '';
		this.amount = amount;
		var time = 0;
		this.travel = travel;
	}
	
	monthlyDue(){
		console.log('Month: ' + this.month + ', Amount: ' + this.amount + ', Type: ' + this.type);
	}
}

module.exports = MonthlyDue;
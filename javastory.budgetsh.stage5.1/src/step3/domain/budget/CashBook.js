class CashBook {
	constructor(travel, monthlyDue, budgetGoal, term){
		this.travel = travel;
		this.club = null;
		this.leader = null;
		this.bankAccount = '';
		this.currencyCode = 'KWR';
		this.monthlyDue = monthlyDue;
		this.budgetGoal = budgetGoal;
		this.term = term;
		this.memo = '';
		this.time = 0;
	}
	
	cashbook(){
		this.travel.travel();
		this.club.idName();
		this.leader.socialian();
		console.log('BankAccount: ' + this.bankAccount + ', CurrencyCode: ' + this.currencyCode + ', MonthlyDue: ' + this.monthlyDue +', BudgetGoal: ' + this.budgetGoal);
		this.term.datePair();
	}
}

module.exports = CashBook;
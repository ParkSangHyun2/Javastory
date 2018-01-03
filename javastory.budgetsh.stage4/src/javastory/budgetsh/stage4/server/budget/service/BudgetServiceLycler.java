package javastory.budgetsh.stage4.server.budget.service;

import javastory.budgetsh.stage4.server.budget.service.TransactionService;

public interface BudgetServiceLycler {
	//
	public AccountMonthlyService createMonthlyService();
	public AccountYearlyService createYearlyService();
	public CashBookService createCashBookService();
	public TransactionService createTransactionService();
}

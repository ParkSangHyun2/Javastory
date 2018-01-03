package javastory.budgetsh.stage3.budget.service;

public interface ServiceLycler {
	//
	public AccountMonthlyService createMonthlyService();
	public AccountYearlyService createYearlyService();
	public CashBookService createCashBookService();
	public TransactionService createTransactionService();
}

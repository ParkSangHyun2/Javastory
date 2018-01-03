package javastory.budgetsh.stage2.budget.service;

public interface ServiceLycler {
	//
	public AccountMonthlyService createMonthlyService();
	public AccountYearlyService createYearlyService();
	public CashBookService createCashBookService();
	public TransactionService createTransactionService();
}

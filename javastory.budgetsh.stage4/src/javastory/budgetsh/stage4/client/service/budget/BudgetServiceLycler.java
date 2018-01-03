package javastory.budgetsh.stage4.client.service.budget;

public interface BudgetServiceLycler {
	//
	public AccountMonthlyService createMonthlyService();
	public AccountYearlyService createYearlyService();
	public CashBookService createCashBookService();
	public TransactionService createTransactionService();
}

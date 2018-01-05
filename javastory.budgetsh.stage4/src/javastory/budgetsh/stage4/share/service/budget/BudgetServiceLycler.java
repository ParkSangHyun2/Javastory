package javastory.budgetsh.stage4.share.service.budget;

public interface BudgetServiceLycler {
	//
	public AccountMonthlyService createMonthlyService();

	public AccountYearlyService createYearlyService();

	public CashBookService createCashBookService();

	public TransactionService createTransactionService();
}

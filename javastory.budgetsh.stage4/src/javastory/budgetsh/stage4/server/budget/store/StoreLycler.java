package javastory.budgetsh.stage4.server.budget.store;

public interface StoreLycler {
	//
	public AccountMonthlyStore createAccountMonthlyStore();
	public AccountYearlyStore createAccountYearlyStore();
	public CashBookStore createCashBookStore();
	public TransactionStore createTransactionStore();
}

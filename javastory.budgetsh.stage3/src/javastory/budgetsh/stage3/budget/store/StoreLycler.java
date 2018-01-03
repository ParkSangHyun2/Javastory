package javastory.budgetsh.stage3.budget.store;

public interface StoreLycler {
	//
	public AccountMonthlyStore createAccountMonthlyStore();
	public AccountYearlyStore createAccountYearlyStore();
	public CashBookStore createCashBookStore();
	public TransactionStore createTransactionStore();
}

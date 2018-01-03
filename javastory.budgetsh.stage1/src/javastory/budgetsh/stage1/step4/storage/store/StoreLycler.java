package javastory.budgetsh.stage1.step4.storage.store;

public interface StoreLycler {
	//
	public AccountMonthlyStore createAccountMonthlyStore();
	public AccountYearlyStore createAccountYearlyStore();
	public CashBookStore createCashBookStore();
	public TransactionStore createTransactionStore();
}

package javastory.budgetsh.stage2.budget.storage.store;

import javastory.budgetsh.stage2.club.store.BoardStore;

public interface StoreLycler {
	//
	public AccountMonthlyStore createAccountMonthlyStore();
	public AccountYearlyStore createAccountYearlyStore();
	public CashBookStore createCashBookStore();
	public TransactionStore createTransactionStore();
}

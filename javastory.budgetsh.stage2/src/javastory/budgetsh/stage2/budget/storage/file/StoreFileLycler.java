package javastory.budgetsh.stage2.budget.storage.file;

import javastory.budgetsh.stage2.budget.storage.store.AccountMonthlyStore;
import javastory.budgetsh.stage2.budget.storage.store.AccountYearlyStore;
import javastory.budgetsh.stage2.budget.storage.store.CashBookStore;
import javastory.budgetsh.stage2.budget.storage.store.StoreLycler;
import javastory.budgetsh.stage2.budget.storage.store.TransactionStore;

public class StoreFileLycler implements StoreLycler{
	//
	private static StoreLycler storeLycler;
	
	private AccountYearlyStore accountYearlyStore;
	private AccountMonthlyStore accountMonthlyStore;
	private CashBookStore cashBookStore;
	private TransactionStore transactionStore;
	
	public StoreFileLycler() {
		//
	}
	
	public synchronized static StoreLycler shareInstance() {
		//
		if (storeLycler == null) {
			storeLycler = new StoreFileLycler();
		}

		return storeLycler;
	}
	
	@Override
	public AccountMonthlyStore createAccountMonthlyStore() {
		//
		if (accountMonthlyStore == null) {
			accountMonthlyStore = new AccountMonthlyFileStore();
		}
		return accountMonthlyStore;
	}

	@Override
	public AccountYearlyStore createAccountYearlyStore() {
		//
		if(accountYearlyStore == null) {
			accountYearlyStore = new AccountYearlyFileStore();
		}
		return accountYearlyStore;
	}

	@Override
	public CashBookStore createCashBookStore() {
		// 
		if(cashBookStore == null) {
			cashBookStore = new CashBookFileStore();
		}
		return cashBookStore;
	}

	@Override
	public TransactionStore createTransactionStore() {
		// 
		if(transactionStore == null) {
			transactionStore = new TransactionFileStore();
		}
		return transactionStore;
	}

}

package javastory.budgetsh.stage2.budget.logic;

import javastory.budgetsh.stage2.budget.entity.budget.CashBook;
import javastory.budgetsh.stage2.budget.service.CashBookService;
import javastory.budgetsh.stage2.budget.storage.file.StoreFileLycler;
import javastory.budgetsh.stage2.budget.storage.store.CashBookStore;
import javastory.budgetsh.stage2.budget.util.FailureException;
import javastory.budgetsh.stage2.budget.util.NoSuchCashBookException;

public class CashBookServiceLogic implements CashBookService{
	//
	private CashBookStore cashBookStore;
	
	public CashBookServiceLogic() {
		//
		cashBookStore = StoreFileLycler.shareInstance().createCashBookStore();
	}

	@Override
	public boolean exist(String bankAccount) {
		// 
		if(cashBookStore.exist(bankAccount)) {
			return true;
		}
		return false;
	}

	@Override
	public void regist(CashBook cashbook) {
		//
		if (cashbook == null) {
			throw new NoSuchCashBookException("No such cashbook");
		}
		cashBookStore.regist(cashbook);
		if(!exist(cashbook.getBankAccount())) {
			throw new FailureException("failed to regist Cashbook");
		}
	}

	@Override
	public CashBook retrieve(String account) {
		//
		CashBook foundCashbook;
		foundCashbook = cashBookStore.retrieve(account);
		return foundCashbook;
	}

	@Override
	public void update(CashBook foundCashbook) {
		//
		if (foundCashbook == null) {
			throw new NoSuchCashBookException("No such cashbook");
		}
		cashBookStore.update(foundCashbook);
	}

	@Override
	public void remove(CashBook foundCashbook) {
		// 
		if (foundCashbook == null) {
			throw new NoSuchCashBookException("No such cashbook");
		}
		cashBookStore.remove(foundCashbook);
		if(exist(foundCashbook.getBankAccount())) {
			throw new FailureException("Failed to remove cashbook");
		}
	}
}

package javastory.budgetsh.stage1.step4.logic;

import javastory.budgetsh.stage1.step1.entity.budget.CashBook;
import javastory.budgetsh.stage1.step4.service.CashBookService;
import javastory.budgetsh.stage1.step4.storage.file.StoreFileLycler;
import javastory.budgetsh.stage1.step4.storage.store.CashBookStore;
import javastory.budgetsh.stage1.step4.util.FailureException;
import javastory.budgetsh.stage1.step4.util.NoSuchCashBookException;

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

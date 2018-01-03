package javastory.budgetsh.stage3.budget.logic;

import java.util.ArrayList;

import javastory.budgetsh.stage3.budget.da.file.StoreFileLycler;
import javastory.budgetsh.stage3.budget.entity.budget.CashBook;
import javastory.budgetsh.stage3.budget.service.CashBookService;
import javastory.budgetsh.stage3.budget.store.CashBookStore;
import javastory.budgetsh.stage3.budget.util.FailureException;
import javastory.budgetsh.stage3.budget.util.NoSuchCashBookException;

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
	public boolean regist(CashBook cashbook) {
		//
		if (cashbook == null) {
			return false;
			//throw new NoSuchCashBookException("No such cashbook");
		}
		cashBookStore.regist(cashbook);

		if(!exist(cashbook.getBankAccount())) {
			return false;
			//throw new FailureException("failed to regist Cashbook");
		}
		return true;
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

	@Override
	public ArrayList<CashBook> retrieveAll() {
		//
		return cashBookStore.retrieveAll();
	}
}

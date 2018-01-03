package javastory.budgetsh.stage3.budget.da.file;

import java.util.ArrayList;

import javastory.budgetsh.stage3.budget.da.file.io.CashBookFile;
import javastory.budgetsh.stage3.budget.entity.budget.CashBook;
import javastory.budgetsh.stage3.budget.store.CashBookStore;
import javastory.budgetsh.stage3.budget.util.BankAccountDuplicationException;

public class CashBookFileStore implements CashBookStore{
	//
	private CashBookFile cashbookFile;
	
	public CashBookFileStore() {
		//
		cashbookFile = new CashBookFile();
	}
	
	@Override
	public boolean exist(String bankAccount) {
		//
		if(cashbookFile.exist(bankAccount)) {
			return true;
		}
		return false;
	}

	@Override
	public CashBook regist(CashBook cashbook) {
		//
		if(this.exist(cashbook.getBankAccount())) {
			new BankAccountDuplicationException("Already exist such Account -->" + cashbook.getBankAccount());
		}
		
		cashbookFile.regist(cashbook);
		return cashbook;
	}

	@Override
	public CashBook retrieve(String account) {
		//
		CashBook foundCashBook;
		if(!this.exist(account)) {
			new BankAccountDuplicationException("No such Account in Cashbook-->" + account);
		}
		foundCashBook = cashbookFile.retrieve(account);
		return foundCashBook;
	}

	@Override
	public void update(CashBook cashbook) {
		// 
		if(!this.exist(cashbook.getBankAccount())) {
			new BankAccountDuplicationException("No such Account in Cashbook-->" + cashbook.getBankAccount());
		}
		
		cashbookFile.update(cashbook);
	}

	@Override
	public void remove(CashBook cashbook) {
		//
		if(!this.exist(cashbook.getBankAccount())) {
			new BankAccountDuplicationException("No such Account in Cashbook-->" + cashbook.getBankAccount());
		}
		cashbookFile.remove(cashbook);
	}
	
	public static void main(String[] args) {
		//
		CashBook cashbook = new CashBook();
		cashbook.getSample();
		
		CashBookStore cashbookStore = StoreFileLycler.shareInstance().createCashBookStore();
		cashbookStore.exist(cashbook.getBankAccount());
		cashbookStore.regist(cashbook);
	}

	@Override
	public ArrayList<CashBook> retrieveAll() {
		//
		return cashbookFile.retrieveAll();

	}

}

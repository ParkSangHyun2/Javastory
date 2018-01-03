package javastory.budgetsh.stage2.budget.storage.store;

import javastory.budgetsh.stage2.budget.entity.budget.CashBook;

public interface CashBookStore {
	//
	public boolean exist(String bankAccount);
	public CashBook regist(CashBook cashbook);
	public CashBook retrieve(String account);
	public void update(CashBook foundCashbook);
	public void remove(CashBook foundCashbook);

}

package javastory.budgetsh.stage3.budget.store;

import java.util.ArrayList;

import javastory.budgetsh.stage3.budget.entity.budget.CashBook;

public interface CashBookStore {
	//
	public boolean exist(String bankAccount);
	public CashBook regist(CashBook cashbook);
	public CashBook retrieve(String account);
	public void update(CashBook foundCashbook);
	public void remove(CashBook foundCashbook);
	public ArrayList<CashBook> retrieveAll();

}

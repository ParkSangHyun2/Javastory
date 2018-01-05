package javastory.budgetsh.stage4.server.store.budget;

import java.util.ArrayList;

import javastory.budgetsh.stage4.share.domain.budget.budget.CashBook;

public interface CashBookStore {
	//
	public boolean exist(String bankAccount);
	public CashBook regist(CashBook cashbook);
	public CashBook retrieve(String account);
	public void update(CashBook foundCashbook);
	public void remove(CashBook foundCashbook);
	public ArrayList<CashBook> retrieveAll();

}

package javastory.budgetsh.stage2.budget.service;

import javastory.budgetsh.stage2.budget.entity.budget.CashBook;

public interface CashBookService {

	boolean exist(String bankAccount);

	void regist(CashBook cashbook);

	CashBook retrieve(String account);

	void update(CashBook foundCashbook);

	void remove(CashBook foundCashbook);

}

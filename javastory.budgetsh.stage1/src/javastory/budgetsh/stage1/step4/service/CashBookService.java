package javastory.budgetsh.stage1.step4.service;

import javastory.budgetsh.stage1.step1.entity.budget.CashBook;

public interface CashBookService {

	boolean exist(String bankAccount);

	void regist(CashBook cashbook);

	CashBook retrieve(String account);

	void update(CashBook foundCashbook);

	void remove(CashBook foundCashbook);

}

package javastory.budgetsh.stage4.server.budget.service;

import java.util.ArrayList;

import javastory.budgetsh.stage4.server.budget.entity.budget.CashBook;

public interface CashBookService {

	boolean exist(String bankAccount);

	boolean regist(CashBook cashbook);

	CashBook retrieve(String account);

	void update(CashBook foundCashbook);

	void remove(CashBook foundCashbook);

	ArrayList<CashBook> retrieveAll();

}

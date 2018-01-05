package javastory.budgetsh.stage4.share.service.budget;

import java.util.ArrayList;

import javastory.budgetsh.stage4.share.domain.budget.budget.CashBook;

public interface CashBookService {

	public boolean exist(String bankAccount);

	public boolean regist(CashBook cashbook);

	public CashBook retrieve(String account);

	public void update(CashBook foundCashbook);

	public void remove(CashBook foundCashbook);

	public ArrayList<CashBook> retrieveAll();

}

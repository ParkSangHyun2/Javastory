package javastory.budgetsh.stage3.budget.service;

import java.util.Collection;

import javastory.budgetsh.stage3.budget.entity.account.AccountYearlyDue;
import javastory.budgetsh.stage3.budget.entity.account.MonthlyDue;

public interface AccountYearlyService {

	boolean exist(String account);

	void remove(String account);

	void update(AccountYearlyDue yearlyDue);

	AccountYearlyDue retrieve(String account);

	void regist(AccountYearlyDue yearlyDue);
	
	Collection<MonthlyDue> retrieveMonthlyDue(String account);
	
	public Collection<AccountYearlyDue> retrieveAll(String account);

	void addMonthlyDue(String year, String account, int month, String amount, String id, String name, String type);
}

package javastory.budgetsh.stage2.budget.service;

import javastory.budgetsh.stage2.budget.entity.account.AccountYearlyDue;

public interface AccountYearlyService {

	boolean exist(String account);

	void remove(String account);

	void update(AccountYearlyDue yearlyDue);

	AccountYearlyDue retrieve(String account);

	void regist(AccountYearlyDue yearlyDue);

}

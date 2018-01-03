package javastory.budgetsh.stage2.budget.storage.store;

import javastory.budgetsh.stage2.budget.entity.account.AccountYearlyDue;

public interface AccountYearlyStore {
	//
	boolean exist(String account);
	void remove(String account);
	void update(AccountYearlyDue yearlyDue);
	AccountYearlyDue retrieve(String account);
	void regist(AccountYearlyDue yearlyDue);
}

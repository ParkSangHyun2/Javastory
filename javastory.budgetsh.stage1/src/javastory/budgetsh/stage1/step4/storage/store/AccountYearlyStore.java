package javastory.budgetsh.stage1.step4.storage.store;

import javastory.budgetsh.stage1.step1.entity.account.AccountYearlyDue;

public interface AccountYearlyStore {
	//
	boolean exist(String account);
	void remove(String account);
	void update(AccountYearlyDue yearlyDue);
	AccountYearlyDue retrieve(String account);
	void regist(AccountYearlyDue yearlyDue);
}

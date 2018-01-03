package javastory.budgetsh.stage3.budget.store;

import java.util.Collection;

import javastory.budgetsh.stage3.budget.entity.account.AccountYearlyDue;

public interface AccountYearlyStore {
	//
	boolean exist(String account);
	void remove(String account);
	void update(AccountYearlyDue yearlyDue);
	AccountYearlyDue retrieve(String account);
	void regist(AccountYearlyDue yearlyDue);
	public Collection<AccountYearlyDue> retrieveAll(String account);
}

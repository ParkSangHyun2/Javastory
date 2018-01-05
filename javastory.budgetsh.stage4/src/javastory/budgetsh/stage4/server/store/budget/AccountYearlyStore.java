package javastory.budgetsh.stage4.server.store.budget;

import java.util.Collection;

import javastory.budgetsh.stage4.share.domain.budget.account.AccountYearlyDue;



public interface AccountYearlyStore {
	//
	boolean exist(String account);
	void remove(String account);
	void update(AccountYearlyDue yearlyDue);
	AccountYearlyDue retrieve(String account);
	void regist(AccountYearlyDue yearlyDue);
	public Collection<AccountYearlyDue> retrieveAll(String account);
}

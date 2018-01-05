package javastory.budgetsh.stage4.server.store.budget;

import java.util.Collection;

import javastory.budgetsh.stage4.share.domain.budget.account.MonthlyDue;

public interface AccountMonthlyStore {
	//
	boolean exist(int month, String yearlyDueAccount);
	void register(MonthlyDue targetMonthlyDue);
	void remove(String yearlyDueAccount, int month);
	void update(String yearlyDueAccount, MonthlyDue monthlyDue);
	Collection<MonthlyDue> findAll(String yearlyDueAccount);
	MonthlyDue retrieve(String yearlyDueAccount, int month);

}

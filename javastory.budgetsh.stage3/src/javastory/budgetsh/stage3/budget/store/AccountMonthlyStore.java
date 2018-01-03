package javastory.budgetsh.stage3.budget.store;

import java.util.Collection;

import javastory.budgetsh.stage3.budget.entity.account.MonthlyDue;

public interface AccountMonthlyStore {
	//
	boolean exist(int month, String yearlyDueAccount);
	void register(MonthlyDue targetMonthlyDue);
	void remove(String yearlyDueAccount, int month);
	void update(String yearlyDueAccount, MonthlyDue monthlyDue);
	Collection<MonthlyDue> findAll(String yearlyDueAccount);
	MonthlyDue retrieve(String yearlyDueAccount, int month);

}

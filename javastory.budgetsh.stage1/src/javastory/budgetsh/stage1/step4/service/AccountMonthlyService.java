package javastory.budgetsh.stage1.step4.service;

import java.util.Collection;

import javastory.budgetsh.stage1.step1.entity.account.MonthlyDue;

public interface AccountMonthlyService {
	//
	boolean exist(int month, String yearlyDueAccount);
	void register(MonthlyDue targetMonthlyDue);
	void remove(String yearlyDueAccount, int month);
	void update(String yearlyDueAccount, MonthlyDue monthlyDue);
	Collection<MonthlyDue> findAll(String yearlyDueAccount);
	MonthlyDue retrieve(String yearlyDueAccount, int month);

}

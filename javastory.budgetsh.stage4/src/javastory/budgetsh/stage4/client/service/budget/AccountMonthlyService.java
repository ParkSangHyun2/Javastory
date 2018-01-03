package javastory.budgetsh.stage4.client.service.budget;

import java.util.Collection;

import javastory.budgetsh.stage4.client.dto.budget.MonthlyDue;

public interface AccountMonthlyService {
	//
	boolean exist(int month, String yearlyDueAccount);
	void register(MonthlyDue targetMonthlyDue);
	void remove(String yearlyDueAccount, int month);
	void update(String yearlyDueAccount, MonthlyDue monthlyDue);
	Collection<MonthlyDue> findAll(String yearlyDueAccount);
	MonthlyDue retrieve(String yearlyDueAccount, int month);

}

package javastory.budgetsh.stage4.share.service.budget;

import java.util.Collection;

import javastory.budgetsh.stage4.share.domain.budget.account.MonthlyDue;

public interface AccountMonthlyService {
	//
	public boolean exist(int month, String yearlyDueAccount);

	public void register(MonthlyDue targetMonthlyDue);

	public void remove(String yearlyDueAccount, int month);

	public void update(String yearlyDueAccount, MonthlyDue monthlyDue);

	public Collection<MonthlyDue> findAll(String yearlyDueAccount);

	public MonthlyDue retrieve(String yearlyDueAccount, int month);

}

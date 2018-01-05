package javastory.budgetsh.stage4.share.service.budget;

import java.util.Collection;

import javastory.budgetsh.stage4.share.domain.budget.account.AccountYearlyDue;
import javastory.budgetsh.stage4.share.domain.budget.account.MonthlyDue;

public interface AccountYearlyService {

	public boolean exist(String account);

	public void remove(String account);

	public void update(AccountYearlyDue yearlyDue);

	public AccountYearlyDue retrieve(String account);

	public void regist(AccountYearlyDue yearlyDue);
	
	public Collection<MonthlyDue> retrieveMonthlyDue(String account);
	
	public Collection<AccountYearlyDue> retrieveAll(String account);

	public void addMonthlyDue(String year, String account, int month, String amount, String id, String name, String type);
}

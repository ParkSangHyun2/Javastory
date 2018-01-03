package javastory.budgetsh.stage1.step4.service;

import javastory.budgetsh.stage1.step1.entity.account.AccountYearlyDue;

public interface AccountYearlyService {

	boolean exist(String account);

	void remove(String account);

	void update(AccountYearlyDue yearlyDue);

	AccountYearlyDue retrieve(String account);

	void regist(AccountYearlyDue yearlyDue);

}

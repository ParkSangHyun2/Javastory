package javastory.budgetsh.stage2.budget.logic;

import java.util.Collection;

import javastory.budgetsh.stage2.budget.entity.account.MonthlyDue;
import javastory.budgetsh.stage2.budget.service.AccountMonthlyService;
import javastory.budgetsh.stage2.budget.storage.file.StoreFileLycler;
import javastory.budgetsh.stage2.budget.storage.store.AccountMonthlyStore;
import javastory.budgetsh.stage2.budget.util.DuplicationException;
import javastory.budgetsh.stage2.budget.util.FailureException;
import javastory.budgetsh.stage2.budget.util.NoSuchMonthlyDueException;

public class AccountMonthlyServiceLogic implements AccountMonthlyService {
	//
	private AccountMonthlyStore accountMonthlyStore;

	public AccountMonthlyServiceLogic() {
		//
		accountMonthlyStore = StoreFileLycler.shareInstance().createAccountMonthlyStore();
	}

	@Override
	public boolean exist(int month, String yearlyDueAccount) {
		//
		if (accountMonthlyStore.exist(month, yearlyDueAccount)) {
			return true;
		}
		return false;
	}

	@Override
	public void register(MonthlyDue targetMonthlyDue) {
		//
		if (targetMonthlyDue != null) {
			accountMonthlyStore.register(targetMonthlyDue);
		}else {
			throw new DuplicationException("The Duplication destination is null.");
		}
	}

	@Override
	public void remove(String yearlyDueAccount, int month) {
		//
		accountMonthlyStore.remove(yearlyDueAccount, month);
		if(exist(month, yearlyDueAccount)) {
			throw new FailureException("Failed to remove");
		}
	}

	@Override
	public void update(String yearlyDueAccount, MonthlyDue monthlyDue) {
		//
		if(monthlyDue == null) {
			throw new NoSuchMonthlyDueException("No such MonthlyDue in Storage");
		}
		
		accountMonthlyStore.update(yearlyDueAccount, monthlyDue);
		
		if(!exist(monthlyDue.getMonth(),yearlyDueAccount)) {
			throw new FailureException("Failed to Modify");
		}
		
		return;
	}

	@Override
	public Collection<MonthlyDue> findAll(String yearlyDueAccount) {
		//
		Collection<MonthlyDue> found = accountMonthlyStore.findAll(yearlyDueAccount);
		
		return found;
	}

	@Override
	public MonthlyDue retrieve(String yearlyDueAccount, int month) {
		// 
		MonthlyDue monthlyDue = accountMonthlyStore.retrieve(yearlyDueAccount, month);
		return monthlyDue;
	}

}

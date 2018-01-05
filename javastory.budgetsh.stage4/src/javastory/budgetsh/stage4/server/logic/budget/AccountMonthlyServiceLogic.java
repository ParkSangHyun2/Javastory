package javastory.budgetsh.stage4.server.logic.budget;

import java.util.Collection;

import javastory.budgetsh.stage4.server.store.budget.AccountMonthlyStore;
import javastory.budgetsh.stage4.server.store.budget.da.file.StoreFileLycler;
import javastory.budgetsh.stage4.share.domain.budget.account.MonthlyDue;
import javastory.budgetsh.stage4.share.service.budget.AccountMonthlyService;
import javastory.budgetsh.stage4.share.util.DuplicationException;
import javastory.budgetsh.stage4.share.util.FailureException;
import javastory.budgetsh.stage4.share.util.NoSuchMonthlyDueException;

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

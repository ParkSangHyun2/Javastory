package javastory.budgetsh.stage1.step4.logic;

import java.util.Collection;
import java.util.List;

import javastory.budgetsh.stage1.step1.entity.account.MonthlyDue;
import javastory.budgetsh.stage1.step4.service.AccountMonthlyService;
import javastory.budgetsh.stage1.step4.storage.file.StoreFileLycler;
import javastory.budgetsh.stage1.step4.storage.store.AccountMonthlyStore;
import javastory.budgetsh.stage1.step4.util.DuplicationException;
import javastory.budgetsh.stage1.step4.util.NoSuchMonthlyDueException;
import javastory.budgetsh.stage1.step4.util.FailureException;

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

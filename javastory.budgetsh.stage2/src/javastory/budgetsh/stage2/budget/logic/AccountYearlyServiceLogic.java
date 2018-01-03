package javastory.budgetsh.stage2.budget.logic;

import javastory.budgetsh.stage2.budget.entity.account.AccountYearlyDue;
import javastory.budgetsh.stage2.budget.service.AccountYearlyService;
import javastory.budgetsh.stage2.budget.storage.file.StoreFileLycler;
import javastory.budgetsh.stage2.budget.storage.store.AccountYearlyStore;
import javastory.budgetsh.stage2.budget.util.FailureException;
import javastory.budgetsh.stage2.budget.util.NoSuchYearlyDueException;

public class AccountYearlyServiceLogic implements AccountYearlyService{
	//
	private AccountYearlyStore accountYearlyStore;
	
	
	public AccountYearlyServiceLogic() {
		//
		accountYearlyStore = StoreFileLycler.shareInstance().createAccountYearlyStore();
	}
	@Override
	public boolean exist(String account) {
		//
		if (accountYearlyStore.exist(account)) {
			return true;
		}
		return false;
	}

	@Override
	public void remove(String account) {
		//
		accountYearlyStore.remove(account);
		if(exist(account)) {
			throw new FailureException("Failed to remove");
		}
	}

	@Override
	public void update(AccountYearlyDue yearlyDue) {
		//
		if(yearlyDue == null) {
			throw new NoSuchYearlyDueException("no such yearlydue");
		}
		accountYearlyStore.update(yearlyDue);
	}

	@Override
	public AccountYearlyDue retrieve(String account) {
		// 
		AccountYearlyDue yearlyDue;
		yearlyDue = accountYearlyStore.retrieve(account);
		return yearlyDue;
	}

	@Override
	public void regist(AccountYearlyDue yearlyDue) {
		//
		if(yearlyDue == null) {
			throw new NoSuchYearlyDueException("no such yearlydue");
		}
		accountYearlyStore.regist(yearlyDue);
		
		if(!exist(yearlyDue.getMember().getId())) {
			throw new FailureException("failed to regist");
		}
	}

}

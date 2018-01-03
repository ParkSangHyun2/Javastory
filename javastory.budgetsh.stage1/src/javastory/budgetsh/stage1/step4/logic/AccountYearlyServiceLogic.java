package javastory.budgetsh.stage1.step4.logic;

import javastory.budgetsh.stage1.step1.entity.account.AccountYearlyDue;
import javastory.budgetsh.stage1.step4.service.AccountYearlyService;
import javastory.budgetsh.stage1.step4.storage.file.StoreFileLycler;
import javastory.budgetsh.stage1.step4.storage.store.AccountYearlyStore;
import javastory.budgetsh.stage1.step4.util.FailureException;
import javastory.budgetsh.stage1.step4.util.NoSuchYearlyDueException;

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

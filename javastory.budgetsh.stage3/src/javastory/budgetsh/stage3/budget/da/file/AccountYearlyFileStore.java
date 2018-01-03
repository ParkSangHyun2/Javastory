package javastory.budgetsh.stage3.budget.da.file;

import java.util.Collection;

import javastory.budgetsh.stage3.budget.da.file.io.YearlyDueFile;
import javastory.budgetsh.stage3.budget.entity.account.AccountYearlyDue;
import javastory.budgetsh.stage3.budget.store.AccountYearlyStore;
import javastory.budgetsh.stage3.budget.util.BankAccountDuplicationException;

public class AccountYearlyFileStore implements AccountYearlyStore {
	//
	private YearlyDueFile yearlyDueFile;
	
	public AccountYearlyFileStore() {
		//
		yearlyDueFile = new YearlyDueFile();
	}

	public boolean exist(String account) {
		//
		if(yearlyDueFile.exist(account)) {
			return true;
		}
		return false;
	}

	public void remove(String account) {
		//
		if(!this.exist(account)) {
			throw new BankAccountDuplicationException("No such Account-->" + account);
		}
		yearlyDueFile.remove(account);
	}

	public void update(AccountYearlyDue yearlyDue) {
		//
		yearlyDueFile.update(yearlyDue);

	}
	
	public Collection<AccountYearlyDue> retrieveAll(String account){
		//
		if (!exist(account)) {
			return null;
		}
		System.out.println("Its findYearlyDue filestore");
		return yearlyDueFile.retrieveAll(account);
	}

	public AccountYearlyDue retrieve(String account) {
		//
		if (!exist(account)) {
			return null;
		}
		return yearlyDueFile.retrieve(account);
	}

	public void regist(AccountYearlyDue yearlyDue) {
		//
		if(this.exist(yearlyDue.getMember().getId())) {
			return;
		}
		
		yearlyDueFile.regist(yearlyDue);
	}
}

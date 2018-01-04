package javastory.budgetsh.stage4.server.budget.logic;

import java.util.ArrayList;
import java.util.Collection;

import javastory.budgetsh.stage4.server.budget.da.file.StoreFileLycler;
import javastory.budgetsh.stage4.server.budget.entity.account.AccountYearlyDue;
import javastory.budgetsh.stage4.server.budget.entity.account.DuesType;
import javastory.budgetsh.stage4.server.budget.entity.account.MonthlyDue;
import javastory.budgetsh.stage4.server.budget.service.AccountYearlyService;
import javastory.budgetsh.stage4.server.budget.share.IdName;
import javastory.budgetsh.stage4.server.budget.store.AccountYearlyStore;
import javastory.budgetsh.stage4.server.budget.util.FailureException;
import javastory.budgetsh.stage4.server.budget.util.NoSuchYearlyDueException;

public class AccountYearlyServiceLogic implements AccountYearlyService {
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
		if (exist(account)) {
			throw new FailureException("Failed to remove");
		}
	}

	@Override
	public void update(AccountYearlyDue yearlyDue) {
		//
		if (yearlyDue == null) {
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
	public Collection<AccountYearlyDue> retrieveAll(String account) {
		//
		System.out.println("Its findYearlyDue service");
		return accountYearlyStore.retrieveAll(account);

	}

	@Override
	public void regist(AccountYearlyDue yearlyDue) {
		//
		if (yearlyDue == null) {
			throw new NoSuchYearlyDueException("no such yearlydue");
		}
		accountYearlyStore.regist(yearlyDue);

		if (!exist(yearlyDue.getMember().getId())) {
			throw new FailureException("failed to regist");
		}
	}

	@Override
	public void addMonthlyDue(String year, String account, int month, String amount, String id, String name,
			String duesType) {
		//
		ArrayList<AccountYearlyDue> yearlyDues = (ArrayList<AccountYearlyDue>) this.retrieveAll(account);
		boolean existYear = false;
		for(AccountYearlyDue yearlyDue : yearlyDues) {
			if(yearlyDue.getYear().equals(year)) {
				existYear = true;
			}
		}
		AccountYearlyDue yearlyDue = null;
		if (!existYear) {
			AccountYearlyDue newYearlyDue = new AccountYearlyDue((new IdName(account, account)),
					Integer.parseInt(year));
			newYearlyDue.setBankAccount(account);
			this.regist(newYearlyDue);
			yearlyDue = newYearlyDue;
		} else {
			for (AccountYearlyDue foundYearlyDue : yearlyDues) {
				if (foundYearlyDue.getYear().equals(year)) {
					yearlyDue = foundYearlyDue;
				}
			}
		}
		MonthlyDue monthlyDue = new MonthlyDue(month, Double.parseDouble(amount), new IdName(id, name));
		DuesType type = null;
		switch (duesType) {
		case "Regular":
			type = DuesType.Regular;
			break;
		case "Donation":
			type = DuesType.Donation;
			break;
		case "Fine":
			type = DuesType.Fine;
			break;
		}
		monthlyDue.setType(type);
		yearlyDue.addMonthlyDue(monthlyDue);
		this.update(yearlyDue);
	}

	public Collection<MonthlyDue> retrieveMonthlyDue(String account) {
		//
		AccountYearlyDue yearlyDue = new AccountYearlyDue();
		AccountYearlyDue foundYearlyDue = this.retrieve(account);

		if (foundYearlyDue != null) {
			if (foundYearlyDue != null) {
				yearlyDue = foundYearlyDue;
			}
		}

		return yearlyDue.getMonthlyDues();
	}

}
